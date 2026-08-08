<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Our Doctors - Smart HMS"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<header class="public-navbar">
    <div class="nav-brand">
        <i class="fa-solid fa-hospital-user" style="font-size: 24px; color: var(--primary);"></i> Smart Hospital
    </div>
    <nav style="display: flex; gap: 24px; font-weight: 600; align-items: center;">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/services">Services</a>
        <a href="${pageContext.request.contextPath}/doctors" style="color: var(--primary);">Doctors</a>
        <a href="${pageContext.request.contextPath}/departments">Departments</a>
        <a href="${pageContext.request.contextPath}/contact">Contact</a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary"><i class="fa-solid fa-right-to-bracket"></i> Portal Login</a>
    </nav>
</header>

<div class="page-body" style="max-width: 1100px; margin: 40px auto;">
    <div class="card-panel">
        <div class="card-header">
            <h2 class="card-title"><i class="fa-solid fa-user-doctor"></i> Medical Specialists Directory</h2>
            <input type="text" id="docSearch" onkeyup="filterTable('docSearch', 'docTable')" class="form-control" placeholder="Search doctor name or specialty..." style="width: 300px;">
        </div>

        <div class="table-responsive">
            <table class="custom-table" id="docTable">
                <thead>
                    <tr>
                        <th>Doctor Name</th>
                        <th>Specialization</th>
                        <th>Department</th>
                        <th>Qualification</th>
                        <th>Experience</th>
                        <th>Consultation Fee</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="doc" items="${doctors}">
                        <tr>
                            <td style="font-weight: 600;">${doc.name}</td>
                            <td><span class="badge badge-confirmed">${doc.specialization}</span></td>
                            <td>${doc.deptName}</td>
                            <td>${doc.qualification}</td>
                            <td>${doc.experienceYears} Years</td>
                            <td style="font-weight: 700; color: var(--success);">₹${doc.consultationFee}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-sm">Book Appointment</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
