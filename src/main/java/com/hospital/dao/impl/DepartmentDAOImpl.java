package com.hospital.dao.impl;

import com.hospital.dao.DepartmentDAO;
import com.hospital.model.Department;
import com.hospital.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {

    @Override
    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY dept_name ASC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapDepartment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Department findById(int deptId) {
        String sql = "SELECT * FROM departments WHERE dept_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDepartment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Department department) {
        String sql = "INSERT INTO departments (dept_name, description, head_doctor_name) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department.getDeptName());
            ps.setString(2, department.getDescription());
            ps.setString(3, department.getHeadDoctorName());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Department department) {
        String sql = "UPDATE departments SET dept_name = ?, description = ?, head_doctor_name = ? WHERE dept_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department.getDeptName());
            ps.setString(2, department.getDescription());
            ps.setString(3, department.getHeadDoctorName());
            ps.setInt(4, department.getDeptId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int deptId) {
        String sql = "DELETE FROM departments WHERE dept_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deptId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getTotalDepartmentsCount() {
        String sql = "SELECT COUNT(*) FROM departments";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setDeptId(rs.getInt("dept_id"));
        d.setDeptName(rs.getString("dept_name"));
        d.setDescription(rs.getString("description"));
        d.setHeadDoctorName(rs.getString("head_doctor_name"));
        return d;
    }
}
