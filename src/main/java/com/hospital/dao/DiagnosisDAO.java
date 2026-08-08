package com.hospital.dao;

import com.hospital.model.Diagnosis;

public interface DiagnosisDAO {
    Diagnosis findByAppointmentId(int appointmentId);
    Diagnosis findById(int diagnosisId);
    boolean create(Diagnosis diagnosis);
    boolean update(Diagnosis diagnosis);
}
