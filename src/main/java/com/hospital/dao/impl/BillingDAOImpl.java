package com.hospital.dao.impl;

import com.hospital.dao.BillingDAO;
import com.hospital.model.Bill;
import com.hospital.model.BillItem;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAOImpl implements BillingDAO {

    private static final String BASE_SELECT = 
        "SELECT b.*, p.name AS patient_name " +
        "FROM bills b " +
        "JOIN patients p ON b.patient_id = p.patient_id ";

    @Override
    public List<Bill> findAll() {
        List<Bill> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY b.bill_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Bill bill = mapBill(rs);
                bill.setItems(findItemsByBillId(bill.getBillId()));
                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Bill findById(int billId) {
        String sql = BASE_SELECT + "WHERE b.bill_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bill bill = mapBill(rs);
                    bill.setItems(findItemsByBillId(bill.getBillId()));
                    return bill;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bill> findByPatientId(int patientId) {
        List<Bill> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE b.patient_id = ? ORDER BY b.bill_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bill bill = mapBill(rs);
                    bill.setItems(findItemsByBillId(bill.getBillId()));
                    list.add(bill);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean createBill(Bill bill, List<BillItem> items) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sqlBill = "INSERT INTO bills (patient_id, appointment_id, consultation_fee, medicine_charges, test_charges, total_amount, payment_status, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            int billId = 0;
            try (PreparedStatement ps = conn.prepareStatement(sqlBill, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, bill.getPatientId());
                if (bill.getAppointmentId() != null && bill.getAppointmentId() > 0) {
                    ps.setInt(2, bill.getAppointmentId());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                ps.setDouble(3, bill.getConsultationFee());
                ps.setDouble(4, bill.getMedicineCharges());
                ps.setDouble(5, bill.getTestCharges());
                ps.setDouble(6, bill.getTotalAmount());
                ps.setString(7, bill.getPaymentStatus() != null ? bill.getPaymentStatus() : "Paid");
                ps.setString(8, bill.getPaymentMethod() != null ? bill.getPaymentMethod() : "Cash");
                ps.executeUpdate();
                try (ResultSet rsKey = ps.getGeneratedKeys()) {
                    if (rsKey.next()) {
                        billId = rsKey.getInt(1);
                        bill.setBillId(billId);
                    }
                }
            }

            if (billId > 0 && items != null && !items.isEmpty()) {
                String sqlItem = "INSERT INTO bill_items (bill_id, item_name, quantity, unit_price, total_price) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psItem = conn.prepareStatement(sqlItem)) {
                    for (BillItem item : items) {
                        psItem.setInt(1, billId);
                        psItem.setString(2, item.getItemName());
                        psItem.setInt(3, item.getQuantity());
                        psItem.setDouble(4, item.getUnitPrice());
                        psItem.setDouble(5, item.getTotalPrice());
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
    public boolean updatePaymentStatus(int billId, String status, String paymentMethod) {
        String sql = "UPDATE bills SET payment_status = ?, payment_method = ? WHERE bill_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, paymentMethod);
            ps.setInt(3, billId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double getTodayRevenue() {
        String sql = "SELECT SUM(total_amount) FROM bills WHERE payment_status = 'Paid' AND DATE(bill_date) = CURRENT_DATE()";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public double getTotalRevenue() {
        String sql = "SELECT SUM(total_amount) FROM bills WHERE payment_status = 'Paid'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public List<BillItem> findItemsByBillId(int billId) {
        List<BillItem> list = new ArrayList<>();
        String sql = "SELECT * FROM bill_items WHERE bill_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BillItem item = new BillItem();
                    item.setBillItemId(rs.getInt("bill_item_id"));
                    item.setBillId(rs.getInt("bill_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getDouble("unit_price"));
                    item.setTotalPrice(rs.getDouble("total_price"));
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Bill mapBill(ResultSet rs) throws SQLException {
        Bill b = new Bill();
        b.setBillId(rs.getInt("bill_id"));
        b.setPatientId(rs.getInt("patient_id"));
        b.setPatientName(rs.getString("patient_name"));
        int apptId = rs.getInt("appointment_id");
        b.setAppointmentId(rs.wasNull() ? null : apptId);
        b.setConsultationFee(rs.getDouble("consultation_fee"));
        b.setMedicineCharges(rs.getDouble("medicine_charges"));
        b.setTestCharges(rs.getDouble("test_charges"));
        b.setTotalAmount(rs.getDouble("total_amount"));
        b.setPaymentStatus(rs.getString("payment_status"));
        b.setPaymentMethod(rs.getString("payment_method"));
        b.setBillDate(rs.getTimestamp("bill_date"));
        return b;
    }
}
