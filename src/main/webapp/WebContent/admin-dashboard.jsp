<%@ page import="model.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 06/12/2024
  Time: 11:57 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    if (!"admin".equals(user.getRole())) {
        response.sendRedirect("user-dashboard.jsp");
        return;
    }
%>

<%-- Hiển thị thông tin người dùng --%>
<div class="header">
    <h1>Welcome to the Admin Dashboard</h1>
    <p>Hi, <%= user.getFullName() %>! You are logged in as <%= user.getRole() %>.</p>
    <a href="<%= request.getRequestURI()+"/logout" %>">Logout</a>
</div>

]<div class="admin-dashboard">
    <h2>Admin Dashboard</h2>
    <p>As an admin, you have access to manage users, products, and orders.</p>
    <ul>
        <li><a href="<%= request.getRequestURI()+"/viewProduct"%>">Manage Products</a></li>
        <li><a href="manage-products.jsp">Manage Products</a></li>
        <li><a href="manage-orders.jsp">Manage Orders</a></li>
    </ul>
</div>

</body>
</html>

