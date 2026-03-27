# 📞 Full-Stack Phonebook Application

A modern contact management system built with **Spring Boot**, **Vue.js 3**, and **Postgres**.

## 🚀 Features
- **Dynamic Search**: Instantly filter contacts by name, phone, or email.
- **Form Validation**: Backend `@Pattern` checks for phone numbers and duplicate email detection.
- **CRUD Operations**: Add, View, Manage (Edit/Delete) contacts.
- **Responsive UI**: High-contrast design for better visibility.
- **Docker Ready**: Easy database setup using Docker Compose.

---

## 🛠️ Tech Stack
- **Frontend**: Vue.js 3 (Vite), Axios, CSS3
- **Backend**: Java 17, Spring Boot 3, Spring Data JPA
- **Database**: PostgreSQL 15
- **DevOps**: Docker, Git

---

## ⚙️ Setup & Installation

### 1. Database (Docker)
Ensure you have Docker Desktop installed. Run the following in the root directory:
```bash
docker-compose up -d
This starts a PostgreSQL instance on port 5432.

2. Backend (Spring Boot)
Navigate to the backend folder.

Update src/main/resources/application.properties with your DB credentials.

Run the application via Eclipse (STS) or Maven:

Bash
./mvnw spring-boot:run
3. Frontend (Vue.js)
Navigate to the frontend folder.

Install dependencies:

Bash
npm install
Start the development server:

Bash
npm run dev
Open http://localhost:5173 in your browser.

////////////////////////////////////////////////////////
Project Structure
Plaintext
PhonebookApp/
├── backend/           # Spring Boot API
├── frontend/          # Vue.js SPA
├── docker-compose.yml # Postgres configuration
└── README.md          # Project documentation
