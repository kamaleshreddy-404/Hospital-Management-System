package com.hospital.dao.impl;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {

    @Override
    public List<Doctor> findAll() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT d.*, dept.dept_name FROM doctors d JOIN departments dept ON d.dept_id = dept.dept_id ORDER BY d.name ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapDoctor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Doctor findById(int doctorId) {
        String sql = "SELECT d.*, dept.dept_name FROM doctors d JOIN departments dept ON d.dept_id = dept.dept_id WHERE d.doctor_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDoctor(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Doctor findByUserId(int userId) {
        String sql = "SELECT d.*, dept.dept_name FROM doctors d JOIN departments dept ON d.dept_id = dept.dept_id WHERE d.user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDoctor(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> findByDepartment(int deptId) {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT d.*, dept.dept_name FROM doctors d JOIN departments dept ON d.dept_id = dept.dept_id WHERE d.dept_id = ? ORDER BY d.name ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapDoctor(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean create(Doctor doctor) {
        String sql = "INSERT INTO doctors (user_id, name, qualification, specialization, experience_years, phone, email, dept_id, consultation_fee, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctor.getUserId());
            ps.setString(2, doctor.getName());
            ps.setString(3, doctor.getQualification());
            ps.setString(4, doctor.getSpecialization());
            ps.setInt(5, doctor.getExperienceYears());
            ps.setString(6, doctor.getPhone());
            ps.setString(7, doctor.getEmail());
            ps.setInt(8, doctor.getDeptId());
            ps.setDouble(9, doctor.getConsultationFee());
            ps.setString(10, doctor.getStatus() != null ? doctor.getStatus() : "Active");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Doctor doctor) {
        String sql = "UPDATE doctors SET name = ?, qualification = ?, specialization = ?, experience_years = ?, phone = ?, email = ?, dept_id = ?, consultation_fee = ?, status = ? WHERE doctor_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getQualification());
            ps.setString(3, doctor.getSpecialization());
            ps.setInt(4, doctor.getExperienceYears());
            ps.setString(5, doctor.getPhone());
            ps.setString(6, doctor.getEmail());
            ps.setInt(7, doctor.getDeptId());
            ps.setDouble(8, doctor.getConsultationFee());
            ps.setString(9, doctor.getStatus());
            ps.setInt(10, doctor.getDoctorId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int doctorId) {
        String sql = "DELETE FROM doctors WHERE doctor_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getTotalDoctorsCount() {
        String sql = "SELECT COUNT(*) FROM doctors";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Doctor mapDoctor(ResultSet rs) throws SQLException {
        Doctor doc = new Doctor();
        doc.setDoctorId(rs.getInt("doctor_id"));
        doc.setUserId(rs.getInt("user_id"));
        doc.setName(rs.getString("name"));
        doc.setQualification(rs.getString("qualification"));
        doc.setSpecialization(rs.getString("specialization"));
        doc.setExperienceYears(rs.getInt("experience_years"));
        doc.setPhone(rs.getString("phone"));
        doc.setEmail(rs.getString("email"));
        doc.setDeptId(rs.getInt("dept_id"));
        doc.setDeptName(rs.getString("dept_name"));
        doc.setConsultationFee(rs.getDouble("consultation_fee"));
        doc.setStatus(rs.getString("status"));
        return doc;
    }
}
