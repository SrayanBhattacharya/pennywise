package com.pennywise.backend.statement.service;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.common.exception.*;
import com.pennywise.backend.common.service.CurrentUserService;
import com.pennywise.backend.statement.detector.StatementFileTypeDetector;
import com.pennywise.backend.statement.domain.StatementData;
import com.pennywise.backend.statement.dto.response.UploadStatementResponse;
import com.pennywise.backend.statement.entity.ImportSession;
import com.pennywise.backend.statement.entity.ImportStatus;
import com.pennywise.backend.statement.extractor.StatementExtractor;
import com.pennywise.backend.statement.extractor.StatementExtractorFactory;
import com.pennywise.backend.statement.model.BankType;
import com.pennywise.backend.statement.model.ParsedTransaction;
import com.pennywise.backend.statement.model.StatementFileType;
import com.pennywise.backend.statement.parser.StatementParserFactory;
import com.pennywise.backend.statement.repository.ImportSessionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class ImportSessionService {
    private final ImportSessionRepository importSessionRepository;
    private final CurrentUserService currentUserService;
    private final StatementFileTypeDetector fileTypeDetector;
    private final StatementExtractorFactory statementExtractorFactory;
    private final StatementImportService statementImportService;
    private final StatementParserFactory statementParserFactory;

    @Value("${app.statement.upload-directory}")
    private String uploadDirectory;

    private String getExtension(String filename) {
        int index = filename.lastIndexOf('.');

        if (index == -1) {
            return "";
        }

        return filename.substring(index + 1);
    }

    public UploadStatementResponse createImportSession(MultipartFile file) {
        User user = currentUserService.getCurrentUser();

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = getExtension(originalFilename);
        String storedFilename = UUID.randomUUID() + "." + extension;

        Path uploadPath = Path.of(uploadDirectory);

        try {
            Files.createDirectories(uploadPath);

            Path destination = uploadPath.resolve(storedFilename);

            file.transferTo(destination);

            StatementFileType fileType = fileTypeDetector.detect(originalFilename);
            StatementExtractor extractor = statementExtractorFactory.getExtractor(fileType);

            ImportSession session = new ImportSession();

            session.setUser(user);
            session.setOriginalFileName(originalFilename);
            session.setStoragePath(destination.toString());
            session.setFileType(extension.toLowerCase());

            try {
                extractor.extract(destination, null);
                session.setStatus(ImportStatus.PROCESSING);
            } catch (PasswordRequiredException e) {
                session.setStatus(ImportStatus.PASSWORD_REQUIRED);
            } catch (StatementExtractionException e) {
                session.setStatus(ImportStatus.FAILED);
                session.setFailureReason(e.getMessage());
            }

            ImportSession saved = importSessionRepository.save(session);

            return new UploadStatementResponse(
                    saved.getId(),
                    saved.getStatus()
            );

        } catch (IOException exception) {
            throw new FileStorageException("Could not store file " + originalFilename + ". Please try again!");
        }
    }

    public UploadStatementResponse unlockStatement(UUID importId, String password) {
        ImportSession session = importSessionRepository
                .findById(importId)
                .orElseThrow(() -> new ImportSessionNotFoundException("Import session with ID " + importId + " not found."));

        if (session.getStatus() != ImportStatus.PASSWORD_REQUIRED) {
            throw new InvalidImportStateException("Import session with ID " + importId + " is not in a state that requires a password.");
        }

        Path file = Path.of(session.getStoragePath());

        StatementFileType fileType = fileTypeDetector.detect(session.getOriginalFileName());

        StatementExtractor extractor = statementExtractorFactory.getExtractor(fileType);

        StatementData statementData = extractor.extract(file, password);

        session.setStatus(ImportStatus.PROCESSING);
        importSessionRepository.save(session);

        List<ParsedTransaction> parsedTransactions = statementParserFactory
                .getParser(BankType.SBI)
                .parse(statementData);

        statementImportService.importTransaction(parsedTransactions, session.getUser());

        session.setStatus(ImportStatus.COMPLETED);
        importSessionRepository.save(session);

        return new UploadStatementResponse(
                session.getId(),
                session.getStatus()
        );
    }
}
