<%@ page import="model.bean.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="model.dto.ProductView" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách sản phẩm</title>
</head>
<body>
<h1>Danh sách sản phẩm</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Mô tả</th>
        <th>Giá</th>
        <th>Kho</th>
        <th>Danh mục</th>
        <th>Hình ảnh</th>
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
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="7">Không có sản phẩm nào phù hợp.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
