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
                String orderId = rs.getString("orderId");
                int userId = rs.getInt("userId");
                Date orderDate = rs.getDate("orderDate");
                double totalAmount = rs.getDouble("totalAmount");
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
        String sql = "SELECT * FROM orders WHERE userId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String orderId = rs.getString("orderId");
                Date orderDate = rs.getDate("orderDate");
                double totalAmount = rs.getDouble("totalAmount");
                orders.add(new Order(orderId, userId, orderDate, totalAmount));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    public boolean insertOrder(Order order) {
        Connection conn = Database.getConnection();
        String sql = "INSERT INTO orders (orderId, userId, orderDate, totalAmount) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, order.getOrderId());
            stmt.setInt(2, order.getUserId());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDouble(4, order.getTotalAmount());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while inserting order: " + e.getMessage(), e);
        }
    }



}
