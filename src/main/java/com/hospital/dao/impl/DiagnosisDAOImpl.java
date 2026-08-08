package com.hospital.dao.impl;

import com.hospital.dao.DiagnosisDAO;
import com.hospital.model.Diagnosis;
import com.hospital.util.DBUtil;

import java.sql.*;

public class DiagnosisDAOImpl implements DiagnosisDAO {

    private static final String BASE_SELECT = 
        "SELECT diag.*, p.name AS patient_name, d.name AS doctor_name " +
        "FROM diagnosis diag " +
        "JOIN patients p ON diag.patient_id = p.patient_id " +
        "JOIN doctors d ON diag.doctor_id = d.doctor_id ";

    @Override
    public Diagnosis findByAppointmentId(int appointmentId) {
        String sql = BASE_SELECT + "WHERE diag.appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDiagnosis(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Diagnosis findById(int diagnosisId) {
        String sql = BASE_SELECT + "WHERE diag.diagnosis_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, diagnosisId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDiagnosis(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Diagnosis diagnosis) {
        String sql = "INSERT INTO diagnosis (appointment_id, patient_id, doctor_id, symptoms, diagnosis_detail, recommended_tests, doctor_notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, diagnosis.getAppointmentId());
            ps.setInt(2, diagnosis.getPatientId());
            ps.setInt(3, diagnosis.getDoctorId());
            ps.setString(4, diagnosis.getSymptoms());
            ps.setString(5, diagnosis.getDiagnosisDetail());
            ps.setString(6, diagnosis.getRecommendedTests());
            ps.setString(7, diagnosis.getDoctorNotes());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Diagnosis diagnosis) {
        String sql = "UPDATE diagnosis SET symptoms = ?, diagnosis_detail = ?, recommended_tests = ?, doctor_notes = ? WHERE diagnosis_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, diagnosis.getSymptoms());
            ps.setString(2, diagnosis.getDiagnosisDetail());
            ps.setString(3, diagnosis.getRecommendedTests());
            ps.setString(4, diagnosis.getDoctorNotes());
            ps.setInt(5, diagnosis.getDiagnosisId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Diagnosis mapDiagnosis(ResultSet rs) throws SQLException {
        Diagnosis diag = new Diagnosis();
        diag.setDiagnosisId(rs.getInt("diagnosis_id"));
        diag.setAppointmentId(rs.getInt("appointment_id"));
        diag.setPatientId(rs.getInt("patient_id"));
        diag.setPatientName(rs.getString("patient_name"));
        diag.setDoctorId(rs.getInt("doctor_id"));
        diag.setDoctorName(rs.getString("doctor_name"));
        diag.setSymptoms(rs.getString("symptoms"));
        diag.setDiagnosisDetail(rs.getString("diagnosis_detail"));
        diag.setRecommendedTests(rs.getString("recommended_tests"));
        diag.setDoctorNotes(rs.getString("doctor_notes"));
        diag.setCreatedAt(rs.getTimestamp("created_at"));
        return diag;
    }
}
