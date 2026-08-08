package com.hospital.controller;

import com.hospital.model.*;
import com.hospital.service.HospitalService;
import com.hospital.service.BillingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/patient/dashboard", "/patient/profile", "/patient/book-appointment", "/patient/prescriptions", "/patient/bills"})
public class PatientServlet extends HttpServlet {

    private HospitalService hospitalService = new HospitalService();
    private BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Patient patient = hospitalService.getPatientByUserId(user.getUserId());

        if (patient == null) {
            // Fallback for admin or unlinked user testing
            patient = hospitalService.getPatientById(1);
        }

        String path = request.getServletPath();

        if (path.equals("/patient/dashboard")) {
            List<Appointment> appts = hospitalService.getAppointmentsByPatient(patient.getPatientId());
            List<Prescription> rxs = hospitalService.getPrescriptionsByPatient(patient.getPatientId());
            List<Bill> bills = billingService.getBillsByPatient(patient.getPatientId());
            List<Doctor> doctors = hospitalService.getAllDoctors();

            request.setAttribute("patient", patient);
            request.setAttribute("appointments", appts);
            request.setAttribute("prescriptions", rxs);
            request.setAttribute("bills", bills);
            request.setAttribute("doctors", doctors);
            request.getRequestDispatcher("/WEB-INF/views/patient/dashboard.jsp").forward(request, response);
        } else if (path.equals("/patient/profile")) {
            request.setAttribute("patient", patient);
            request.getRequestDispatcher("/WEB-INF/views/patient/profile.jsp").forward(request, response);
        } else if (path.equals("/patient/prescriptions")) {
            request.setAttribute("prescriptions", hospitalService.getPrescriptionsByPatient(patient.getPatientId()));
            request.getRequestDispatcher("/WEB-INF/views/patient/prescriptions.jsp").forward(request, response);
        } else if (path.equals("/patient/bills")) {
            request.setAttribute("bills", billingService.getBillsByPatient(patient.getPatientId()));
            request.getRequestDispatcher("/WEB-INF/views/patient/bills.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Patient patient = hospitalService.getPatientByUserId(user.getUserId());
        if (patient == null) patient = hospitalService.getPatientById(1);

        String action = request.getParameter("action");
        if ("bookAppointment".equals(action)) {
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            Date date = Date.valueOf(request.getParameter("appointmentDate"));
            String time = request.getParameter("appointmentTime");
            String symptoms = request.getParameter("symptoms");

            Appointment app = new Appointment();
            app.setPatientId(patient.getPatientId());
            app.setDoctorId(doctorId);
            app.setAppointmentDate(date);
            app.setAppointmentTime(time);
            app.setSymptoms(symptoms);
            app.setStatus("Pending");

            hospitalService.bookAppointment(app);
            response.sendRedirect(request.getContextPath() + "/patient/dashboard?msg=Appointment request submitted successfully!");
        } else if ("updateProfile".equals(action)) {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String emergency = request.getParameter("emergencyContact");

            patient.setName(name);
            patient.setPhone(phone);
            patient.setAddress(address);
            patient.setEmergencyContact(emergency);

            hospitalService.updatePatient(patient);
            response.sendRedirect(request.getContextPath() + "/patient/profile?msg=Profile updated successfully!");
        }
    }
}
