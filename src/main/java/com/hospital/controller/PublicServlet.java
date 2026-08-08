package com.hospital.controller;

import com.hospital.service.HospitalService;
import com.hospital.model.Doctor;
import com.hospital.model.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"", "/home", "/about", "/services", "/doctors", "/departments", "/contact"})
public class PublicServlet extends HttpServlet {

    private HospitalService hospitalService = new HospitalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if (servletPath == null || servletPath.isEmpty() || servletPath.equals("/") || servletPath.equals("/home")) {
            List<Department> depts = hospitalService.getAllDepartments();
            List<Doctor> doctors = hospitalService.getAllDoctors();
            request.setAttribute("departments", depts);
            request.setAttribute("doctors", doctors);
            request.getRequestDispatcher("/WEB-INF/views/public/index.jsp").forward(request, response);
        } else if (servletPath.equals("/about")) {
            request.getRequestDispatcher("/WEB-INF/views/public/about.jsp").forward(request, response);
        } else if (servletPath.equals("/services")) {
            request.getRequestDispatcher("/WEB-INF/views/public/services.jsp").forward(request, response);
        } else if (servletPath.equals("/doctors")) {
            List<Doctor> doctors = hospitalService.getAllDoctors();
            request.setAttribute("doctors", doctors);
            request.getRequestDispatcher("/WEB-INF/views/public/doctors.jsp").forward(request, response);
        } else if (servletPath.equals("/departments")) {
            List<Department> depts = hospitalService.getAllDepartments();
            request.setAttribute("departments", depts);
            request.getRequestDispatcher("/WEB-INF/views/public/departments.jsp").forward(request, response);
        } else if (servletPath.equals("/contact")) {
            request.getRequestDispatcher("/WEB-INF/views/public/contact.jsp").forward(request, response);
        }
    }
}
