<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="My Prescriptions - Patient Portal"/>
<c:set var="pagePath" value="patient-rxs"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">Digital Doctor Prescriptions</h2>

            <c:forEach var="rx" items="${prescriptions}">
                <div class="card-panel printable-document" style="margin-bottom: 24px;">
                    <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid var(--gray-200); padding-bottom: 12px; margin-bottom: 16px;">
                        <div>
                            <h3 style="color: var(--primary); font-size: 18px;"><i class="fa-solid fa-prescription"></i> Prescription #RX-${rx.prescriptionId}</h3>
                            <span style="font-size: 13px; color: var(--gray-700);">Prescribed by: <strong>${rx.doctorName}</strong> on ${rx.prescribedDate}</span>
                        </div>
                        <button onclick="printPage()" class="btn btn-outline btn-sm no-print"><i class="fa-solid fa-print"></i> Download / Print</button>
                    </div>

                    <h4 style="margin-bottom: 8px; font-size: 14px;">Prescribed Medication Schedule:</h4>
                    <div class="table-responsive">
                        <table class="custom-table">
                            <thead>
                                <tr>
                                    <th>Medicine Name</th>
                                    <th>Dosage</th>
                                    <th>Frequency</th>
                                    <th>Duration</th>
                                    <th>Special Instructions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${rx.items}">
                                    <tr>
                                        <td style="font-weight: 600; color: var(--primary);">${item.medicineName}</td>
                                        <td>${item.dosage}</td>
                                        <td>${item.frequency}</td>
                                        <td>${item.duration}</td>
                                        <td style="font-size: 13px;">${item.instructions}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div style="margin-top: 16px; background: var(--gray-100); padding: 12px; border-radius: var(--radius); font-size: 14px;">
                        <strong>Doctor's Advice & Care Notes:</strong> ${rx.advice}
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty prescriptions}">
                <div class="card-panel" style="text-align: center; padding: 40px; color: var(--gray-700);">
                    <i class="fa-solid fa-prescription-bottle-medical" style="font-size: 40px; color: var(--gray-500); margin-bottom: 12px;"></i>
                    <p>No active prescriptions found in your medical history.</p>
                </div>
            </c:if>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
