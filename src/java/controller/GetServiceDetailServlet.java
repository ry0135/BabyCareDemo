package controller;

import entity.Feedback;
import entity.Service;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.ServiceRespository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "getServiceDetailServlet", value = "/getservicedetail")
public class GetServiceDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("serviceID");
        if (id == null) {
            response.sendRedirect("index.jsp");
        } else {
            try {
                System.out.println("===============>");
                System.out.println("===============> " + id);
                Service s = ServiceRespository.getService(id);
                ArrayList<Feedback> feedbackList = ServiceRespository.getFeedBackByServiceID(id);
                
                int TotalRating = ServiceRespository.getTotalRatingForService(id);
                double getAverageRating = ServiceRespository.getAverageRatingForService(id);
                int EvaluateRating = ServiceRespository.getTotalEvaluateForService(id);
                
                request.setAttribute("TotalRating", TotalRating );
                
                request.setAttribute("getAverageRating",getAverageRating);
                request.setAttribute("EvaluateRating" ,EvaluateRating);
                
                request.setAttribute("feedbackList", feedbackList);
                request.setAttribute("service", s);
                request.getRequestDispatcher("service-detail.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(GetServiceDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GetServiceDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}