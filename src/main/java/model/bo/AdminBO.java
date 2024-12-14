package model.bo;

import model.dao.*;
import model.bean.*;
import model.dto.OrderDetailsView;
import model.dto.OrderView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AdminBO {
    private UserDAO userDAO = new UserDAO();
    private CategoriesDAO categoryDAO = new CategoriesDAO();
    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    // Quản lý người dùng
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean addUser(User user) {
        return userDAO.insertUser(user);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }
    public  User getUserById(int userId) {return userDAO.getUserById(userId);}

    // Quản lý danh mục
    public List<Categories> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public boolean addCategory(Categories category) {
        return categoryDAO.insertCategory(category);
    }

    public boolean updateCategory(Categories category) {
        return categoryDAO.updateCategory(category);
    }

    public boolean deleteCategory(int categoryId) {
        return categoryDAO.deleteCategory(categoryId);
    }

    // Quản lý sản phẩm
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public boolean addProduct(Product product) {
        return productDAO.insertProduct(product);
    }

    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
    }
    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }
    public Categories getCategories(int idCategory) {
        return categoryDAO.getCategoryById(idCategory);
    }

    // Quản lý đơn hàng
    public List<OrderView> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public List<OrderView> getOrderByIdUserId(int idUser) {return  orderDAO.getOrdersByUserId(idUser);}

    public Order getOrder(int orderId) {return orderDAO.getOrderById(orderId);}

    public List<OrderDetailsView> getOrderDetailsByOrderId(String orderId) {
        return orderDetailDAO.getOrderDetailsByOrderId(orderId);
    }
    public Map<LocalDate, Integer> getOrdersInMonth(int month , int year)
    {
        return orderDAO.getOrdersInMonth(month,year);
    }


}
