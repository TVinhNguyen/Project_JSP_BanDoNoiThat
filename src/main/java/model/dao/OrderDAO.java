package model.dao;

import DB.Database;
import model.bean.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE is_deleted = 0";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int userId = rs.getInt("user_id");
                Date orderDate = rs.getDate("order_date");
                double totalAmount = rs.getDouble("total_amount");
                orders.add(new Order(orderId, userId, orderDate, totalAmount));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all orders: " + e.getMessage(), e);
        }
        return orders;
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? AND is_deleted = 0";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    Date orderDate = rs.getDate("order_date");
                    double totalAmount = rs.getDouble("total_amount");
                    orders.add(new Order(orderId, userId, orderDate, totalAmount));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching orders by user ID: " + e.getMessage(), e);
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE id = ? AND is_deleted = 0";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    Date orderDate = rs.getDate("order_date");
                    double totalAmount = rs.getDouble("total_amount");
                    return new Order(orderId, userId, orderDate, totalAmount);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching order by ID: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean insertOrder(Order order) {
        String sql = "INSERT INTO orders (id, user_id, order_date, total_amount, is_deleted) VALUES (?, ?, ?, ?, 0)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getOrderId());
            stmt.setInt(2, order.getUserId());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDouble(4, order.getTotalAmount());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting order: " + e.getMessage(), e);
        }
    }

    public boolean deleteOrder(int orderId) {
        String sql = "UPDATE orders SET is_deleted = 1, deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting order: " + e.getMessage(), e);
        }
    }

    public boolean restoreOrder(int orderId) {
        String sql = "UPDATE orders SET is_deleted = 0, deleted_at = NULL WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while restoring order: " + e.getMessage(), e);
        }
    }
}
