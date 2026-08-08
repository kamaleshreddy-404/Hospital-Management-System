<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="My Bills - Patient Portal"/>
<c:set var="pagePath" value="patient-bills"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">Medical Bills & Invoices</h2>

            <div class="card-panel">
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>Invoice ID</th>
                                <th>Consultation Fee</th>
                                <th>Medicine Fee</th>
                                <th>Test Fee</th>
                                <th>Total Bill Amount</th>
                                <th>Payment Status</th>
                                <th>Date</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${bills}">
                                <tr>
                                    <td>#INV-${b.billId}</td>
                                    <td>₹${b.consultationFee}</td>
                                    <td>₹${b.medicineCharges}</td>
                                    <td>₹${b.testCharges}</td>
                                    <td style="font-weight: 700; color: var(--primary);">₹${b.totalAmount}</td>
                                    <td><span class="badge ${b.paymentStatus == 'Paid' ? 'badge-paid' : 'badge-unpaid'}">${b.paymentStatus}</span></td>
                                    <td>${b.billDate}</td>
                                    <td>
                                        <button onclick="printPage()" class="btn btn-outline btn-sm"><i class="fa-solid fa-print"></i> Print Invoice</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty bills}">
                                <tr><td colspan="8" style="text-align: center; color: var(--gray-700);">No billing records found.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
