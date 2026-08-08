package com.hospital.dao;

import com.hospital.model.Department;
import java.util.List;

public interface DepartmentDAO {
    List<Department> findAll();
    Department findById(int deptId);
    boolean create(Department department);
    boolean update(Department department);
    boolean delete(int deptId);
    int getTotalDepartmentsCount();
}
