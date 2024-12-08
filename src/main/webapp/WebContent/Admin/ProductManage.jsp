<%@ page import="java.util.List" %>
<%@ page import="model.bean.ProductView" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="/WebContent/Admin/css/style.css">
  <script src="/WebContent/Admin/js/script.js" defer></script>
</head>
<body>
<div class="dashboard-container">
  <%@ include file="dashboard.jsp" %>

  <div class="main-content" id="mainContent">
    <h2>Manage Products</h2>
    <button class="btn-add" id="btnAddProduct">Add Product</button>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Stock</th>
        <th>Category</th>
        <th>Image</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <%
        List<ProductView> products = (List<ProductView>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
          for (ProductView product : products) {
      %>
      <tr>
        <td><%= product.getId() %></td>
        <td><%= product.getName() %></td>
        <td><%= product.getDescription() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getStock() %></td>
        <td><%= product.getCategoryName() %></td>
        <td><img src="<%= product.getImageUrl() %>" alt="<%= product.getName() %>" width="100"></td>
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
        <td colspan="8">Không có sản phẩm nào phù hợp.</td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  </div>
</div>

<!-- Overlay Add Product -->
<div id="overlayAddProduct" class="overlay">
  <div class="overlay-content">
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
        <button type="button" id="btnCloseOverlay" class="btn-cancel">Cancel</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>
