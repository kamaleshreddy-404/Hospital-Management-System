<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Manage Patients - Admin Portal"/>
<c:set var="pagePath" value="admin-patients"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">Patient Medical Records Directory</h2>

            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-procedures"></i> Registered Patients (${patients.size()})</h3>
                    <input type="text" id="patientSearch" onkeyup="filterTable('patientSearch', 'patientTable')" class="form-control" placeholder="Search by name, phone, blood group..." style="width: 300px;">
                </div>

                <div class="table-responsive">
                    <table class="custom-table" id="patientTable">
                        <thead>
                            <tr>
                                <th>Patient ID</th>
                                <th>Full Name</th>
                                <th>Gender / Age</th>
                                <th>Blood Group</th>
                                <th>Contact Phone</th>
                                <th>Emergency Contact</th>
                                <th>Address</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${patients}">
                                <tr>
                                    <td>#PAT-${p.patientId}</td>
                                    <td style="font-weight: 600;">${p.name}</td>
                                    <td>${p.gender}, ${p.age} Yrs</td>
                                    <td><span class="badge badge-pending" style="color: var(--danger); background: var(--danger-bg);">${p.bloodGroup}</span></td>
                                    <td>${p.phone}</td>
                                    <td style="color: var(--danger); font-weight: 600;">${p.emergencyContact}</td>
                                    <td style="font-size: 13px;">${p.address}</td>
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
