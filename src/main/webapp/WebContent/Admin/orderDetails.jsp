<%@ page import="java.util.List" %>
<%@ page import="model.bean.OrderDetail" %>
<%@ page import="model.bean.Order" %>
<%@ page import="model.dto.OrderDetailsView" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Order Details</title>
  <link rel="stylesheet" href="/WebContent/Admin/css/style.css">
  <style>
    /* Style cho overlay */
    .overlay {
      display: none;
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.7);
      z-index: 1000;
      align-items: center;
      justify-content: center;
    }

    .overlay-content {
      background-color: white;
      padding: 20px;
      max-width: 600px;
      width: 100%;
      text-align: center;
    }

    .overlay .close-btn {
      position: absolute;
      top: 10px;
      right: 20px;
      font-size: 30px;
      color: #000;
      cursor: pointer;
    }

    .btn-view-details {
      cursor: pointer;
      color: blue;
      text-decoration: underline;
    }
  </style>
</head>
<body>
<div class="dashboard-container">
  <%@ include file="dashboard.jsp" %>

  <div class="main-content" id="mainContent">
    <h2>Order Details</h2>
    <button class="btn-back">
      <a href="/admin/OrderManage">Back to Orders</a>
    </button>
    <%
      Order order =(Order) request.getAttribute("order");
    %>
    <h3>Order ID: <%=order.getOrderId() %></h3>
    <table>
      <thead>
      <tr>
        <th>Product Name</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Total</th>
      </tr>
      </thead>
      <tbody>
      <%
        List<OrderDetailsView> orderDetails = (List<OrderDetailsView>) request.getAttribute("orderDetails");
        if (orderDetails != null && !orderDetails.isEmpty()) {
          for (OrderDetailsView detail : orderDetails) {
      %>
      <tr>
        <td><span class="btn-view-details" onclick="viewProductDetails('<%= detail.getProductId() %>')"><%= detail.getProductName() %></span></td>
        <td><%= detail.getQuantity() %></td>
        <td><%= detail.getPrice() %></td>
        <td><%= detail.getQuantity() * detail.getPrice() %></td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="4">No details available for this order.</td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  </div>
</div>

<div id="productOverlay" class="overlay">
  <div class="overlay-content">
    <span class="close-btn" onclick="closeOverlay()">×</span>
    <h3>Product Details</h3>
    <div id="productDetails"></div>
  </div>
</div>

<script>
  function viewProductDetails(productId) {
    fetch(`/productDetails?id=${productId}`)
            .then(response => response.json())
            .then(data => {
              let productDetails = `
                    <p><strong>Name:</strong> ${data.name}</p>
                    <p><strong>Description:</strong> ${data.description}</p>
                    <p><strong>Price:</strong> ${data.price}</p>
                    <p><strong>Stock:</strong> ${data.stock}</p>
                    <img src="${data.image}" alt="${data.name}" style="width: 100px; height: auto;">
                `;
              document.getElementById('productDetails').innerHTML = productDetails;
              document.getElementById('productOverlay').style.display = 'flex';
            })
            .catch(error => console.error('Error:', error));
  }

  function closeOverlay() {
    document.getElementById('productOverlay').style.display = 'none';
  }
</script>
</body>
</html>
