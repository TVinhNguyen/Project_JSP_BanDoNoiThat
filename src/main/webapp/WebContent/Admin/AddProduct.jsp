<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 12/8/2024
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <link rel="stylesheet" href="/WebContent/Admin/css/CU.css">
</head>
<body>
<div class="form-container">
    <h2>Add Product</h2>
    <form action="/addProduct" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4" required></textarea>
        </div>
        <div class="form-group">
            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" required>
        </div>
        <div class="form-group">
            <label for="stock">Stock:</label>
            <input type="number" id="stock" name="stock" required>
        </div>
        <div class="form-group">
            <label for="category">Category:</label>
            <select id="category" name="category" required>
                <%-- Categories can be dynamically loaded here --%>
                <option value="1">Electronics</option>
                <option value="2">Clothing</option>
                <option value="3">Home & Garden</option>
            </select>
        </div>
        <div class="form-group">
            <label for="image">Image:</label>
            <input type="file" id="image" name="image" accept="image/*" required>
        </div>
        <div class="form-group">
            <button type="submit" class="btn-submit">Add Product</button>
        </div>
    </form>
</div>
</body>
</html>
