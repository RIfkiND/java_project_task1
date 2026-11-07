package com.rifki.java_project_task1.view;

import com.rifki.java_project_task1.controller.BookController;
import com.rifki.java_project_task1.controller.BorrowingController;
import com.rifki.java_project_task1.model.enums.BorrowingStatus;
import com.rifki.java_project_task1.model.Book;
import com.rifki.java_project_task1.model.Borrowing;
import com.rifki.java_project_task1.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {

    private final User loggedInUser;
    private final BookController bookController;
    private final BorrowingController borrowingController;

    private JTable bookTable;
    private DefaultTableModel bookTableModel;

    private JTable borrowingTable;
    private DefaultTableModel borrowingTableModel;

    public MainFrame(User loggedInUser, BookController bookController, BorrowingController borrowingController) {
        this.loggedInUser = loggedInUser;
        this.bookController = bookController;
        this.borrowingController = borrowingController;

        initUI();
        loadBookData();
        loadBorrowingData();
    }

    private void initUI() {
        setTitle("Sistem Manajemen Perpustakaan - Selamat Datang, " + (loggedInUser != null ? loggedInUser.getNim() : "Guest"));
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Daftar Buku", createBookPanel());
        tabbedPane.addTab("Peminjaman Saya", createBorrowingPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField tfSearch = new JTextField(20);
        JButton btnSearch = new JButton("Cari");
        JButton btnRefresh = new JButton("Refresh");

        topPanel.add(new JLabel("Cari Buku:"));
        topPanel.add(tfSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnRefresh);

        String[] columns = {"ID", "Judul", "Pengarang", "Tahun Terbit", "Aksi"};
        bookTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; 
            }
        };
        bookTable = new JTable(bookTableModel);

        Action pinjamAction = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int row = Integer.parseInt(e.getActionCommand());
                handleBorrowBook(row);
            }
        };
        new ButtonColumn(bookTable, pinjamAction, 4);

        btnSearch.addActionListener(e -> {
            String query = tfSearch.getText().trim();
            if (!query.isEmpty()) {
                List<Book> books = bookController.searchBooks(query);
                populateBookTable(books);
            }
        });

        btnRefresh.addActionListener(e -> {
            tfSearch.setText("");
            loadBookData();
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBorrowingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnReturnBook = new JButton("Kembalikan Buku");
        JButton btnRefresh = new JButton("Refresh");

        topPanel.add(btnReturnBook);
        topPanel.add(btnRefresh);

        String[] columns = {"ID Peminjaman", "NIM", "ID Buku", "Tgl Pinjam", "Tgl Kembali", "Status"};
        borrowingTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        borrowingTable = new JTable(borrowingTableModel);

        btnReturnBook.addActionListener(e -> handleReturnBook());
        btnRefresh.addActionListener(e -> loadBorrowingData());

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(borrowingTable), BorderLayout.CENTER);

        return panel;
    }

    private void loadBookData() {
        List<Book> books = bookController.getAllBooks();
        populateBookTable(books);
    }

    private void populateBookTable(List<Book> books) {
        bookTableModel.setRowCount(0);
        if (books != null) {
            for (Book b : books) {
                bookTableModel.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getYear(),
                    "Pinjam" 
                });
            }
        }
    }

    private void loadBorrowingData() {
        borrowingTableModel.setRowCount(0);
        if (loggedInUser == null) return;

        List<Borrowing> borrowings = borrowingController.getBorrowingsByNim(loggedInUser.getNim());
        if (borrowings != null) {
            for (Borrowing b : borrowings) {
                borrowingTableModel.addRow(new Object[]{
                    b.getId(),
                    b.getNim(),
                    b.getBookId(),
                    b.getBorrowDate(),
                    b.getReturnDate(),
                    b.getStatus()
                });
            }
        }
    }

    private void handleBorrowBook(int row) {
        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(this, "Anda harus login terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookId = (int) bookTableModel.getValueAt(row, 0);
        String bookTitle = (String) bookTableModel.getValueAt(row, 1);

        List<Borrowing> myBorrowings = borrowingController.getBorrowingsByNim(loggedInUser.getNim());
        boolean isAlreadyBorrowed = false;

        if (myBorrowings != null) {
            for (Borrowing b : myBorrowings) {
                if (b.getBookId() == bookId && (b.getStatus() == BorrowingStatus.BORROWED || "BORROWED".equalsIgnoreCase(String.valueOf(b.getStatus())))) {
                    isAlreadyBorrowed = true;
                    break;
                }
            }
        }

        if (isAlreadyBorrowed) {
            JOptionPane.showMessageDialog(this, "Buku '" + bookTitle + "' sedang Anda pinjam dan belum dikembalikan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda ingin meminjam buku '" + bookTitle + "'?",
                "Konfirmasi Pinjam Buku",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Borrowing borrowing = new Borrowing();
                borrowing.setNim(loggedInUser.getNim());
                borrowing.setBookId(bookId);
                borrowing.setBorrowDate(LocalDate.now().toString());
                borrowing.setReturnDate(LocalDate.now().plusDays(7).toString());
                borrowing.setStatus(BorrowingStatus.BORROWED);

                borrowingController.addBorrowing(borrowing);

                JOptionPane.showMessageDialog(this, "Berhasil meminjam buku!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadBorrowingData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal meminjam buku: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleReturnBook() {
        int selectedRow = borrowingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi peminjaman pada tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int borrowingId = (int) borrowingTableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin mengembalikan buku ini?",
                "Konfirmasi Pengembalian",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                borrowingController.returnBook(borrowingId, loggedInUser.getNim());
                JOptionPane.showMessageDialog(this, "Buku berhasil dikembalikan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadBorrowingData();
                loadBookData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal mengembalikan buku: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}