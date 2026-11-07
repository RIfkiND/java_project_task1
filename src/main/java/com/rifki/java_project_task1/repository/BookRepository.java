package com.rifki.java_project_task1.repository;

import com.rifki.java_project_task1.model.Book;
import  com.rifki.java_project_task1.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("published_year")
                );
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            return books;
        }
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("published_year")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
