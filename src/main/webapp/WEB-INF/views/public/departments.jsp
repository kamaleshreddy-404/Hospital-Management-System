<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Departments - Smart HMS"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<header class="public-navbar">
    <div class="nav-brand">
        <i class="fa-solid fa-hospital-user" style="font-size: 24px; color: var(--primary);"></i> Smart Hospital
    </div>
    <nav style="display: flex; gap: 24px; font-weight: 600; align-items: center;">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/services">Services</a>
        <a href="${pageContext.request.contextPath}/doctors">Doctors</a>
        <a href="${pageContext.request.contextPath}/departments" style="color: var(--primary);">Departments</a>
        <a href="${pageContext.request.contextPath}/contact">Contact</a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary"><i class="fa-solid fa-right-to-bracket"></i> Portal Login</a>
    </nav>
</header>

<div class="page-body" style="max-width: 1100px; margin: 40px auto;">
    <h2 style="font-size: 26px; font-weight: 700; margin-bottom: 24px; color: var(--gray-900);">Clinical Departments</h2>

    <div class="grid-3">
        <c:forEach var="dept" items="${departments}">
            <div class="card-panel" style="margin-bottom: 0;">
                <h3 style="color: var(--primary); font-size: 20px; margin-bottom: 8px;"><i class="fa-solid fa-building-user"></i> ${dept.deptName}</h3>
                <p style="color: var(--gray-700); font-size: 14px; margin-bottom: 16px;">${dept.description}</p>
                <div style="font-size: 13px; font-weight: 600; color: var(--gray-900);">
                    Head of Dept: <span style="color: var(--primary);">${dept.headDoctorName != null ? dept.headDoctorName : 'Unassigned'}</span>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
