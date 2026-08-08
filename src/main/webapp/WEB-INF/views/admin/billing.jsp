<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Billing & Revenue - Admin Portal"/>
<c:set var="pagePath" value="admin-billing"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">Financial Invoices & Revenue Audit</h2>

            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-file-invoice-dollar"></i> Invoice Register</h3>
                    <input type="text" id="billSearch" onkeyup="filterTable('billSearch', 'billTable')" class="form-control" placeholder="Search bill ID or patient..." style="width: 280px;">
                </div>

                <div class="table-responsive">
                    <table class="custom-table" id="billTable">
                        <thead>
                            <tr>
                                <th>Invoice ID</th>
                                <th>Patient Name</th>
                                <th>Consultation Fee</th>
                                <th>Medicine Fee</th>
                                <th>Test Fee</th>
                                <th>Total Amount</th>
                                <th>Status</th>
                                <th>Payment Method</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${bills}">
                                <tr>
                                    <td>#INV-${b.billId}</td>
                                    <td style="font-weight: 600;">${b.patientName}</td>
                                    <td>₹${b.consultationFee}</td>
                                    <td>₹${b.medicineCharges}</td>
                                    <td>₹${b.testCharges}</td>
                                    <td style="font-weight: 700; color: var(--primary);">₹${b.totalAmount}</td>
                                    <td><span class="badge ${b.paymentStatus == 'Paid' ? 'badge-paid' : 'badge-unpaid'}">${b.paymentStatus}</span></td>
                                    <td>${b.paymentMethod}</td>
                                    <td style="font-size: 13px;">${b.billDate}</td>
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
