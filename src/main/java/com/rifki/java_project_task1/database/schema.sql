CREATE DATABASE IF NOT EXISTS library ;

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_year INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS users (
    NIM VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
);

CREATE TABLE IF NOT EXISTS borrows (
    id INT AUTO_INCREMENT PRIMARY KEY,
    NIM VARCHAR(20) NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    borrow_status ENUM('borrowed', 'returned', 'late') NOT NULL,
    FOREIGN KEY (NIM) REFERENCES users(NIM),
    FOREIGN KEY (book_id) REFERENCES books(id),
);
