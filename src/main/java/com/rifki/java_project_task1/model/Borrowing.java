package com.rifki.java_project_task1.model;

import com.rifki.java_project_task1.model.enums.BorrowingStatus;

public class Borrowing {
    private String nim;
    private int Book_id;
    private String borrow_date;
    private String return_date;
    private BorrowingStatus status;

    public Borrowing(String nim, int book_id, String borrow_date, String return_date, BorrowingStatus status) {
        this.nim = nim;
        this.Book_id = book_id;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
        this.status = status;
    }

    public String getNim() {
        return nim;
    }

    public int getBook_id() {
        return Book_id;
    }

    public String getBorrow_date() {
        return borrow_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public BorrowingStatus getStatus() {
        return status;
    }
}
