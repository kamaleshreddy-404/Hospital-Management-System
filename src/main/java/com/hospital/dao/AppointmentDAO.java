package com.hospital.dao;

import com.hospital.model.Appointment;
import java.util.List;

public interface AppointmentDAO {
    List<Appointment> findAll();
    Appointment findById(int appointmentId);
    List<Appointment> findByPatientId(int patientId);
    List<Appointment> findByDoctorId(int doctorId);
    List<Appointment> findTodayAppointmentsByDoctor(int doctorId);
    List<Appointment> findTodayAppointmentsAll();
    boolean create(Appointment appointment);
    boolean updateStatus(int appointmentId, String status);
    boolean update(Appointment appointment);
    boolean delete(int appointmentId);
    int getTodayAppointmentsCount();
}
