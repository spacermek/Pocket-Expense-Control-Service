package com.wallet.dao;

import com.wallet.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public User addUser(User user) throws SQLException {
        String SQL = "insert into users (name, password, created_date) values(?,?, now()) ";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
            return user;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        String SQL = "select * from users";
        try(Connection conn = new DbConnection().connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)){
            while (rs.next()){
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getDate("created_date")
                );
                userList.add(user);
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return userList;
    }

    public User getUserById(Long id){
        String SQL = "select * from users where id = ?";
        User user = null;
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next())
                    user = new User(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getDate("created_date")
                    );
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return user;
    }

    public boolean deleteUser(Long id){
        String SQL = "delete from users where id = ?";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    public User updateUser(Long id, User user){
        String SQL = "update users set name = ?, password = ? where id = ?";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, id);
            stmt.executeUpdate();
            return user;
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
}