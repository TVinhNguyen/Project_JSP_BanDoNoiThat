package model.dao;

import DB.Database;
import model.bean.OrderDetail;
import model.dto.OrderDetailsView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    public List<OrderDetailsView> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetailsView> orderDetailsViews = new ArrayList<>();
        String sql = "SELECT od.*, p.name AS productName FROM order_details od " +
                "JOIN products p ON od.product_id = p.id WHERE od.order_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String productId = rs.getString("product_id");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    orderDetailsViews.add(new OrderDetailsView(id, orderId, productId, productName, quantity, price));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching order details by orderId: " + e.getMessage(), e);
        }
        return orderDetailsViews;
    }

    public List<OrderDetail> getOrderDetailsByProductId(String productId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM order_details WHERE product_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String orderId = rs.getString("order_id");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    orderDetails.add(new OrderDetail(id, orderId, productId, quantity, price));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching order details by productId: " + e.getMessage(), e);
        }
        return orderDetails;
    }

    public boolean insertOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderDetail.getOrderId());
            stmt.setString(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.setDouble(4, orderDetail.getPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting order detail: " + e.getMessage(), e);
        }
    }

    public boolean updateOrderDetail(OrderDetail orderDetail) {
        String sql = "UPDATE order_details SET quantity = ?, price = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getQuantity());
            stmt.setDouble(2, orderDetail.getPrice());
            stmt.setInt(3, orderDetail.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating order detail: " + e.getMessage(), e);
        }
    }

    public boolean deleteOrderDetail(int id) {
        String sql = "DELETE FROM order_details WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting order detail: " + e.getMessage(), e);
        }
    }

    public boolean deleteOrderDetailsByOrderId(String orderId) {
        String sql = "DELETE FROM order_details WHERE order_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting order details by orderId: " + e.getMessage(), e);
        }
    }

    public boolean updateQuantity(int orderDetailId, int newQuantity) {
        String sql = "UPDATE order_details SET quantity = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, orderDetailId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating quantity: " + e.getMessage(), e);
        }
    }
}
