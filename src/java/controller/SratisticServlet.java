/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import entity.Revenue;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import repository1.StatisticsResponsitory;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SratisticServlet", urlPatterns = {"/Sratistic"})
public class SratisticServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int numberOfUsers = StatisticsResponsitory.getUserAmount();
        int numberOfCTV = StatisticsResponsitory.getCTVAmount();
        int numberOfOrderToConfirm = StatisticsResponsitory.getNumberOfOrdersToConfirm();
        int numberOfProductLeft = StatisticsResponsitory.getNumberOfProductLeft();
        double orderRevenue = StatisticsResponsitory.getOrderRevenue();
//        ArrayList<Revenue> listOderRevenues = StatisticsResponsitory.getOrderRevenueEachMonths();
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        System.out.println(formatter.format(orderRevenue));
        ArrayList listProduct = StatisticsResponsitory.getListRankOfProduct();
        request.setAttribute("numberOfUsers", numberOfUsers);
         request.setAttribute("numberOfCTV", numberOfCTV);
        request.setAttribute("numberOfOrderToConfirm", numberOfOrderToConfirm);
        request.setAttribute("numberOfProductLeft", numberOfProductLeft);
        request.setAttribute("orderRevenue", formatter.format(orderRevenue));
        request.setAttribute("listProduct", listProduct);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
  
   

}
}
