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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository1.ProductRepository;
import repository1.ServiceRespository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateCategoryServlet", urlPatterns = {"/UpdateCategory"})
public class UpdateCategoryServlet extends HttpServlet {

    
   

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("categoryID");
        Category category = ProductRepository.getCategory(id);
        System.out.println(category);
        request.setAttribute("category", category);
        request.getRequestDispatcher("category-update.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      int categoryID = Integer.parseInt(request.getParameter("categoryID"));
      String categoryName = request.getParameter("categoryName");
      String categoryImg = request.getParameter("categoryImg");
       System.out.println("Updating service  "  + categoryName + "" + categoryImg );
       Category category = new Category(categoryID, categoryName, categoryImg);
       boolean updateSuccess = false;
        try {
            updateSuccess = ProductRepository.updateCategory(category);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateServiceServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
       if (updateSuccess) {
        request.setAttribute("thongbao", "Cập nhật thành công");
    } else {
        request.setAttribute("thongbao", "Cập nhật thất bại");
    }
    request.setAttribute("category", category);
    request.getRequestDispatcher("category-update.jsp").forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
   

}
