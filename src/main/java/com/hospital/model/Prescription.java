package com.hospital.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private int prescriptionId;
    private int appointmentId;
    private int patientId;
    private String patientName;
    private int doctorId;
    private String doctorName;
    private Date prescribedDate;
    private String advice;
    private List<PrescriptionItem> items = new ArrayList<>();

    public Prescription() {}

    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }

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

    public Date getPrescribedDate() { return prescribedDate; }
    public void setPrescribedDate(Date prescribedDate) { this.prescribedDate = prescribedDate; }

    public String getAdvice() { return advice; }
    public void setAdvice(String advice) { this.advice = advice; }

    public List<PrescriptionItem> getItems() { return items; }
    public void setItems(List<PrescriptionItem> items) { this.items = items; }
}
