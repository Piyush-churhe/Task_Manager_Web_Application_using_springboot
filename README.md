# 📋 Task Manager Application

A full-stack Task Manager web application built with **Spring Boot (REST API)** as backend and **HTML/CSS/JavaScript** as frontend. It supports **user-based task management** with authentication using JWT, role-based access for Admin, and persistent storage via **MySQL**.

---

## 🚀 Features

### 👤 User Functionality
- Register/Login using JWT authentication
- Add new tasks
- View tasks filtered by status (Ongoing, Pending, Completed)
- Edit or delete their own tasks
- Tasks saved with date, time, priority, and status

### 👑 Admin Functionality
- Admin Dashboard with access to:
  - View/Delete all users
  - View/Delete all tasks
  - Assign tasks to users
- Restricted admin-only access

---

## 💻 Technologies Used

| Layer         | Technology                     |
|---------------|---------------------------------|
| Backend       | Spring Boot, Spring Security, JWT |
| Frontend      | HTML, CSS, JavaScript (Vanilla) |
| Database      | MySQL                          |
| Build Tool    | Maven                          |
| Authentication| JWT Token-Based Auth           |

---

## 🛠️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/task-manager.git
cd task-manager
```

### 2. Configure MySQL Database

```
CREATE DATABASE task_manager;
```

Update your application.properties or application.yml:

```
spring.datasource.url=jdbc:mysql://localhost:3306/task_manager
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run the Spring Boot App

```
./mvnw spring-boot:run
```
```
http://localhost:8080
```

## 📁 Project Structure

```
task-manager/
├── src/
│   └── main/
│       ├── java/com/taskmanager/
│       │   ├── controller/        # API Controllers
│       │   ├── entity/            # Entities (User, Task)
│       │   ├── repository/        # Spring Data JPA Repositories
│       │   ├── security/          # JWT Security Config
│       │   ├── service/           # Business Logic
│       └── resources/
│           ├── static/            # HTML, CSS, JS files
│           └── application.properties
├── pom.xml
└── README.md
```

## 🔐 Admin Login Details

> You can manually insert an admin user in MySQL or pre-configure it in code (e.g., data.sql or CommandLineRunner).

```
INSERT INTO users (username, password, role) VALUES ('admin', 'encrypted_password', 'ADMIN');
```

## 📸 Screenshots

![image](https://github.com/user-attachments/assets/150c487d-23b8-4e21-93ec-55457fbc8722)
![image](https://github.com/user-attachments/assets/c4a04bdb-b80a-404e-83b2-9111ff8a941f)
![image](https://github.com/user-attachments/assets/1e056e0d-96ed-4818-9821-4eadf0a70b89)
![image](https://github.com/user-attachments/assets/05cc8c92-7e77-48bf-9023-03150635ada2)
![image](https://github.com/user-attachments/assets/78a2766e-5dd6-467b-8b86-40ff841fe5c2)
![image](https://github.com/user-attachments/assets/4890e818-e0b0-4611-90a0-62ce0d03bb30)
![image](https://github.com/user-attachments/assets/218e2ec3-4246-4a0a-b0a5-c51d628fea8c)





