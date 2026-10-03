package com.hospital.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database Utility Class for managing JDBC connections.
 * Features auto-fallback to embedded H2 database with auto-schema seeding if MySQL is unavailable.
 */
public class DBUtil {

    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());
    private static Properties props = new Properties();
    private static boolean isH2Fallback = false;
    private static boolean h2Initialized = false;

    static {
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load db.properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (!isH2Fallback) {
            try {
                String driver = props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.password");
                Class.forName(driver);
                return DriverManager.getConnection(url, user, pass);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "MySQL Connection failed. Falling back to embedded H2 Database...", e);
                isH2Fallback = true;
            }
        }

        // Embedded H2 Database Mode
        try {
            String driver = props.getProperty("h2.driver", "org.h2.Driver");
            String url = props.getProperty("h2.url", "jdbc:h2:mem:hospital_db;DB_CLOSE_DELAY=-1;MODE=MySQL");
            String user = props.getProperty("h2.user", "sa");
            String pass = props.getProperty("h2.password", "");
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass);
            if (!h2Initialized) {
                initializeH2Database(conn);
                h2Initialized = true;
            }
            return conn;
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "H2 Driver Class not found", e);
            throw new SQLException("Database driver error", e);
        }
    }

    private static synchronized void initializeH2Database(Connection conn) {
        LOGGER.info("Initializing embedded H2 database tables and demo seed data...");
        try (Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS roles (role_id INT AUTO_INCREMENT PRIMARY KEY, role_name VARCHAR(50) NOT NULL UNIQUE);");
            st.execute("CREATE TABLE IF NOT EXISTS users (user_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL, phone VARCHAR(20), role_id INT NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
            st.execute("CREATE TABLE IF NOT EXISTS departments (dept_id INT AUTO_INCREMENT PRIMARY KEY, dept_name VARCHAR(100) NOT NULL UNIQUE, description TEXT, head_doctor_name VARCHAR(100));");
            st.execute("CREATE TABLE IF NOT EXISTS doctors (doctor_id INT AUTO_INCREMENT PRIMARY KEY, user_id INT NOT NULL UNIQUE, name VARCHAR(100) NOT NULL, qualification VARCHAR(100) NOT NULL, specialization VARCHAR(100) NOT NULL, experience_years INT NOT NULL, phone VARCHAR(20) NOT NULL, email VARCHAR(100) NOT NULL, dept_id INT NOT NULL, consultation_fee DECIMAL(10, 2) NOT NULL DEFAULT 500.00, status VARCHAR(20) NOT NULL DEFAULT 'Active');");
            st.execute("CREATE TABLE IF NOT EXISTS patients (patient_id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, name VARCHAR(100) NOT NULL, gender VARCHAR(10) NOT NULL, age INT NOT NULL, blood_group VARCHAR(5) NOT NULL, phone VARCHAR(20) NOT NULL, address TEXT, email VARCHAR(100), emergency_contact VARCHAR(20), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
            st.execute("CREATE TABLE IF NOT EXISTS appointments (appointment_id INT AUTO_INCREMENT PRIMARY KEY, patient_id INT NOT NULL, doctor_id INT NOT NULL, appointment_date DATE NOT NULL, appointment_time VARCHAR(20) NOT NULL, symptoms TEXT, status VARCHAR(20) NOT NULL DEFAULT 'Pending', created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
            st.execute("CREATE TABLE IF NOT EXISTS diagnosis (diagnosis_id INT AUTO_INCREMENT PRIMARY KEY, appointment_id INT NOT NULL UNIQUE, patient_id INT NOT NULL, doctor_id INT NOT NULL, symptoms TEXT, diagnosis_detail TEXT NOT NULL, recommended_tests TEXT, doctor_notes TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
            st.execute("CREATE TABLE IF NOT EXISTS medicines (medicine_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL UNIQUE, category VARCHAR(50), manufacturer VARCHAR(100), price DECIMAL(10, 2) NOT NULL, stock_quantity INT NOT NULL DEFAULT 0, expiry_date DATE NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
            st.execute("CREATE TABLE IF NOT EXISTS prescriptions (prescription_id INT AUTO_INCREMENT PRIMARY KEY, appointment_id INT NOT NULL UNIQUE, patient_id INT NOT NULL, doctor_id INT NOT NULL, prescribed_date DATE NOT NULL, advice TEXT);");
            st.execute("CREATE TABLE IF NOT EXISTS prescription_items (item_id INT AUTO_INCREMENT PRIMARY KEY, prescription_id INT NOT NULL, medicine_id INT NOT NULL, dosage VARCHAR(50) NOT NULL, frequency VARCHAR(50) NOT NULL, duration VARCHAR(50) NOT NULL, instructions TEXT);");
            st.execute("CREATE TABLE IF NOT EXISTS bills (bill_id INT AUTO_INCREMENT PRIMARY KEY, patient_id INT NOT NULL, appointment_id INT, consultation_fee DECIMAL(10, 2) DEFAULT 0.00, medicine_charges DECIMAL(10, 2) DEFAULT 0.00, test_charges DECIMAL(10, 2) DEFAULT 0.00, total_amount DECIMAL(10, 2) NOT NULL, payment_status VARCHAR(20) NOT NULL DEFAULT 'Unpaid', payment_method VARCHAR(50) DEFAULT 'Cash', bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
            st.execute("CREATE TABLE IF NOT EXISTS bill_items (bill_item_id INT AUTO_INCREMENT PRIMARY KEY, bill_id INT NOT NULL, item_name VARCHAR(100) NOT NULL, quantity INT NOT NULL DEFAULT 1, unit_price DECIMAL(10, 2) NOT NULL, total_price DECIMAL(10, 2) NOT NULL);");

            // Seed initial data if roles count is 0
            var rs = st.executeQuery("SELECT COUNT(*) FROM roles");
            if (rs.next() && rs.getInt(1) == 0) {
                st.execute("INSERT INTO roles (role_id, role_name) VALUES (1, 'Administrator'), (2, 'Doctor'), (3, 'Receptionist'), (4, 'Pharmacist'), (5, 'Patient');");
                st.execute("INSERT INTO users (user_id, name, email, password, phone, role_id) VALUES " +
                        "(1, 'System Administrator', 'admin@hospital.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '9876543210', 1), " +
                        "(2, 'Dr. Neha', 'doctor@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543211', 2), " +
                        "(3, 'Dr. Shobana', 'shobana@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543213', 2), " +
                        "(4, 'Dr. Sruthi', 'sruthi@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543218', 2), " +
                        "(5, 'Dr. Jagadeesh', 'jagadeesh@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543215', 2), " +
                        "(6, 'Dr. Jaswanth', 'jaswanth@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543214', 2), " +
                        "(7, 'Dr. Harsha', 'harsha@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543221', 2), " +
                        "(8, 'Dr. Haasith', 'haasith@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543222', 2), " +
                        "(9, 'Dr. Kamalesh', 'kamalesh@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543212', 2), " +
                        "(10, 'Sarah Jenkins', 'reception@hospital.com', 'a937a0c0e5a882a859e9c8dfb2f15e8b4e7a0e5b7b9015c7e16346294d13c79c', '9876543219', 3), " +
                        "(11, 'Michael Vance', 'pharma@hospital.com', 'c8303f83d9d3004d3e8e24484b35ef58406566085a53930b8d5a1b3c9597793d', '9876543220', 4), " +
                        "(12, 'John Doe', 'patient@hospital.com', '9559c77ef52fb9a7e6bf2a35368a5c4d0a9202ff9478f7e2d93e1ef6f364024b', '9876543216', 5), " +
                        "(13, 'Anita Roy', 'anita@gmail.com', '9559c77ef52fb9a7e6bf2a35368a5c4d0a9202ff9478f7e2d93e1ef6f364024b', '9876543217', 5);");

                st.execute("INSERT INTO departments (dept_id, dept_name, description, head_doctor_name) VALUES " +
                        "(1, 'Cardiology', 'Heart health, cardiac surgery, and vascular treatment.', 'Dr. Neha'), " +
                        "(2, 'Orthopedics', 'Bone, joint, ligament, and musculoskeletal surgery & therapy.', 'Dr. Shobana'), " +
                        "(3, 'Dermatology', 'Advanced skin, laser surgery, and cosmetic dermatology.', 'Dr. Sruthi'), " +
                        "(4, 'Gastroenterology', 'Digestive health, endoscopy, liver care, and hepatology.', 'Dr. Jagadeesh'), " +
                        "(5, 'Pediatric Cardiology', 'Comprehensive medical care for infants and children with heart conditions.', 'Dr. Jaswanth'), " +
                        "(6, 'Pulmonology', 'Advanced respiratory care, asthma, COPD, and critical care pulmonology.', 'Dr. Harsha'), " +
                        "(7, 'Endocrinology', 'Diabetes management, thyroid disorders, and metabolic hormone care.', 'Dr. Haasith'), " +
                        "(8, 'Neurology', 'Disorders of the brain, spinal cord, and neurosurgery.', 'Dr. Kamalesh');");

                st.execute("INSERT INTO doctors (doctor_id, user_id, name, qualification, specialization, experience_years, phone, email, dept_id, consultation_fee, status) VALUES " +
                        "(1, 2, 'Dr. Neha', 'MS, M.Ch (Cardiothoracic Surgery)', 'Heart Surgeon', 16, '9876543211', 'doctor@hospital.com', 1, 900.00, 'Active'), " +
                        "(2, 3, 'Dr. Shobana', 'MS (Orthopedics), Joint Replacement Fellow', 'Orthopedic Surgeon', 10, '9876543213', 'shobana@hospital.com', 2, 750.00, 'Active'), " +
                        "(3, 4, 'Dr. Sruthi', 'MD (Dermatology, Venereology & Leprosy)', 'Dermatologist & Cosmetologist', 5, '9876543218', 'sruthi@hospital.com', 3, 600.00, 'Active'), " +
                        "(4, 5, 'Dr. Jagadeesh', 'MD (Gen Med), DM (Gastroenterology)', 'Gastroenterologist', 7, '9876543215', 'jagadeesh@hospital.com', 4, 650.00, 'Active'), " +
                        "(5, 6, 'Dr. Jaswanth', 'MD (Pediatrics), DM (Pediatric Cardiology)', 'Pediatric Cardiologist', 8, '9876543214', 'jaswanth@hospital.com', 5, 700.00, 'Active'), " +
                        "(6, 7, 'Dr. Harsha', 'MD (Gen Med), DM (Pulmonology)', 'Pulmonologist & Critical Care', 9, '9876543221', 'harsha@hospital.com', 6, 750.00, 'Active'), " +
                        "(7, 8, 'Dr. Haasith', 'MD (Gen Med), DM (Endocrinology)', 'Endocrinologist & Diabetologist', 11, '9876543222', 'haasith@hospital.com', 7, 780.00, 'Active'), " +
                        "(8, 9, 'Dr. Kamalesh', 'MD (Neurology), M.Ch (Neurosurgery)', 'Neurosurgeon', 12, '9876543212', 'kamalesh@hospital.com', 8, 800.00, 'Active');");

                st.execute("INSERT INTO patients (patient_id, user_id, name, gender, age, blood_group, phone, address, email, emergency_contact) VALUES " +
                        "(1, 12, 'John Doe', 'Male', 35, 'O+', '9876543216', '123 MG Road, Bangalore', 'patient@hospital.com', '9876500001'), " +
                        "(2, 13, 'Anita Roy', 'Female', 29, 'A+', '9876543217', '45 Park Street, Kolkata', 'anita@gmail.com', '9876500002'), " +
                        "(3, NULL, 'Robert Smith', 'Male', 52, 'B+', '9876543218', '78 Nehru Place, New Delhi', 'robert@yahoo.com', '9876500003'), " +
                        "(4, NULL, 'Emily Davis', 'Female', 41, 'AB+', '9876543219', '12 Jubilee Hills, Hyderabad', 'emily@gmail.com', '9876500004');");

                st.execute("INSERT INTO medicines (medicine_id, name, category, manufacturer, price, stock_quantity, expiry_date) VALUES " +
                        "(1, 'Paracetamol 650mg', 'Analgesic', 'Cipla', 25.00, 150, '2027-12-31'), " +
                        "(2, 'Amoxicillin 500mg', 'Antibiotic', 'Sun Pharma', 85.00, 80, '2027-08-30'), " +
                        "(3, 'Atorvastatin 10mg', 'Cardiovascular', 'Lupin', 120.00, 200, '2028-01-15'), " +
                        "(4, 'Pantoprazole 40mg', 'Antacid', 'Torrent', 45.00, 30, '2026-11-20'), " +
                        "(5, 'Cetirizine 10mg', 'Antihistamine', 'Dr. Reddys', 18.00, 120, '2027-06-10'), " +
                        "(6, 'Metformin 500mg', 'Antidiabetic', 'Abbott', 35.00, 180, '2027-10-05'), " +
                        "(7, 'Ibuprofen 400mg', 'NSAID', 'Cipla', 30.00, 90, '2027-04-18');");

                st.execute("INSERT INTO appointments (appointment_id, patient_id, doctor_id, appointment_date, appointment_time, symptoms, status) VALUES " +
                        "(1, 1, 1, CURRENT_DATE(), '10:30 AM', 'Mild chest tightness after physical exercise and shortness of breath.', 'Completed'), " +
                        "(2, 2, 2, CURRENT_DATE(), '11:15 AM', 'Severe lower back pain when bending down.', 'Confirmed'), " +
                        "(3, 3, 6, CURRENT_DATE(), '02:00 PM', 'Frequent migraines and dizziness.', 'Pending'), " +
                        "(4, 4, 5, CURRENT_DATE(), '04:30 PM', 'Child heart rate evaluation.', 'Pending');");

                st.execute("INSERT INTO diagnosis (diagnosis_id, appointment_id, patient_id, doctor_id, symptoms, diagnosis_detail, recommended_tests, doctor_notes) VALUES " +
                        "(1, 1, 1, 1, 'Chest tightness, fatigue', 'Mild Angina Pectoris / Exercise Induced Muscle Stress', 'ECG, Lipid Profile', 'Patient advised 2 weeks rest, low sodium diet, and follow-up ECG.');");

                st.execute("INSERT INTO prescriptions (prescription_id, appointment_id, patient_id, doctor_id, prescribed_date, advice) VALUES " +
                        "(1, 1, 1, 1, CURRENT_DATE(), 'Take medicines strictly after food. Avoid heavy lifting and stay hydrated.');");

                st.execute("INSERT INTO prescription_items (item_id, prescription_id, medicine_id, dosage, frequency, duration, instructions) VALUES " +
                        "(1, 1, 3, '10mg', '0-0-1 (Night)', '30 Days', 'After dinner'), " +
                        "(2, 1, 1, '650mg', '1-0-1 (Morning-Night)', '5 Days', 'As needed for pain');");

                st.execute("INSERT INTO bills (bill_id, patient_id, appointment_id, consultation_fee, medicine_charges, test_charges, total_amount, payment_status, payment_method, bill_date) VALUES " +
                        "(1, 1, 1, 900.00, 145.00, 500.00, 1545.00, 'Paid', 'Credit Card', CURRENT_TIMESTAMP());");

                st.execute("INSERT INTO bill_items (bill_item_id, bill_id, item_name, quantity, unit_price, total_price) VALUES " +
                        "(1, 1, 'Doctor Consultation Fee', 1, 900.00, 900.00), " +
                        "(2, 1, 'Atorvastatin 10mg', 1, 120.00, 120.00), " +
                        "(3, 1, 'Paracetamol 650mg', 1, 25.00, 25.00), " +
                        "(4, 1, 'ECG Diagnostic Test', 1, 500.00, 500.00);");
            }
            LOGGER.info("Embedded H2 database initialization completed successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize H2 database", e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing connection", e);
            }
        }
    }
}
