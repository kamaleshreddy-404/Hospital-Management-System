<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Manage Departments - Admin Portal"/>
<c:set var="pagePath" value="admin-depts"/>
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
                <h2 style="font-size: 22px; font-weight: 700;">Hospital Clinical Departments</h2>
                <button onclick="document.getElementById('addDeptModal').style.display='block'" class="btn btn-primary">
                    <i class="fa-solid fa-plus"></i> Add Department
                </button>
            </div>

            <!-- Add Department Modal -->
            <div id="addDeptModal" class="card-panel" style="display: none; border: 2px solid var(--primary);">
                <div class="card-header">
                    <h3 class="card-title">Add Department</h3>
                    <button onclick="document.getElementById('addDeptModal').style.display='none'" class="btn btn-secondary btn-sm">Close</button>
                </div>
                <form action="${pageContext.request.contextPath}/admin/departments" method="post">
                    <input type="hidden" name="action" value="addDepartment">
                    <div class="form-row">
                        <div class="form-group">
                            <label>Department Name *</label>
                            <input type="text" name="deptName" class="form-control" required placeholder="e.g. ENT, Dermatology">
                        </div>
                        <div class="form-group">
                            <label>Head Doctor Name</label>
                            <input type="text" name="headDoctorName" class="form-control" placeholder="e.g. Dr. Rajesh Sharma">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea name="description" class="form-control" rows="2" placeholder="Brief description..."></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="fa-solid fa-check"></i> Save Department</button>
                </form>
            </div>

            <div class="card-panel">
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>Dept ID</th>
                                <th>Department Name</th>
                                <th>Description</th>
                                <th>Head Doctor</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dept" items="${departments}">
                                <tr>
                                    <td>#DEPT-${dept.deptId}</td>
                                    <td style="font-weight: 600; color: var(--primary);">${dept.deptName}</td>
                                    <td>${dept.description}</td>
                                    <td style="font-weight: 600;">${dept.headDoctorName != null ? dept.headDoctorName : 'N/A'}</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/admin/departments" method="post" onsubmit="return confirm('Delete department?');">
                                            <input type="hidden" name="action" value="deleteDepartment">
                                            <input type="hidden" name="deptId" value="${dept.deptId}">
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
