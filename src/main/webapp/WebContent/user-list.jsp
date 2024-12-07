<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 07/12/2024
  Time: 2:54 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="model.bean.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách người dùng</title>
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
        .actions {
            text-align: center;
        }
        .actions a {
            margin: 0 5px;
            text-decoration: none;
            color: blue;
        }
    </style>
</head>
<body>
<h1>Danh sách người dùng</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên người dùng</th>
        <th>Họ và tên</th>
        <th>Email</th>
        <th>Số điện thoại</th>
        <th>Địa chỉ</th>
        <th>Quyền</th>
        <th>Thao tác</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
    %>
    <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getUsername() %></td>
        <td><%= user.getFullName() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getPhone() %></td>
        <td><%= user.getAddress() %></td>
        <td><%= user.getRole() %></td>
        <td class="actions">
            <a href="/Users/<%= user.getId() %>">Xem</a>
<%--            <a href="/Users/edit/<%= user.getId() %>">Sửa</a>--%>
<%--            <a href="/Users/delete/<%= user.getId() %>" onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng này không?')">Xóa</a>--%>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="8">Không có người dùng nào.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>

