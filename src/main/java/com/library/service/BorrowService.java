package com.library.service;

import com.library.model.BorrowRecord;
import com.library.model.Book;
import com.library.model.Member;
import com.library.repository.BorrowRecordRepository;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {

    @Autowired private BorrowRecordRepository borrowRepo;
    @Autowired private BookRepository bookRepo;
    @Autowired private MemberRepository memberRepo;
    @Autowired private BookService bookService;

    @Transactional
    public BorrowRecord borrowBook(Long bookId, Long memberId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (member.getStatus() != Member.MemberStatus.ACTIVE)
            throw new RuntimeException("Member account is suspended");
        if (book.getAvailableCopies() <= 0)
            throw new RuntimeException("No copies available");
        if (borrowRepo.countActiveBorrowsByMember(memberId) >= 5)
            throw new RuntimeException("Member has reached borrow limit (5 books)");
        if (borrowRepo.existsByBookIdAndMemberIdAndStatus(bookId, memberId, BorrowRecord.BorrowStatus.BORROWED))
            throw new RuntimeException("Member already has this book borrowed");

        bookService.decrementAvailable(bookId);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setMember(member);
        return borrowRepo.save(record);
    }

    @Transactional
    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRepo.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
        if (record.getStatus() == BorrowRecord.BorrowStatus.RETURNED)
            throw new RuntimeException("Book already returned");

        record.setReturnDate(LocalDate.now());
        record.setStatus(BorrowRecord.BorrowStatus.RETURNED);
        bookService.incrementAvailable(record.getBook().getId());
        return borrowRepo.save(record);
    }

    public List<BorrowRecord> getAllRecords() {
        return borrowRepo.findAll();
    }

    public List<BorrowRecord> getActiveBorrows() {
        return borrowRepo.findByStatus(BorrowRecord.BorrowStatus.BORROWED);
    }

    public List<BorrowRecord> getOverdueRecords() {
        return borrowRepo.findOverdue(LocalDate.now());
    }

    public List<BorrowRecord> getMemberHistory(Long memberId) {
        return borrowRepo.findByMemberId(memberId);
    }

    public long countActiveBorrows() {
        return borrowRepo.findByStatus(BorrowRecord.BorrowStatus.BORROWED).size();
    }

    public long countOverdue() {
        return borrowRepo.findOverdue(LocalDate.now()).size();
    }
}
