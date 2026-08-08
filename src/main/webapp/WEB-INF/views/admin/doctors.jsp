<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Manage Doctors - Admin Portal"/>
<c:set var="pagePath" value="admin-doctors"/>
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
                <h2 style="font-size: 22px; font-weight: 700;">Doctor Directory & Staff Management</h2>
                <button onclick="document.getElementById('addDocModal').style.display='block'" class="btn btn-primary">
                    <i class="fa-solid fa-user-plus"></i> Add New Doctor
                </button>
            </div>

            <!-- Doctor Add Form Card / Modal -->
            <div id="addDocModal" class="card-panel" style="display: none; background: #fff; border: 2px solid var(--primary);">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-user-doctor"></i> Add Doctor Profile</h3>
                    <button onclick="document.getElementById('addDocModal').style.display='none'" class="btn btn-secondary btn-sm">Close</button>
                </div>
                <form action="${pageContext.request.contextPath}/admin/doctors" method="post">
                    <input type="hidden" name="action" value="addDoctor">
                    <div class="form-row">
                        <div class="form-group">
                            <label>Doctor Full Name *</label>
                            <input type="text" name="name" class="form-control" required placeholder="Dr. Rajesh Sharma">
                        </div>
                        <div class="form-group">
                            <label>Email Address *</label>
                            <input type="email" name="email" class="form-control" required placeholder="doctor@hospital.com">
                        </div>
                        <div class="form-group">
                            <label>Phone Number *</label>
                            <input type="text" name="phone" class="form-control" required placeholder="10-digit mobile">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Qualification *</label>
                            <input type="text" name="qualification" class="form-control" required placeholder="e.g. MD, DM Cardiology">
                        </div>
                        <div class="form-group">
                            <label>Specialization *</label>
                            <input type="text" name="specialization" class="form-control" required placeholder="e.g. Cardiology">
                        </div>
                        <div class="form-group">
                            <label>Experience (Years) *</label>
                            <input type="number" name="experienceYears" class="form-control" required placeholder="e.g. 10">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Department *</label>
                            <select name="deptId" class="form-control" required>
                                <c:forEach var="dept" items="${departments}">
                                    <option value="${dept.deptId}">${dept.deptName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Consultation Fee (₹) *</label>
                            <input type="number" step="50" name="consultationFee" class="form-control" required placeholder="e.g. 700">
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary"><i class="fa-solid fa-check"></i> Save Doctor Record</button>
                </form>
            </div>

            <!-- Doctors Table -->
            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title">Registered Doctors</h3>
                    <input type="text" id="docSearch" onkeyup="filterTable('docSearch', 'docTable')" class="form-control" placeholder="Search doctor name..." style="width: 260px;">
                </div>

                <div class="table-responsive">
                    <table class="custom-table" id="docTable">
                        <thead>
                            <tr>
                                <th>Doctor ID</th>
                                <th>Name</th>
                                <th>Specialization</th>
                                <th>Department</th>
                                <th>Phone / Email</th>
                                <th>Fee</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="doc" items="${doctors}">
                                <tr>
                                    <td>#DOC-${doc.doctorId}</td>
                                    <td style="font-weight: 600;">${doc.name}</td>
                                    <td><span class="badge badge-confirmed">${doc.specialization}</span></td>
                                    <td>${doc.deptName}</td>
                                    <td style="font-size: 13px;">${doc.phone}<br><span style="color: var(--gray-700);">${doc.email}</span></td>
                                    <td style="font-weight: 700; color: var(--success);">₹${doc.consultationFee}</td>
                                    <td><span class="badge badge-completed">${doc.status}</span></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/admin/doctors" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to remove this doctor?');">
                                            <input type="hidden" name="action" value="deleteDoctor">
                                            <input type="hidden" name="doctorId" value="${doc.doctorId}">
                                            <button type="submit" class="btn btn-danger btn-sm"><i class="fa-solid fa-trash"></i> Delete</button>
                                        </form>
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
