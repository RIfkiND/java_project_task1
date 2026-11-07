
package com.rifki.java_project_task1.model;

import com.rifki.java_project_task1.model.enums.BorrowingStatus;

public class Borrowing {
    private int id;
    private String nim;
    private int bookId;
    private String borrowDate;
    private String returnDate;
    private BorrowingStatus status;

    public Borrowing() {
     }
    public Borrowing(
        int id,
        String nim,
        int bookId,
        String borrowDate,
        String returnDate,
        BorrowingStatus status
    ) {
        this.id = id;
        this.nim = nim;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getNim() {
        return nim;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public BorrowingStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(BorrowingStatus status) {
        this.status = status;
    }
}
