package com.rifki.java_project_task1.services;

import com.rifki.java_project_task1.model.Borrowing;
import com.rifki.java_project_task1.repository.BorrowingRepository;
import java.util.List;

public class BorrowingService {
    private final BorrowingRepository borrowingRepository;

    public BorrowingService(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public List<Borrowing> getBorrowingsByNim(String nim) {
        return borrowingRepository.findByNim(nim);
    }

    public void addBorrowing(Borrowing borrowing) {
        borrowingRepository.save(borrowing);
    }

    public void returnBook(int borrowingId, String loggedInNim) {
        borrowingRepository.returnBook(borrowingId, loggedInNim);
    }
}