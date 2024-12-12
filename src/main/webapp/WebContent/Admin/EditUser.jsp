<%@ page import="model.bean.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div id="overlayEditUser" class="overlay">
    <div class="overlay-content">
        <button type="button" id="btnCloseEditOverlay" class="btn-close">
            <i class="fas fa-times"></i>
        </button>
        <h2 id="overlayTitle">Edit User</h2>
        <form action="/admin/UserManage/update" method="post" id="userForm">
            <input type="hidden" id="userIde" name="userId">

            <div class="form-group">
                <label for="usernamee">Username:</label>
                <input type="text" id="usernamee" name="username" required>
            </div>
            <div class="form-group">
                <label for="passworde">Password:</label>
                <input type="password" id="passworde" name="password" required>
            </div>
            <input type="hidden" id="role" name="role" value="user">

            <div class="form-group">
                <label for="fullNamee">Full Name:</label>
                <input type="text" id="fullNamee" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="emaile">Email:</label>
                <input type="email" id="emaile" name="email" required>
            </div>
            <div class="form-group">
                <label for="phonee">Phone:</label>
                <input type="tel" id="phonee" name="phone" required>
            </div>
            <div class="form-group">
                <label for="addresse">Address:</label>
                <textarea id="addresse" name="address" rows="4" required></textarea>
            </div>
            <div class="form-group">
                <button type="submit" class="btn-submit">Save User</button>
            </div>
        </form>
    </div>
</div>
