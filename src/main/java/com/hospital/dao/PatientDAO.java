package com.hospital.dao;

import com.hospital.model.Patient;
import java.util.List;

public interface PatientDAO {
    List<Patient> findAll();
    Patient findById(int patientId);
    Patient findByUserId(int userId);
    List<Patient> searchPatients(String keyword);
    boolean create(Patient patient);
    boolean update(Patient patient);
    boolean delete(int patientId);
    int getTotalPatientsCount();
}
