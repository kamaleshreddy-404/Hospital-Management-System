package com.hospital.dao.impl;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patient_id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapPatient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Patient findById(int patientId) {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPatient(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Patient findByUserId(int userId) {
        String sql = "SELECT * FROM patients WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPatient(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> searchPatients(String keyword) {
        List<Patient> list = new ArrayList<>();
        String query = "%" + keyword + "%";
        String sql = "SELECT * FROM patients WHERE name LIKE ? OR phone LIKE ? OR email LIKE ? ORDER BY name ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, query);
            ps.setString(2, query);
            ps.setString(3, query);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapPatient(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean create(Patient patient) {
        String sql = "INSERT INTO patients (user_id, name, gender, age, blood_group, phone, address, email, emergency_contact) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (patient.getUserId() != null && patient.getUserId() > 0) {
                ps.setInt(1, patient.getUserId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString(2, patient.getName());
            ps.setString(3, patient.getGender());
            ps.setInt(4, patient.getAge());
            ps.setString(5, patient.getBloodGroup());
            ps.setString(6, patient.getPhone());
            ps.setString(7, patient.getAddress());
            ps.setString(8, patient.getEmail());
            ps.setString(9, patient.getEmergencyContact());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        patient.setPatientId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Patient patient) {
        String sql = "UPDATE patients SET name = ?, gender = ?, age = ?, blood_group = ?, phone = ?, address = ?, email = ?, emergency_contact = ? WHERE patient_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getName());
            ps.setString(2, patient.getGender());
            ps.setInt(3, patient.getAge());
            ps.setString(4, patient.getBloodGroup());
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getAddress());
            ps.setString(7, patient.getEmail());
            ps.setString(8, patient.getEmergencyContact());
            ps.setInt(9, patient.getPatientId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int patientId) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getTotalPatientsCount() {
        String sql = "SELECT COUNT(*) FROM patients";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Patient mapPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setPatientId(rs.getInt("patient_id"));
        int uid = rs.getInt("user_id");
        p.setUserId(rs.wasNull() ? null : uid);
        p.setName(rs.getString("name"));
        p.setGender(rs.getString("gender"));
        p.setAge(rs.getInt("age"));
        p.setBloodGroup(rs.getString("blood_group"));
        p.setPhone(rs.getString("phone"));
        p.setAddress(rs.getString("address"));
        p.setEmail(rs.getString("email"));
        p.setEmergencyContact(rs.getString("emergency_contact"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        return p;
    }
}
