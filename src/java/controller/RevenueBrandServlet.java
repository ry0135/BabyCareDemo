package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.StatisticsResponsitory;

@WebServlet(name = "RevenueBrandServlet", urlPatterns = {"/revenuebrand"})
public class RevenueBrandServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        String brandId = request.getParameter("brandID");
        String CTVID = request.getParameter("CTVID");
        String brandName = request.getParameter("brandName");
        String revenueStr = request.getParameter("revenue");
        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");

        if (brandId == null || brandId.isEmpty() ||
            CTVID == null || CTVID.isEmpty() ||
            revenueStr == null || revenueStr.isEmpty() ||
            monthStr == null || monthStr.isEmpty() ||
            yearStr == null || yearStr.isEmpty()) {
            response.getWriter().write("failure");
            return;
        }

        try {
            double revenue = Double.parseDouble(revenueStr);
            int month = Integer.parseInt(monthStr);
            int year = Integer.parseInt(yearStr);

            boolean success = false;
            try {
                success = StatisticsResponsitory.insertRevenue(brandId, CTVID, revenue, month, year);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RevenueBrandServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (success) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("failure");
        }
    }
}
