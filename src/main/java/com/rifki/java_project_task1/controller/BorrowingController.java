package com.rifki.java_project_task1.controller;

import com.rifki.java_project_task1.services.BorrowingService;
import com.rifki.java_project_task1.model.Borrowing;

import java.util.List;

public class BorrowingController {

    private BorrowingService BorrowingService;

    public BorrowingController(BorrowingService BorrowingService) {
        this.BorrowingService = BorrowingService;
    }

    public List<Borrowing> getAllBorrowings() {
        return BorrowingService.getAllBorrowings();
    }

    public List<Borrowing> getBorrowingsByNim(String nim) {
        return BorrowingService.getBorrowingsByNim(nim);
    }

    public void addBorrowing(Borrowing borrowing) {
        BorrowingService.addBorrowing(borrowing);
    }

    public void returnBook(int borrowingId, String loggedInNim) {
        BorrowingService.returnBook(borrowingId, loggedInNim);
    }
}