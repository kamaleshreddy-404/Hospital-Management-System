package com.hospital.dao.impl;

import com.hospital.dao.MedicineDAO;
import com.hospital.model.Medicine;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAOImpl implements MedicineDAO {

    @Override
    public List<Medicine> findAll() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines ORDER BY name ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapMedicine(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Medicine findById(int medicineId) {
        String sql = "SELECT * FROM medicines WHERE medicine_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicineId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapMedicine(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medicine> findLowStockMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines WHERE stock_quantity <= 50 ORDER BY stock_quantity ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapMedicine(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean create(Medicine medicine) {
        String sql = "INSERT INTO medicines (name, category, manufacturer, price, stock_quantity, expiry_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicine.getName());
            ps.setString(2, medicine.getCategory());
            ps.setString(3, medicine.getManufacturer());
            ps.setDouble(4, medicine.getPrice());
            ps.setInt(5, medicine.getStockQuantity());
            ps.setDate(6, medicine.getExpiryDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Medicine medicine) {
        String sql = "UPDATE medicines SET name = ?, category = ?, manufacturer = ?, price = ?, stock_quantity = ?, expiry_date = ? WHERE medicine_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medicine.getName());
            ps.setString(2, medicine.getCategory());
            ps.setString(3, medicine.getManufacturer());
            ps.setDouble(4, medicine.getPrice());
            ps.setInt(5, medicine.getStockQuantity());
            ps.setDate(6, medicine.getExpiryDate());
            ps.setInt(7, medicine.getMedicineId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStock(int medicineId, int newStock) {
        String sql = "UPDATE medicines SET stock_quantity = ? WHERE medicine_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, medicineId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean reduceStock(int medicineId, int quantity) {
        String sql = "UPDATE medicines SET stock_quantity = stock_quantity - ? WHERE medicine_id = ? AND stock_quantity >= ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, medicineId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int medicineId) {
        String sql = "DELETE FROM medicines WHERE medicine_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicineId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getTotalMedicinesCount() {
        String sql = "SELECT COUNT(*) FROM medicines";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Medicine mapMedicine(ResultSet rs) throws SQLException {
        Medicine m = new Medicine();
        m.setMedicineId(rs.getInt("medicine_id"));
        m.setName(rs.getString("name"));
        m.setCategory(rs.getString("category"));
        m.setManufacturer(rs.getString("manufacturer"));
        m.setPrice(rs.getDouble("price"));
        m.setStockQuantity(rs.getInt("stock_quantity"));
        m.setExpiryDate(rs.getDate("expiry_date"));
        m.setCreatedAt(rs.getTimestamp("created_at"));
        return m;
    }
}
