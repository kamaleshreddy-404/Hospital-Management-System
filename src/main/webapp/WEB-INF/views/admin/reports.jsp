<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Reports - Admin Portal"/>
<c:set var="pagePath" value="admin-reports"/>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="app-container">
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="main-content">
        <%@ include file="/WEB-INF/views/common/navbar.jsp" %>

        <div class="page-body">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
                <h2 style="font-size: 22px; font-weight: 700;">Executive Hospital Reports</h2>
                <button onclick="printPage()" class="btn btn-outline"><i class="fa-solid fa-print"></i> Print Report Summary</button>
            </div>

            <div class="card-panel printable-document">
                <h3 style="color: var(--primary); margin-bottom: 16px;"><i class="fa-solid fa-chart-pie"></i> Operational & Revenue Summary</h3>
                
                <div class="grid-3" style="margin-bottom: 24px;">
                    <div style="background: var(--gray-100); padding: 16px; border-radius: var(--radius);">
                        <div style="font-size: 13px; color: var(--gray-700);">Total Revenue Collections</div>
                        <div style="font-size: 24px; font-weight: 700; color: var(--success); margin-top: 4px;">₹${stats.totalRevenue}</div>
                    </div>
                    <div style="background: var(--gray-100); padding: 16px; border-radius: var(--radius);">
                        <div style="font-size: 13px; color: var(--gray-700);">Today's Revenue</div>
                        <div style="font-size: 24px; font-weight: 700; color: var(--primary); margin-top: 4px;">₹${stats.todayRevenue}</div>
                    </div>
                    <div style="background: var(--gray-100); padding: 16px; border-radius: var(--radius);">
                        <div style="font-size: 13px; color: var(--gray-700);">Low Stock Medicine Alert</div>
                        <div style="font-size: 24px; font-weight: 700; color: var(--danger); margin-top: 4px;">${stats.lowStockCount} Items</div>
                    </div>
                </div>

                <h4 style="margin-bottom: 12px; margin-top: 24px;">Pharmacy Low Stock Inspection</h4>
                <div class="table-responsive">
                    <table class="custom-table">
                        <thead>
                            <tr>
                                <th>Medicine Name</th>
                                <th>Category</th>
                                <th>Current Stock</th>
                                <th>Price</th>
                                <th>Expiry Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${lowStockMedicines}">
                                <tr>
                                    <td style="font-weight: 600; color: var(--danger);">${m.name}</td>
                                    <td>${m.category}</td>
                                    <td><span class="badge badge-cancelled">${m.stockQuantity} Units</span></td>
                                    <td>₹${m.price}</td>
                                    <td>${m.expiryDate}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty lowStockMedicines}">
                                <tr><td colspan="5" style="text-align: center; color: var(--success);">All pharmacy inventory levels are adequate.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
