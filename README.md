# Smart Hospital Management System

## Project Overview
The **Smart Hospital Management System** is a complete, enterprise-ready Java Full-Stack web application designed for academic major projects, portfolio showcases, and technical interviews. It provides a centralized digital healthcare portal for managing patients, doctors, OPD appointment scheduling, medical diagnoses, electronic prescriptions, pharmacy inventory, billing invoicing, and executive reporting.

The project is built using standard Java Web technologies (Servlets, JSP, JDBC, DAO pattern, MVC architecture) without complex frameworks, making it clean, easy to understand, and lightweight.

---

## Features
* **Role-Based Portal Security**: Multi-tier authentication filter system protecting specialized portals for Administrators, Doctors, Receptionists, Pharmacists, and Patients.
* **Executive Administrative Dashboard**: Metrics overview including total patients, active doctors, daily appointment queues, pharmacy inventory health, and financial revenue trackers.
* **Patient Management & Registration**: Self-service patient portal registration and receptionist intake form with medical history recording.
* **Doctor OPD Management & Consultation**: Doctor portal for viewing scheduled patient appointments, submitting diagnoses, recommending diagnostic tests, and creating electronic prescriptions.
* **Electronic Prescription & Pharmacy Control**: Automated link between doctor prescriptions and pharmacy inventory stock control, including low-stock alerts.
* **Billing & Invoicing**: Automated bill generation for doctor consultation fees, diagnostic test charges, and pharmacy charges with printable receipt support (`@media print`).
* **Auto-Fallback Database Utility**: Automatic transition to an embedded H2 database with pre-seeded data if MySQL is unavailable locally.

---

## User Roles

| Role | Default Username | Default Password | Access Rights |
|---|---|---|---|
| **Admin** | `admin@hospital.com` | `admin123` | Complete administrative authority over users, doctors, departments, inventory, and revenue reports. |
| **Doctor** | `doctor@hospital.com` | `doctor123` | Consultation queue, patient medical records, diagnosis writer, and prescription creation. |
| **Receptionist** | `reception@hospital.com` | `reception123` | Patient intake, appointment booking, token slip generation, and consultation billing. |
| **Pharmacist** | `pharma@hospital.com` | `pharma123` | Prescription fulfillment queue, medicine inventory stock updates, and pharmacy invoicing. |
| **Patient** | `patient@hospital.com` | `patient123` | Self registration, booking appointment requests, profile management, viewing prescriptions, and invoices. |

---

## Technology Stack
* **Java**: Java 11 / 17
* **Servlets**: Java Servlet API (`javax.servlet`)
* **JSP**: JavaServer Pages & JSTL (JavaServer Pages Standard Tag Library)
* **JDBC**: Java Database Connectivity with `PreparedStatement` and SQL injection defense
* **MySQL**: MySQL 8.0+ Database Server
* **Maven**: Apache Maven build automation & dependency manager
* **Apache Tomcat**: Apache Tomcat 9 / 10 application server
* **HTML5**: Semantic web structure
* **CSS3**: Custom modern clinical design system with responsive layouts
* **JavaScript**: Client-side validation and interactive UI controls

---

## Architecture
The application follows standard 3-Tier Enterprise MVC (Model-View-Controller) Architecture:

* **Controller Layer (`com.hospital.controller`)**: Role-specific Servlets handling HTTP requests, parameters, session validation, and view forwarding.
* **Service Layer (`com.hospital.service`)**: Business logic orchestrators linking controllers to DAO implementations and transactional operations.
* **DAO Layer (`com.hospital.dao`)**: Data Access Object interfaces defining CRUD contracts.
* **DAO Implementation Layer (`com.hospital.dao.impl`)**: JDBC implementations executing optimized SQL statements using `PreparedStatement` and resource management.
* **Model Layer (`com.hospital.model`)**: Plain Old Java Objects (POJO) encapsulation beans representing domain entities (User, Patient, Doctor, Appointment, Bill, Medicine, etc.).
* **Filter Layer (`com.hospital.filter`)**: HTTP WebFilters enforcing authentication, role authorization, and request UTF-8 encoding.
* **Utility Layer (`com.hospital.util`)**: Helper classes for Database Connections (`DBUtil`), Password Hashing SHA-256 (`PasswordUtil`), and Input Validation (`ValidationUtil`).
* **JSP View Layer (`WEB-INF/views`)**: Server-rendered JSP templates organized securely inside `WEB-INF` by access role.

---

