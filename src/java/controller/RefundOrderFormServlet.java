/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.CustomerRefund;
import entity.OrderRefund;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository1.OrderRepository;
import repository1.ProductRepository;
import repository1.ServiceRespository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="RefundOrderFormServlet", urlPatterns={"/refundOrderForm"})
public class RefundOrderFormServlet extends HttpServlet {
   
 
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        
        String id = request.getParameter("id");
        request.setAttribute("id", id);
        double total = OrderRepository.getTotalPriceByBillID(id);
        request.setAttribute("total", total);
        
        request.getRequestDispatcher("orderRefund.jsp").forward(request, response);
        
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // Check user
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userID = user.getUserId();
        
      
            // Extract parameters from the request
            String billID = request.getParameter("id");
            int accountNumber = Integer.parseInt(request.getParameter("accountNumber"));
            String bankName = request.getParameter("bankName");
            String accountName = request.getParameter("accountName");
            double refundAmount = Double.parseDouble(request.getParameter("refundAmount"));
            String note = request.getParameter("note");

        
 

            // Call the insertCustomerRefund method
         boolean isInserted = OrderRepository.insertOrderRefund(billID, userID, accountNumber, accountName, bankName, refundAmount, note);
         
            if (isInserted) {
                OrderRepository.cancelOrderRefund(billID);
                
                
                response.sendRedirect("getorderhistoryservlet");
            } else {
            
                request.setAttribute("errorMessage", "Failed to submit refund request.");
            }
           
               
    }
    }

    

