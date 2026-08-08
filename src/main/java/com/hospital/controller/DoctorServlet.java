package com.hospital.controller;

import com.hospital.model.*;
import com.hospital.service.HospitalService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/doctor/dashboard", "/doctor/appointments", "/doctor/diagnosis", "/doctor/prescription"})
public class DoctorServlet extends HttpServlet {

    private HospitalService hospitalService = new HospitalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Doctor doctor = hospitalService.getDoctorByUserId(user.getUserId());

        if (doctor == null) {
            // Fallback for admin viewing doctor portal or default doctor ID 1
            doctor = hospitalService.getDoctorById(1);
        }

        String path = request.getServletPath();

        if (path.equals("/doctor/dashboard") || path.equals("/doctor/appointments")) {
            List<Appointment> todayAppts = hospitalService.getTodayAppointmentsByDoctor(doctor.getDoctorId());
            List<Appointment> allAppts = hospitalService.getAppointmentsByDoctor(doctor.getDoctorId());
            request.setAttribute("doctor", doctor);
            request.setAttribute("todayAppointments", todayAppts);
            request.setAttribute("allAppointments", allAppts);
            request.getRequestDispatcher("/WEB-INF/views/doctor/dashboard.jsp").forward(request, response);
        } else if (path.equals("/doctor/diagnosis")) {
            int apptId = Integer.parseInt(request.getParameter("appointmentId"));
            Appointment appt = hospitalService.getAppointmentById(apptId);
            Diagnosis existingDiag = hospitalService.getDiagnosisByAppointment(apptId);
            Prescription existingRx = hospitalService.getPrescriptionByAppointment(apptId);
            List<Medicine> medicines = hospitalService.getAllMedicines();

            request.setAttribute("appointment", appt);
            request.setAttribute("diagnosis", existingDiag);
            request.setAttribute("prescription", existingRx);
            request.setAttribute("medicines", medicines);
            request.getRequestDispatcher("/WEB-INF/views/doctor/diagnosis.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Doctor doctor = hospitalService.getDoctorByUserId(user.getUserId());
        if (doctor == null) doctor = hospitalService.getDoctorById(1);

        String action = request.getParameter("action");
        if ("saveDiagnosis".equals(action)) {
            int apptId = Integer.parseInt(request.getParameter("appointmentId"));
            int patientId = Integer.parseInt(request.getParameter("patientId"));
            String symptoms = request.getParameter("symptoms");
            String diagnosisDetail = request.getParameter("diagnosisDetail");
            String recommendedTests = request.getParameter("recommendedTests");
            String notes = request.getParameter("doctorNotes");

            Diagnosis diag = new Diagnosis();
            diag.setAppointmentId(apptId);
            diag.setPatientId(patientId);
            diag.setDoctorId(doctor.getDoctorId());
            diag.setSymptoms(symptoms);
            diag.setDiagnosisDetail(diagnosisDetail);
            diag.setRecommendedTests(recommendedTests);
            diag.setDoctorNotes(notes);

            hospitalService.addDiagnosis(diag);

            // Handle Prescription lines if provided
            String[] medicineIds = request.getParameterValues("medicineId");
            String advice = request.getParameter("advice");

            if (medicineIds != null && medicineIds.length > 0) {
                Prescription rx = new Prescription();
                rx.setAppointmentId(apptId);
                rx.setPatientId(patientId);
                rx.setDoctorId(doctor.getDoctorId());
                rx.setPrescribedDate(new Date(System.currentTimeMillis()));
                rx.setAdvice(advice);

                List<PrescriptionItem> items = new ArrayList<>();
                for (int i = 0; i < medicineIds.length; i++) {
                    if (medicineIds[i] != null && !medicineIds[i].isEmpty()) {
                        PrescriptionItem item = new PrescriptionItem();
                        item.setMedicineId(Integer.parseInt(medicineIds[i]));
                        item.setDosage(request.getParameterValues("dosage")[i]);
                        item.setFrequency(request.getParameterValues("frequency")[i]);
                        item.setDuration(request.getParameterValues("duration")[i]);
                        item.setInstructions(request.getParameterValues("instructions")[i]);
                        items.add(item);
                    }
                }
                hospitalService.addPrescription(rx, items);
            }

            // Update appointment status to Completed
            hospitalService.updateAppointmentStatus(apptId, "Completed");

            response.sendRedirect(request.getContextPath() + "/doctor/dashboard?msg=Diagnosis and Prescription completed!");
        }
    }
}
