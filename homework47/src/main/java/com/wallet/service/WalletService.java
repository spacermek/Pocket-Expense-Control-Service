package com.wallet.service;

import com.wallet.dao.WalletDao;
import com.wallet.model.Wallet;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/wallets")
public class WalletService {
    private final WalletDao walletDao = new WalletDao();
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Wallet> getAllWallets(){
        return walletDao.getAllWallets();
    }
    @GET
    @Path("/{walletId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Wallet getWalletById(@PathParam("walletId") Long walletId){
        return walletDao.getWalletById(walletId);
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Wallet createWallet(Wallet wallet) throws SQLException {
        return walletDao.addWallet(wallet);
    }
    @PUT
    @Path("/{walletId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Wallet updateWallet(@PathParam("walletId") Long id , Wallet wallet){
        return walletDao.updateWallets(id, wallet);
    }
    @DELETE
    @Path("/{walletId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteWallet(@PathParam("walletId") Long walletId){
        walletDao.deleteWallet(walletId);
    }
}