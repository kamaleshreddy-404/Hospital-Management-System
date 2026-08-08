package com.hospital.model;

import java.sql.Timestamp;

public class Diagnosis {
    private int diagnosisId;
    private int appointmentId;
    private int patientId;
    private String patientName;
    private int doctorId;
    private String doctorName;
    private String symptoms;
    private String diagnosisDetail;
    private String recommendedTests;
    private String doctorNotes;
    private Timestamp createdAt;

    public Diagnosis() {}

    public int getDiagnosisId() { return diagnosisId; }
    public void setDiagnosisId(int diagnosisId) { this.diagnosisId = diagnosisId; }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getDiagnosisDetail() { return diagnosisDetail; }
    public void setDiagnosisDetail(String diagnosisDetail) { this.diagnosisDetail = diagnosisDetail; }

    public String getRecommendedTests() { return recommendedTests; }
    public void setRecommendedTests(String recommendedTests) { this.recommendedTests = recommendedTests; }

    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
