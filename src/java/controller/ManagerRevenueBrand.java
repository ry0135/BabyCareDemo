package controller;

import entity.Brand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.ProductRepository;
import repository1.StatisticsResponsitory;

@WebServlet(name = "ManagerRevenueBrand", urlPatterns = {"/ManagerRevenueBrand"})
public class ManagerRevenueBrand extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Brand> listBrands = (ArrayList<Brand>) ProductRepository.getAllBrands();
        request.setAttribute("listBrands", listBrands);
        request.getRequestDispatcher("manager-revenue-brand.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));

        String[] ctvids = request.getParameterValues("CTVID");

        Map<String, Double> revenueMap = new HashMap<>();
        Map<String, Integer> statusMap = new HashMap<>();

        for (String ctvid : ctvids) {
            double revenue = StatisticsResponsitory.getMonthlyRevenueByCTV(ctvid, month, year);
            revenueMap.put(ctvid, revenue);

            int status = 0;
            try {
                status = StatisticsResponsitory.getStatusRevenue(ctvid, month, year);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ManagerRevenueBrand.class.getName()).log(Level.SEVERE, null, ex);
            }
            statusMap.put(ctvid, status);
        }

        ArrayList<Brand> listBrands = (ArrayList<Brand>) ProductRepository.getAllBrands();

        request.setAttribute("listBrands", listBrands);
        request.setAttribute("revenue", revenueMap);
        request.setAttribute("statusMap", statusMap);
        request.setAttribute("month", month);
        request.setAttribute("year", year);

        // Log các giá trị của statusMap để kiểm tra
        System.out.println("StatusMap: " + statusMap);

        request.getRequestDispatcher("manager-revenue-brand.jsp").forward(request, response);
    }
}

