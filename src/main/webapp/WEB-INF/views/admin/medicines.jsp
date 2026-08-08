<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Pharmacy Stock - Admin Portal"/>
<c:set var="pagePath" value="admin-medicines"/>
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
                <h2 style="font-size: 22px; font-weight: 700;">Pharmacy Inventory & Medicine Stock</h2>
                <button onclick="document.getElementById('addMedModal').style.display='block'" class="btn btn-primary">
                    <i class="fa-solid fa-plus"></i> Add New Medicine
                </button>
            </div>

            <!-- Add Medicine Modal -->
            <div id="addMedModal" class="card-panel" style="display: none; border: 2px solid var(--primary);">
                <div class="card-header">
                    <h3 class="card-title">Add Medicine to Stock</h3>
                    <button onclick="document.getElementById('addMedModal').style.display='none'" class="btn btn-secondary btn-sm">Close</button>
                </div>
                <form action="${pageContext.request.contextPath}/admin/medicines" method="post">
                    <input type="hidden" name="action" value="addMedicine">
                    <div class="form-row">
                        <div class="form-group">
                            <label>Medicine Name *</label>
                            <input type="text" name="name" class="form-control" required placeholder="e.g. Paracetamol 650mg">
                        </div>
                        <div class="form-group">
                            <label>Category *</label>
                            <input type="text" name="category" class="form-control" required placeholder="e.g. Analgesic">
                        </div>
                        <div class="form-group">
                            <label>Manufacturer</label>
                            <input type="text" name="manufacturer" class="form-control" placeholder="e.g. Cipla">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Price per Unit (₹) *</label>
                            <input type="number" step="0.5" name="price" class="form-control" required placeholder="25.00">
                        </div>
                        <div class="form-group">
                            <label>Stock Quantity *</label>
                            <input type="number" name="stockQuantity" class="form-control" required placeholder="100">
                        </div>
                        <div class="form-group">
                            <label>Expiry Date *</label>
                            <input type="date" name="expiryDate" class="form-control" required>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary"><i class="fa-solid fa-check"></i> Add Medicine</button>
                </form>
            </div>

            <div class="card-panel">
                <div class="card-header">
                    <h3 class="card-title"><i class="fa-solid fa-pills"></i> Inventory Registry</h3>
                    <input type="text" id="medSearch" onkeyup="filterTable('medSearch', 'medTable')" class="form-control" placeholder="Search medicine name..." style="width: 260px;">
                </div>

                <div class="table-responsive">
                    <table class="custom-table" id="medTable">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Medicine Name</th>
                                <th>Category</th>
                                <th>Manufacturer</th>
                                <th>Unit Price</th>
                                <th>Stock Qty</th>
                                <th>Expiry Date</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${medicines}">
                                <tr>
                                    <td>#MED-${m.medicineId}</td>
                                    <td style="font-weight: 600;">${m.name}</td>
                                    <td>${m.category}</td>
                                    <td>${m.manufacturer}</td>
                                    <td style="font-weight: 700; color: var(--success);">₹${m.price}</td>
                                    <td>
                                        <span class="badge ${m.stockQuantity <= 50 ? 'badge-cancelled' : 'badge-completed'}">
                                            ${m.stockQuantity} Units ${m.stockQuantity <= 50 ? '(LOW STOCK)' : ''}
                                        </span>
                                    </td>
                                    <td>${m.expiryDate}</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/admin/medicines" method="post" onsubmit="return confirm('Remove medicine?');">
                                            <input type="hidden" name="action" value="deleteMedicine">
                                            <input type="hidden" name="medicineId" value="${m.medicineId}">
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
