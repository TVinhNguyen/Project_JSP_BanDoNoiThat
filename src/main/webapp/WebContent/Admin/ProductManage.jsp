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
  <link rel="stylesheet" href="/WebContent/Admin/css/style.css?v=<%= System.currentTimeMillis() %>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <script src="/WebContent/Admin/js/script.js?v=<%= System.currentTimeMillis() %>" defer></script>
</head>
<body>
<div class="dashboard-container">
  <%@ include file="dashboard.jsp" %>

  <div class="main-content" id="mainContent">
    <h2>Manage Products</h2>

    <div class="search-container">
      <h3>Search Products</h3>
      <input type="text" id="searchName" name="searchName" placeholder="Search by Name" value="<%= request.getParameter("searchName") != null ? request.getParameter("searchName") : "" %>">

      <input type="number" id="priceMin" name="priceMin" placeholder="Min Price" min="0" value="<%= request.getParameter("priceMin") != null ? request.getParameter("priceMin") : "" %>">
      <input type="number" id="priceMax" name="priceMax" placeholder="Max Price" min="0" value="<%= request.getParameter("priceMax") != null ? request.getParameter("priceMax") : "" %>">

      <select id="idcategory" name="idcategory">
        <option value="" selected>-- All Categories --</option>
        <%
          List<Categories> categoriesList = (List<Categories>) request.getAttribute("categories");
          String selectedCategoryId =(String) request.getAttribute("idcategory");
          if (categoriesList != null && !categoriesList.isEmpty()) {
            for (Categories category : categoriesList) {
              String selected = selectedCategoryId != null && Integer.valueOf(selectedCategoryId)  == category.getId() ? "selected" : "";
        %>
        <option value="<%= category.getId() %>" <%= selected %>><%= category.getName() %></option>
        <%
            }
          }
        %>
      </select>
      <button type="button" id="btnSearch" onclick="submitSearch()">Search</button>
    </div>

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
        <td><%= new java.text.DecimalFormat("#,###.##").format(product.getPrice()) %> VND</td>
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