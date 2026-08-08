<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Admin Dashboard - Smart HMS"/>
<c:set var="pagePath" value="admin-dashboard"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <c:if test="${not empty param.msg}">
                <div class="alert alert-success"><i class="fa-solid fa-circle-check"></i> ${param.msg}</div>
            </c:if>

            <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 20px;">Hospital Operational Dashboard</h2>

            <!-- Metrics Counters Grid -->
            <div class="dashboard-grid">
                <div class="stat-card">
                    <div>
                        <div class="stat-label">Total Patients</div>
                        <div class="stat-number">${stats.totalPatients}</div>
                    </div>
                    <div class="stat-icon"><i class="fa-solid fa-procedures"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-label">Active Doctors</div>
                        <div class="stat-number">${stats.totalDoctors}</div>
                    </div>
                    <div class="stat-icon" style="background-color: #e6f4ea; color: #1e8e3e;"><i class="fa-solid fa-user-doctor"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-label">Today's Appts</div>
                        <div class="stat-number">${stats.todayAppointments}</div>
                    </div>
                    <div class="stat-icon" style="background-color: #fef7e0; color: #e37400;"><i class="fa-solid fa-calendar-day"></i></div>
                </div>

                <div class="stat-card">
                    <div>
                        <div class="stat-label">Today's Revenue</div>
                        <div class="stat-number" style="font-size: 22px; color: var(--success);">₹${stats.todayRevenue}</div>
                    </div>
                    <div class="stat-icon" style="background-color: #e8f0fe; color: #0b57d0;"><i class="fa-solid fa-indian-rupee-sign"></i></div>
                </div>
            </div>

            <!-- Recent Appointments Table -->
            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-calendar-check"></i> Today's Appointment Schedule</h3>
                    <a href="${pageContext.request.contextPath}/admin/appointments" class="btn btn-outline btn-sm">View All Appointments</a>
                </div>

                <div class="table-responsive">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>Appt ID</th>
                                <th>Patient Name</th>
                                <th>Doctor</th>
                                <th>Specialization</th>
                                <th>Time Slot</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="app" items="${recentAppointments}">
                                <tr>
                                    <td>#APT-${app.appointmentId}</td>
                                    <td style="font-weight: 600;">${app.patientName}</td>
                                    <td>${app.doctorName}</td>
                                    <td>${app.doctorSpecialization}</td>
                                    <td><i class="fa-regular fa-clock"></i> ${app.appointmentTime}</td>
                                    <td>
                                        <span class="badge ${app.status == 'Completed' ? 'badge-completed' : (app.status == 'Confirmed' ? 'badge-confirmed' : 'badge-pending')}">
                                            ${app.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if var="emptyAppts" test="${empty recentAppointments}">
                                <tr><td colspan="6" style="text-align: center; color: var(--gray-700);">No appointments scheduled for today yet.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
