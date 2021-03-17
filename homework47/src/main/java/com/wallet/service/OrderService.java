package com.wallet.service;

import com.wallet.dao.OrderDao;
import com.wallet.model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/orders")
public class OrderService {
    private final OrderDao orderDao = new OrderDao();
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }
    @GET
    @Path("/{orderId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Order getOrderById(@PathParam("orderId") Long orderId) {
        return orderDao.getOrderById(orderId);
    }
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Order createOrder(Order order) throws SQLException {
        return orderDao.addOrder(order);
    }
    @PUT
    @Path("/{orderId}")
    @Produces (MediaType.APPLICATION_JSON)
    public Order updateOrder(Order order, @PathParam("orderId") Long orderId){
        return orderDao.updateOrder(orderId, order);
    }
    @DELETE
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteOrder(@PathParam("orderId") Long orderId){
        orderDao.deleteOrder(orderId);
    }

}