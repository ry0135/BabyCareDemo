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

        String ctvid = user.getUserId();
       double orderRevenuectv = StatisticsResponsitory.getOrderRevenuebyCTV(ctvid);
       int numberPendingOrders = StatisticsResponsitory.getPendingOrdersCountCTV(ctvid);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        System.out.println(formatter.format(orderRevenuectv));
         ArrayList listProduct = StatisticsResponsitory.getListRankOfProductByCTVID(ctvid);
        request.setAttribute("orderRevenuectv", formatter.format(orderRevenuectv));
          request.setAttribute("listProduct", listProduct);
         request.setAttribute("numberPendingOrders", numberPendingOrders);
          double revenue2020 = StatisticsResponsitory.getOrderRevenueByYearCTV(2020,ctvid);
        double revenue2021 = StatisticsResponsitory.getOrderRevenueByYearCTV(2021, ctvid);
        double revenue2022 = StatisticsResponsitory.getOrderRevenueByYearCTV(2022, ctvid);
        double revenue2023 = StatisticsResponsitory.getOrderRevenueByYearCTV(2023, ctvid);
        double revenue2024 = StatisticsResponsitory.getOrderRevenueByYearCTV(2024, ctvid);
        double revenue2025 = StatisticsResponsitory.getOrderRevenueByYearCTV(2025, ctvid);
        double revenue2026 = StatisticsResponsitory.getOrderRevenueByYearCTV(2026, ctvid);
        double revenue2027 = StatisticsResponsitory.getOrderRevenueByYearCTV(2027, ctvid);
        double revenue2028 = StatisticsResponsitory.getOrderRevenueByYearCTV(2028, ctvid);
        double revenue2029 = StatisticsResponsitory.getOrderRevenueByYearCTV(2029, ctvid);
        double revenue2030 = StatisticsResponsitory.getOrderRevenueByYearCTV(2030, ctvid);
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
         request.getRequestDispatcher("statisticsctv.jsp").forward(request, response);
    }

     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession(false); // Lấy session hiện tại nếu có, không tạo mới
        User user = (User) session.getAttribute("user");
        String ctvid = user.getUserId();
        String yearRevenueParam = request.getParameter("yearRevenue");
        int yearRevenue = (yearRevenueParam != null && !yearRevenueParam.isEmpty()) ? Integer.parseInt(yearRevenueParam) : 2024;
        double orderRevenuectv = StatisticsResponsitory.getOrderRevenuebyCTV(ctvid);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        System.out.println(formatter.format(orderRevenuectv));
      int numberPendingOrders = StatisticsResponsitory.getPendingOrdersCountCTV(ctvid);

        ArrayList listProduct = StatisticsResponsitory.getListRankOfProduct();
        request.setAttribute("orderRevenuectv", formatter.format(orderRevenuectv));
        request.setAttribute("listProduct", listProduct);
         request.setAttribute("numberPendingOrders", numberPendingOrders);
        double revenue2020 = StatisticsResponsitory.getOrderRevenueByYearCTV(2020,ctvid);
        double revenue2021 = StatisticsResponsitory.getOrderRevenueByYearCTV(2021, ctvid);
        double revenue2022 = StatisticsResponsitory.getOrderRevenueByYearCTV(2022, ctvid);
        double revenue2023 = StatisticsResponsitory.getOrderRevenueByYearCTV(2023, ctvid);
        double revenue2024 = StatisticsResponsitory.getOrderRevenueByYearCTV(2024, ctvid);
        double revenue2025 = StatisticsResponsitory.getOrderRevenueByYearCTV(2025, ctvid);
        double revenue2026 = StatisticsResponsitory.getOrderRevenueByYearCTV(2026, ctvid);
        double revenue2027 = StatisticsResponsitory.getOrderRevenueByYearCTV(2027, ctvid);
        double revenue2028 = StatisticsResponsitory.getOrderRevenueByYearCTV(2028, ctvid);
        double revenue2029 = StatisticsResponsitory.getOrderRevenueByYearCTV(2029, ctvid);
        double revenue2030 = StatisticsResponsitory.getOrderRevenueByYearCTV(2030, ctvid);
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
        
        double m1 = StatisticsResponsitory.getMonthlyRevenueByCTV(1, yearRevenue,ctvid);
        double m2 = StatisticsResponsitory.getMonthlyRevenueByCTV(2, yearRevenue,ctvid);
        double m3 = StatisticsResponsitory.getMonthlyRevenueByCTV(3, yearRevenue,ctvid);
        double m4 = StatisticsResponsitory.getMonthlyRevenueByCTV(4, yearRevenue,ctvid);
        double m5 = StatisticsResponsitory.getMonthlyRevenueByCTV(5, yearRevenue,ctvid);
        double m6 = StatisticsResponsitory.getMonthlyRevenueByCTV(6, yearRevenue,ctvid);
        double m7 = StatisticsResponsitory.getMonthlyRevenueByCTV(7, yearRevenue,ctvid);
        double m8 = StatisticsResponsitory.getMonthlyRevenueByCTV(8, yearRevenue,ctvid);
        double m9 = StatisticsResponsitory.getMonthlyRevenueByCTV(9, yearRevenue,ctvid);
        double m10 = StatisticsResponsitory.getMonthlyRevenueByCTV(10, yearRevenue,ctvid);
        double m11 = StatisticsResponsitory.getMonthlyRevenueByCTV(11, yearRevenue,ctvid);
        double m12 = StatisticsResponsitory.getMonthlyRevenueByCTV(12, yearRevenue,ctvid);
        
        
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
          request.getRequestDispatcher("statisticsctv.jsp").forward(request, response);


    }

  

}
