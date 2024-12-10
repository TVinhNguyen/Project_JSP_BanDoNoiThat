package model.dao;

import DB.Database;
import model.bean.Categories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAO {

    public List<Categories> getAllCategories() {
        List<Categories> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE is_deleted = 0";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all categories: " + e.getMessage(), e);
        }
        return categories;
    }

    public List<Categories> searchCategoryByName(String keyword) {
        List<Categories> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE name LIKE ? AND is_deleted = 0";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching category by name: " + e.getMessage(), e);
        }
        return categories;
    }

    public Categories getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ? AND is_deleted = 0";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching category by ID: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean insertCategory(Categories category) {
        String sql = "INSERT INTO categories (name, is_deleted) VALUES (?, 0)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting category: " + e.getMessage(), e);
        }
    }

    public boolean updateCategory(Categories category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating category: " + e.getMessage(), e);
        }
    }

    public boolean deleteCategory(int id) {
        String sql = "UPDATE categories SET is_deleted = 1, deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting category: " + e.getMessage(), e);
        }
    }

    public boolean restoreCategory(int id) {
        String sql = "UPDATE categories SET is_deleted = 0, deleted_at = NULL WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error restoring category: " + e.getMessage(), e);
        }
    }

    public List<Categories> getDeletedCategories() {
        List<Categories> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE is_deleted = 1";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching deleted categories: " + e.getMessage(), e);
        }
        return categories;
    }

    private Categories mapResultSetToCategory(ResultSet rs) throws SQLException {
        return new Categories(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
