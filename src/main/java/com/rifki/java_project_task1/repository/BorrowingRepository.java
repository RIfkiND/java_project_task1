package com.rifki.java_project_task1.repository;

import com.rifki.java_project_task1.model.Borrowing;
import com.rifki.java_project_task1.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.rifki.java_project_task1.model.enums.BorrowingStatus;


public class BorrowingRepository {

    public List<Borrowing> getBorrowings() {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT * FROM borrowings";
        try (
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Borrowing borrowing = new Borrowing(
                    rs.getInt("id"),
                    rs.getString("nim"),    
                    rs.getInt("book_id"),
                    rs.getString("borrow_date"),
                    rs.getString("return_date"),
                    BorrowingStatus.valueOf(rs.getString("status"))
                );
                borrowings.add(borrowing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowings;
    }
    
    public void addBorrowing(Borrowing borrowing) {
        String updateStockSql = "UPDATE books SET stock = stock - 1 WHERE id = ? AND stock > 0";
        String insertBorrowingSql = "INSERT INTO borrowings (nim, book_id, borrow_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
    
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement update = conn.prepareStatement(updateStockSql)) {
                update.setInt(1, borrowing.getBookId());
                int updatedRows = update.executeUpdate();
                if (updatedRows == 0) {
                    throw new SQLException("Buku tidak tersedia atau stok habis.");
                }
            }
            try (PreparedStatement insert = conn.prepareStatement(insertBorrowingSql)) {
                insert.setString(1, borrowing.getNim());
                insert.setInt(2, borrowing.getBookId());
                insert.setString(3, borrowing.getBorrowDate());
                insert.setString(4, borrowing.getReturnDate());
                insert.setString(5, borrowing.getStatus().name());
                insert.executeUpdate();
            }
                conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Gagal memproses peminjaman: " + e.getMessage(), e);
        } finally {            
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}