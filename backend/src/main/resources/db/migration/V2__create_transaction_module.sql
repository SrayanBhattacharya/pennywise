CREATE TABLE transaction_categories (
                                        id UUID PRIMARY KEY,

                                        name VARCHAR(50) NOT NULL,
                                        type VARCHAR(20) NOT NULL,

                                        icon VARCHAR(50),
                                        colour VARCHAR(20),

                                        system_category BOOLEAN NOT NULL DEFAULT TRUE,

                                        created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                        updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

                                        CONSTRAINT uk_transaction_category_name_type
                                            UNIQUE (name, type)
);

CREATE TABLE transactions (
                              id UUID PRIMARY KEY,

                              user_id UUID NOT NULL,
                              category_id UUID NOT NULL,

                              amount NUMERIC(19,2) NOT NULL,

                              title VARCHAR(255) NOT NULL,

                              merchant_name VARCHAR(150),

                              transaction_date DATE NOT NULL,

                              notes VARCHAR(500),

                              recurring BOOLEAN NOT NULL DEFAULT FALSE,

                              deleted BOOLEAN NOT NULL DEFAULT FALSE,

                              created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                              updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

                              CONSTRAINT fk_transaction_user
                                  FOREIGN KEY (user_id)
                                      REFERENCES users(id),

                              CONSTRAINT fk_transaction_category
                                  FOREIGN KEY (category_id)
                                      REFERENCES transaction_categories(id)
);

CREATE INDEX idx_transaction_user
    ON transactions(user_id);

CREATE INDEX idx_transaction_date
    ON transactions(transaction_date);

CREATE INDEX idx_transaction_category
    ON transactions(category_id);