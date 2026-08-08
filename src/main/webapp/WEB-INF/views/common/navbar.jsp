<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="top-navbar">
    <div class="nav-brand">
        <i class="fa-solid fa-hospital"></i> Smart Hospital Portal
    </div>
    <div class="user-profile-badge">
        <div class="avatar-circle">
            ${sessionScope.user != null ? sessionScope.user.name.substring(0, 1) : 'U'}
        </div>
        <div>
            <div style="font-weight: 600; font-size: 14px;">${sessionScope.user != null ? sessionScope.user.name : 'User'}</div>
            <div style="font-size: 12px; color: var(--gray-700);">${sessionScope.user != null ? sessionScope.user.roleName : 'Guest'}</div>
        </div>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline btn-sm" style="margin-left: 12px;">
            <i class="fa-solid fa-right-from-bracket"></i> Logout
        </a>
    </div>
</header>
