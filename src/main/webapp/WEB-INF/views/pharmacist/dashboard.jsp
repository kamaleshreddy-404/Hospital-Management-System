<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Pharmacist Portal - Smart HMS"/>
<c:set var="pagePath" value="pharma-dashboard"/>
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
                <h2 style="font-size: 22px; font-weight: 700;">Pharmacy Counter & Prescriptions Queue</h2>
                <a href="${pageContext.request.contextPath}/pharmacist/medicines" class="btn btn-outline">
                    <i class="fa-solid fa-boxes-packing"></i> Manage Stock Inventory
                </a>
            </div>

            <!-- Low Stock Alert Warning -->
            <c:if test="${not empty lowStock}">
                <div class="alert alert-danger" style="display: flex; align-items: center; justify-content: space-between;">
                    <div>
                        <i class="fa-solid fa-triangle-exclamation"></i> <strong>Low Stock Alert:</strong> ${lowStock.size()} medicines require immediate inventory re-stocking.
                    </div>
                    <a href="${pageContext.request.contextPath}/pharmacist/medicines" class="btn btn-danger btn-sm">Update Stock</a>
                </div>
            </c:if>

            <!-- Prescriptions Master Register -->
            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-prescription"></i> Doctor Electronic Prescriptions (${prescriptions.size()})</h3>
                    <input type="text" id="rxSearch" onkeyup="filterTable('rxSearch', 'rxTable')" class="form-control" placeholder="Search patient, doctor..." style="width: 280px;">
                </div>

                <div class="table-responsive">
                    <table class="custom-table" id="rxTable">
                        <thead>
                            <tr>
                                <th>Rx ID</th>
                                <th>Patient Name</th>
                                <th>Doctor</th>
                                <th>Prescribed Date</th>
                                <th>Prescribed Line Items & Dosage</th>
                                <th>Doctor Advice</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rx" items="${prescriptions}">
                                <tr>
                                    <td>#RX-${rx.prescriptionId}</td>
                                    <td style="font-weight: 600;">${rx.patientName}</td>
                                    <td>${rx.doctorName}</td>
                                    <td>${rx.prescribedDate}</td>
                                    <td>
                                        <ul style="margin-left: 16px; font-size: 13px;">
                                            <c:forEach var="item" items="${rx.items}">
                                                <li>
                                                    <strong>${item.medicineName}</strong> - ${item.dosage} (${item.frequency}, ${item.duration})
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </td>
                                    <td style="font-size: 13px; color: var(--gray-700);">${rx.advice}</td>
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
