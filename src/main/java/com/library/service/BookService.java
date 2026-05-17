package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new RuntimeException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        int borrowedCopies = existing.getTotalCopies() - existing.getAvailableCopies();
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setGenre(updatedBook.getGenre());
        existing.setPublishedYear(updatedBook.getPublishedYear());
        existing.setDescription(updatedBook.getDescription());
        existing.setTotalCopies(updatedBook.getTotalCopies());
        existing.setAvailableCopies(Math.max(0, updatedBook.getTotalCopies() - borrowedCopies));

        return bookRepository.save(existing);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }

    public List<String> getAllGenres() {
        return bookRepository.findAllGenres();
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);
    }

    // Called by BorrowService
    public void decrementAvailable(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getAvailableCopies() <= 0)
            throw new RuntimeException("No copies available for: " + book.getTitle());
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }

    public void incrementAvailable(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailableCopies(Math.min(book.getAvailableCopies() + 1, book.getTotalCopies()));
        bookRepository.save(book);
    }

    public long countAvailableBooks() {
        return bookRepository.countAvailableBooks();
    }

    public long countTotalBooks() {
        return bookRepository.count();
    }
}
