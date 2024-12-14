package model.dao;

import DB.Database;
import model.bean.Order;
import model.bean.OrderDetail;
import model.dto.OrderDetailsView;
import model.dto.OrderView;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    public List<OrderView> getAllOrders() {
        List<OrderView> orders = new ArrayList<>();
        String sql = "SELECT o.*,u.full_name as full_name FROM orders o " +
                "JOIN users u ON o.user_id = u.id WHERE o.is_deleted = 0";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                Date orderDate = rs.getDate("order_date");
                double totalAmount = rs.getDouble("total_amount");
                orders.add(new OrderView(orderId, userId,fullName, orderDate, totalAmount));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all orders: " + e.getMessage(), e);
        }
        return orders;
    }
    public  Map<LocalDate, Integer> getOrdersInMonth(int month, int year) {
        Map<LocalDate, Integer> orders = new HashMap<>();
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT DATE(order_date) as date, COUNT(*) as count " +
                    "FROM orders " +
                    "WHERE MONTH(order_date) = ? AND YEAR(order_date) = ? " +
                    "GROUP BY DATE(order_date)";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, month);
                stmt.setInt(2, year);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        LocalDate date = rs.getDate("date").toLocalDate();
                        int count = rs.getInt("count");
                        orders.put(date, count);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<OrderView> getOrdersByUserId(int userId) {
        List<OrderView> orders = new ArrayList<>();
        String sql = "SELECT o.*,u.full_name AS full_name FROM orders o "+
                "JOIN users u ON o.user_id = u.id WHERE o.user_id = ? AND o.is_deleted = 0 ";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    String fullName = rs.getString("full_name");
                    Date orderDate = rs.getDate("order_date");
                    double totalAmount = rs.getDouble("total_amount");
                    orders.add(new OrderView(orderId,userId,fullName,orderDate,totalAmount));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching orders by user ID: " + e.getMessage(), e);
        }
        return orders;
    }
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
