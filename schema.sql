-- ════════════════════════════════════════════════
-- Library Management System - MySQL Schema
-- Spring Boot auto-creates tables via JPA/Hibernate
-- This file is for reference and manual setup
-- ════════════════════════════════════════════════

CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- Books table
CREATE TABLE IF NOT EXISTS books (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(200)        NOT NULL,
    author          VARCHAR(100)        NOT NULL,
    isbn            VARCHAR(20)         NOT NULL UNIQUE,
    genre           VARCHAR(50)         NOT NULL,
    published_year  INT,
    total_copies    INT DEFAULT 1,
    available_copies INT DEFAULT 1,
    description     VARCHAR(500),
    created_at      DATETIME            DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_genre (genre),
    INDEX idx_author (author)
);

-- Members table
CREATE TABLE IF NOT EXISTS members (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)        NOT NULL,
    email       VARCHAR(150)        NOT NULL UNIQUE,
    phone       VARCHAR(15),
    status      ENUM('ACTIVE','SUSPENDED') DEFAULT 'ACTIVE',
    joined_at   DATETIME            DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email)
);

-- Borrow records table (JOIN table with business logic)
CREATE TABLE IF NOT EXISTS borrow_records (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id     BIGINT              NOT NULL,
    member_id   BIGINT              NOT NULL,
    borrow_date DATE                NOT NULL DEFAULT (CURDATE()),
    due_date    DATE                NOT NULL,
    return_date DATE,
    status      ENUM('BORROWED','RETURNED','OVERDUE') DEFAULT 'BORROWED',
    created_at  DATETIME            DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id)   REFERENCES books(id)   ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE,
    INDEX idx_status (status),
    INDEX idx_due_date (due_date)
);

-- ── Useful Queries ───────────────────────────────

-- All currently borrowed books with member info
SELECT b.title, m.name, br.borrow_date, br.due_date, br.status
FROM borrow_records br
JOIN books b   ON br.book_id   = b.id
JOIN members m ON br.member_id = m.id
WHERE br.status = 'BORROWED';

-- Overdue books
SELECT b.title, m.name, m.email, br.due_date,
       DATEDIFF(CURDATE(), br.due_date) AS days_overdue
FROM borrow_records br
JOIN books b   ON br.book_id   = b.id
JOIN members m ON br.member_id = m.id
WHERE br.status = 'BORROWED' AND br.due_date < CURDATE();

-- Most borrowed books
SELECT b.title, b.author, COUNT(*) AS times_borrowed
FROM borrow_records br
JOIN books b ON br.book_id = b.id
GROUP BY b.id
ORDER BY times_borrowed DESC
LIMIT 10;

-- Member borrow statistics
SELECT m.name, m.email,
       COUNT(CASE WHEN br.status = 'BORROWED' THEN 1 END)  AS active,
       COUNT(CASE WHEN br.status = 'RETURNED' THEN 1 END)  AS returned,
       COUNT(*) AS total
FROM members m
LEFT JOIN borrow_records br ON m.id = br.member_id
GROUP BY m.id;
