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
import repository1.ProductRepository;


/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddCategoryServlet", urlPatterns = {"/AddCategory"})
public class AddCategoryServlet extends HttpServlet {

   
   

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String categoryName = request.getParameter("Name");
       String  categoryImg = request.getParameter("categoryImg");
        System.out.println("Adding Category: "+ categoryName + "" + categoryImg );
        Category category = new Category(categoryName, categoryImg);
         boolean isAdded = ProductRepository.addCategory(category);
         if (isAdded) {
        request.setAttribute("thongbao", "Thêm thành công");
    } else {
        request.setAttribute("thongbao", "Thêm thất bại");
    }
    request.getRequestDispatcher("category-add.jsp").forward(request, response);
    }

   

}
