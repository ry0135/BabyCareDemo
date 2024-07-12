package controller;

import entity.ServiceBooked;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.ServiceRespository;
import com.google.gson.Gson;

@WebServlet(name="ProfitServiceServlet", urlPatterns={"/ProfitServiceServlet"})
public class ProfitServiceServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ArrayList<ServiceBooked> allProfit = ServiceRespository.getBillService();
        int numberElementsInPage = 4;
        int size = allProfit.size();

        // Calculate the total number of pages
        int numberPage = (size % numberElementsInPage == 0) ? (size / numberElementsInPage) : (size / numberElementsInPage + 1);

        // Get the current page number from the request
        String xpage = request.getParameter("page");
        int page;
        try {
            page = Integer.parseInt(xpage);
            if (page < 1) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            page = 1;  // Default to the first page if the page parameter is invalid
        }

        // Determine the start and end indices for the sublist
        int start = (page - 1) * numberElementsInPage;
        int end = Math.min(page * numberElementsInPage, size);

        // Get the sublist of services for the current page
        ArrayList<ServiceBooked> list = new ArrayList<>(allProfit.subList(start, end));

        // Calculate total revenue from all services (not just the current page)
        double totalRevenue = calculateTotalRevenue(allProfit);

        // Format the total revenue
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotalRevenue = df.format(totalRevenue);

        // Get service count map and convert to JSON
        HashMap<String, Integer> serviceCountMap = ServiceRespository.countServiceBookings();
        Gson gson = new Gson();
        String serviceCountJson = gson.toJson(serviceCountMap);

        // Set attributes for the request
        request.setAttribute("listB", list);
        request.setAttribute("numberPage", numberPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalRevenue", formattedTotalRevenue);
        request.setAttribute("serviceCountJson", serviceCountJson);

        // Forward to JSP
        request.getRequestDispatcher("profitService.jsp").forward(request, response);
    }

    private double calculateTotalRevenue(ArrayList<ServiceBooked> list) {
        double totalRevenue = 0;
        for (ServiceBooked serviceBooked : list) {
            totalRevenue += serviceBooked.getPrice();
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