<%@ page import="model.bean.Categories" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 12/8/2024
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div id="overlayAddProduct" class="overlay">
    <div class="overlay-content">
        <button type="button" id="btnCloseOverlay" class="btn-close">
            <i class="fas fa-times"></i>
        </button>
        <h2 id="overlayTitle">Add Product</h2>
        <form action="/admin/ProductManage/create" method="post" id="productForm" enctype="multipart/form-data">
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
                <input type="file" id="image" name="image">
            </div>
            <div class="form-group">
                <button type="submit" class="btn-submit">Save Product</button>
            </div>
        </form>
    </div>
</div>

