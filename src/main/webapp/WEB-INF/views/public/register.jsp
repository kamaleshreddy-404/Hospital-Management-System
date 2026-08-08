<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Patient Registration - Smart HMS"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div style="min-height: 100vh; display: flex; align-items: center; justify-content: center; background-color: var(--gray-100); padding: 40px 20px;">
    <div style="width: 100%; max-width: 600px; background: var(--white); border: 1px solid var(--gray-200); border-radius: var(--radius-lg); padding: 32px; box-shadow: var(--shadow-md);">
        
        <div style="text-align: center; margin-bottom: 24px;">
            <h2 style="font-size: 24px; font-weight: 700; color: var(--primary);"><i class="fa-solid fa-user-plus"></i> Patient Account Registration</h2>
            <p style="font-size: 13px; color: var(--gray-700); margin-top: 4px;">Create your personal medical portal profile</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger"><i class="fa-solid fa-triangle-exclamation"></i> ${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label>Full Name *</label>
                    <input type="text" name="name" class="form-control" required placeholder="John Doe">
                </div>
                <div class="form-group">
                    <label>Email Address *</label>
                    <input type="email" name="email" class="form-control" required placeholder="john@example.com">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Password *</label>
                    <input type="password" name="password" class="form-control" required minlength="6" placeholder="••••••••">
                </div>
                <div class="form-group">
                    <label>Phone Number *</label>
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
                    <label>Age *</label>
                    <input type="number" name="age" class="form-control" required min="1" max="120" placeholder="e.g. 28">
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

            <div class="form-group">
                <label>Address</label>
                <input type="text" name="address" class="form-control" placeholder="Street Address, City">
            </div>

            <div class="form-group">
                <label>Emergency Contact Phone</label>
                <input type="tel" name="emergencyContact" class="form-control" placeholder="Emergency contact mobile">
            </div>

            <button type="submit" class="btn btn-primary" style="width: 100%; justify-content: center; padding: 12px; font-size: 15px; margin-top: 8px;">
                <i class="fa-solid fa-check"></i> Register Account
            </button>
        </form>

        <div style="text-align: center; margin-top: 20px;">
            Already registered? <a href="${pageContext.request.contextPath}/login">Login Here</a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
