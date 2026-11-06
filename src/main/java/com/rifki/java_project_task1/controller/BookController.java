package com.rifki.java_project_task1.controller;

import com.rifki.java_project_task1.services.BookService;
import com.rifki.java_project_task1.model.Book;

import java.util.List;

public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public List<Book> searchBooks(String query) {
        return bookService.searchBooks(query);
    }
    
}