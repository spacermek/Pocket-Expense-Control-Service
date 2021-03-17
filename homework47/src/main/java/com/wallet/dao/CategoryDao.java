package com.wallet.dao;

import com.wallet.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private final UserDao userDao = new UserDao();

    public Category addCategory(Category category) {
        String SQL = "insert into categories (name, user_id, parent_category_id, is_active, created_date) values(?,?,?,?, now()) ";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setString(1, category.getName());
            stmt.setLong(2, category.getUserId().getId());
            stmt.setBoolean(4, category.getIsActive());
            if (category.getParentCategoryId().getId() == null) {
                stmt.setNull(3,  Types.BIGINT);
            }
            else {
                stmt.setLong(3, category.getParentCategoryId().getId());
            }
            stmt.executeUpdate();
            return category;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getAllCategories(){
        List<Category> categoryList = new ArrayList<>();
        String SQL = "select * from categories";
        try(Connection conn = new DbConnection().connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)){
            while (rs.next()){
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setUserId(userDao.getUserById(rs.getLong("user_id")));
                category.setIsActive(rs.getBoolean("is_active"));
                category.setCreatedDate(rs.getDate("created_date"));
                category.setParentCategoryId(getCategoryById(rs.getLong("parent_category_id")));
                categoryList.add(category);
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return categoryList;
    }

    public Category getCategoryById(Long id){
        String SQL = "select * from categories where id = ?";
        Category category = null;
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    category = new Category();
                    category.setId(rs.getLong("id"));
                    category.setName(rs.getString("name"));
                    category.setUserId(userDao.getUserById(rs.getLong("user_id")));
                    category.setIsActive(rs.getBoolean("is_active"));
                    category.setCreatedDate(rs.getDate("created_date"));
                    category.setParentCategoryId(getCategoryById(rs.getLong("parent_category_id")));
                }
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return category;
    }

    public boolean deleteCategory(Long id){
        String SQL = "delete from categories where id = ?";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Category updateCategory(Long id, Category category){
        String SQL = "update categories set name = ?, user_id = ?, parent_category_id = ?, is_active = ? where id = ?";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setString(1, category.getName());
            stmt.setLong(2, category.getUserId().getId());
            stmt.setBoolean(4, category.getIsActive());
            stmt.setLong(5, id);
            if (category.getParentCategoryId().getId() == null) {
                stmt.setNull(3,  Types.BIGINT);
            }
            else {
                stmt.setLong(3, category.getParentCategoryId().getId());
            }

            stmt.executeUpdate();
            return category;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
