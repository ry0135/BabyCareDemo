/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.User;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import repository1.StatisticsResponsitory;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "StatisticCTVServlet", urlPatterns = {"/StatisticCTV"})
public class StatisticCTVServlet extends HttpServlet {

   
   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is not logged in
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String CTVID = user.getUserId();
       double orderRevenuectv = StatisticsResponsitory.getOrderRevenuebyCTV(CTVID);
       int numberPendingOrders = StatisticsResponsitory.getPendingOrdersCountCTV(CTVID);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        System.out.println(formatter.format(orderRevenuectv));
         ArrayList listProduct = StatisticsResponsitory.getListRankOfProductByCTVID(CTVID);
        request.setAttribute("orderRevenuectv", formatter.format(orderRevenuectv));
          request.setAttribute("listProduct", listProduct);
         request.setAttribute("numberPendingOrders", numberPendingOrders);

         request.getRequestDispatcher("statisticsctv.jsp").forward(request, response);
    }

    
  

}
