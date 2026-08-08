<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Patient Registration - Receptionist Portal"/>
<c:set var="pagePath" value="reception-reg"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body" style="max-width: 800px;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                <h2 style="font-size: 22px; font-weight: 700;">Walk-in Patient Intake Registration</h2>
                <a href="${pageContext.request.contextPath}/receptionist/dashboard" class="btn btn-secondary"><i class="fa-solid fa-arrow-left"></i> Back to Reception</a>
            </div>

            <div class="card-panel">
                <form action="${pageContext.request.contextPath}/receptionist/register-patient" method="post">
                    <input type="hidden" name="action" value="registerPatient">

                    <div class="form-row">
                        <div class="form-group">
                            <label>Patient Full Name *</label>
                            <input type="text" name="name" class="form-control" required placeholder="e.g. Robert Smith">
                        </div>
                        <div class="form-group">
                            <label>Contact Phone Number *</label>
                            <input type="tel" name="phone" class="form-control" required pattern="[0-9]{10}" placeholder="10-digit mobile">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Gender *</label>
                            <select name="gender" class="form-control" required>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Age (Years) *</label>
                            <input type="number" name="age" class="form-control" required min="1" max="120" placeholder="e.g. 45">
                        </div>
                        <div class="form-group">
                            <label>Blood Group *</label>
                            <select name="bloodGroup" class="form-control" required>
                                <option value="O+">O+</option>
                                <option value="A+">A+</option>
                                <option value="B+">B+</option>
                                <option value="AB+">AB+</option>
                                <option value="O-">O-</option>
                                <option value="A-">A-</option>
                                <option value="B-">B-</option>
                                <option value="AB-">AB-</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Email Address</label>
                            <input type="email" name="email" class="form-control" placeholder="patient@example.com">
                        </div>
                        <div class="form-group">
                            <label>Emergency Contact Phone</label>
                            <input type="tel" name="emergencyContact" class="form-control" placeholder="Emergency relative mobile">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Residential Address</label>
                        <textarea name="address" class="form-control" rows="2" placeholder="Full residential street address"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary" style="padding: 12px 24px;"><i class="fa-solid fa-check"></i> Register Patient Record</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
