package controller;

import entity.MonthlyRevenue;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.ServiceRespository;
import com.google.gson.Gson;

@WebServlet(name = "ProfitServiceServlet", urlPatterns = {"/ProfitServiceServlet"})
public class ProfitServiceServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Get the year from the request parameter, default to "2024" if not provided
        int year = 2024;
        ArrayList<MonthlyRevenue> list = ServiceRespository.getMonthlyRevenue(year);
        
        // Calculate total revenue from all services
        double totalRevenue = calculateTotalRevenue(list);

        // Format the total revenue
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotalRevenue = df.format(totalRevenue);

        // Prepare data for JSON
        Gson gson = new Gson();
        String monthlyRevenueJson = gson.toJson(list);

        // Set attributes for the request
        request.setAttribute("listB", list);
        request.setAttribute("totalRevenue", formattedTotalRevenue);
        request.setAttribute("monthlyRevenueJson", monthlyRevenueJson);

        // Forward to JSP
        request.getRequestDispatcher("profitService.jsp").forward(request, response);
    }

    private double calculateTotalRevenue(ArrayList<MonthlyRevenue> list) {
        double totalRevenue = 0;
        for (MonthlyRevenue monthlyRevenue : list) {
            totalRevenue += monthlyRevenue.getTotalRevenue();
        }
        return totalRevenue;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Profit Service Servlet";
    }
}
