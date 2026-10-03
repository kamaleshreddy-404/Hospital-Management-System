package com.hospital.service;

import com.hospital.dao.*;
import com.hospital.dao.impl.*;
import com.hospital.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HospitalService {

    private DepartmentDAO departmentDAO = new DepartmentDAOImpl();
    private DoctorDAO doctorDAO = new DoctorDAOImpl();
    private PatientDAO patientDAO = new PatientDAOImpl();
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private DiagnosisDAO diagnosisDAO = new DiagnosisDAOImpl();
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAOImpl();
    private MedicineDAO medicineDAO = new MedicineDAOImpl();
    private BillingDAO billingDAO = new BillingDAOImpl();

    public Map<String, Object> getAdminDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPatients", patientDAO.getTotalPatientsCount());
        stats.put("totalDoctors", doctorDAO.getTotalDoctorsCount());
        stats.put("todayAppointments", appointmentDAO.getTodayAppointmentsCount());
        stats.put("totalDepartments", departmentDAO.getTotalDepartmentsCount());
        stats.put("totalMedicines", medicineDAO.getTotalMedicinesCount());
        stats.put("todayRevenue", billingDAO.getTodayRevenue());
        stats.put("totalRevenue", billingDAO.getTotalRevenue());
        stats.put("lowStockCount", medicineDAO.findLowStockMedicines().size());
        return stats;
    }

    // Department operations
    public List<Department> getAllDepartments() { return departmentDAO.findAll(); }
    public boolean addDepartment(Department d) { return departmentDAO.create(d); }
    public boolean updateDepartment(Department d) { return departmentDAO.update(d); }
    public boolean deleteDepartment(int id) { return departmentDAO.delete(id); }

    // Doctor operations
    public List<Doctor> getAllDoctors() { return doctorDAO.findAll(); }
    public Doctor getDoctorById(int id) { return doctorDAO.findById(id); }
    public Doctor getDoctorByUserId(int userId) { return doctorDAO.findByUserId(userId); }
    public boolean addDoctor(Doctor d) { return doctorDAO.create(d); }
    public boolean updateDoctor(Doctor d) { return doctorDAO.update(d); }
    public boolean deleteDoctor(int id) { return doctorDAO.delete(id); }

    // Patient operations
    public List<Patient> getAllPatients() { return patientDAO.findAll(); }
    public Patient getPatientById(int id) { return patientDAO.findById(id); }
    public Patient getPatientByUserId(int userId) { return patientDAO.findByUserId(userId); }
    public List<Patient> searchPatients(String q) { return patientDAO.searchPatients(q); }
    public boolean addPatient(Patient p) { return patientDAO.create(p); }
    public boolean updatePatient(Patient p) { return patientDAO.update(p); }
    public boolean deletePatient(int id) { return patientDAO.delete(id); }

    // Appointment operations
    public List<Appointment> getAllAppointments() { return appointmentDAO.findAll(); }
    public Appointment getAppointmentById(int id) { return appointmentDAO.findById(id); }
    public List<Appointment> getAppointmentsByDoctor(int doctorId) { return appointmentDAO.findByDoctorId(doctorId); }
    public List<Appointment> getTodayAppointmentsByDoctor(int doctorId) { return appointmentDAO.findTodayAppointmentsByDoctor(doctorId); }
    public List<Appointment> getTodayAppointmentsAll() { return appointmentDAO.findTodayAppointmentsAll(); }
    public List<Appointment> getAppointmentsByPatient(int patientId) { return appointmentDAO.findByPatientId(patientId); }
    public boolean bookAppointment(Appointment app) { return appointmentDAO.create(app); }
    public boolean updateAppointmentStatus(int apptId, String status) { return appointmentDAO.updateStatus(apptId, status); }

    // Diagnosis & Prescription
    public Diagnosis getDiagnosisByAppointment(int apptId) { return diagnosisDAO.findByAppointmentId(apptId); }
    public boolean addDiagnosis(Diagnosis diag) { return diagnosisDAO.create(diag); }
    
    public Prescription getPrescriptionByAppointment(int apptId) { return prescriptionDAO.findByAppointmentId(apptId); }
    public List<Prescription> getPrescriptionsByPatient(int patientId) { return prescriptionDAO.findByPatientId(patientId); }
    public List<Prescription> getAllPrescriptions() { return prescriptionDAO.findAll(); }
    public boolean addPrescription(Prescription rx, List<PrescriptionItem> items) {
        return prescriptionDAO.createPrescription(rx, items);
    }

    // Medicine operations
    public List<Medicine> getAllMedicines() { return medicineDAO.findAll(); }
    public List<Medicine> getLowStockMedicines() { return medicineDAO.findLowStockMedicines(); }
    public Medicine getMedicineById(int id) { return medicineDAO.findById(id); }
    public boolean addMedicine(Medicine m) { return medicineDAO.create(m); }
    public boolean updateMedicine(Medicine m) { return medicineDAO.update(m); }
    public boolean updateMedicineStock(int id, int stock) { return medicineDAO.updateStock(id, stock); }
    public boolean deleteMedicine(int id) { return medicineDAO.delete(id); }
}
