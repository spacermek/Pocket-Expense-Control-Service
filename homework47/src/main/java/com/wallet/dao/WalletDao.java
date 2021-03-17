package com.wallet.dao;

import com.wallet.model.User;
import com.wallet.model.Wallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletDao {

    private final UserDao userDao = new UserDao();

    public Wallet addWallet(Wallet wallet) throws SQLException {
        String SQL = "insert into wallets (name, user_id, is_active, created_date) values(?,?,?,now())";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setString(1, wallet.getName());
            stmt.setLong(2, wallet.getUserId().getId());
            stmt.setBoolean(3, wallet.getIsActive());
            stmt.executeUpdate();
            return wallet;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wallet> getAllWallets(){
        List<Wallet> walletList = new ArrayList<>();
        String SQL = "select * from wallets";
        try(Connection conn = new DbConnection().connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)){
            while (rs.next()){

                Wallet wallet = new Wallet(
                        rs.getLong("id"),
                        rs.getString("name"),
                        userDao.getUserById(rs.getLong("user_id")),
                        rs.getBoolean("is_active"),
                        rs.getDate("created_date")
                );
                walletList.add(wallet);
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return walletList;
    }

    public Wallet getWalletById(Long id) {
        String SQL = "select * from wallets where wallets.id = ?";
        Wallet wallet = null;
        User user = null;
        try (Connection conn = new DbConnection().connect();
             PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())

                wallet = new Wallet(
                        rs.getLong("id"),
                        rs.getString("name"),
                        userDao.getUserById(rs.getLong("user_id")),
                        rs.getBoolean("is_active"),
                        rs.getDate("created_date")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return wallet;
    }

    public boolean deleteWallet(Long id){
        String SQL = "delete from wallets where id = ?";
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

    public Wallet updateWallets(Long id, Wallet wallet){
        String SQL = "update wallets set name = ?, user_id = ?, is_active = ? where id = ?";
        try(Connection conn = new DbConnection().connect();
            PreparedStatement stmt = conn.prepareStatement(SQL)){
            stmt.setString(1, wallet.getName());
            stmt.setLong(2, wallet.getUserId().getId());
            stmt.setBoolean(3, wallet.getIsActive());
            stmt.setLong(4, id);
            stmt.executeUpdate();
            return wallet;
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
}
