# Library Management System

A full-stack Library Management System built using Java, Spring Boot, Hibernate, Maven, and MySQL. The project helps manage books, members, and borrowing records through REST APIs and a responsive frontend.

---

## Features

- Add, update, delete books
- Manage library members
- Borrow and return books
- RESTful API architecture
- MySQL database integration
- Exception handling
- Data validation
- Frontend integration

---

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven

### Database
- MySQL

### Frontend
- HTML
- CSS
- JavaScript

---

## Project Structure

```text
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── model/
 ├── config/
```

---

## Installation & Setup

### 1. Clone Repository

```bash
git clone https://github.com/LohithSM05/library-management.git
```

### 2. Open Project

```bash
cd library-management
```

### 3. Configure Database

Update:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/librarydb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

---

### 4. Run Project

```bash
mvn spring-boot:run
```

Server runs at:

```text
http://localhost:8080
```

---

## API Endpoints

### Books

- GET `/books`
- POST `/books`
- PUT `/books/{id}`
- DELETE `/books/{id}`

### Members

- GET `/members`
- POST `/members`

### Borrow Records

- POST `/borrow`
- POST `/return`

---

## Future Enhancements

- JWT Authentication
- Swagger API Documentation
- React Frontend
- Docker Deployment
- Cloud Database Integration
- Role-Based Access Control

---

## Author

### Lohith S M

GitHub:
https://github.com/LohithSM05