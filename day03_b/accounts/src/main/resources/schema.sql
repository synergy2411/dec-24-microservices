CREATE TABLE IF NOT EXISTS `customer` (
    `customer_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR (30) NOT NULL,
    `email` VARCHAR(30) NOT NULL,
    `mobile_number` VARCHAR(30) NOT NULL,
    `created_at` DATE NOT NULL,
    `created_by` VARCHAR(30) NOT NULL,
    `updated_at` DATE DEFAULT NULL,
    `updated_by` VARCHAR(30) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `accounts` (
    `account_id` INT AUTO_INCREMENT PRIMARY KEY,
    `account_number` INT NOT NULL,
    `account_type` VARCHAR(30) NOT NULL,
    `branch_address` VARCHAR(60) NOT NULL,
    `customer_id` INT NOT NULL,
    `created_at` DATE NOT NULL,
    `created_by` VARCHAR(30) NOT NULL,
    `updated_at` DATE DEFAULT NULL,
    `updated_by` VARCHAR(30) DEFAULT NULL
);

