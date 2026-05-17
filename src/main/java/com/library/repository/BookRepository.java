package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);

    List<Book> findByGenreIgnoreCase(String genre);

    List<Book> findByAvailableCopiesGreaterThan(int copies);

    @Query("SELECT DISTINCT b.genre FROM Book b ORDER BY b.genre")
    List<String> findAllGenres();

    @Query("SELECT COUNT(b) FROM Book b WHERE b.availableCopies > 0")
    long countAvailableBooks();
}
