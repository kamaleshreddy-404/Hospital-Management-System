<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="sidebar">
    <div class="sidebar-header">
        <i class="fa-solid fa-notes-medical"></i> SmartHMS
    </div>
    <nav class="sidebar-nav">
        <c:choose>
            <%-- Admin Role (1) --%>
            <c:when="${sessionScope.user != null && sessionScope.user.roleId == 1}">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-item ${pagePath == 'admin-dashboard' ? 'active' : ''}">
                    <i class="fa-solid fa-chart-line"></i> Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/admin/doctors" class="nav-item ${pagePath == 'admin-doctors' ? 'active' : ''}">
                    <i class="fa-solid fa-user-doctor"></i> Manage Doctors
                </a>
                <a href="${pageContext.request.contextPath}/admin/patients" class="nav-item ${pagePath == 'admin-patients' ? 'active' : ''}">
                    <i class="fa-solid fa-procedures"></i> Manage Patients
                </a>
                <a href="${pageContext.request.contextPath}/admin/departments" class="nav-item ${pagePath == 'admin-depts' ? 'active' : ''}">
                    <i class="fa-solid fa-building-user"></i> Departments
                </a>
                <a href="${pageContext.request.contextPath}/admin/medicines" class="nav-item ${pagePath == 'admin-medicines' ? 'active' : ''}">
                    <i class="fa-solid fa-pills"></i> Pharmacy Stock
                </a>
                <a href="${pageContext.request.contextPath}/admin/appointments" class="nav-item ${pagePath == 'admin-appts' ? 'active' : ''}">
                    <i class="fa-solid fa-calendar-check"></i> Appointments
                </a>
                <a href="${pageContext.request.contextPath}/admin/billing" class="nav-item ${pagePath == 'admin-billing' ? 'active' : ''}">
                    <i class="fa-solid fa-file-invoice-dollar"></i> Billing & Revenue
                </a>
                <a href="${pageContext.request.contextPath}/admin/reports" class="nav-item ${pagePath == 'admin-reports' ? 'active' : ''}">
                    <i class="fa-solid fa-chart-pie"></i> Reports
                </a>
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-item ${pagePath == 'admin-users' ? 'active' : ''}">
                    <i class="fa-solid fa-users-gear"></i> System Users
                </a>
            </c:when>

            <%-- Doctor Role (2) --%>
            <c:when="${sessionScope.user != null && sessionScope.user.roleId == 2}">
                <a href="${pageContext.request.contextPath}/doctor/dashboard" class="nav-item ${pagePath == 'doctor-dashboard' ? 'active' : ''}">
                    <i class="fa-solid fa-stethoscope"></i> Today's Schedule
                </a>
                <a href="${pageContext.request.contextPath}/doctor/appointments" class="nav-item ${pagePath == 'doctor-appts' ? 'active' : ''}">
                    <i class="fa-solid fa-clock-rotate-left"></i> Appointment Queue
                </a>
            </c:when>

            <%-- Receptionist Role (3) --%>
            <c:when="${sessionScope.user != null && sessionScope.user.roleId == 3}">
                <a href="${pageContext.request.contextPath}/receptionist/dashboard" class="nav-item ${pagePath == 'reception-dashboard' ? 'active' : ''}">
                    <i class="fa-solid fa-hospital-user"></i> Reception Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/receptionist/register-patient" class="nav-item ${pagePath == 'reception-reg' ? 'active' : ''}">
                    <i class="fa-solid fa-user-plus"></i> Patient Intake
                </a>
                <a href="${pageContext.request.contextPath}/receptionist/billing" class="nav-item ${pagePath == 'reception-billing' ? 'active' : ''}">
                    <i class="fa-solid fa-receipt"></i> Generate Invoice
                </a>
            </c:when>

            <%-- Pharmacist Role (4) --%>
            <c:when="${sessionScope.user != null && sessionScope.user.roleId == 4}">
                <a href="${pageContext.request.contextPath}/pharmacist/dashboard" class="nav-item ${pagePath == 'pharma-dashboard' ? 'active' : ''}">
                    <i class="fa-solid fa-prescription"></i> Prescriptions Queue
                </a>
                <a href="${pageContext.request.contextPath}/pharmacist/medicines" class="nav-item ${pagePath == 'pharma-medicines' ? 'active' : ''}">
                    <i class="fa-solid fa-boxes-packing"></i> Stock Inventory
                </a>
            </c:when>

            <%-- Patient Role (5) --%>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/patient/dashboard" class="nav-item ${pagePath == 'patient-dashboard' ? 'active' : ''}">
                    <i class="fa-solid fa-house-medical"></i> Patient Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/patient/profile" class="nav-item ${pagePath == 'patient-profile' ? 'active' : ''}">
                    <i class="fa-solid fa-id-card"></i> Medical Profile
                </a>
                <a href="${pageContext.request.contextPath}/patient/prescriptions" class="nav-item ${pagePath == 'patient-rxs' ? 'active' : ''}">
                    <i class="fa-solid fa-prescription-bottle-medical"></i> Prescriptions
                </a>
                <a href="${pageContext.request.contextPath}/patient/bills" class="nav-item ${pagePath == 'patient-bills' ? 'active' : ''}">
                    <i class="fa-solid fa-wallet"></i> My Invoices
                </a>
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/home" class="nav-item" style="margin-top: 20px; border-top: 1px solid var(--gray-200); padding-top: 16px;">
            <i class="fa-solid fa-globe"></i> Hospital Website
        </a>
    </nav>
    <div class="sidebar-footer">
        <i class="fa-solid fa-graduation-cap"></i> Major Project v1.0
    </div>
</aside>
