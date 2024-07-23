/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Category;
import entity.Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import repository1.ProductRepository;


/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CategoryListServlet", urlPatterns = {"/CategoryList"})
public class CategoryListServlet extends HttpServlet {

  

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Category> list = ProductRepository.getAllCategory();
                 request.setAttribute("listCategory",list);
    request.getRequestDispatcher("category-list-manager.jsp").forward(request,response);
    }

}