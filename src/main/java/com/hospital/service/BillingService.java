package com.hospital.service;

import com.hospital.dao.BillingDAO;
import com.hospital.dao.MedicineDAO;
import com.hospital.dao.impl.BillingDAOImpl;
import com.hospital.dao.impl.MedicineDAOImpl;
import com.hospital.model.Bill;
import com.hospital.model.BillItem;

import java.util.List;

public class BillingService {

    private BillingDAO billingDAO = new BillingDAOImpl();
    private MedicineDAO medicineDAO = new MedicineDAOImpl();

    public List<Bill> getAllBills() {
        return billingDAO.findAll();
    }

    public Bill getBillById(int billId) {
        return billingDAO.findById(billId);
    }

    public List<Bill> getBillsByPatient(int patientId) {
        return billingDAO.findByPatientId(patientId);
    }

    public boolean createBill(Bill bill, List<BillItem> items) {
        boolean created = billingDAO.createBill(bill, items);
        if (created && items != null) {
            // Automatically reduce medicine stock for billed medicine items if applicable
            for (BillItem item : items) {
                // If item matches a medicine name or medicine item, attempt stock deduction
                List<com.hospital.model.Medicine> medicines = medicineDAO.findAll();
                for (com.hospital.model.Medicine med : medicines) {
                    if (med.getName().equalsIgnoreCase(item.getItemName())) {
                        medicineDAO.reduceStock(med.getMedicineId(), item.getQuantity());
                        break;
                    }
                }
            }
        }
        return created;
    }

    public boolean markBillPaid(int billId, String paymentMethod) {
        return billingDAO.updatePaymentStatus(billId, "Paid", paymentMethod);
    }
}
