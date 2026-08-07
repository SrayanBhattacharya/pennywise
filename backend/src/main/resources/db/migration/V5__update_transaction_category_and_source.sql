-- Make category optional
ALTER TABLE transactions
    ALTER COLUMN category_id DROP NOT NULL;

-- Add transaction source
ALTER TABLE transactions
    ADD COLUMN source VARCHAR(20) NOT NULL DEFAULT 'IMPORTED';