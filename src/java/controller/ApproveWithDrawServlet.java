/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.OrderRepository;
import repository1.ProductRepository;
import repository1.TransactionRepository;
import repository1.UserRepository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="ApproveWithDrawServlet", urlPatterns={"/ApproveWithDraw"})
public class ApproveWithDrawServlet extends HttpServlet {
   
  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        TransactionRepository transactionRepository = new TransactionRepository();
        int withdrawID= Integer.parseInt(request.getParameter("withdrawID"));
        String brandID =request.getParameter("brandID");
        Double amount =Double.parseDouble(request.getParameter("amount"));

        
        TransactionRepository.updatewithdrawStatus(withdrawID);
        String email = TransactionRepository.getEmailByBrandID(brandID);

            UserRepository userRepository = new UserRepository();
            transactionRepository.sendCodeToEmailWithDraw(email,amount);
        String CTVID = null;
        
        response.sendRedirect("listwithdraw");
        
        
    } 


   

 
}
