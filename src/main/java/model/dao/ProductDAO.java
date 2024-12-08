package model.dao;

import DB.Database;
import model.bean.Product;
import model.dto.ProductView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // Lấy tất cả các sản phẩm
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();

        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM products";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int categoryID = rs.getInt("categoryID");
                String imageUrl = rs.getString("image_url");
                products.add(new Product(id, name, description, price, stock, categoryID, imageUrl));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    // Thêm sản phẩm mới
    public boolean insertProduct(Product product) {
        Connection conn = Database.getConnection();
        String sql = "INSERT INTO products (id, name, description, price, stock, categoryID, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStock());
            stmt.setInt(6, product.getCategoryID());
            stmt.setString(7, product.getImageUrl());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Cập nhật sản phẩm
    public boolean updateProduct(Product product) {
        Connection conn = Database.getConnection();
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, categoryID = ?, image_url = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getStock());
            stmt.setInt(5, product.getCategoryID());
            stmt.setString(6, product.getImageUrl());
            stmt.setInt(7, product.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // In lỗi nếu cần thiết hoặc log vào hệ thống
            e.printStackTrace();
            throw new RuntimeException("Error deleting product with id: " + id, e);
        }
    }


    // Tìm kiếm sản phẩm theo tên
    public List<Product> searchByName(String name) {
        List<Product> products = new ArrayList<>();
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int category = rs.getInt("categoryID");
                String imageUrl = rs.getString("image_url");
                products.add(new Product(id, productName, description, price, stock, category, imageUrl));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(int id) {
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String productName = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int category = rs.getInt("categoryID");
                String imageUrl = rs.getString("image_url");
                return new Product(id, productName, description, price, stock, category, imageUrl);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<ProductView> getProducts(String name, int categoryID, Double minPrice, Double maxPrice) {
        StringBuilder query = new StringBuilder(
                "SELECT p.id, p.name, p.description, p.price, p.stock, p.image_url, c.name AS categoryName " +
                        "FROM products p " +
                        "JOIN categories c ON p.category_id = c.id WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        // Thêm điều kiện lọc theo tên sản phẩm
        if (name != null && !name.trim().isEmpty()) {
            query.append(" AND p.name LIKE ?");
            params.add("%" + name.trim() + "%");
        }

        // Thêm điều kiện lọc theo ID danh mục
        if (categoryID > 0) {
            query.append(" AND p.category_id = ?");
            params.add(categoryID);
        }

        // Thêm điều kiện lọc giá tối thiểu
        if (minPrice != null) {
            query.append(" AND p.price >= ?");
            params.add(minPrice);
        }

        // Thêm điều kiện lọc giá tối đa
        if (maxPrice != null) {
            query.append(" AND p.price <= ?");
            params.add(maxPrice);
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            List<ProductView> products = new ArrayList<>();
            while (rs.next()) {
                ProductView product = new ProductView(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("categoryName"),
                        rs.getString("image_url")
                );
                products.add(product);
            }
            return products;

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching products: " + e.getMessage(), e);
        }
    }



    public List<Product> searchByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM products WHERE categoryID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int category = rs.getInt("categoryID");
                String imageUrl = rs.getString("image_url");
                products.add(new Product(id, name, description, price, stock, category, imageUrl));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
