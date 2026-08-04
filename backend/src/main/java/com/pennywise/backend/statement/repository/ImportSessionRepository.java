package com.pennywise.backend.statement.repository;

import com.pennywise.backend.statement.entity.ImportSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImportSessionRepository extends JpaRepository<ImportSession, UUID> {
}
