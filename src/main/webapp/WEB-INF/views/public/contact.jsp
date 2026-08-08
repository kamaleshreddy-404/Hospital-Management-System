<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Contact Us - Smart HMS"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<header class="public-navbar">
    <div class="nav-brand">
        <i class="fa-solid fa-hospital-user" style="font-size: 24px; color: var(--primary);"></i> Smart Hospital
    </div>
    <nav style="display: flex; gap: 24px; font-weight: 600; align-items: center;">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/services">Services</a>
        <a href="${pageContext.request.contextPath}/doctors">Doctors</a>
        <a href="${pageContext.request.contextPath}/departments">Departments</a>
        <a href="${pageContext.request.contextPath}/contact" style="color: var(--primary);">Contact</a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary"><i class="fa-solid fa-right-to-bracket"></i> Portal Login</a>
    </nav>
</header>

<div class="page-body" style="max-width: 900px; margin: 40px auto;">
    <div class="card-panel">
        <h2 style="font-size: 26px; color: var(--primary); margin-bottom: 16px;"><i class="fa-solid fa-headset"></i> Hospital Contact & Location</h2>
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 24px;">
            <div>
                <h4 style="margin-bottom: 8px;">Address:</h4>
                <p style="color: var(--gray-700); font-size: 14px;">123 Healthcare Boulevard, Tech City, Karnataka 560001</p>

                <h4 style="margin-top: 16px; margin-bottom: 8px;">Emergency Helpline:</h4>
                <p style="color: var(--danger); font-weight: 700; font-size: 18px;"><i class="fa-solid fa-phone-volume"></i> 1800-123-9999 / 102</p>

                <h4 style="margin-top: 16px; margin-bottom: 8px;">Email Enquiry:</h4>
                <p style="color: var(--gray-700); font-size: 14px;">contact@smarthospital.org</p>
            </div>
            <div>
                <form onsubmit="alert('Thank you for contacting Smart Hospital. Our team will get back to you shortly.'); return false;">
                    <div class="form-group">
                        <label>Your Name</label>
                        <input type="text" class="form-control" required placeholder="Enter full name">
                    </div>
                    <div class="form-group">
                        <label>Email Address</label>
                        <input type="email" class="form-control" required placeholder="Enter email">
                    </div>
                    <div class="form-group">
                        <label>Message</label>
                        <textarea class="form-control" rows="3" required placeholder="Write your inquiry..."></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary" style="width: 100%;">Send Message</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
