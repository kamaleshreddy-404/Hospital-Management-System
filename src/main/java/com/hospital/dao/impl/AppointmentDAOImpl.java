package com.hospital.dao.impl;

import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Appointment;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {

    private static final String BASE_SELECT = 
        "SELECT a.*, p.name AS patient_name, p.phone AS patient_phone, d.name AS doctor_name, d.specialization AS doctor_specialization " +
        "FROM appointments a " +
        "JOIN patients p ON a.patient_id = p.patient_id " +
        "JOIN doctors d ON a.doctor_id = d.doctor_id ";

    @Override
    public List<Appointment> findAll() {
        List<Appointment> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY a.appointment_date DESC, a.appointment_time ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Appointment findById(int appointmentId) {
        String sql = BASE_SELECT + "WHERE a.appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAppointment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Appointment> findByPatientId(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE a.patient_id = ? ORDER BY a.appointment_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Appointment> findByDoctorId(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE a.doctor_id = ? ORDER BY a.appointment_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Appointment> findTodayAppointmentsByDoctor(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE a.doctor_id = ? AND a.appointment_date = CURRENT_DATE() ORDER BY a.appointment_time ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAppointment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Appointment> findTodayAppointmentsAll() {
        List<Appointment> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE a.appointment_date = CURRENT_DATE() ORDER BY a.appointment_time ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean create(Appointment appointment) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, symptoms, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setDate(3, appointment.getAppointmentDate());
            ps.setString(4, appointment.getAppointmentTime());
            ps.setString(5, appointment.getSymptoms());
            ps.setString(6, appointment.getStatus() != null ? appointment.getStatus() : "Pending");
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        appointment.setAppointmentId(rs.getInt(1));
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
    public boolean updateStatus(int appointmentId, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Appointment appointment) {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, appointment_time = ?, symptoms = ?, status = ? WHERE appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setDate(3, appointment.getAppointmentDate());
            ps.setString(4, appointment.getAppointmentTime());
            ps.setString(5, appointment.getSymptoms());
            ps.setString(6, appointment.getStatus());
            ps.setInt(7, appointment.getAppointmentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getTodayAppointmentsCount() {
        String sql = "SELECT COUNT(*) FROM appointments WHERE appointment_date = CURRENT_DATE()";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Appointment mapAppointment(ResultSet rs) throws SQLException {
        Appointment app = new Appointment();
        app.setAppointmentId(rs.getInt("appointment_id"));
        app.setPatientId(rs.getInt("patient_id"));
        app.setPatientName(rs.getString("patient_name"));
        app.setPatientPhone(rs.getString("patient_phone"));
        app.setDoctorId(rs.getInt("doctor_id"));
        app.setDoctorName(rs.getString("doctor_name"));
        app.setDoctorSpecialization(rs.getString("doctor_specialization"));
        app.setAppointmentDate(rs.getDate("appointment_date"));
        app.setAppointmentTime(rs.getString("appointment_time"));
        app.setSymptoms(rs.getString("symptoms"));
        app.setStatus(rs.getString("status"));
        app.setCreatedAt(rs.getTimestamp("created_at"));
        return app;
    }
}
