-- Seed Data for Smart Hospital Management System (Real Hospital Edition)
USE hospital_db;

-- 1. Roles
INSERT INTO roles (role_id, role_name) VALUES
(1, 'Administrator'),
(2, 'Doctor'),
(3, 'Receptionist'),
(4, 'Pharmacist'),
(5, 'Patient')
ON DUPLICATE KEY UPDATE role_name=VALUES(role_name);

-- 2. Users (Ordered according to Doctor List: Neha, Shobana, Sruthi, Jagadeesh, Jaswanth, Kamalesh)
INSERT INTO users (user_id, name, email, password, phone, role_id) VALUES
(1, 'System Administrator', 'admin@hospital.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '9876543210', 1),
(2, 'Dr. Neha', 'doctor@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543211', 2),
(3, 'Dr. Shobana', 'shobana@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543213', 2),
(4, 'Dr. Sruthi', 'sruthi@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543218', 2),
(5, 'Dr. Jagadeesh', 'jagadeesh@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543215', 2),
(6, 'Dr. Jaswanth', 'jaswanth@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543214', 2),
(7, 'Dr. Kamalesh', 'kamalesh@hospital.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '9876543212', 2),
(8, 'Sarah Jenkins', 'reception@hospital.com', 'a937a0c0e5a882a859e9c8dfb2f15e8b4e7a0e5b7b9015c7e16346294d13c79c', '9876543219', 3),
(9, 'Michael Vance', 'pharma@hospital.com', 'c8303f83d9d3004d3e8e24484b35ef58406566085a53930b8d5a1b3c9597793d', '9876543220', 4),
(10, 'John Doe', 'patient@hospital.com', '9559c77ef52fb9a7e6bf2a35368a5c4d0a9202ff9478f7e2d93e1ef6f364024b', '9876543216', 5),
(11, 'Anita Roy', 'anita@gmail.com', '9559c77ef52fb9a7e6bf2a35368a5c4d0a9202ff9478f7e2d93e1ef6f364024b', '9876543217', 5)
ON DUPLICATE KEY UPDATE email=VALUES(email);

-- 3. Departments
INSERT INTO departments (dept_id, dept_name, description, head_doctor_name) VALUES
(1, 'Cardiology', 'Heart health, cardiac surgery, and vascular interventions.', 'Dr. Neha'),
(2, 'Orthopedics', 'Joint replacement, bone fractures, and spinal therapy.', 'Dr. Shobana'),
(3, 'Dermatology & Cosmetic Care', 'Skin care, laser dermatology, and cosmetic surgery.', 'Dr. Sruthi'),
(4, 'Gastroenterology', 'Digestive health, liver care, endoscopy, and hepatology.', 'Dr. Jagadeesh'),
(5, 'Pediatric Cardiology', 'Pediatric heart care, congenital defects, and neonatal care.', 'Dr. Jaswanth'),
(6, 'Neurology', 'Brain, spinal cord, and neurosurgical procedures.', 'Dr. Kamalesh')
ON DUPLICATE KEY UPDATE dept_name=VALUES(dept_name);

-- 4. Doctors (Strict Order: 1. Dr. Neha, 2. Dr. Shobana, 3. Dr. Sruthi, 4. Dr. Jagadeesh, 5. Dr. Jaswanth, 6. Dr. Kamalesh)
INSERT INTO doctors (doctor_id, user_id, name, qualification, specialization, experience_years, phone, email, dept_id, consultation_fee, status) VALUES
(1, 2, 'Dr. Neha', 'MS, M.Ch (Cardiothoracic Surgery)', 'Heart Surgeon', 16, '9876543211', 'doctor@hospital.com', 1, 900.00, 'Active'),
(2, 3, 'Dr. Shobana', 'MS (Orthopedics), Joint Replacement Fellow', 'Orthopedic Surgeon', 10, '9876543213', 'shobana@hospital.com', 2, 750.00, 'Active'),
(3, 4, 'Dr. Sruthi', 'MD (Dermatology, Venereology & Leprosy)', 'Dermatologist & Cosmetologist', 5, '9876543218', 'sruthi@hospital.com', 3, 600.00, 'Active'),
(4, 5, 'Dr. Jagadeesh', 'MD (Gen Med), DM (Gastroenterology)', 'Gastroenterologist', 7, '9876543215', 'jagadeesh@hospital.com', 4, 650.00, 'Active'),
(5, 6, 'Dr. Jaswanth', 'MD (Pediatrics), DM (Pediatric Cardiology)', 'Pediatric Cardiologist', 8, '9876543214', 'jaswanth@hospital.com', 5, 700.00, 'Active'),
(6, 7, 'Dr. Kamalesh', 'MD (Neurology), M.Ch (Neurosurgery)', 'Neurosurgeon', 12, '9876543212', 'kamalesh@hospital.com', 6, 800.00, 'Active')
ON DUPLICATE KEY UPDATE doctor_id=VALUES(doctor_id);

-- 5. Patients
INSERT INTO patients (patient_id, user_id, name, gender, age, blood_group, phone, address, email, emergency_contact) VALUES
(1, 10, 'John Doe', 'Male', 35, 'O+', '9876543216', '123 MG Road, Bangalore', 'patient@hospital.com', '9876500001'),
(2, 11, 'Anita Roy', 'Female', 29, 'A+', '9876543217', '45 Park Street, Kolkata', 'anita@gmail.com', '9876500002'),
(3, NULL, 'Robert Smith', 'Male', 52, 'B+', '9876543218', '78 Nehru Place, New Delhi', 'robert@yahoo.com', '9876500003'),
(4, NULL, 'Emily Davis', 'Female', 41, 'AB+', '9876543219', '12 Jubilee Hills, Hyderabad', 'emily@gmail.com', '9876500004')
ON DUPLICATE KEY UPDATE patient_id=VALUES(patient_id);

-- 6. Medicines
INSERT INTO medicines (medicine_id, name, category, manufacturer, price, stock_quantity, expiry_date) VALUES
(1, 'Paracetamol 650mg', 'Analgesic', 'Cipla', 25.00, 150, '2027-12-31'),
(2, 'Amoxicillin 500mg', 'Antibiotic', 'Sun Pharma', 85.00, 80, '2027-08-30'),
(3, 'Atorvastatin 10mg', 'Cardiovascular', 'Lupin', 120.00, 200, '2028-01-15'),
(4, 'Pantoprazole 40mg', 'Antacid', 'Torrent', 45.00, 30, '2026-11-20'),
(5, 'Cetirizine 10mg', 'Antihistamine', 'Dr. Reddys', 18.00, 120, '2027-06-10'),
(6, 'Metformin 500mg', 'Antidiabetic', 'Abbott', 35.00, 180, '2027-10-05'),
(7, 'Ibuprofen 400mg', 'NSAID', 'Cipla', 30.00, 90, '2027-04-18')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- 7. Appointments
INSERT INTO appointments (appointment_id, patient_id, doctor_id, appointment_date, appointment_time, symptoms, status) VALUES
(1, 1, 1, CURRENT_DATE, '10:30 AM', 'Mild chest tightness after physical exercise and shortness of breath.', 'Completed'),
(2, 2, 2, CURRENT_DATE, '11:15 AM', 'Severe lower back pain when bending down.', 'Confirmed'),
(3, 3, 6, CURRENT_DATE, '02:00 PM', 'Frequent migraines and dizziness.', 'Pending'),
(4, 4, 5, CURRENT_DATE, '04:30 PM', 'Child heart rate evaluation.', 'Pending')
ON DUPLICATE KEY UPDATE appointment_id=VALUES(appointment_id);

-- 8. Diagnosis
INSERT INTO diagnosis (diagnosis_id, appointment_id, patient_id, doctor_id, symptoms, diagnosis_detail, recommended_tests, doctor_notes) VALUES
(1, 1, 1, 1, 'Chest tightness, fatigue', 'Mild Angina Pectoris / Exercise Induced Muscle Stress', 'ECG, Coronary Angiogram', 'Patient advised 2 weeks rest, low sodium diet, and follow-up ECG.')
ON DUPLICATE KEY UPDATE diagnosis_id=VALUES(diagnosis_id);

-- 9. Prescriptions
INSERT INTO prescriptions (prescription_id, appointment_id, patient_id, doctor_id, prescribed_date, advice) VALUES
(1, 1, 1, 1, CURRENT_DATE, 'Take medicines strictly after food. Avoid heavy lifting and stay hydrated.')
ON DUPLICATE KEY UPDATE prescription_id=VALUES(prescription_id);

-- 10. Prescription Items
INSERT INTO prescription_items (item_id, prescription_id, medicine_id, dosage, frequency, duration, instructions) VALUES
(1, 1, 3, '10mg', '0-0-1 (Night)', '30 Days', 'After dinner'),
(2, 1, 1, '650mg', '1-0-1 (Morning-Night)', '5 Days', 'As needed for pain')
ON DUPLICATE KEY UPDATE item_id=VALUES(item_id);

-- 11. Bills
INSERT INTO bills (bill_id, patient_id, appointment_id, consultation_fee, medicine_charges, test_charges, total_amount, payment_status, payment_method, bill_date) VALUES
(1, 1, 1, 900.00, 145.00, 500.00, 1545.00, 'Paid', 'Credit Card', CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE bill_id=VALUES(bill_id);

-- 12. Bill Items
INSERT INTO bill_items (bill_item_id, bill_id, item_name, quantity, unit_price, total_price) VALUES
(1, 1, 'Doctor Consultation Fee', 1, 900.00, 900.00),
(2, 1, 'Atorvastatin 10mg', 1, 120.00, 120.00),
(3, 1, 'Paracetamol 650mg', 1, 25.00, 25.00),
(4, 1, 'ECG Diagnostic Test', 1, 500.00, 500.00)
ON DUPLICATE KEY UPDATE bill_item_id=VALUES(bill_item_id);
