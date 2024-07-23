/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.OrderAccept;
import entity.OrderRefund;
import entity.User;
import entity.WithDraw;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository1.OrderRepository;
import repository1.TransactionRepository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="ListWithDrawServlet", urlPatterns={"/listwithdraw"})
public class ListWithDrawServlet extends HttpServlet {
   


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       
        try {
            response.setContentType("text/html;charset=UTF-8");
            TransactionRepository transactionRepository = new TransactionRepository();
            
            List<WithDraw> listB = TransactionRepository.getWithdraws();
            request.setAttribute("listB", listB);
            
            
            
            request.getRequestDispatcher("list-withdraw.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ListWithDrawServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListWithDrawServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    } 




