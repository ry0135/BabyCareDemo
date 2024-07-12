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
import repository1.UserRepository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="ApproveOrderRefundServlet", urlPatterns={"/ApproveOrderRefund"})
public class ApproveOrderRefundServlet extends HttpServlet {
   
  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        int refundID= Integer.parseInt(request.getParameter("refundID"));
        String billID =request.getParameter("billID");
        
        OrderRepository.updateRefundStatus(refundID);
        OrderRepository orderRepository = new OrderRepository();
            String name = orderRepository.getNameByOrderId(billID);
            String email = orderRepository.getEmailByOrderId(billID);
            UserRepository userRepository = new UserRepository();
            OrderRepository.sendCodeToEmailOrderRefund(email,billID,name);
        String CTVID = null;
        try {
            CTVID = OrderRepository.getCTVIDByBILLID(billID);
        } catch (SQLException ex) {
            Logger.getLogger(ApproveOrderRefundServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ApproveOrderRefundServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        OrderRepository.cancelOrder(billID, CTVID); 
        OrderRepository.updateProductByCancelOrder(billID);
        ProductRepository.updateProductSoldByCancelOrder(billID);
        OrderRepository.deleteOrderDetailsByBillID(billID);
        response.sendRedirect("listOrderRefund");
        
        
    } 


   

 
}
