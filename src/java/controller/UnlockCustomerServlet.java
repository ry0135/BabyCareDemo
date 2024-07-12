package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import repository1.UserRepository;

import java.io.IOException;

@WebServlet(name = "UnlockCustomerServlet", value = "/unlockcustomer")
public class UnlockCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empID=request.getParameter("empID");
        UserRepository.unlockCustomer(empID);
        response.sendRedirect("manage-cus-account");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
