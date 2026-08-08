package com.hospital.controller;

import com.hospital.model.User;
import com.hospital.model.Patient;
import com.hospital.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/register", "/logout"})
public class AuthServlet extends HttpServlet {

    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login?msg=Successfully logged out");
            return;
        }

        if (path.equals("/register")) {
            request.getRequestDispatcher("/WEB-INF/views/public/register.jsp").forward(request, response);
            return;
        }

        // Login page
        request.getRequestDispatcher("/WEB-INF/views/public/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/login")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = authService.login(email, password);

            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

                // Redirect based on role
                switch (user.getRoleId()) {
                    case 1: // Admin
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                        break;
                    case 2: // Doctor
                        response.sendRedirect(request.getContextPath() + "/doctor/dashboard");
                        break;
                    case 3: // Receptionist
                        response.sendRedirect(request.getContextPath() + "/receptionist/dashboard");
                        break;
                    case 4: // Pharmacist
                        response.sendRedirect(request.getContextPath() + "/pharmacist/dashboard");
                        break;
                    case 5: // Patient
                        response.sendRedirect(request.getContextPath() + "/patient/dashboard");
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/home");
                        break;
                }
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/WEB-INF/views/public/login.jsp").forward(request, response);
            }
        } else if (path.equals("/register")) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String ageStr = request.getParameter("age");
            String bloodGroup = request.getParameter("bloodGroup");
            String address = request.getParameter("address");
            String emergencyContact = request.getParameter("emergencyContact");

            int age = 0;
            try { age = Integer.parseInt(ageStr); } catch (Exception ignored) {}

            User user = new User(0, name, email, password, phone, 5);
            Patient patient = new Patient();
            patient.setName(name);
            patient.setGender(gender);
            patient.setAge(age);
            patient.setBloodGroup(bloodGroup);
            patient.setPhone(phone);
            patient.setAddress(address);
            patient.setEmail(email);
            patient.setEmergencyContact(emergencyContact);

            boolean success = authService.registerPatientUser(user, patient);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/login?msg=Registration successful! Please login.");
            } else {
                request.setAttribute("error", "Registration failed! Email may already be registered.");
                request.getRequestDispatcher("/WEB-INF/views/public/register.jsp").forward(request, response);
            }
        }
    }
}
