package model.dao;

import DB.Database;
import model.bean.Product;
import model.dto.ProductView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_deleted = 0";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all products: " + e.getMessage(), e);
        }
        return products;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ? AND is_deleted = 0";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching product by ID: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean insertProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, stock, category_id, image_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setProductToStatement(stmt, product);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting product: " + e.getMessage(), e);
        }
    }

    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, category_id = ?, image_url = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setProductToStatement(stmt, product);
            stmt.setInt(7, product.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product: " + e.getMessage(), e);
        }
    }

    public boolean deleteProduct(int id) {
        String sql = "UPDATE products SET is_deleted = 1, deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product: " + e.getMessage(), e);
        }
    }

    // Khôi phục sản phẩm đã xóa
    public boolean restoreProduct(int id) {
        String sql = "UPDATE products SET is_deleted = 0, deleted_at = NULL WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error restoring product: " + e.getMessage(), e);
        }
    }

    public List<ProductView> getProducts(String name, int categoryID, Double minPrice, Double maxPrice) {
        StringBuilder query = new StringBuilder(
                "SELECT p.id, p.name, p.description, p.price, p.stock, p.image_url, c.name AS categoryName " +
                        "FROM products p " +
                        "JOIN categories c ON p.category_id = c.id WHERE 1=1 AND p.is_deleted = 0"
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
        String sql = "SELECT * FROM products WHERE category_id = ? AND is_deleted = 0";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching products by category: " + e.getMessage(), e);
        }
        return products;
    }

    public List<Product> getDeletedProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_deleted = 1";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching deleted products: " + e.getMessage(), e);
        }
        return products;
    }

    // Helper: Ánh xạ từ ResultSet sang Product
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getInt("stock"),
                rs.getInt("category_id"),
                rs.getString("image_url")
        );
    }

    // Helper: Gán dữ liệu từ Product vào PreparedStatement
    private void setProductToStatement(PreparedStatement stmt, Product product) throws SQLException {
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getDescription());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getStock());
        stmt.setInt(5, product.getCategoryID());
        stmt.setString(6, product.getImageUrl());
    }
}
