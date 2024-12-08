package model.dao;

import DB.Database;
import model.bean.OrderDetail;
import model.dto.OrderDetailsView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    public List<OrderDetailsView> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetailsView> OrderDetailsViews = new ArrayList<>();
        Connection conn = Database.getConnection();
        String sql = "SELECT od.*, p.name AS productName FROM order_details od " +
                "JOIN product p ON od.product_id = p.id WHERE od.order_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String productId = rs.getString("productId");
                String productName = rs.getString("productName");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                OrderDetailsViews.add(new OrderDetailsView(id, orderId, productId, productName, quantity, price)); // Thêm productName
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return OrderDetailsViews;
    }

    public List<OrderDetail> getOrderDetailsByProductId(String productId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM order_details WHERE product_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String orderId = rs.getString("order_id");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price"); // Thêm dòng này để lấy price
                orderDetails.add(new OrderDetail(id, orderId, productId, quantity, price)); // Sử dụng price
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetails;
    }

    public boolean insertOrderDetail(OrderDetail orderDetail) {
        Connection conn = Database.getConnection();
        String sql = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)"; // Cập nhật SQL để thêm price
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, orderDetail.getOrderId());
            stmt.setString(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.setDouble(4, orderDetail.getPrice()); // Thêm giá trị price
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting order detail: " + e.getMessage(), e);
        }
    }
}
