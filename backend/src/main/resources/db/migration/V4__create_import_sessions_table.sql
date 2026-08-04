CREATE TABLE import_sessions (
                                 id UUID PRIMARY KEY,

                                 user_id UUID NOT NULL,

                                 original_file_name VARCHAR(255) NOT NULL,

                                 storage_path VARCHAR(500) NOT NULL,

                                 file_type VARCHAR(20) NOT NULL,

                                 status VARCHAR(50) NOT NULL,

                                 failure_reason TEXT,

                                 created_at TIMESTAMP NOT NULL,

                                 updated_at TIMESTAMP NOT NULL,

                                 CONSTRAINT fk_import_sessions_user
                                     FOREIGN KEY (user_id)
                                         REFERENCES users(id)
);