package com.wallet.dao;

import com.wallet.model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    private final CategoryDao categoryDao = new CategoryDao();
    private final WalletDao walletDao = new WalletDao();

    public Order addOrder(Order order) throws SQLException {
        String SQL = "insert into orders (category_id, amount, description, wallet_id, is_expense, created_date) values(?,?,?,?,?, now()) ";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setLong(1, order.getCategoryId().getId());
            stmt.setInt(2, order.getAmount());
            stmt.setString(3, order.getDescription());
            stmt.setLong(4, order.getWalletId().getId());
            stmt.setBoolean(5, order.getIsExpense());
            stmt.executeUpdate();
            return order;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getAllOrders(){
        List<Order> orderList = new ArrayList<>();
        String SQL = "select * from orders";
        try(Connection conn = new DbConnection().connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)){
            while (rs.next()){
                Order order = new Order(
                        rs.getLong("id"),
                        categoryDao.getCategoryById(rs.getLong("category_id")),
                        rs.getInt("amount"),
                        rs.getString("description"),
                        walletDao.getWalletById(rs.getLong("wallet_id")),
                        rs.getBoolean("is_expense"),
                        rs.getDate("created_date")
                );
                orderList.add(order);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return orderList;
    }

    public Order getOrderById(Long id){
        String SQL = "select * from orders where id = ?";
        Order order = null;
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next())
                    order = new Order(
                            rs.getLong("id"),
                            categoryDao.getCategoryById(rs.getLong("category_id")),
                            rs.getInt("amount"),
                            rs.getString("description"),
                            walletDao.getWalletById(rs.getLong("wallet_id")),
                            rs.getBoolean("is_expense"),
                            rs.getDate("created_date")
                    );
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return order;
    }

    public boolean deleteOrder(Long id){
        String SQL = "delete from orders where id = ?";
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

    public Order updateOrder(Long id, Order order){
        String SQL = "update orders set category_id = ?, amount = ?, description = ?, wallet_id = ?, is_expense = ? where id = ?";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setLong(1, order.getCategoryId().getId());
            stmt.setInt(2, order.getAmount());
            stmt.setString(3, order.getDescription());
            stmt.setLong(4, order.getWalletId().getId());
            stmt.setBoolean(5, order.getIsExpense());
            stmt.setLong(6, id);
            stmt.executeUpdate();
            return order;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
