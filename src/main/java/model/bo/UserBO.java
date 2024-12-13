package model.bo;

import model.dao.*;
import model.bean.*;
import model.dto.*;
import java.util.List;
public class UserBO {
        private UserDAO userDAO = new UserDAO();
        private OrderDAO orderDAO = new OrderDAO();
        private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        private ProductDAO productDAO = new ProductDAO();

        public User getUserById(int userId) {
            return userDAO.getUserById(userId);
        }

        public boolean updateUserProfile(User user) {
            return userDAO.updateUser(user);
        }


        public List<OrderView> getOrdersByUserId(int userId) {
            return orderDAO.getOrdersByUserId(userId);
        }

        public List<ProductView> getProducts(String name, int category, Double minPrice, Double maxPrice)
        {
            return productDAO.getProducts(name, category, minPrice, maxPrice);
        }

        // Đặt hàng
        public boolean placeOrder(Order order, List<OrderDetail> orderDetails) {
            boolean isOrderPlaced = orderDAO.insertOrder(order);
            if (isOrderPlaced) {
                for (OrderDetail detail : orderDetails) {
                    orderDetailDAO.insertOrderDetail(detail);
                }
            }
            return isOrderPlaced;
        }




}
