## 📌 Project Overview  
**Fotosalon** is a Java Spring Boot web application designed to manage a photography salon business.  
It includes features for managing clients, employees, photo sessions, discounts, warehouse inventory, and more.  
The system also supports file uploads (e.g., avatars), scheduling, and basic financial tracking.

---

## 🚀 Features  
- **User Roles**: Admin, Photographer, Client  
- **Client Management**: Create, update, and view client profiles  
- **Employee Management**: Manage staff schedules and contact info  
- **Warehouse**: Track photography equipment and materials  
- **Discount System**: Apply and manage discounts for customers  
- **Scheduling**: Manage photo session dates and photographer assignments  
- **File Upload**: Upload avatars and photographs  
- **Financial Module**: Track income and expenses  

---

## 🛠 Technologies Used  
- **Java 17+**  
- **Spring Boot** (Web, Data JPA, Security)  
- **Maven** (Maven Wrapper included)  
- **Thymeleaf** (HTML templates)  
- **Hibernate** (ORM)  
- **H2 / MySQL** (configurable database)  
- **Lombok** (boilerplate reduction)  

---

## 📂 Project Structure  
```
fotosalon/
 ├── photos/                # Uploaded avatars and photos
 ├── src/main/java/ru/project/fotosalon/
 │   ├── config/             # App configuration (MVC setup)
 │   ├── controllers/        # REST & MVC controllers
 │   ├── dto/                # Data Transfer Objects
 │   ├── entities/           # JPA entity models (if present)
 │   ├── repositories/       # Spring Data repositories
 │   ├── services/           # Business logic layer
 │   └── FotosalonApplication.java
 ├── src/main/resources/
 │   ├── templates/          # Thymeleaf templates (HTML)
 │   ├── static/              # Static assets (CSS, JS, images)
 │   └── application.properties
 ├── pom.xml                 # Maven dependencies
 ├── mvnw / mvnw.cmd         # Maven Wrapper scripts
 └── README.md               # Project documentation
```

---

## ⚙️ Installation & Setup  

### 1️⃣ Clone the repository
```bash
git clone https://github.com/username/fotosalon.git
cd fotosalon
```

### 2️⃣ Build the project  
```bash
./mvnw clean install
```

### 3️⃣ Run the application  
```bash
./mvnw spring-boot:run
```

The app will start at:  
```
http://localhost:8080
```

---

## 🔧 Configuration  
Edit `src/main/resources/application.properties` to configure:  
- **Database** (H2 in-memory or MySQL)  
- **File storage path** for uploads  
- **Server port**  

Example for MySQL:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fotosalon
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

## 📸 API Endpoints (Example)  

| Method | Endpoint                | Description                     |
|--------|------------------------|---------------------------------|
| GET    | `/clients`              | Get all clients                 |
| POST   | `/clients`              | Add new client                  |
| GET    | `/employees`            | Get employee list               |
| POST   | `/upload`               | Upload file (photo/avatar)      |

---

## 👤 Default Credentials (Example)  
If using a preconfigured database, the default admin might be:  
```
Username: admin
Password: admin
```
