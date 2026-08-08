<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Patient Portal Dashboard - Smart HMS"/>
<c:set var="pagePath" value="patient-dashboard"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <c:if test="${not empty param.msg}">
                <div class="alert alert-success"><i class="fa-solid fa-circle-check"></i> ${param.msg}</div>
            </c:if>

            <div class="card-panel" style="background: linear-gradient(135deg, #0b57d0 0%, #0045a5 100%); color: var(--white); padding: 28px;">
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <h2 style="font-size: 24px; font-weight: 700;">Welcome, ${patient.name}</h2>
                        <p style="opacity: 0.9; font-size: 14px; margin-top: 4px;">
                            Patient ID: <strong>#PAT-${patient.patientId}</strong> | Blood Group: <strong>${patient.bloodGroup}</strong> | Age: <strong>${patient.age} Yrs</strong>
                        </p>
                    </div>
                    <button onclick="document.getElementById('patientBookModal').style.display='block'" class="btn btn-primary" style="background: #fff; color: var(--primary);">
                        <i class="fa-solid fa-calendar-plus"></i> Book Doctor Appointment
                    </button>
                </div>
            </div>

            <!-- Book Appointment Modal for Patient -->
            <div id="patientBookModal" class="card-panel" style="display: none; border: 2px solid var(--primary);">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-calendar-check"></i> Schedule an Appointment</h3>
                    <button onclick="document.getElementById('patientBookModal').style.display='none'" class="btn btn-secondary btn-sm">Close</button>
                </div>
                <form action="${pageContext.request.contextPath}/patient/book-appointment" method="post">
                    <input type="hidden" name="action" value="bookAppointment">

                    <div class="form-row">
                        <div class="form-group">
                            <label>Select Specialist Doctor *</label>
                            <select name="doctorId" class="form-control" required>
                                <c:forEach var="doc" items="${doctors}">
                                    <option value="${doc.doctorId}">${doc.name} - ${doc.specialization} (${doc.deptName}) - Fee: ₹${doc.consultationFee}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Appointment Date *</label>
                            <input type="date" name="appointmentDate" class="form-control" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Preferred Time Slot *</label>
                            <select name="appointmentTime" class="form-control" required>
                                <option value="09:30 AM">09:30 AM</option>
                                <option value="11:00 AM">11:00 AM</option>
                                <option value="02:30 PM">02:30 PM</option>
                                <option value="04:00 PM">04:00 PM</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Symptoms / Main Concern</label>
                            <input type="text" name="symptoms" class="form-control" placeholder="Describe symptoms briefly...">
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary"><i class="fa-solid fa-check"></i> Submit Appointment Request</button>
                </form>
            </div>

            <!-- Appointment Visit History -->
            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-clock-rotate-left"></i> My Appointment History</h3>
                </div>

                <div class="table-responsive">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>Appt ID</th>
                                <th>Doctor Name</th>
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
                                    <td style="font-weight: 600;">${app.doctorName}</td>
                                    <td>${app.doctorSpecialization}</td>
                                    <td>${app.appointmentDate} at ${app.appointmentTime}</td>
                                    <td>${app.symptoms}</td>
                                    <td>
                                        <span class="badge ${app.status == 'Completed' ? 'badge-completed' : (app.status == 'Confirmed' ? 'badge-confirmed' : 'badge-pending')}">
                                            ${app.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty appointments}">
                                <tr><td colspan="6" style="text-align: center; color: var(--gray-700);">No appointment records found.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
