<%@ page import="model.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 06/12/2024
  Time: 11:59 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Dashboard</title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>

<%-- Kiểm tra người dùng đã đăng nhập hay chưa --%>
<%
  User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  if (!"user".equals(user.getRole())) {
    response.sendRedirect("admin-dashboard.jsp");
    return;
  }
%>

<%-- Hiển thị thông tin người dùng --%>
<div class="header">
  <h1>Welcome to the User Dashboard</h1>
  <p>Hi, <%= user.getFullName() %>! You are logged in as <%= user.getRole() %>.</p>
  <a href="/logout">Logout</a>
</div>

<%-- Nội dung dành cho user --%>
<div class="user-dashboard">
  <h2>User Dashboard</h2>
  <p>Welcome, you can browse products and view your orders.</p>
  <ul>
    <li><a href="view-products.jsp">View Products</a></li>
    <li><a href="view-orders.jsp">View My Orders</a></li>
  </ul>
</div>

</body>
</html>

