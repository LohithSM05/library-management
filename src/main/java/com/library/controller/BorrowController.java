package com.library.controller;

import com.library.model.BorrowRecord;
import com.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrows")
@CrossOrigin(origins = "*")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @GetMapping
    public List<BorrowRecord> all() {
        return borrowService.getAllRecords();
    }

    @GetMapping("/active")
    public List<BorrowRecord> active() {
        return borrowService.getActiveBorrows();
    }

    @GetMapping("/overdue")
    public List<BorrowRecord> overdue() {
        return borrowService.getOverdueRecords();
    }

    @GetMapping("/member/{memberId}")
    public List<BorrowRecord> byMember(@PathVariable Long memberId) {
        return borrowService.getMemberHistory(memberId);
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@RequestParam Long bookId, @RequestParam Long memberId) {
        try {
            return ResponseEntity.ok(borrowService.borrowBook(bookId, memberId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/return/{recordId}")
    public ResponseEntity<?> returnBook(@PathVariable Long recordId) {
        try {
            return ResponseEntity.ok(borrowService.returnBook(recordId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/stats")
    public Map<String, Long> stats() {
        return Map.of(
            "active", borrowService.countActiveBorrows(),
            "overdue", borrowService.countOverdue()
        );
    }
}
