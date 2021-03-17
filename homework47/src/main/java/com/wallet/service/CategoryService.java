package com.wallet.service;

import com.wallet.dao.CategoryDao;
import com.wallet.model.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/categories")
public class CategoryService {
    private final CategoryDao categoryDao = new CategoryDao();
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Category> getAllCategories(){
        return categoryDao.getAllCategories();
    }
    @GET
    @Path("/{categoryId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Category getCategoryById(@PathParam("categoryId") Long categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Category createCategory(Category category) throws SQLException {
        return categoryDao.addCategory(category);
    }
    @PUT
    @Path("/{categoryId}")
    @Produces (MediaType.APPLICATION_JSON)
    public Category updateCategory(Category category, @PathParam("categoryId") Long categoryId){
        return categoryDao.updateCategory(categoryId, category);
    }
    @DELETE
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteOrder(@PathParam("categoryId") Long categoryId){
        categoryDao.deleteCategory(categoryId);
    }

}