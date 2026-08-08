<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Add Diagnosis & Prescription - Doctor Portal"/>
<c:set var="pagePath" value="doctor-dashboard"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                <h2 style="font-size: 22px; font-weight: 700;">Clinical Consultation & Electronic Prescription</h2>
                <a href="${pageContext.request.contextPath}/doctor/dashboard" class="btn btn-secondary"><i class="fa-solid fa-arrow-left"></i> Back to Schedule</a>
            </div>

            <!-- Patient Profile & Appointment Info Summary -->
            <div class="card-panel" style="background: var(--primary-light); border-color: #b3d1ff;">
                <div style="display: flex; justify-content: space-between; flex-wrap: wrap; gap: 16px;">
                    <div>
                        <div style="font-size: 13px; color: var(--gray-700);">Patient Name</div>
                        <div style="font-size: 18px; font-weight: 700; color: var(--primary);">${appointment.patientName}</div>
                    </div>
                    <div>
                        <div style="font-size: 13px; color: var(--gray-700);">Contact Phone</div>
                        <div style="font-size: 15px; font-weight: 600;">${appointment.patientPhone}</div>
                    </div>
                    <div>
                        <div style="font-size: 13px; color: var(--gray-700);">Appointment ID</div>
                        <div style="font-size: 15px; font-weight: 600;">#APT-${appointment.appointmentId}</div>
                    </div>
                    <div>
                        <div style="font-size: 13px; color: var(--gray-700);">Reported Symptoms</div>
                        <div style="font-size: 14px; font-weight: 500; color: var(--danger);">${appointment.symptoms}</div>
                    </div>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/doctor/diagnosis" method="post">
                <input type="hidden" name="action" value="saveDiagnosis">
                <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                <input type="hidden" name="patientId" value="${appointment.patientId}">

                <!-- 1. Clinical Diagnosis Section -->
                <div class="card-panel">
                    <h3 class="card-title" style="margin-bottom: 16px;"><i class="fa-solid fa-file-waveform"></i> Clinical Assessment & Notes</h3>
                    
                    <div class="form-group">
                        <label>Chief Complaints / Symptoms *</label>
                        <textarea name="symptoms" class="form-control" rows="2" required>${diagnosis != null ? diagnosis.symptoms : appointment.symptoms}</textarea>
                    </div>

                    <div class="form-group">
                        <label>Clinical Diagnosis Details *</label>
                        <textarea name="diagnosisDetail" class="form-control" rows="3" required placeholder="Write clinical diagnosis findings...">${diagnosis != null ? diagnosis.diagnosisDetail : ''}</textarea>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Recommended Diagnostics & Lab Tests</label>
                            <input type="text" name="recommendedTests" class="form-control" value="${diagnosis != null ? diagnosis.recommendedTests : ''}" placeholder="e.g. Complete Blood Count, ECG, Lipid Profile">
                        </div>
                        <div class="form-group">
                            <label>Doctor Instructions & Notes</label>
                            <input type="text" name="doctorNotes" class="form-control" value="${diagnosis != null ? diagnosis.doctorNotes : ''}" placeholder="Follow up after 7 days, low salt diet">
                        </div>
                    </div>
                </div>

                <!-- 2. Electronic Prescription Section -->
                <div class="card-panel">
                    <div class="card-header">
                        <h3 class="card-title"><i class="fa-solid fa-prescription"></i> Prescribe Medicines</h3>
                        <button type="button" onclick="addPrescriptionRow()" class="btn btn-outline btn-sm"><i class="fa-solid fa-plus"></i> Add Medicine Line</button>
                    </div>

                    <div id="prescriptionItemsContainer">
                        <div class="prescription-row" style="display: flex; gap: 12px; margin-bottom: 12px; align-items: flex-end;">
                            <div style="flex: 2;">
                                <label style="font-size: 12px; font-weight: 600;">Select Medicine</label>
                                <select name="medicineId" class="form-control">
                                    <option value="">-- Choose Medicine --</option>
                                    <c:forEach var="med" items="${medicines}">
                                        <option value="${med.medicineId}">${med.name} (${med.category} - Stock: ${med.stockQuantity})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div style="flex: 1;">
                                <label style="font-size: 12px; font-weight: 600;">Dosage</label>
                                <input type="text" name="dosage" class="form-control" placeholder="e.g. 500mg">
                            </div>
                            <div style="flex: 1;">
                                <label style="font-size: 12px; font-weight: 600;">Frequency</label>
                                <input type="text" name="frequency" class="form-control" placeholder="e.g. 1-0-1">
                            </div>
                            <div style="flex: 1;">
                                <label style="font-size: 12px; font-weight: 600;">Duration</label>
                                <input type="text" name="duration" class="form-control" placeholder="e.g. 5 Days">
                            </div>
                            <div style="flex: 2;">
                                <label style="font-size: 12px; font-weight: 600;">Instructions</label>
                                <input type="text" name="instructions" class="form-control" placeholder="After meals">
                            </div>
                            <div>
                                <button type="button" onclick="removePrescriptionRow(this)" class="btn btn-danger btn-sm"><i class="fa-solid fa-trash"></i></button>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" style="margin-top: 16px;">
                        <label>General Advice & Precautions</label>
                        <input type="text" name="advice" class="form-control" value="${prescription != null ? prescription.advice : ''}" placeholder="Drink plenty of water and rest well.">
                    </div>

                    <button type="submit" class="btn btn-primary" style="padding: 12px 24px; font-size: 16px; margin-top: 12px;">
                        <i class="fa-solid fa-check-double"></i> Complete Appointment & Issue Prescription
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
