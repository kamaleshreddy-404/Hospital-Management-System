package com.hospital.controller;

import com.hospital.model.*;
import com.hospital.service.HospitalService;
import com.hospital.service.BillingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/pharmacist/dashboard", "/pharmacist/prescriptions", "/pharmacist/medicines"})
public class PharmacistServlet extends HttpServlet {

    private HospitalService hospitalService = new HospitalService();
    private BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/pharmacist/dashboard") || path.equals("/pharmacist/prescriptions")) {
            request.setAttribute("prescriptions", hospitalService.getAllPrescriptions());
            request.setAttribute("lowStock", hospitalService.getLowStockMedicines());
            request.setAttribute("allMedicines", hospitalService.getAllMedicines());
            request.getRequestDispatcher("/WEB-INF/views/pharmacist/dashboard.jsp").forward(request, response);
        } else if (path.equals("/pharmacist/medicines")) {
            request.setAttribute("medicines", hospitalService.getAllMedicines());
            request.getRequestDispatcher("/WEB-INF/views/pharmacist/medicines.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "updateStock": {
                int medId = Integer.parseInt(request.getParameter("medicineId"));
                int stock = Integer.parseInt(request.getParameter("stockQuantity"));
                hospitalService.updateMedicineStock(medId, stock);
                response.sendRedirect(request.getContextPath() + "/pharmacist/medicines?msg=Medicine stock updated!");
                break;
            }
            case "addMedicine": {
                String name = request.getParameter("name");
                String category = request.getParameter("category");
                String manufacturer = request.getParameter("manufacturer");
                double price = Double.parseDouble(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stockQuantity"));
                Date expDate = Date.valueOf(request.getParameter("expiryDate"));

                Medicine m = new Medicine();
                m.setName(name);
                m.setCategory(category);
                m.setManufacturer(manufacturer);
                m.setPrice(price);
                m.setStockQuantity(stock);
                m.setExpiryDate(expDate);

                hospitalService.addMedicine(m);
                response.sendRedirect(request.getContextPath() + "/pharmacist/medicines?msg=New medicine added to pharmacy inventory!");
                break;
            }
            case "generatePharmacyBill": {
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                String[] medNames = request.getParameterValues("itemNames");
                String[] qtys = request.getParameterValues("quantities");
                String[] prices = request.getParameterValues("unitPrices");

                double total = 0.0;
                List<BillItem> items = new ArrayList<>();
                if (medNames != null) {
                    for (int i = 0; i < medNames.length; i++) {
                        if (medNames[i] != null && !medNames[i].trim().isEmpty()) {
                            int q = Integer.parseInt(qtys[i]);
                            double p = Double.parseDouble(prices[i]);
                            items.add(new BillItem(medNames[i], q, p));
                            total += (q * p);
                        }
                    }
                }

                Bill bill = new Bill();
                bill.setPatientId(patientId);
                bill.setConsultationFee(0.0);
                bill.setTestCharges(0.0);
                bill.setMedicineCharges(total);
                bill.setTotalAmount(total);
                bill.setPaymentStatus("Paid");
                bill.setPaymentMethod("Cash");

                billingService.createBill(bill, items);
                response.sendRedirect(request.getContextPath() + "/pharmacist/dashboard?msg=Pharmacy bill generated and stock updated!");
                break;
            }
            default:
                response.sendRedirect(request.getContextPath() + "/pharmacist/dashboard");
                break;
        }
    }
}
