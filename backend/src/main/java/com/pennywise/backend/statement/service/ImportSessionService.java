package com.pennywise.backend.statement.service;

import com.pennywise.backend.auth.entity.User;
import com.pennywise.backend.common.exception.FileStorageException;
import com.pennywise.backend.common.service.CurrentUserService;
import com.pennywise.backend.statement.dto.response.UploadStatementResponse;
import com.pennywise.backend.statement.entity.ImportSession;
import com.pennywise.backend.statement.entity.ImportStatus;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ImportSessionService {
    private final ImportSessionRepository importSessionRepository;
    private final CurrentUserService currentUserService;

    @Value("${app.statement.upload-directory}")
    private String uploadDirectory;

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

            ImportSession session = new ImportSession();

            session.setUser(user);
            session.setOriginalFileName(originalFilename);
            session.setStoragePath(destination.toString());
            session.setFileType(extension.toLowerCase());
            session.setStatus(ImportStatus.UPLOADED);

            ImportSession saved = importSessionRepository.save(session);

            return new UploadStatementResponse(
                    saved.getId(),
                    saved.getStatus()
            );

        } catch (IOException exception) {
            throw new FileStorageException("Could not store file " + originalFilename + ". Please try again!");
        }
    }

    private String getExtension(String filename) {

        int index = filename.lastIndexOf('.');

        if (index == -1) {
            return "";
        }

        return filename.substring(index + 1);
    }
}
