package com.hospital.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Bill {
    private int billId;
    private int patientId;
    private String patientName;
    private Integer appointmentId;
    private double consultationFee;
    private double medicineCharges;
    private double testCharges;
    private double totalAmount;
    private String paymentStatus; // Unpaid, Paid
    private String paymentMethod;
    private Timestamp billDate;
    private List<BillItem> items = new ArrayList<>();

    public Bill() {}

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public Integer getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Integer appointmentId) { this.appointmentId = appointmentId; }

    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }

    public double getMedicineCharges() { return medicineCharges; }
    public void setMedicineCharges(double medicineCharges) { this.medicineCharges = medicineCharges; }

    public double getTestCharges() { return testCharges; }
    public void setTestCharges(double testCharges) { this.testCharges = testCharges; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Timestamp getBillDate() { return billDate; }
    public void setBillDate(Timestamp billDate) { this.billDate = billDate; }

    public List<BillItem> getItems() { return items; }
    public void setItems(List<BillItem> items) { this.items = items; }
}
