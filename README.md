# Shangri-La Petition Platform (SLPP)

## Project Overview
The Shangri-La Petition Platform (SLPP) is a web application that enables citizens of Shangri-La to create and sign petitions on matters within the government's responsibility. The platform facilitates parliamentary discussion through a petition system where citizens can propose and support petitions. Once a petition reaches the signature threshold set by the Petitions Committee, it qualifies for parliamentary debate.

## Technology Stack
- Backend: Java Spring Boot
- Frontend: HTML, CSS, JavaScript
- Database: MySQL
- Authentication: Spring Security
- Build Tool: Maven

## Prerequisites
Before running the application, ensure you have the following installed:
- Java JDK 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Modern web browser (Chrome, Firefox, Safari, or Edge)

## Database Setup
1. Install MySQL if not already installed
2. Create a new database named 'slpp':
```sql
CREATE DATABASE slpp;
```
3. Import the database schema and initial data:
```bash
mysql -u your_username -p slpp < path_to_sql_file/Your_email_ID.sql
```

## Application Configuration
1. Open `src/main/resources/application.properties`
2. Update the following properties with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/slpp
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Building and Running the Application
1. Clone the repository or unzip the project files
2. Navigate to the project root directory
3. Build the project using Maven:
```bash
mvn clean install
```
4. Run the application:
```bash
mvn spring-boot:run
```
5. Access the application at: `http://localhost:8080`

## Default Admin Credentials
- Username: admin@petition.parliament.sr
- Password: 2025%shangrila

## Features
- **User Registration and Authentication**
    - Email-based registration
    - BioID validation
    - QR code scanning for BioID
    - Secure password storage

- **Petitioner Features**
    - Create new petitions
    - View all petitions and their status
    - Sign open petitions
    - Dashboard access

- **Committee Features**
    - Set signature thresholds
    - View petition details and statistics
    - Provide responses to qualifying petitions
    - Close petitions

- **REST API Endpoints**
    - GET /slpp/petitions - Retrieve all petitions
    - GET /slpp/petitions?status=open - Retrieve open petitions

## Security Features
- Password hashing using SHA-256
- Session management
- Role-based access control
- Form validation and sanitization
- CSRF protection

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com.shangri_la.slpp/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── repository/
│   │       ├── security/
│   │       └── service/
│   └── resources/
│       ├── static/
│       └── templates/
```

