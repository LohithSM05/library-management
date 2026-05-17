# 📚 LibraryOS — Library Management System

A full-stack Library Management System built with **Java Spring Boot**, **MySQL**, and a responsive **HTML/CSS/JS** frontend.

---

## 🏗️ Tech Stack

| Layer      | Technology                          |
|------------|-------------------------------------|
| Backend    | Java 17, Spring Boot 3.2            |
| ORM        | Spring Data JPA / Hibernate         |
| Database   | MySQL 8 (H2 for local dev)          |
| Validation | Jakarta Bean Validation             |
| Frontend   | HTML5, CSS3, Vanilla JavaScript     |
| Build Tool | Maven                               |

---

## 📁 Project Structure

```
library-management/
├── pom.xml
├── schema.sql                         ← SQL reference schema
├── frontend/
│   └── index.html                     ← Full frontend UI
└── src/main/
    ├── java/com/library/
    │   ├── LibraryApplication.java    ← Entry point
    │   ├── model/
    │   │   ├── Book.java              ← Book entity
    │   │   ├── Member.java            ← Member entity
    │   │   └── BorrowRecord.java      ← Join entity with business logic
    │   ├── repository/
    │   │   ├── BookRepository.java
    │   │   ├── MemberRepository.java
    │   │   └── BorrowRecordRepository.java
    │   ├── service/
    │   │   ├── BookService.java       ← Business logic
    │   │   ├── MemberService.java
    │   │   └── BorrowService.java     ← Transactional borrow/return
    │   ├── controller/
    │   │   ├── BookController.java    ← REST endpoints
    │   │   ├── MemberController.java
    │   │   └── BorrowController.java
    │   └── config/
    │       ├── DataSeeder.java        ← Sample data on startup
    │       └── GlobalExceptionHandler.java
    └── resources/
        └── application.properties
```

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8 (or use H2 for zero-config dev)

### With MySQL
```bash
# 1. Create database
mysql -u root -p -e "CREATE DATABASE library_db;"

# 2. Update credentials in application.properties
# spring.datasource.username=root
# spring.datasource.password=your_password

# 3. Run
mvn spring-boot:run
```

### With H2 (no database needed)
In `application.properties`, comment out MySQL config and uncomment H2 lines, then:
```bash
mvn spring-boot:run
# H2 console: http://localhost:8080/h2-console
```

---

## 🔌 REST API Endpoints

### Books
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books` | List all books |
| GET | `/api/books/{id}` | Get book by ID |
| POST | `/api/books` | Add a book |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book |
| GET | `/api/books/search?q=...` | Search books |
| GET | `/api/books/available` | Available books |
| GET | `/api/books/stats` | Statistics |

### Members
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/members` | List all members |
| POST | `/api/members` | Register member |
| PUT | `/api/members/{id}` | Update member |
| DELETE | `/api/members/{id}` | Delete member |
| GET | `/api/members/search?q=...` | Search members |

### Borrow Records
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/borrows` | All records |
| GET | `/api/borrows/active` | Active borrows |
| GET | `/api/borrows/overdue` | Overdue books |
| POST | `/api/borrows/borrow?bookId=1&memberId=2` | Issue a book |
| PUT | `/api/borrows/return/{id}` | Return a book |

---

## 💡 Key Features

- ✅ Full CRUD for Books and Members
- ✅ Borrow / Return workflow with inventory tracking
- ✅ Overdue detection
- ✅ Business rule enforcement (5-book limit, active members only)
- ✅ `@Transactional` on borrow/return — atomic operations
- ✅ Input validation with `@Valid` + custom error messages
- ✅ Global exception handler for clean error responses
- ✅ Sample data seeded automatically on first run
- ✅ Responsive frontend with live search and filtering
