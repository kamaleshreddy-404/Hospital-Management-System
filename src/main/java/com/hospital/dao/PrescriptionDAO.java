package com.hospital.dao;

import com.hospital.model.Prescription;
import com.hospital.model.PrescriptionItem;
import java.util.List;

public interface PrescriptionDAO {
    List<Prescription> findAll();
    Prescription findById(int prescriptionId);
    Prescription findByAppointmentId(int appointmentId);
    List<Prescription> findByPatientId(int patientId);
    List<Prescription> findByDoctorId(int doctorId);
    boolean createPrescription(Prescription prescription, List<PrescriptionItem> items);
    List<PrescriptionItem> findItemsByPrescriptionId(int prescriptionId);
}
