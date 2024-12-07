package model.dao;

import DB.Database;
import model.bean.Categories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAO {
    public List<Categories> getAllCategories() {
        List<Categories> categories = new ArrayList<>();
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM categories";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categories.add(new Categories(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
    public List<Categories> searchCategoryByName(String keyword) {
        List<Categories> categories = new ArrayList<>();
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM categories WHERE name LIKE ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categories.add(new Categories(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
    public Categories getCategoryById(int id) {
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM categories WHERE id = ?";
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                return new Categories(id, name);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean insertCategory(Categories category) {
        Connection conn = Database.getConnection();
        String sql = "INSERT INTO categories (id, name) VALUES (?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, category.getId());
            stmt.setString(2, category.getName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteCategory(String id) {
        Connection conn = Database.getConnection();
        String sql = "DELETE FROM categories WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateCategory(Categories category) {
        Connection conn = Database.getConnection();
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
