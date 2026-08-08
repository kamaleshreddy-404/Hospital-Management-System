<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Smart Hospital Management System - Home"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<header class="public-navbar">
    <div class="nav-brand">
        <i class="fa-solid fa-hospital-user" style="font-size: 24px; color: var(--primary);"></i> Smart Hospital
    </div>
    <nav style="display: flex; gap: 24px; font-weight: 600; align-items: center;">
        <a href="${pageContext.request.contextPath}/home" style="color: var(--primary);">Home</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/services">Services</a>
        <a href="${pageContext.request.contextPath}/doctors">Doctors</a>
        <a href="${pageContext.request.contextPath}/departments">Departments</a>
        <a href="${pageContext.request.contextPath}/contact">Contact</a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary"><i class="fa-solid fa-right-to-bracket"></i> Portal Login</a>
    </nav>
</header>

<section class="hero-banner">
    <h1>Smart Hospital Management System</h1>
    <p>Empowering multi-specialty clinical care, streamlined patient intake, automated pharmacy stock management, and digital health records.</p>
    <div style="display: flex; justify-content: center; gap: 16px;">
        <a href="${pageContext.request.contextPath}/register" class="btn btn-primary" style="background: var(--white); color: var(--primary); font-size: 16px; padding: 12px 24px;">
            <i class="fa-solid fa-user-plus"></i> Patient Registration
        </a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline" style="border-color: var(--white); color: var(--white); font-size: 16px; padding: 12px 24px;">
            <i class="fa-solid fa-lock"></i> Staff Login
        </a>
    </div>
</section>

<div class="page-body" style="max-width: 1200px; margin: 0 auto;">

    <!-- Key Hospital Services -->
    <div style="text-align: center; margin-bottom: 36px;">
        <h2 style="font-size: 28px; font-weight: 700; color: var(--gray-900);">Clinical Specialties & Medical Services</h2>
        <p style="color: var(--gray-700); margin-top: 8px;">24/7 Emergency, State-of-the-Art Diagnostics, Expert Specialists</p>
    </div>

    <div class="grid-3" style="margin-bottom: 48px;">
        <div class="service-card">
            <div class="service-card-icon"><i class="fa-solid fa-heart-pulse"></i></div>
            <h3 style="margin-bottom: 8px;">Cardiology</h3>
            <p style="color: var(--gray-700); font-size: 14px;">Advanced ECG, echocardiography, and vascular disease interventions led by senior cardiologists.</p>
        </div>
        <div class="service-card">
            <div class="service-card-icon"><i class="fa-solid fa-brain"></i></div>
            <h3 style="margin-bottom: 8px;">Neurology</h3>
            <p style="color: var(--gray-700); font-size: 14px;">Comprehensive neuro-diagnostics, EEG scanning, stroke rehabilitation, and headache clinics.</p>
        </div>
        <div class="service-card">
            <div class="service-card-icon"><i class="fa-solid fa-bone"></i></div>
            <h3 style="margin-bottom: 8px;">Orthopedics</h3>
            <p style="color: var(--gray-700); font-size: 14px;">Joint replacement, spinal care, fracture trauma restoration, and physical medicine.</p>
        </div>
    </div>

    <!-- Doctor Directory Preview -->
    <div class="card-panel">
        <div class="card-header">
            <h3 class="card-title"><i class="fa-solid fa-user-doctor"></i> Featured Specialist Doctors</h3>
            <a href="${pageContext.request.contextPath}/doctors" class="btn btn-outline btn-sm">View All Doctors</a>
        </div>
        <div class="table-responsive">
            <table class="custom-table">
                <thead>
                    <tr>
                        <th>Doctor Name</th>
                        <th>Specialization</th>
                        <th>Qualification</th>
                        <th>Experience</th>
                        <th>Consultation Fee</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="doc" items="${doctors}">
                        <tr>
                            <td style="font-weight: 600;"><i class="fa-solid fa-user-md" style="color: var(--primary);"></i> ${doc.name}</td>
                            <td><span class="badge badge-confirmed">${doc.specialization}</span></td>
                            <td>${doc.qualification}</td>
                            <td>${doc.experienceYears} Years</td>
                            <td style="font-weight: 700; color: var(--success);">₹${doc.consultationFee}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

<footer style="background: var(--white); border-top: 1px solid var(--gray-200); padding: 30px 40px; text-align: center; color: var(--gray-700); font-size: 14px; margin-top: 40px;">
    Smart Hospital Management System &copy; 2026 | Computer Science Academic Final Year Project
</footer>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
