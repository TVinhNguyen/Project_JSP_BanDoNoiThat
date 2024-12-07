<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 07/12/2024
  Time: 2:51 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="model.bean.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin người dùng</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>Thông tin người dùng</h1>
<%
    User user = (User) request.getAttribute("user");
    if (user != null) {
%>
<table>
    <tr>
        <th>ID</th>
        <td><%= user.getId() %></td>
    </tr>
    <tr>
        <th>Tên người dùng</th>
        <td><%= user.getUsername() %></td>
    </tr>
    <tr>
        <th>Họ và Tên</th>
        <td><%= user.getFullName() %></td>
    </tr>
    <tr>
        <th>Email</th>
        <td><%= user.getEmail() %></td>
    </tr>
    <tr>
        <th>Số điện thoại</th>
        <td><%= user.getPhone() %></td>
    </tr>
    <tr>
        <th>Địa chỉ</th>
        <td><%= user.getAddress() %></td>
    </tr>
</table>
<%
} else {
%>
<p>Không tìm thấy thông tin người dùng.</p>
<%
    }
%>
</body>
</html>
