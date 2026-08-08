# Smart Hospital Management System (Student Major Project Edition)

Java web-based Hospital Management System — patients, appointments, billing, prescriptions, and staff management.

Hospital Management System is a Java EE web application (Servlets + JSP) backed by MySQL. It provides user authentication and modules for patient registration, appointment scheduling, billing, prescription management, and basic reporting.

---

## 🌟 Technology Stack

* **Frontend:** HTML5, CSS3 (Modern Medical Theme), JavaScript (Vanilla), JSP, JSTL
* **Backend:** Java 17/11, Servlets (javax.servlet), JDBC, POJO Models, DAO Pattern
* **Database:** MySQL Database (`schema.sql` & `data.sql` included) + Embedded H2 Database auto-fallback
* **Build Tool:** Apache Maven
* **Server:** Apache Tomcat 9 / 10 compatible WAR packaging

---

## 🏗️ Architecture & Package Structure

```
SmartHospitalManagement/
├── pom.xml
├── schema.sql
├── data.sql
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/hospital/
    │   │       ├── model/             # POJO Bean Model Classes
    │   │       ├── dao/               # Data Access Object Interfaces
    │   │       ├── dao/impl/          # JDBC Implementations (PreparedStatements)
    │   │       ├── service/           # Business Logic Layer
    │   │       ├── controller/        # Role-based Servlets
    │   │       ├── filter/            # Authentication & Encoding Filters
    │   │       └── util/              # DBUtil, PasswordUtil (SHA-256), ValidationUtil
    │   ├── resources/
    │   │   └── db.properties          # Database Configuration
    │   └── webapp/
    │       ├── assets/                # Medical CSS & JS
    │       └── WEB-INF/
    │           ├── web.xml
    │           └── views/             # JSP Views per Role (Admin, Doctor, Receptionist, Pharmacist, Patient, Public)
```

---

## 👥 User Roles & Credentials

The system comes pre-loaded with demonstration accounts (`data.sql` / auto-seeded):

| Role | Email | Password | Access Rights |
|---|---|---|---|
| **Administrator** | `admin@hospital.com` | `admin123` | Full System Control, Doctors, Patients, Depts, Medicines, Financial Revenue, Reports |
| **Doctor** | `doctor@hospital.com` | `doctor123` | OPD Queue, Patient History, Diagnosis Entry, Electronic Prescriptions |
| **Receptionist** | `reception@hospital.com` | `reception123` | Patient Registration, Doctor Schedule Booking, Invoicing, Token Slip Printing |
| **Pharmacist** | `pharma@hospital.com` | `pharma123` | Prescriptions Queue, Inventory Stock Management, Stock Deductions |
| **Patient** | `patient@hospital.com` | `patient123` | Self Registration, Profile, Booking Request, Prescription Download, Invoices |

---

## 🚀 How to Build & Run

### Method A: Build WAR file for Apache Tomcat
1. Compile and package using Maven:
   ```bash
   mvn clean package
   ```
2. Deploy the generated `target/SmartHospitalManagement.war` to your Apache Tomcat `webapps/` folder.
3. Access in browser: `http://localhost:8080/SmartHospitalManagement`

### Method B: Database Setup (MySQL)
1. Import `schema.sql` into MySQL Workbench or command line:
   ```sql
   source d:/Hospital Management System/schema.sql;
   ```
2. Import `data.sql` for demo seed data:
   ```sql
   source d:/Hospital Management System/data.sql;
   ```
3. Update `src/main/resources/db.properties` with your MySQL username and password if different from `root`/`root`.

*(Note: If MySQL is not running on port 3306, `DBUtil` automatically falls back to embedded in-memory H2 database with auto-seeded data so you can test the application seamlessly without database errors!)*

---

## 📋 Key Features & Modules

1. **Role-Based Authentication & WebFilter Security**: Protected endpoints preventing unauthorized route navigation.
2. **Executive Admin Dashboard**: Real-time counter metrics for patients, active doctors, today's appointments, and total revenue collections.
3. **Electronic Prescription Writer**: Doctors select medicines from stock, specify dosage (e.g. 500mg), frequency (e.g. 1-0-1), duration (5 Days), and advice.
4. **Pharmacy Inventory & Auto-Deduction**: Real-time stock alerts for low inventory (<= 50 units) and automatic stock updates upon billing.
5. **Printable Invoices & OPD Slips**: Dedicated CSS `@media print` rules for generating clean, paper-ready appointment slips and billing receipts.
