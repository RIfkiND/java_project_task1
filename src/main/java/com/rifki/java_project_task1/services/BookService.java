package com.rifki.java_project_task1.services;


import com.rifki.java_project_task1.model.Book;
import com.rifki.java_project_task1.repository.BookRepository;
import java.util.List;

public class BookService {

    private final BookRepository bookRepository; 

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.getBooks();
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }
    
}
