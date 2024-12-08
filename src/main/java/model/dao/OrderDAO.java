package model.dao;

import DB.Database;
import model.bean.Order;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class OrderDAO {
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM orders";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int orderId = rs.getInt("id");
                int userId = rs.getInt("user_id");
                Date orderDate = rs.getDate("order_date");
                double totalAmount = rs.getDouble("total_amount");
                orders.add(new Order(orderId, userId, orderDate, totalAmount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("id");
                Date orderDate = rs.getDate("order_date");
                double totalAmount = rs.getDouble("total_amount");
                orders.add(new Order(orderId, userId, orderDate, totalAmount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    public Order getOrderById(int orderId) {
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM orders WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                Date orderDate = rs.getDate("order_date");
                double totalAmount = rs.getDouble("total_amount");
                return new Order(orderId, userId, orderDate, totalAmount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean insertOrder(Order order) {
        Connection conn = Database.getConnection();
        String sql = "INSERT INTO orders (id, user_id, order_date, total_amount) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getOrderId());
            stmt.setInt(2, order.getUserId());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDouble(4, order.getTotalAmount());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting order: " + e.getMessage(), e);
        }
    }



}
