/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import entity.Category;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import repository1.ProductRepository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "FetchCategoriesServlet", urlPatterns = {"/FetchCategories"})
public class FetchCategoriesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Category> categories = ProductRepository.fetchCategories();
        System.out.println(categories);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("product-add.jsp").forward(request, response);
    }



}