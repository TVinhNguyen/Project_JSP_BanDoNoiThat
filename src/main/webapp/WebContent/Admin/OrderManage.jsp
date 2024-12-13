<%@ page import="java.util.List" %>
<%@ page import="model.bean.Order" %>
<%@ page import="model.bean.OrderDetail" %>
<%@ page import="model.dto.OrderView" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/WebContent/Admin/css/style.css">
</head>
<body>
<div class="dashboard-container">
    <%@ include file="dashboard.jsp" %>

    <div class="main-content" id="mainContent">
        <h2>Manage Orders</h2>
        <button class="btn-add">Add Order</button>
        <table>
            <thead>
            <tr>
                <th>Order ID</th>
                <th>Customer Name</th>
                <th>Order Date</th>
                <th>Total Amount</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<OrderView> orders = (List<OrderView>) request.getAttribute("orders");
                if (orders != null && !orders.isEmpty()) {
                    for (OrderView order : orders) {
            %>
            <tr>
                <td><%= order.getOrderId() %></td>
                <td><%= order.getCustomerName() %></td>
                <td><%= order.getOrderDate() %></td>
                <td><%= order.getTotalAmount() %></td>
                <td>
                    <a href="/admin/OrderManage/orderDetails?orderId=<%= order.getOrderId() %>">
                        <button class="btn-view-details">View Details</button>
                    </a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5">Không có đơn hàng nào phù hợp.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
