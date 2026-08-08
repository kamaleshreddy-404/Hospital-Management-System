package com.hospital.dao.impl;

import com.hospital.dao.PrescriptionDAO;
import com.hospital.model.Prescription;
import com.hospital.model.PrescriptionItem;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAOImpl implements PrescriptionDAO {

    private static final String BASE_SELECT = 
        "SELECT p.*, pat.name AS patient_name, doc.name AS doctor_name " +
        "FROM prescriptions p " +
        "JOIN patients pat ON p.patient_id = pat.patient_id " +
        "JOIN doctors doc ON p.doctor_id = doc.doctor_id ";

    @Override
    public List<Prescription> findAll() {
        List<Prescription> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY p.prescribed_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prescription rx = mapPrescription(rs);
                rx.setItems(findItemsByPrescriptionId(rx.getPrescriptionId()));
                list.add(rx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Prescription findById(int prescriptionId) {
        String sql = BASE_SELECT + "WHERE p.prescription_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prescriptionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prescription rx = mapPrescription(rs);
                    rx.setItems(findItemsByPrescriptionId(rx.getPrescriptionId()));
                    return rx;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prescription findByAppointmentId(int appointmentId) {
        String sql = BASE_SELECT + "WHERE p.appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prescription rx = mapPrescription(rs);
                    rx.setItems(findItemsByPrescriptionId(rx.getPrescriptionId()));
                    return rx;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Prescription> findByPatientId(int patientId) {
        List<Prescription> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE p.patient_id = ? ORDER BY p.prescribed_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prescription rx = mapPrescription(rs);
                    rx.setItems(findItemsByPrescriptionId(rx.getPrescriptionId()));
                    list.add(rx);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Prescription> findByDoctorId(int doctorId) {
        List<Prescription> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE p.doctor_id = ? ORDER BY p.prescribed_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prescription rx = mapPrescription(rs);
                    rx.setItems(findItemsByPrescriptionId(rx.getPrescriptionId()));
                    list.add(rx);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean createPrescription(Prescription rx, List<PrescriptionItem> items) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sqlRx = "INSERT INTO prescriptions (appointment_id, patient_id, doctor_id, prescribed_date, advice) VALUES (?, ?, ?, ?, ?)";
            int rxId = 0;
            try (PreparedStatement ps = conn.prepareStatement(sqlRx, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, rx.getAppointmentId());
                ps.setInt(2, rx.getPatientId());
                ps.setInt(3, rx.getDoctorId());
                ps.setDate(4, rx.getPrescribedDate());
                ps.setString(5, rx.getAdvice());
                ps.executeUpdate();
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rxId = generatedKeys.getInt(1);
                        rx.setPrescriptionId(rxId);
                    }
                }
            }

            if (rxId > 0 && items != null && !items.isEmpty()) {
                String sqlItem = "INSERT INTO prescription_items (prescription_id, medicine_id, dosage, frequency, duration, instructions) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement psItem = conn.prepareStatement(sqlItem)) {
                    for (PrescriptionItem item : items) {
                        psItem.setInt(1, rxId);
                        psItem.setInt(2, item.getMedicineId());
                        psItem.setString(3, item.getDosage());
                        psItem.setString(4, item.getFrequency());
                        psItem.setString(5, item.getDuration());
                        psItem.setString(6, item.getInstructions());
                        psItem.addBatch();
                    }
                    psItem.executeBatch();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return false;
    }

    @Override
    public List<PrescriptionItem> findItemsByPrescriptionId(int prescriptionId) {
        List<PrescriptionItem> list = new ArrayList<>();
        String sql = "SELECT pi.*, m.name AS medicine_name FROM prescription_items pi JOIN medicines m ON pi.medicine_id = m.medicine_id WHERE pi.prescription_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prescriptionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PrescriptionItem item = new PrescriptionItem();
                    item.setItemId(rs.getInt("item_id"));
                    item.setPrescriptionId(rs.getInt("prescription_id"));
                    item.setMedicineId(rs.getInt("medicine_id"));
                    item.setMedicineName(rs.getString("medicine_name"));
                    item.setDosage(rs.getString("dosage"));
                    item.setFrequency(rs.getString("frequency"));
                    item.setDuration(rs.getString("duration"));
                    item.setInstructions(rs.getString("instructions"));
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Prescription mapPrescription(ResultSet rs) throws SQLException {
        Prescription rx = new Prescription();
        rx.setPrescriptionId(rs.getInt("prescription_id"));
        rx.setAppointmentId(rs.getInt("appointment_id"));
        rx.setPatientId(rs.getInt("patient_id"));
        rx.setPatientName(rs.getString("patient_name"));
        rx.setDoctorId(rs.getInt("doctor_id"));
        rx.setDoctorName(rs.getString("doctor_name"));
        rx.setPrescribedDate(rs.getDate("prescribed_date"));
        rx.setAdvice(rs.getString("advice"));
        return rx;
    }
}