## Project Structure

```
SmartHospitalManagement/
├── pom.xml
├── schema.sql
├── data.sql
├── README.md
├── .gitignore
├── src/
│   └── main/
│       ├── java/
│       │   └── com/hospital/
│       │       ├── controller/
│       │       │   ├── AdminServlet.java
│       │       │   ├── AuthServlet.java
│       │       │   ├── DoctorServlet.java
│       │       │   ├── PatientServlet.java
│       │       │   ├── PharmacistServlet.java
│       │       │   ├── PublicServlet.java
│       │       │   └── ReceptionistServlet.java
│       │       ├── dao/
│       │       │   ├── AppointmentDAO.java
│       │       │   ├── BillingDAO.java
│       │       │   ├── DepartmentDAO.java
│       │       │   ├── DiagnosisDAO.java
│       │       │   ├── DoctorDAO.java
│       │       │   ├── MedicineDAO.java
│       │       │   ├── PatientDAO.java
│       │       │   ├── PrescriptionDAO.java
│       │       │   ├── UserDAO.java
│       │       │   └── impl/
│       │       ├── filter/
│       │       │   ├── AuthFilter.java
│       │       │   └── EncodingFilter.java
│       │       ├── model/
│       │       │   ├── Appointment.java
│       │       │   ├── Bill.java
│       │       │   ├── BillItem.java
│       │       │   ├── Department.java
│       │       │   ├── Diagnosis.java
│       │       │   ├── Doctor.java
│       │       │   ├── Medicine.java
│       │       │   ├── Patient.java
│       │       │   ├── Prescription.java
│       │       │   ├── PrescriptionItem.java
│       │       │   ├── Role.java
│       │       │   └── User.java
│       │       ├── service/
│       │       │   ├── AuthService.java
│       │       │   ├── BillingService.java
│       │       │   └── HospitalService.java
│       │       └── util/
│       │           ├── DBUtil.java
│       │           ├── PasswordUtil.java
│       │           └── ValidationUtil.java
│       ├── resources/
│       │   ├── db.properties.example
│       │   └── db.properties (git ignored)
│       └── webapp/
│           ├── index.jsp
│           ├── assets/
│           │   ├── css/
│           │   │   └── style.css
│           │   ├── js/
│           │   │   └── main.js
│           │   └── images/
│           └── WEB-INF/
│               ├── web.xml
│               └── views/
│                   ├── admin/
│                   ├── common/
│                   ├── doctor/
│                   ├── patient/
│                   ├── pharmacist/
│                   ├── public/
│                   └── receptionist/
```

---

## Database Setup

1. **Create Database**:
   Create a MySQL database named `hospital_db`:
   ```sql
   CREATE DATABASE hospital_db;
   USE hospital_db;
   ```
2. **Run Table Schema Script**:
   Execute `schema.sql` to construct all relational tables:
   ```bash
   mysql -u root -p hospital_db < schema.sql
   ```
3. **Run Seed Data Script**:
   Execute `data.sql` to populate initial roles, admin user, specialist doctors, departments, and medicines:
   ```bash
   mysql -u root -p hospital_db < data.sql
   ```
4. **Configure Database Credentials**:
   Copy `src/main/resources/db.properties.example` to `src/main/resources/db.properties` and configure your database credentials:
   ```properties
   db.driver=com.mysql.cj.jdbc.Driver
   db.url=jdbc:mysql://localhost:3306/hospital_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   db.user=root
   db.password=your_mysql_password
   ```

*(Note: Never commit real database passwords or credentials to version control.)*

---

## Running Locally

1. **Build the WAR package using Maven**:
   ```bash
   mvn clean package
   ```
2. **Locate Generated WAR**:
   The build process produces `target/SmartHospitalManagement.war`.
3. **Deploy to Apache Tomcat**:
   Copy `SmartHospitalManagement.war` to your Apache Tomcat `webapps/` directory and start Tomcat (`bin/startup.bat` or `bin/startup.sh`).
4. **Open Application**:
   Navigate to `http://localhost:8080/SmartHospitalManagement` in your browser.

---

## GitHub Repository
GitHub is used as the source-code repository for version control, code review, and project history.

*Note: GitHub Pages is designed only for static HTML/CSS/JS websites and cannot run Java Servlets, JSP templates, JDBC database connections, MySQL servers, or Apache Tomcat. To host the live running web application, deploy the generated WAR package to a Java-compatible cloud server or Tomcat hosting platform.*
