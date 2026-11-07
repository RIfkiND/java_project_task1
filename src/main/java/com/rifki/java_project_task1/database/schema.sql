CREATE DATABASE IF NOT EXISTS library;
USE library;

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_year INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
    nim VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS borrows (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nim VARCHAR(20) NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    borrow_status ENUM('borrowed', 'returned', 'late') NOT NULL,
    FOREIGN KEY (nim) REFERENCES users(nim),
    FOREIGN KEY (book_id) REFERENCES books(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO users (nim, name) VALUES
('109090250002', 'Rifki Nauval Dzaki'),
('109090250090', 'Sucipto'),
('109090250069', 'Ambatron');

INSERT INTO books (title, author, published_year) VALUES
('Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', 2008),
('The Pragmatic Programmer', 'Andrew Hunt & David Thomas', 1999),
('Design Patterns: Elements of Reusable Object-Oriented Software', 'Erich Gamma et al.', 1994),
('Introduction to Algorithms', 'Thomas H. Cormen', 2009),
('Structure and Interpretation of Computer Programs', 'Harold Abelson', 1996),
('Head First Design Patterns', 'Eric Freeman', 2004),
('Refactoring: Improving the Design of Existing Code', 'Martin Fowler', 1999),
('Effective Java', 'Joshua Bloch', 2018),
('Java: A Beginner\'s Guide', 'Herbert Schildt', 2021),
('Algorithms', 'Robert Sedgewick', 2011),
('Code Complete', 'Steve McConnell', 2004),
('You Don\'t Know JS Yet', 'Kyle Simpson', 2020),
('Designing Data-Intensive Applications', 'Martin Kleppmann', 2017),
('Database System Concepts', 'Abraham Silberschatz', 2019),
('Operating System Concepts', 'Abraham Silberschatz', 2018),
('Computer Networks', 'Andrew S. Tanenbaum', 2010),
('Artificial Intelligence: A Modern Approach', 'Stuart Russell', 2020),
('Compilers: Principles, Techniques, and Tools', 'Alfred V. Aho', 2006),
('Domain-Driven Design', 'Eric Evans', 2003),
('Test Driven Development: By Example', 'Kent Beck', 2002);