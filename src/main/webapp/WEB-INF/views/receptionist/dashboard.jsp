<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Receptionist Portal - Smart HMS"/>
<c:set var="pagePath" value="reception-dashboard"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <c:if test="${not empty param.msg}">
                <div class="alert alert-success"><i class="fa-solid fa-circle-check"></i> ${param.msg}</div>
            </c:if>

            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
                <h2 style="font-size: 22px; font-weight: 700;">Front Desk & Reception Desk</h2>
                <div style="display: flex; gap: 12px;">
                    <a href="${pageContext.request.contextPath}/receptionist/register-patient" class="btn btn-outline">
                        <i class="fa-solid fa-user-plus"></i> Walk-in Patient Intake
                    </a>
                    <button onclick="document.getElementById('bookApptModal').style.display='block'" class="btn btn-primary">
                        <i class="fa-solid fa-calendar-plus"></i> Book Doctor Appointment
                    </button>
                </div>
            </div>

            <!-- Book Appointment Modal -->
            <div id="bookApptModal" class="card-panel" style="display: none; border: 2px solid var(--primary);">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-calendar-check"></i> Schedule Doctor Appointment</h3>
                    <button onclick="document.getElementById('bookApptModal').style.display='none'" class="btn btn-secondary btn-sm">Close</button>
                </div>

                <form action="${pageContext.request.contextPath}/receptionist/appointments" method="post">
                    <input type="hidden" name="action" value="bookAppointment">
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label>Select Patient *</label>
                            <select name="patientId" class="form-control" required>
                                <c:forEach var="p" items="${patients}">
                                    <option value="${p.patientId}">${p.name} (Phone: ${p.phone})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Select Doctor *</label>
                            <select name="doctorId" class="form-control" required>
                                <c:forEach var="doc" items="${doctors}">
                                    <option value="${doc.doctorId}">${doc.name} - ${doc.specialization} (₹${doc.consultationFee})</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Appointment Date *</label>
                            <input type="date" name="appointmentDate" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Preferred Time Slot *</label>
                            <select name="appointmentTime" class="form-control" required>
                                <option value="09:00 AM">09:00 AM</option>
                                <option value="10:00 AM">10:00 AM</option>
                                <option value="11:00 AM">11:00 AM</option>
                                <option value="12:00 PM">12:00 PM</option>
                                <option value="02:00 PM">02:00 PM</option>
                                <option value="03:30 PM">03:30 PM</option>
                                <option value="05:00 PM">05:00 PM</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Chief Symptoms</label>
                        <input type="text" name="symptoms" class="form-control" placeholder="e.g. Fever, chest pain, backache">
                    </div>

                    <button type="submit" class="btn btn-primary"><i class="fa-solid fa-check"></i> Confirm Appointment</button>
                </form>
            </div>

            <!-- Today's Master Appointments Table -->
            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title">Appointments Register</h3>
                    <input type="text" id="recApptSearch" onkeyup="filterTable('recApptSearch', 'recApptTable')" class="form-control" placeholder="Search patient, doctor..." style="width: 260px;">
                </div>

                <div class="table-responsive">
                    <table class="custom-table" id="recApptTable">
                        <thead>
                            <tr>
                                <th>Appt ID</th>
                                <th>Patient Name</th>
                                <th>Doctor Name</th>
                                <th>Specialization</th>
                                <th>Date & Time</th>
                                <th>Status</th>
                                <th>Actions</th>
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
                                    <td>
                                        <span class="badge ${app.status == 'Completed' ? 'badge-completed' : (app.status == 'Confirmed' ? 'badge-confirmed' : 'badge-pending')}">
                                            ${app.status}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/receptionist/slip?appointmentId=${app.appointmentId}" class="btn btn-outline btn-sm">
                                            <i class="fa-solid fa-print"></i> Slip
                                        </a>
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
