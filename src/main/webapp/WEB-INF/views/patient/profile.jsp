<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Medical Profile - Patient Portal"/>
<c:set var="pagePath" value="patient-profile"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body" style="max-width: 800px;">
            <c:if test="${not empty param.msg}">
                <div class="alert alert-success"><i class="fa-solid fa-circle-check"></i> ${param.msg}</div>
            </c:if>

            <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">Personal Health & Profile Information</h2>

            <div class="card-panel">
                <form action="${pageContext.request.contextPath}/patient/profile" method="post">
                    <input type="hidden" name="action" value="updateProfile">

                    <div class="form-row">
                        <div class="form-group">
                            <label>Full Name *</label>
                            <input type="text" name="name" class="form-control" value="${patient.name}" required>
                        </div>
                        <div class="form-group">
                            <label>Phone Number *</label>
                            <input type="tel" name="phone" class="form-control" value="${patient.phone}" required pattern="[0-9]{10}">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Gender</label>
                            <input type="text" class="form-control" value="${patient.gender}" disabled style="background: var(--gray-100);">
                        </div>
                        <div class="form-group">
                            <label>Age</label>
                            <input type="text" class="form-control" value="${patient.age} Yrs" disabled style="background: var(--gray-100);">
                        </div>
                        <div class="form-group">
                            <label>Blood Group</label>
                            <input type="text" class="form-control" value="${patient.bloodGroup}" disabled style="background: var(--gray-100);">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Emergency Contact Phone</label>
                        <input type="tel" name="emergencyContact" class="form-control" value="${patient.emergencyContact}">
                    </div>

                    <div class="form-group">
                        <label>Residential Address</label>
                        <textarea name="address" class="form-control" rows="3">${patient.address}</textarea>
                    </div>

                    <button type="submit" class="btn btn-primary" style="padding: 10px 24px;"><i class="fa-solid fa-check"></i> Save Profile Updates</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
