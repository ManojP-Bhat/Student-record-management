-- Run this file once in MySQL before starting the application.
-- Example: mysql -u root -p < database/schema.sql

CREATE DATABASE IF NOT EXISTS srms_db;
USE srms_db;

CREATE TABLE IF NOT EXISTS students (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    email   VARCHAR(100) NOT NULL,
    course  VARCHAR(100) NOT NULL,
    year    INT NOT NULL
);

-- Optional: a couple of sample rows so the table isn't empty on first run
INSERT INTO students (name, email, course, year) VALUES
    ('Aisha Rao', 'aisha.rao@example.com', 'MCA', 1),
    ('Rohan Mehta', 'rohan.mehta@example.com', 'MCA', 2);
