package com.hospital.dao;

import com.hospital.model.Doctor;
import java.util.List;

public interface DoctorDAO {
    List<Doctor> findAll();
    Doctor findById(int doctorId);
    Doctor findByUserId(int userId);
    List<Doctor> findByDepartment(int deptId);
    boolean create(Doctor doctor);
    boolean update(Doctor doctor);
    boolean delete(int doctorId);
    int getTotalDoctorsCount();
}
