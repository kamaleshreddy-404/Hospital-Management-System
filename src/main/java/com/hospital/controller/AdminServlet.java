package com.hospital.controller;

import com.hospital.service.HospitalService;
import com.hospital.service.BillingService;
import com.hospital.model.*;
import com.hospital.dao.UserDAO;
import com.hospital.dao.impl.UserDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/admin/dashboard", "/admin/doctors", "/admin/patients", "/admin/departments", "/admin/medicines", "/admin/appointments", "/admin/billing", "/admin/reports", "/admin/users"})
public class AdminServlet extends HttpServlet {

    private HospitalService hospitalService = new HospitalService();
    private BillingService billingService = new BillingService();
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/admin/dashboard")) {
            Map<String, Object> stats = hospitalService.getAdminDashboardStats();
            request.setAttribute("stats", stats);
            request.setAttribute("recentAppointments", hospitalService.getTodayAppointmentsAll());
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
        } else if (path.equals("/admin/doctors")) {
            request.setAttribute("doctors", hospitalService.getAllDoctors());
            request.setAttribute("departments", hospitalService.getAllDepartments());
            request.getRequestDispatcher("/WEB-INF/views/admin/doctors.jsp").forward(request, response);
        } else if (path.equals("/admin/patients")) {
            request.setAttribute("patients", hospitalService.getAllPatients());
            request.getRequestDispatcher("/WEB-INF/views/admin/patients.jsp").forward(request, response);
        } else if (path.equals("/admin/departments")) {
            request.setAttribute("departments", hospitalService.getAllDepartments());
            request.getRequestDispatcher("/WEB-INF/views/admin/departments.jsp").forward(request, response);
        } else if (path.equals("/admin/medicines")) {
            request.setAttribute("medicines", hospitalService.getAllMedicines());
            request.getRequestDispatcher("/WEB-INF/views/admin/medicines.jsp").forward(request, response);
        } else if (path.equals("/admin/appointments")) {
            request.setAttribute("appointments", hospitalService.getAllAppointments());
            request.getRequestDispatcher("/WEB-INF/views/admin/appointments.jsp").forward(request, response);
        } else if (path.equals("/admin/billing")) {
            request.setAttribute("bills", billingService.getAllBills());
            request.getRequestDispatcher("/WEB-INF/views/admin/billing.jsp").forward(request, response);
        } else if (path.equals("/admin/reports")) {
            Map<String, Object> stats = hospitalService.getAdminDashboardStats();
            request.setAttribute("stats", stats);
            request.setAttribute("lowStockMedicines", hospitalService.getLowStockMedicines());
            request.getRequestDispatcher("/WEB-INF/views/admin/reports.jsp").forward(request, response);
        } else if (path.equals("/admin/users")) {
            request.setAttribute("users", userDAO.findAllUsers());
            request.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "addDoctor": {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String qualification = request.getParameter("qualification");
                String specialization = request.getParameter("specialization");
                int exp = Integer.parseInt(request.getParameter("experienceYears"));
                int deptId = Integer.parseInt(request.getParameter("deptId"));
                double fee = Double.parseDouble(request.getParameter("consultationFee"));

                // Create user account for doctor
                User u = new User(0, name, email, "doctor123", phone, 2);
                if (userDAO.createUser(u)) {
                    Doctor doc = new Doctor();
                    doc.setUserId(u.getUserId());
                    doc.setName(name);
                    doc.setQualification(qualification);
                    doc.setSpecialization(specialization);
                    doc.setExperienceYears(exp);
                    doc.setPhone(phone);
                    doc.setEmail(email);
                    doc.setDeptId(deptId);
                    doc.setConsultationFee(fee);
                    doc.setStatus("Active");
                    hospitalService.addDoctor(doc);
                }
                response.sendRedirect(request.getContextPath() + "/admin/doctors?msg=Doctor added successfully");
                break;
            }
            case "deleteDoctor": {
                int id = Integer.parseInt(request.getParameter("doctorId"));
                hospitalService.deleteDoctor(id);
                response.sendRedirect(request.getContextPath() + "/admin/doctors?msg=Doctor deleted successfully");
                break;
            }
            case "addDepartment": {
                String deptName = request.getParameter("deptName");
                String description = request.getParameter("description");
                String headDoctorName = request.getParameter("headDoctorName");
                Department d = new Department(0, deptName, description, headDoctorName);
                hospitalService.addDepartment(d);
                response.sendRedirect(request.getContextPath() + "/admin/departments?msg=Department created successfully");
                break;
            }
            case "deleteDepartment": {
                int id = Integer.parseInt(request.getParameter("deptId"));
                hospitalService.deleteDepartment(id);
                response.sendRedirect(request.getContextPath() + "/admin/departments?msg=Department deleted successfully");
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
                response.sendRedirect(request.getContextPath() + "/admin/medicines?msg=Medicine added to stock successfully");
                break;
            }
            case "deleteMedicine": {
                int id = Integer.parseInt(request.getParameter("medicineId"));
                hospitalService.deleteMedicine(id);
                response.sendRedirect(request.getContextPath() + "/admin/medicines?msg=Medicine removed successfully");
                break;
            }
            default:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
        }
    }
}
