package com.hospital.model;

public class Department {
    private int deptId;
    private String deptName;
    private String description;
    private String headDoctorName;

    public Department() {}

    public Department(int deptId, String deptName, String description, String headDoctorName) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.description = description;
        this.headDoctorName = headDoctorName;
    }

    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHeadDoctorName() { return headDoctorName; }
    public void setHeadDoctorName(String headDoctorName) { this.headDoctorName = headDoctorName; }
}
