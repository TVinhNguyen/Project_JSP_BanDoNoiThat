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
        <td><img src="<%= product.getImageUrl() + "?timestamp=" + System.currentTimeMillis() %>" alt="<%= product.getName() %>" width="100"></td>
        <td>
          <button class="btn-edit"  id="btnEditProduct" onclick="openEditForm('<%= product.getId() %>')">Edit</button>
          <button class="btn-delete" onclick="openDeleteForm('<%= product.getId() %>')">Delete</button>
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
<%@ include file="AddProduct.jsp" %>
<%@ include file="EditProduct.jsp"%>


</body>
</html>
