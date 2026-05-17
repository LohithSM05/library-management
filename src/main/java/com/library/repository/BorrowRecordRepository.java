package com.library.repository;

import com.library.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    List<BorrowRecord> findByMemberId(Long memberId);

    List<BorrowRecord> findByBookId(Long bookId);

    List<BorrowRecord> findByStatus(BorrowRecord.BorrowStatus status);

    @Query("SELECT br FROM BorrowRecord br WHERE br.status = 'BORROWED' AND br.dueDate < :today")
    List<BorrowRecord> findOverdue(LocalDate today);

    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.member.id = :memberId AND br.status = 'BORROWED'")
    long countActiveBorrowsByMember(Long memberId);

    boolean existsByBookIdAndMemberIdAndStatus(Long bookId, Long memberId, BorrowRecord.BorrowStatus status);
}
