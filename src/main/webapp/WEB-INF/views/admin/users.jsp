<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="System Users - Admin Portal"/>
<c:set var="pagePath" value="admin-users"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">System Accounts & Role Management</h2>

            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-users-gear"></i> System User Accounts (${users.size()})</h3>
                    <input type="text" id="userSearch" onkeyup="filterTable('userSearch', 'userTable')" class="form-control" placeholder="Search user name or email..." style="width: 280px;">
                </div>

                <div class="table-responsive">
                    <table class="custom-table" id="userTable">
                        <thead>
                            <tr>
                                <th>User ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Assigned Role</th>
                                <th>Created At</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td>#USR-${u.userId}</td>
                                    <td style="font-weight: 600;">${u.name}</td>
                                    <td>${u.email}</td>
                                    <td>${u.phone}</td>
                                    <td><span class="badge badge-confirmed">${u.roleName}</span></td>
                                    <td style="font-size: 13px;">${u.createdAt}</td>
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
