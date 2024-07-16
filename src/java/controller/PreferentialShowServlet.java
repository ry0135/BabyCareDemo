package controller;

import entity.Preferential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import repository1.PreferentialRepository;
import repository1.UserRepository;

@WebServlet(name = "PrefrentialShowServlet", urlPatterns = "/preferential")
public class PreferentialShowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Preferential> listPreferential = PreferentialRepository.getListPreferential();
        request.setAttribute("preferentialList", listPreferential);
        request.getRequestDispatcher("/preferential.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String preferentialId = request.getParameter("preferentialId");
        String userEmail = request.getParameter("email");  // Retrieve email from form input

        if (userEmail == null || userEmail.trim().isEmpty()) {
            request.setAttribute("message", "Email address is required.");
            doGet(request, response);
            return;
        }

        try {
            String discountCode = PreferentialRepository.getDiscountCode(preferentialId);
            if (discountCode != null) {
                UserRepository.sendDCodeToEmail(userEmail, discountCode);  // Assuming this method exists and is correctly implemented
                request.setAttribute("message", "Discount code sent to your email: " + userEmail);
            } else {
                request.setAttribute("message", "Error retrieving discount code.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Server error: " + e.getMessage());
        }
        doGet(request, response);
    }
}
