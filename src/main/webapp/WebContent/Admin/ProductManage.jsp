<%@ page import="java.util.List" %>
<%@ page import="model.dto.ProductView" %>
<%@ page import="model.bean.Categories" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="/WebContent/Admin/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
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
          <button class="btn-edit" onclick="openEditForm(<%= product.getId() %>)">Edit</button>
          <button class="btn-delete" data-id="<%= product.getId() %>">Delete</button>
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

<!-- Overlay Add/Edit Product -->
<div id="overlayProduct" class="overlay">
  <div class="overlay-content">
    <button type="button" id="btnCloseOverlay" class="btn-close">
      <i class="fas fa-times"></i>
    </button>
    <h2 id="overlayTitle">Add Product</h2>
    <form action="/admin/ProductManage" method="post" enctype="multipart/form-data" id="productForm">
      <input type="hidden" id="productId" name="productId">
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
        <input type="number" id="price" name="price" step="0.1" required>
      </div>
      <div class="form-group">
        <label for="stock">Stock:</label>
        <input type="number" id="stock" name="stock" required>
      </div>
      <div class="form-group">
        <label for="idcategory">Category:</label>
        <select id="idcategory" name="idcategory" required>
          <option value="" disabled selected>-- Select Category --</option>
          <%
            List<Categories> categoriesList = (List<Categories>) request.getAttribute("categories");
            if (categoriesList != null && !categoriesList.isEmpty()) {
              for (Categories category : categoriesList) {
          %>
          <option value="<%= category.getId() %>"><%= category.getName() %></option>
          <%
              }
            }
          %>
        </select>
      </div>
      <div class="form-group">
        <label for="image">Image:</label>
        <input type="file" id="image" name="image" accept="image/*">
      </div>
      <div class="form-group">
        <button type="submit" class="btn-submit">Save Product</button>
      </div>
    </form>
  </div>
</div>

</body>
</html>
