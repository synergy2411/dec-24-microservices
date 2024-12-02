CREATE TABLE IF NOT EXISTS `customer` (
    `customer_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR (30) NOT NULL,
    `email` VARCHAR(30) NOT NULL,
    `mobile_number` VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS `accounts` (
    `account_id` INT AUTO_INCREMENT PRIMARY KEY,
    `account_number` VARCHAR(30) NOT NULL,
    `account_type` VARCHAR(30) NOT NULL,
    `branch_address` VARCHAR(60) NOT NULL
);

