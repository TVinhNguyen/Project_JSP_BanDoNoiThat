package model.bean;

import java.time.LocalDate;
import java.util.Date;

public class Order {
    private int orderId;
    private int userId;
    private Date orderDate;
    private double totalAmount;

    public Order(int orderId, int userId, Date orderDate, double totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }
    public Order( int userId, double totalAmount) {

        this.userId = userId;
        this.orderDate = java.sql.Date.valueOf(LocalDate.now());
        this.totalAmount = totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

