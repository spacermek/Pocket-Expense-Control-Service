package com.wallet.service;

import com.wallet.dao.UserDao;
import com.wallet.model.User;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/users")
public class UserService {
    private final UserDao userDao = new UserDao();
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("userId") Long userId){
        return userDao.getUserById(userId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) throws SQLException {
        return userDao.addUser(user);
    }

    @PUT
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(User user, @PathParam("userId") Long userId){
        return userDao.updateUser(userId, user);
    }

    @DELETE
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("userId") Long userId){
        userDao.deleteUser(userId);
    }
}