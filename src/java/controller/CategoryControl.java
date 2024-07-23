/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Category;
import entity.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import repository1.ProductRepository;


/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CategoryControl", value = "/category")
public class CategoryControl extends HttpServlet { 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         String ID = request.getParameter("id");
    
    if (ID != null && !ID.trim().isEmpty()) {
        try {
            int cateID = Integer.parseInt(ID);
            ArrayList<Product> listProduct = ProductRepository.getListProductByCID(cateID);
            ArrayList<Category> listC = ProductRepository.getAllCategory();
          request.setAttribute("listProduct", listProduct);
          request.setAttribute("listC", listC);

          request.getRequestDispatcher("product.jsp").forward(request, response);
        } catch (NumberFormatException e) {
           
            e.printStackTrace();
        }
    } else {
        System.out.println("abc");
    }      
    }
}

    

