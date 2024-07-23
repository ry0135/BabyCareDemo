package controller;

import entity.OrderAccept;
import entity.Transaction;
import entity.User;
import repository1.OrderRepository;
import repository1.TransactionRepository;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "TransactionServlet", urlPatterns = {"/transaction"})
public class TransactionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String CTVID = user.getUserId();
        // Retrieve transaction list
        ArrayList<Transaction> listTransaction = TransactionRepository.getListTransactionByCTVID(CTVID);
        request.setAttribute("transactions", listTransaction);
        
        // Retrieve completed orders list
        ArrayList<OrderAccept> listCompletedOrders = OrderRepository.getAllOrderMonnyByCTVId(CTVID);
        if (listCompletedOrders == null) {
            listCompletedOrders = new ArrayList<>(); // Ensure there's no null attribute
        }
        request.setAttribute("completedOrders", listCompletedOrders);
        
        // Forward to JSP
        request.getRequestDispatcher("transaction.jsp").forward(request, response);
        
        // Logging for debug purposes
        System.out.println("CTVID: " + CTVID);
        System.out.println("Transaction List Size: " + listTransaction.size());
        System.out.println("Completed Orders List Size: " + listCompletedOrders.size());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request if needed
    }
}
