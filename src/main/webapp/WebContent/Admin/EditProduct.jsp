<%@ page import="model.bean.Categories" %>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.Product" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 12/8/2024
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<div id="overlayEditProduct" class="overlay">
    <div class="overlay-content">
        <button type="button" id="btnCloseOverlaye" class="btn-close">
            <i class="fas fa-times"></i>
        </button>
        <h2 id="overlayTitle">Edit Product</h2>
        <form action="/admin/ProductManage/update" method="post" id="productForm" enctype="multipart/form-data">
            <% Product pr = (Product) request.getAttribute("product"); %>
            <input type="hidden" id="productIde" name="productId" value="<%= pr != null ? pr.getId() : "" %>">
            <div class="form-group">
                <label for="namee">Name:</label>
                <input type="text" id="namee" name="name" value="<%= pr != null ? pr.getName() : "" %>" required>
            </div>
            <div class="form-group">
                <label for="descriptione">Description:</label>
                <textarea id="descriptione" name="description" rows="4" required><%= pr != null ? pr.getDescription() : "" %></textarea>
            </div>
            <div class="form-group">
                <label for="pricee">Price:</label>
                <input type="number" id="pricee" name="price" value="<%= pr != null ? pr.getPrice() : "" %>" step="0.1" required>
            </div>
            <div class="form-group">
                <label for="stocke">Stock:</label>
                <input type="number" id="stocke" name="stock" value="<%= pr != null ? pr.getStock() : "" %>" required>
            </div>
            <div class="form-group">
                <label for="idcategorye">Category:</label>
                <select id="idcategorye" name="idcategory" required>
                    <option value="" disabled selected>-- Select Category --</option>
                    <%
                        List<Categories> categoriesListEdit = (List<Categories>) request.getAttribute("categories");
                        if (categoriesListEdit != null && !categoriesListEdit.isEmpty()) {
                            for (Categories category : categoriesListEdit) {
                                String selected = pr != null && pr.getCategoryID() == category.getId() ? "selected" : "";
                    %>
                    <option value="<%= category.getId() %>" <%= selected %>><%= category.getName() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="imagee">Image:</label>
                <img id="currentImagePreview"  src="" alt="Product Image" width="100" />
                <input type="hidden" id="currentImage" name="currentImage">

                <input type="file" id="imagee" name="image">
            </div>
            <div class="form-group">
                <button type="submit" class="btn-submit">Save Product</button>
            </div>
        </form>
    </div>
</div>
