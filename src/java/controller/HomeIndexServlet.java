/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Transaction;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository1.TransactionRepository;

/**
 *
 * @author Admin
 */
 
@WebServlet(name = "HomeIndexServlet", urlPatterns = {"/index"})
public class HomeIndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String CTVID = user.getUserId();
        ArrayList<Transaction> listTransaction = TransactionRepository.getListTransactionByCTVID(CTVID);
        request.setAttribute("transactions", listTransaction);
        request.getRequestDispatcher("index.jsp").forward(request, response);
        System.out.println("CTVID: " + CTVID);
        System.out.println("List Size: " + listTransaction.size()); // Logging
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request if needed
    }
}