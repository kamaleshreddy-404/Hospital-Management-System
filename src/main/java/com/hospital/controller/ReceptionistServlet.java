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

@WebServlet(urlPatterns = {"/receptionist/dashboard", "/receptionist/register-patient", "/receptionist/appointments", "/receptionist/billing", "/receptionist/slip"})
public class ReceptionistServlet extends HttpServlet {

    private HospitalService hospitalService = new HospitalService();
    private BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/receptionist/dashboard") || path.equals("/receptionist/appointments")) {
            request.setAttribute("patients", hospitalService.getAllPatients());
            request.setAttribute("doctors", hospitalService.getAllDoctors());
            request.setAttribute("appointments", hospitalService.getAllAppointments());
            request.getRequestDispatcher("/WEB-INF/views/receptionist/dashboard.jsp").forward(request, response);
        } else if (path.equals("/receptionist/register-patient")) {
            request.getRequestDispatcher("/WEB-INF/views/receptionist/register-patient.jsp").forward(request, response);
        } else if (path.equals("/receptionist/billing")) {
            request.setAttribute("patients", hospitalService.getAllPatients());
            request.setAttribute("appointments", hospitalService.getAllAppointments());
            request.setAttribute("bills", billingService.getAllBills());
            request.getRequestDispatcher("/WEB-INF/views/receptionist/billing.jsp").forward(request, response);
        } else if (path.equals("/receptionist/slip")) {
            int apptId = Integer.parseInt(request.getParameter("appointmentId"));
            Appointment appt = hospitalService.getAppointmentById(apptId);
            Patient patient = hospitalService.getPatientById(appt.getPatientId());
            Doctor doctor = hospitalService.getDoctorById(appt.getDoctorId());

            request.setAttribute("appointment", appt);
            request.setAttribute("patient", patient);
            request.setAttribute("doctor", doctor);
            request.getRequestDispatcher("/WEB-INF/views/receptionist/slip.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "registerPatient": {
                String name = request.getParameter("name");
                String gender = request.getParameter("gender");
                int age = Integer.parseInt(request.getParameter("age"));
                String bloodGroup = request.getParameter("bloodGroup");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String email = request.getParameter("email");
                String emergency = request.getParameter("emergencyContact");

                Patient p = new Patient();
                p.setName(name);
                p.setGender(gender);
                p.setAge(age);
                p.setBloodGroup(bloodGroup);
                p.setPhone(phone);
                p.setAddress(address);
                p.setEmail(email);
                p.setEmergencyContact(emergency);

                hospitalService.addPatient(p);
                response.sendRedirect(request.getContextPath() + "/receptionist/dashboard?msg=Patient registered successfully!");
                break;
            }
            case "bookAppointment": {
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                int doctorId = Integer.parseInt(request.getParameter("doctorId"));
                Date date = Date.valueOf(request.getParameter("appointmentDate"));
                String time = request.getParameter("appointmentTime");
                String symptoms = request.getParameter("symptoms");

                Appointment app = new Appointment();
                app.setPatientId(patientId);
                app.setDoctorId(doctorId);
                app.setAppointmentDate(date);
                app.setAppointmentTime(time);
                app.setSymptoms(symptoms);
                app.setStatus("Confirmed");

                hospitalService.bookAppointment(app);
                response.sendRedirect(request.getContextPath() + "/receptionist/dashboard?msg=Appointment booked successfully!");
                break;
            }
            case "updateAppointmentStatus": {
                int apptId = Integer.parseInt(request.getParameter("appointmentId"));
                String status = request.getParameter("status");
                hospitalService.updateAppointmentStatus(apptId, status);
                response.sendRedirect(request.getContextPath() + "/receptionist/dashboard?msg=Appointment status updated!");
                break;
            }
            case "generateBill": {
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                String apptIdStr = request.getParameter("appointmentId");
                double consultFee = Double.parseDouble(request.getParameter("consultationFee"));
                double testCharges = Double.parseDouble(request.getParameter("testCharges"));
                double medCharges = Double.parseDouble(request.getParameter("medicineCharges"));
                String paymentStatus = request.getParameter("paymentStatus");
                String paymentMethod = request.getParameter("paymentMethod");

                Integer apptId = (apptIdStr != null && !apptIdStr.isEmpty()) ? Integer.parseInt(apptIdStr) : null;
                double total = consultFee + testCharges + medCharges;

                Bill bill = new Bill();
                bill.setPatientId(patientId);
                bill.setAppointmentId(apptId);
                bill.setConsultationFee(consultFee);
                bill.setTestCharges(testCharges);
                bill.setMedicineCharges(medCharges);
                bill.setTotalAmount(total);
                bill.setPaymentStatus(paymentStatus);
                bill.setPaymentMethod(paymentMethod);

                List<BillItem> items = new ArrayList<>();
                if (consultFee > 0) items.add(new BillItem("Doctor Consultation Fee", 1, consultFee));
                if (testCharges > 0) items.add(new BillItem("Diagnostic & Lab Tests", 1, testCharges));
                if (medCharges > 0) items.add(new BillItem("Prescription Pharmacy Charges", 1, medCharges));

                billingService.createBill(bill, items);
                response.sendRedirect(request.getContextPath() + "/receptionist/billing?msg=Invoice generated successfully!");
                break;
            }
            default:
                response.sendRedirect(request.getContextPath() + "/receptionist/dashboard");
                break;
        }
    }
}
