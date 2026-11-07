package com.rifki.java_project_task1.repository;

import com.rifki.java_project_task1.database.DatabaseConnection;
import com.rifki.java_project_task1.model.Borrowing;
import com.rifki.java_project_task1.model.enums.BorrowingStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowingRepository {

    public List<Borrowing> findAll() {
        List<Borrowing> borrows = new ArrayList<>();
        String sql = "SELECT * FROM borrows";
        try (
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                borrows.add(mapResultSetToBorrowing(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    public List<Borrowing> findByNim(String nim) {
        List<Borrowing> borrows = new ArrayList<>();
        String sql = "SELECT id, nim, book_id, borrow_date, return_date, borrow_status FROM borrows WHERE nim = ?";
        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, nim);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    borrows.add(mapResultSetToBorrowing(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        return borrows;
    }

    public void save(Borrowing borrowing) {
        String insertBorrowingSql =
            "INSERT INTO borrows (nim, book_id, borrow_date, return_date, borrow_status) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            try (
                PreparedStatement insert = conn.prepareStatement(
                    insertBorrowingSql
                )
            ) {
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
            throw new RuntimeException(
                "Gagal memproses peminjaman: " + e.getMessage(),
                e
            );
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

    public void returnBook(int borrowingId, String loggedInNim) {
        String findBorrowingSql =
            "SELECT book_id, borrow_status, nim FROM borrows WHERE id = ?";
        String updateBorrowingSql =
            "UPDATE borrows SET borrow_status = ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            int bookId = -1;
            String currentStatus = null;
            String borrowerNim = null;

            try (
                PreparedStatement findStmt = conn.prepareStatement(
                    findBorrowingSql
                )
            ) {
                findStmt.setInt(1, borrowingId);
                try (ResultSet rs = findStmt.executeQuery()) {
                    if (rs.next()) {
                        bookId = rs.getInt("book_id");
                        currentStatus = rs.getString("borrow_status");
                        borrowerNim = rs.getString("nim");
                    } else {
                        throw new SQLException(
                            "Data peminjaman tidak ditemukan."
                        );
                    }
                }
            }

            if (loggedInNim == null || !loggedInNim.equals(borrowerNim)) {
                throw new SQLException(
                    "Akses ditolak: Anda tidak dapat mengembalikan buku milik NIM lain."
                );
            }

            if (
                BorrowingStatus.RETURNED.name().equalsIgnoreCase(currentStatus)
            ) {
                throw new SQLException(
                    "Buku dengan ID transaksi ini sudah dikembalikan sebelumnya."
                );
            }

            try (
                PreparedStatement updateBorrowing = conn.prepareStatement(
                    updateBorrowingSql
                )
            ) {
                updateBorrowing.setString(1, BorrowingStatus.RETURNED.name());
                updateBorrowing.setInt(2, borrowingId);
                updateBorrowing.executeUpdate();
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
            throw new RuntimeException(
                "Gagal memproses pengembalian buku: " + e.getMessage(),
                e
            );
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

    private Borrowing mapResultSetToBorrowing(ResultSet rs) throws SQLException {
        String statusStr = rs.getString("borrow_status");
        BorrowingStatus status = null;
        
        if (statusStr != null) {
            try {
                status = BorrowingStatus.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
           return new Borrowing(
            rs.getInt("id"),
            rs.getString("nim"),
            rs.getInt("book_id"),
            rs.getString("borrow_date"),
            rs.getString("return_date"),
            status
        );
    }
}
