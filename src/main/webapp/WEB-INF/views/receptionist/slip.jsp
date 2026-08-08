<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Print Appointment Slip - Smart HMS"/>
<c:set var="pagePath" value="reception-dashboard"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body" style="max-width: 650px; margin: 0 auto;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;" class="no-print">
                <h2 style="font-size: 20px; font-weight: 700;">Appointment Token Slip</h2>
                <div>
                    <button onclick="printPage()" class="btn btn-primary"><i class="fa-solid fa-print"></i> Print Slip</button>
                    <a href="${pageContext.request.contextPath}/receptionist/dashboard" class="btn btn-secondary">Back</a>
                </div>
            </div>

            <div class="card-panel printable-document" style="border: 2px dashed var(--gray-500); padding: 32px; background: #fff;">
                <div style="text-align: center; border-bottom: 2px solid var(--primary); padding-bottom: 16px; margin-bottom: 20px;">
                    <h2 style="color: var(--primary); font-size: 24px; font-weight: 800;"><i class="fa-solid fa-hospital"></i> SMART HOSPITAL</h2>
                    <p style="font-size: 13px; color: var(--gray-700);">123 Healthcare Blvd | Phone: 1800-123-9999</p>
                    <div style="display: inline-block; background: var(--primary-light); color: var(--primary); font-weight: 700; padding: 4px 16px; border-radius: 20px; font-size: 14px; margin-top: 8px;">
                        OPD CONSULTATION TOKEN SLIP
                    </div>
                </div>

                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 24px; font-size: 15px;">
                    <div>
                        <strong>Appointment ID:</strong> #APT-${appointment.appointmentId}<br>
                        <strong>Patient Name:</strong> ${patient.name}<br>
                        <strong>Age / Gender:</strong> ${patient.age} Yrs / ${patient.gender}<br>
                        <strong>Blood Group:</strong> ${patient.bloodGroup}<br>
                        <strong>Contact:</strong> ${patient.phone}
                    </div>
                    <div>
                        <strong>Doctor:</strong> ${doctor.name}<br>
                        <strong>Department:</strong> ${doctor.deptName}<br>
                        <strong>Specialization:</strong> ${doctor.specialization}<br>
                        <strong>Appt Date:</strong> ${appointment.appointmentDate}<br>
                        <strong>Time Slot:</strong> ${appointment.appointmentTime}
                    </div>
                </div>

                <div style="border-top: 1px solid var(--gray-200); padding-top: 12px; margin-bottom: 20px;">
                    <strong>Chief Complaints / Symptoms:</strong><br>
                    <span style="color: var(--gray-700);">${appointment.symptoms}</span>
                </div>

                <div style="display: flex; justify-content: space-between; align-items: flex-end; border-top: 1px solid var(--gray-200); padding-top: 16px;">
                    <div>
                        <strong>Consultation Fee:</strong> ₹${doctor.consultationFee}<br>
                        <span class="badge badge-completed">Status: ${appointment.status}</span>
                    </div>
                    <div style="text-align: right; font-size: 12px; color: var(--gray-700);">
                        Receptionist Signature<br>
                        <span style="font-family: cursive; font-size: 16px; color: var(--gray-900);">Sarah J.</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
