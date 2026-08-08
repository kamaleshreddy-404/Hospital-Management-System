<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Services - Smart HMS"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<header class="public-navbar">
    <div class="nav-brand">
        <i class="fa-solid fa-hospital-user" style="font-size: 24px; color: var(--primary);"></i> Smart Hospital
    </div>
    <nav style="display: flex; gap: 24px; font-weight: 600; align-items: center;">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/services" style="color: var(--primary);">Services</a>
        <a href="${pageContext.request.contextPath}/doctors">Doctors</a>
        <a href="${pageContext.request.contextPath}/departments">Departments</a>
        <a href="${pageContext.request.contextPath}/contact">Contact</a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary"><i class="fa-solid fa-right-to-bracket"></i> Portal Login</a>
    </nav>
</header>

<div class="page-body" style="max-width: 1100px; margin: 40px auto;">
    <h2 style="font-size: 26px; font-weight: 700; margin-bottom: 24px; color: var(--gray-900);">Hospital Facilities & Clinical Services</h2>

    <div class="grid-3">
        <div class="service-card">
            <div class="service-card-icon"><i class="fa-solid fa-truck-medical"></i></div>
            <h3>24/7 Emergency Care</h3>
            <p style="color: var(--gray-700); font-size: 14px; margin-top: 8px;">Round-the-clock emergency trauma response, ICU support, and cardiac life support ambulances.</p>
        </div>
        <div class="service-card">
            <div class="service-card-icon"><i class="fa-solid fa-vials"></i></div>
            <h3>Diagnostic Laboratory</h3>
            <p style="color: var(--gray-700); font-size: 14px; margin-top: 8px;">Fully automated biochemistry, pathology, blood bank, and radiological testing labs.</p>
        </div>
        <div class="service-card">
            <div class="service-card-icon"><i class="fa-solid fa-pills"></i></div>
            <h3>24/7 In-House Pharmacy</h3>
            <p style="color: var(--gray-700); font-size: 14px; margin-top: 8px;">Inventory-controlled pharmaceutical dispensing with instant prescription verification.</p>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
