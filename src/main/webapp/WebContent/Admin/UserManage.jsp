<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 12/8/2024
  Time: 12:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.User" %>
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
        <h2>Manage Users</h2>
        <button class="btn-add">Add User</button>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Role</th>
                <th>Actions</th>
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
                <td>
                    <button class="btn-edit">Edit</button>
                    <button class="btn-delete">Delete</button>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="8">Không có người dùng nào phù hợp.</td>
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
