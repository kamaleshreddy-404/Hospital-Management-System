<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Doctor Portal - Today's Appointments"/>
<c:set var="pagePath" value="doctor-dashboard"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <c:if test="${not empty param.msg}">
                <div class="alert alert-success"><i class="fa-solid fa-circle-check"></i> ${param.msg}</div>
            </c:if>

            <div class="card-panel" style="background: linear-gradient(135deg, #0b57d0 0%, #0045a5 100%); color: var(--white); padding: 24px;">
                <h2 style="font-size: 24px; font-weight: 700;">Welcome, ${doctor.name}</h2>
                <p style="opacity: 0.9; font-size: 14px; margin-top: 4px;">
                    Department: <strong>${doctor.deptName}</strong> | Specialization: <strong>${doctor.specialization}</strong>
                </p>
            </div>

            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-stethoscope"></i> Today's Appointment Queue (${todayAppointments.size()})</h3>
                </div>

                <div class="table-responsive">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>Appt ID</th>
                                <th>Time Slot</th>
                                <th>Patient Name</th>
                                <th>Contact Phone</th>
                                <th>Symptoms / Chief Complaints</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="app" items="${todayAppointments}">
                                <tr>
                                    <td>#APT-${app.appointmentId}</td>
                                    <td style="font-weight: 600; color: var(--primary);"><i class="fa-regular fa-clock"></i> ${app.appointmentTime}</td>
                                    <td style="font-weight: 600;">${app.patientName}</td>
                                    <td>${app.patientPhone}</td>
                                    <td style="font-size: 13px;">${app.symptoms}</td>
                                    <td>
                                        <span class="badge ${app.status == 'Completed' ? 'badge-completed' : (app.status == 'Confirmed' ? 'badge-confirmed' : 'badge-pending')}">
                                            ${app.status}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/doctor/diagnosis?appointmentId=${app.appointmentId}" class="btn btn-primary btn-sm">
                                            <i class="fa-solid fa-notes-medical"></i> ${app.status == 'Completed' ? 'View / Edit Diagnosis' : 'Diagnose & Prescribe'}
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty todayAppointments}">
                                <tr><td colspan="7" style="text-align: center; color: var(--gray-700);">No appointments queued for today.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
