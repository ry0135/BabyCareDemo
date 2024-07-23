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
    double revenue2020 = StatisticsResponsitory.getOrderRevenueByYear(2020);
    double revenue2021 = StatisticsResponsitory.getOrderRevenueByYear(2021);
    double revenue2022 = StatisticsResponsitory.getOrderRevenueByYear(2022);
    double revenue2023 = StatisticsResponsitory.getOrderRevenueByYear(2023);
    double revenue2024 = StatisticsResponsitory.getOrderRevenueByYear(2024);
    double revenue2025 = StatisticsResponsitory.getOrderRevenueByYear(2025);
    double revenue2026 = StatisticsResponsitory.getOrderRevenueByYear(2026);
    double revenue2027 = StatisticsResponsitory.getOrderRevenueByYear(2027);
    double revenue2028 = StatisticsResponsitory.getOrderRevenueByYear(2028);
    double revenue2029 = StatisticsResponsitory.getOrderRevenueByYear(2029);
    double revenue2030 = StatisticsResponsitory.getOrderRevenueByYear(2030);
        request.setAttribute("revenue2020", revenue2020);
        request.setAttribute("revenue2021", revenue2021);
        request.setAttribute("revenue2022", revenue2022);
        request.setAttribute("revenue2023", revenue2023);
        request.setAttribute("revenue2024", revenue2024);
        request.setAttribute("revenue2025", revenue2025);
        request.setAttribute("revenue2026", revenue2026);
        request.setAttribute("revenue2027", revenue2027);
        request.setAttribute("revenue2028", revenue2028);
        request.setAttribute("revenue2029", revenue2029);
        request.setAttribute("revenue2030", revenue2030);

                
        
//         int month = Integer.parseInt(request.getParameter("month"));
//        int year = Integer.parseInt(request.getParameter("year"));
//        
//        
//        
//        // Initialize variables
//        double monthlyRevenue = 0.0;
//        monthlyRevenue = StatisticsResponsitory.getMonthlyRevenueByAdmin(month, year);
//        request.setAttribute("monthlyRevenue", monthlyRevenue);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
  
   

}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String monthParam = request.getParameter("month");
    String yearParam = request.getParameter("year");
    String yearRevenueParam = request.getParameter("yearRevenue");
    int month = (monthParam != null && !monthParam.isEmpty()) ? Integer.parseInt(monthParam) : 1; // Default to 1 if monthParam is null
    int year = (yearParam != null && !yearParam.isEmpty()) ? Integer.parseInt(yearParam) : 2024; // Default to 2024 if yearParam is null
    int yearRevenue = (yearRevenueParam != null && !yearRevenueParam.isEmpty()) ? Integer.parseInt(yearRevenueParam) : 2024; // Default to 2024 if yearRevenueParam is null
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
        
        request.setAttribute("month", month);
        request.setAttribute("year", year);
        
        // Initialize variables
        double monthlyRevenue = 0.0;
        monthlyRevenue = StatisticsResponsitory.getMonthlyRevenueByAdmin(month, year);
        request.setAttribute("monthlyRevenue", formatter.format(monthlyRevenue));
          double revenue2020 = StatisticsResponsitory.getOrderRevenueByYear(2020);
        double revenue2021 = StatisticsResponsitory.getOrderRevenueByYear(2021);
    double revenue2022 = StatisticsResponsitory.getOrderRevenueByYear(2022);
    double revenue2023 = StatisticsResponsitory.getOrderRevenueByYear(2023);
    double revenue2024 = StatisticsResponsitory.getOrderRevenueByYear(2024);
    double revenue2025 = StatisticsResponsitory.getOrderRevenueByYear(2025);
    double revenue2026 = StatisticsResponsitory.getOrderRevenueByYear(2026);
    double revenue2027 = StatisticsResponsitory.getOrderRevenueByYear(2027);
    double revenue2028 = StatisticsResponsitory.getOrderRevenueByYear(2028);
    double revenue2029 = StatisticsResponsitory.getOrderRevenueByYear(2029);
    double revenue2030 = StatisticsResponsitory.getOrderRevenueByYear(2030);
        request.setAttribute("revenue2020", revenue2020);
        request.setAttribute("revenue2021", revenue2021);
        request.setAttribute("revenue2022", revenue2022);
        request.setAttribute("revenue2023", revenue2023);
        request.setAttribute("revenue2024", revenue2024);
        request.setAttribute("revenue2025", revenue2025);
        request.setAttribute("revenue2026", revenue2026);
        request.setAttribute("revenue2027", revenue2027);
        request.setAttribute("revenue2028", revenue2028);
        request.setAttribute("revenue2029", revenue2029);
        request.setAttribute("revenue2030", revenue2030);
 
    double m1 = StatisticsResponsitory.getMonthlyRevenueByAdmin(1,yearRevenue);
    double m2 = StatisticsResponsitory.getMonthlyRevenueByAdmin(2,yearRevenue);
    double m3 = StatisticsResponsitory.getMonthlyRevenueByAdmin(3,yearRevenue);
    double m4 = StatisticsResponsitory.getMonthlyRevenueByAdmin(4,yearRevenue);
    double m5 = StatisticsResponsitory.getMonthlyRevenueByAdmin(5,yearRevenue);
    double m6 = StatisticsResponsitory.getMonthlyRevenueByAdmin(6,yearRevenue);
    double m7 = StatisticsResponsitory.getMonthlyRevenueByAdmin(7,yearRevenue);
    double m8 = StatisticsResponsitory.getMonthlyRevenueByAdmin(8,yearRevenue);
    double m9 = StatisticsResponsitory.getMonthlyRevenueByAdmin(9,yearRevenue);
    double m10 = StatisticsResponsitory.getMonthlyRevenueByAdmin(10,yearRevenue);
    double m11 = StatisticsResponsitory.getMonthlyRevenueByAdmin(11,yearRevenue);
    double m12 = StatisticsResponsitory.getMonthlyRevenueByAdmin(12,yearRevenue);

        request.setAttribute("m1", m1);
        request.setAttribute("m2", m2);
        request.setAttribute("m3", m3);
        request.setAttribute("m4", m4);
        request.setAttribute("m5", m5);
        request.setAttribute("m6", m6);
        request.setAttribute("m7", m7);
        request.setAttribute("m8", m8);
        request.setAttribute("m9", m9);
        request.setAttribute("m10", m10);
        request.setAttribute("m11", m11);
        request.setAttribute("m12", m12);

         request.setAttribute("yearRevenue", yearRevenue);
        
        
        request.getRequestDispatcher("admin.jsp").forward(request, response);
        
        
        
        
        
    }
    
}
