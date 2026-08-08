<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Appointments - Admin Portal"/>
<c:set var="pagePath" value="admin-appts"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">Master Appointment Register</h2>

            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-calendar-days"></i> All Appointments (${appointments.size()})</h3>
                    <input type="text" id="apptSearch" onkeyup="filterTable('apptSearch', 'apptTable')" class="form-control" placeholder="Search patient, doctor, status..." style="width: 300px;">
                </div>

                <div class="table-responsive">
                    <table class="custom-table" id="apptTable">
                        <thead>
                            <tr>
                                <th>Appt ID</th>
                                <th>Patient Name</th>
                                <th>Doctor</th>
                                <th>Specialization</th>
                                <th>Date & Time</th>
                                <th>Symptoms</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="app" items="${appointments}">
                                <tr>
                                    <td>#APT-${app.appointmentId}</td>
                                    <td style="font-weight: 600;">${app.patientName}</td>
                                    <td>${app.doctorName}</td>
                                    <td>${app.doctorSpecialization}</td>
                                    <td>${app.appointmentDate} at ${app.appointmentTime}</td>
                                    <td style="font-size: 13px;">${app.symptoms}</td>
                                    <td>
                                        <span class="badge ${app.status == 'Completed' ? 'badge-completed' : (app.status == 'Confirmed' ? 'badge-confirmed' : (app.status == 'Cancelled' ? 'badge-cancelled' : 'badge-pending'))}">
                                            ${app.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
