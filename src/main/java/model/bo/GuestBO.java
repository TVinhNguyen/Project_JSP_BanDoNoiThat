package model.bo;

import model.bean.Categories;
import model.bean.Product;
import model.dto.ProductView;
import model.bean.User;
import model.dao.*;

import java.util.List;

public class GuestBO {
    private CategoriesDAO categoryDAO = new CategoriesDAO();
    private ProductDAO productDAO = new ProductDAO();
    private UserDAO userDAO = new UserDAO();

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
    // Xem danh mục
    public List<Categories> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    // Xem sản phẩm theo danh mục
    public List<Product> getProductsByCategoryId(int categoryId) {
        return productDAO.searchByCategoryId(categoryId);
    }
    public List<ProductView> getProducts(String name, int category, Double minPrice, Double maxPrice)
    {
        return productDAO.getProducts(name, category, minPrice, maxPrice);
    }

    // Xem chi tiết sản phẩm
    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

    // Đăng ký tài khoản
    public boolean registerUser(User user) {
        return userDAO.insertUser(user);
    }
    // Đăng nhập tài khoản
    public User login(String username, String password) {
        return userDAO.login(username, password);
    }


}
