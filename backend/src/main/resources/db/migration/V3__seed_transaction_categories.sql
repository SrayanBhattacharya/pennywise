INSERT INTO transaction_categories (
    name,
    type,
    system_category,
    created_at,
    updated_at
)
VALUES
-- Expense Categories
('Food', 'EXPENSE', TRUE, NOW(), NOW()),
('Shopping', 'EXPENSE', TRUE, NOW(), NOW()),
('Transport', 'EXPENSE', TRUE, NOW(), NOW()),
('Bills', 'EXPENSE', TRUE, NOW(), NOW()),
('Healthcare', 'EXPENSE', TRUE, NOW(), NOW()),
('Entertainment', 'EXPENSE', TRUE, NOW(), NOW()),
('Education', 'EXPENSE', TRUE, NOW(), NOW()),
('Travel', 'EXPENSE', TRUE, NOW(), NOW()),
('Housing', 'EXPENSE', TRUE, NOW(), NOW()),
('Utilities', 'EXPENSE', TRUE, NOW(), NOW()),
('Insurance', 'EXPENSE', TRUE, NOW(), NOW()),
('Personal Care', 'EXPENSE', TRUE, NOW(), NOW()),
('Other Expense', 'EXPENSE', TRUE, NOW(), NOW()),


-- Income Categories
('Salary', 'INCOME', TRUE, NOW(), NOW()),
('Freelance', 'INCOME', TRUE, NOW(), NOW()),
('Business', 'INCOME', TRUE, NOW(), NOW()),
('Investment', 'INCOME', TRUE, NOW(), NOW()),
('Gift', 'INCOME', TRUE, NOW(), NOW()),
('Refund', 'INCOME', TRUE, NOW(), NOW()),
('Other Income', 'INCOME', TRUE, NOW(), NOW());