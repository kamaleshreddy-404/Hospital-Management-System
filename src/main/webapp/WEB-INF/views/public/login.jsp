<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Portal Login - Smart HMS"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div style="min-height: 100vh; display: flex; align-items: center; justify-content: center; background-color: var(--gray-100); padding: 20px;">
    <div style="width: 100%; max-width: 440px; background: var(--white); border: 1px solid var(--gray-200); border-radius: var(--radius-lg); padding: 32px; box-shadow: var(--shadow-md);">
        
        <div style="text-align: center; margin-bottom: 24px;">
            <div style="width: 56px; height: 56px; background-color: var(--primary-light); color: var(--primary); border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 24px; margin: 0 auto 12px;">
                <i class="fa-solid fa-hospital-user"></i>
            </div>
            <h2 style="font-size: 22px; font-weight: 700; color: var(--gray-900);">Smart Hospital Portal</h2>
            <p style="font-size: 13px; color: var(--gray-700); margin-top: 4px;">Sign in to access your role dashboard</p>
        </div>

        <c:if test="${not empty param.msg}">
            <div class="alert alert-success"><i class="fa-solid fa-circle-check"></i> ${param.msg}</div>
        </c:if>

        <c:if test="${not empty param.error || not empty error}">
            <div class="alert alert-danger"><i class="fa-solid fa-triangle-exclamation"></i> ${not empty error ? error : param.error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email"><i class="fa-solid fa-envelope"></i> Email Address</label>
                <input type="email" id="email" name="email" class="form-control" required placeholder="name@hospital.com">
            </div>

            <div class="form-group">
                <label for="password"><i class="fa-solid fa-key"></i> Password</label>
                <input type="password" id="password" name="password" class="form-control" required placeholder="••••••••">
            </div>

            <button type="submit" class="btn btn-primary" style="width: 100%; justify-content: center; padding: 10px; font-size: 15px; margin-top: 8px;">
                <i class="fa-solid fa-right-to-bracket"></i> Login to Portal
            </button>
        </form>

        <div style="margin-top: 24px; padding-top: 16px; border-top: 1px solid var(--gray-200); font-size: 13px;">
            <p style="font-weight: 600; color: var(--gray-700); margin-bottom: 8px; text-align: center;">Demo Login Shortcuts:</p>
            <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 6px;">
                <button type="button" onclick="fillCreds('admin@hospital.com', 'admin123')" class="btn btn-outline btn-sm">Admin</button>
                <button type="button" onclick="fillCreds('doctor@hospital.com', 'doctor123')" class="btn btn-outline btn-sm">Doctor</button>
                <button type="button" onclick="fillCreds('reception@hospital.com', 'reception123')" class="btn btn-outline btn-sm">Reception</button>
                <button type="button" onclick="fillCreds('pharma@hospital.com', 'pharma123')" class="btn btn-outline btn-sm">Pharma</button>
                <button type="button" onclick="fillCreds('patient@hospital.com', 'patient123')" class="btn btn-outline btn-sm">Patient</button>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary btn-sm" style="justify-content: center;">Register</a>
            </div>
        </div>

        <div style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/home" style="font-size: 13px; color: var(--gray-700);">
                <i class="fa-solid fa-arrow-left"></i> Back to Hospital Website
            </a>
        </div>
    </div>
</div>

<script>
function fillCreds(e, p) {
    document.getElementById('email').value = e;
    document.getElementById('password').value = p;
}
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
