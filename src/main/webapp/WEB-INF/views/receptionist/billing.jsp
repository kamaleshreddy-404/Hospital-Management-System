<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Billing & Invoicing - Receptionist Portal"/>
<c:set var="pagePath" value="reception-billing"/>
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
                <h2 style="font-size: 22px; font-weight: 700;">Patient Billing & Invoice Generation</h2>
                <button onclick="document.getElementById('genBillModal').style.display='block'" class="btn btn-primary">
                    <i class="fa-solid fa-receipt"></i> Create Patient Invoice
                </button>
            </div>

            <!-- Create Invoice Modal -->
            <div id="genBillModal" class="card-panel" style="display: none; border: 2px solid var(--primary);">
                <div class="card-header">
                    <h3 class="card-title">Generate Billing Invoice</h3>
                    <button onclick="document.getElementById('genBillModal').style.display='none'" class="btn btn-secondary btn-sm">Close</button>
                </div>
                <form action="${pageContext.request.contextPath}/receptionist/billing" method="post">
                    <input type="hidden" name="action" value="generateBill">

                    <div class="form-row">
                        <div class="form-group">
                            <label>Patient *</label>
                            <select name="patientId" class="form-control" required>
                                <c:forEach var="p" items="${patients}">
                                    <option value="${p.patientId}">${p.name} (ID: #PAT-${p.patientId})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Associated Appointment (Optional)</label>
                            <select name="appointmentId" class="form-control">
                                <option value="">-- None / Walk-in --</option>
                                <c:forEach var="app" items="${appointments}">
                                    <option value="${app.appointmentId}">#APT-${app.appointmentId} - ${app.patientName} (${app.doctorName})</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Consultation Fee (₹)</label>
                            <input type="number" step="10" name="consultationFee" class="form-control" value="500">
                        </div>
                        <div class="form-group">
                            <label>Diagnostic Test Charges (₹)</label>
                            <input type="number" step="10" name="testCharges" class="form-control" value="0">
                        </div>
                        <div class="form-group">
                            <label>Pharmacy Charges (₹)</label>
                            <input type="number" step="10" name="medicineCharges" class="form-control" value="0">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Payment Status</label>
                            <select name="paymentStatus" class="form-control">
                                <option value="Paid">Paid</option>
                                <option value="Unpaid">Unpaid</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Payment Method</label>
                            <select name="paymentMethod" class="form-control">
                                <option value="Cash">Cash</option>
                                <option value="Credit Card">Credit Card / Debit Card</option>
                                <option value="UPI">UPI / Net Banking</option>
                                <option value="Insurance">Health Insurance</option>
                            </select>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary"><i class="fa-solid fa-check"></i> Generate Invoice</button>
                </form>
            </div>

            <!-- Billing Invoices Table -->
            <div class="card-panel">
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>Invoice ID</th>
                                <th>Patient Name</th>
                                <th>Consultation</th>
                                <th>Tests</th>
                                <th>Medicines</th>
                                <th>Total Amount</th>
                                <th>Status</th>
                                <th>Date</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${bills}">
                                <tr>
                                    <td>#INV-${b.billId}</td>
                                    <td style="font-weight: 600;">${b.patientName}</td>
                                    <td>₹${b.consultationFee}</td>
                                    <td>₹${b.testCharges}</td>
                                    <td>₹${b.medicineCharges}</td>
                                    <td style="font-weight: 700; color: var(--primary);">₹${b.totalAmount}</td>
                                    <td><span class="badge ${b.paymentStatus == 'Paid' ? 'badge-paid' : 'badge-unpaid'}">${b.paymentStatus}</span></td>
                                    <td>${b.billDate}</td>
                                    <td>
                                        <button onclick="printPage()" class="btn btn-outline btn-sm"><i class="fa-solid fa-print"></i> Print</button>
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
