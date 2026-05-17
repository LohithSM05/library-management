package com.library.config;

import com.library.model.Book;
import com.library.model.Member;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private BookRepository bookRepo;
    @Autowired private MemberRepository memberRepo;

    @Override
    public void run(String... args) {
        if (bookRepo.count() > 0) return; // Skip if data already exists

        // Seed Books
        String[][] books = {
            {"The Great Gatsby",       "F. Scott Fitzgerald", "978-0-7432-7356-5", "Fiction",    "1925", "3", "A story of the fabulously wealthy Jay Gatsby."},
            {"Clean Code",             "Robert C. Martin",    "978-0-1323-5088-4", "Technology", "2008", "2", "A guide to writing readable and maintainable code."},
            {"Atomic Habits",          "James Clear",         "978-0-7352-1129-2", "Self-Help",  "2018", "4", "Tiny changes that create remarkable results."},
            {"The Alchemist",          "Paulo Coelho",        "978-0-0617-2110-4", "Fiction",    "1988", "2", "A philosophical novel about a shepherd's journey."},
            {"Sapiens",                "Yuval Noah Harari",   "978-0-0624-3965-1", "History",    "2011", "3", "A brief history of humankind."},
            {"Design Patterns",        "Gang of Four",        "978-0-2016-3361-5", "Technology", "1994", "1", "Elements of reusable object-oriented software."},
            {"Thinking, Fast & Slow",  "Daniel Kahneman",    "978-0-3743-5 163-2","Psychology", "2011", "2", "Explores the two systems that drive the way we think."},
            {"To Kill a Mockingbird",  "Harper Lee",          "978-0-4462-3107-8", "Fiction",    "1960", "3", "A classic of American literature."},
            {"Spring Boot in Action",  "Craig Walls",         "978-1-6172-9292-5", "Technology", "2015", "2", "Practical guide to Spring Boot development."},
            {"Deep Work",              "Cal Newport",         "978-1-4555-8669-1", "Self-Help",  "2016", "2", "Rules for focused success in a distracted world."},
        };

        for (String[] b : books) {
            Book book = new Book();
            book.setTitle(b[0]); book.setAuthor(b[1]); book.setIsbn(b[2]);
            book.setGenre(b[3]); book.setPublishedYear(Integer.parseInt(b[4]));
            int copies = Integer.parseInt(b[5]);
            book.setTotalCopies(copies); book.setAvailableCopies(copies);
            book.setDescription(b[6]);
            bookRepo.save(book);
        }

        // Seed Members
        String[][] members = {
            {"Aarav Sharma",   "aarav@example.com",   "9876543210"},
            {"Priya Patel",    "priya@example.com",   "9123456789"},
            {"Rahul Gupta",    "rahul@example.com",   "9988776655"},
            {"Ananya Singh",   "ananya@example.com",  "9012345678"},
            {"Karan Mehta",    "karan@example.com",   "9871234560"},
        };

        for (String[] m : members) {
            Member member = new Member();
            member.setName(m[0]); member.setEmail(m[1]); member.setPhone(m[2]);
            memberRepo.save(member);
        }

        System.out.println("✅ Sample data seeded successfully!");
    }
}
