<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="About Hospital - Smart HMS"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<header class="public-navbar">
    <div class="nav-brand">
        <i class="fa-solid fa-hospital-user" style="font-size: 24px; color: var(--primary);"></i> Smart Hospital
    </div>
    <nav style="display: flex; gap: 24px; font-weight: 600; align-items: center;">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/about" style="color: var(--primary);">About</a>
        <a href="${pageContext.request.contextPath}/services">Services</a>
        <a href="${pageContext.request.contextPath}/doctors">Doctors</a>
        <a href="${pageContext.request.contextPath}/departments">Departments</a>
        <a href="${pageContext.request.contextPath}/contact">Contact</a>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary"><i class="fa-solid fa-right-to-bracket"></i> Portal Login</a>
    </nav>
</header>

<div class="page-body" style="max-width: 960px; margin: 40px auto;">
    <div class="card-panel">
        <h2 style="font-size: 26px; color: var(--primary); margin-bottom: 16px;"><i class="fa-solid fa-circle-info"></i> About Smart Hospital</h2>
        <p style="margin-bottom: 16px; line-height: 1.7;">
            Smart Hospital is a modern multi-specialty healthcare institution dedicated to delivering compassionate clinical care, precise diagnostics, and seamlessly managed medical services.
        </p>
        <p style="margin-bottom: 24px; line-height: 1.7;">
            This system was engineered as a CS Final Year Major Project demonstrating standard Java Web application development, Model-View-Controller (MVC) architecture, DAO design patterns, secure database integration via JDBC, and role-based portal security.
        </p>

        <h3 style="margin-bottom: 12px;">Core Architecture & Technical Stack</h3>
        <ul style="margin-left: 24px; line-height: 1.8; color: var(--gray-700);">
            <li><strong>Frontend:</strong> HTML5, CSS3, Vanilla JavaScript, JSP, JSTL</li>
            <li><strong>Backend:</strong> Java EE / Jakarta Servlets, JDBC, Java Beans POJOs</li>
            <li><strong>Database:</strong> MySQL Database with prepared statements (H2 Fallback mode)</li>
            <li><strong>Security:</strong> SHA-256 Password Hashing & WebFilter Auth Enforcement</li>
        </ul>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
