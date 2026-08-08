package com.hospital.dao;

import com.hospital.model.Bill;
import com.hospital.model.BillItem;
import java.util.List;

public interface BillingDAO {
    List<Bill> findAll();
    Bill findById(int billId);
    List<Bill> findByPatientId(int patientId);
    boolean createBill(Bill bill, List<BillItem> items);
    boolean updatePaymentStatus(int billId, String status, String paymentMethod);
    double getTodayRevenue();
    double getTotalRevenue();
    List<BillItem> findItemsByBillId(int billId);
}
