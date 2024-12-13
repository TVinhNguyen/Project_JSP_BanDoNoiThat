package model.dto;

import java.time.LocalDate;
import java.util.Date;

public class OrderView {

        private int orderId;
        private int userId;
        private String customerName;
        private Date orderDate;
        private double totalAmount;

        public OrderView(int orderId, int userId,String customerName , Date orderDate, double totalAmount) {
            this.orderId = orderId;
            this.userId = userId;
            this.customerName = customerName;
            this.orderDate = orderDate;
            this.totalAmount = totalAmount;
        }
        public OrderView( int userId, double totalAmount) {

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

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }
}
