<%@ page import="model.bean.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div id="overlayAddUser" class="overlay">
    <div class="overlay-content">
        <button type="button" id="btnCloseAddOverlay" class="btn-close">
            <i class="fas fa-times"></i>
        </button>
        <h2 id="overlayTitle">Add User</h2>
        <form action="/admin/UserManage/create" method="post" id="userForm">
            <input type="hidden" id="userId" name="userId">

            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <input type="hidden" id="role" name="role" value="user">

            <div class="form-group">
                <label for="fullName">Full Name:</label>
                <input type="text" id="fullName" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="tel" id="phone" name="phone" required>
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <textarea id="address" name="address" rows="4" required></textarea>
            </div>
            <div class="form-group">
                <button type="submit" class="btn-submit">Save User</button>
            </div>
        </form>
    </div>
</div>
