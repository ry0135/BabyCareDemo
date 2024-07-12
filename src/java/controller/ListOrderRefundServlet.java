/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.OrderAccept;
import entity.OrderRefund;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository1.OrderRepository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="ListOrderRefundServlet", urlPatterns={"/listOrderRefund"})
public class ListOrderRefundServlet extends HttpServlet {
   


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<OrderRefund> listB = OrderRepository.getOrderRefund();
        
        // Iterate over the list and set the full name for each OrderRefund object
        for (OrderRefund orderRefund : listB) {
            String billID = orderRefund.getBillID();
            String fullName = OrderRepository.getCustomerFullNameByBillID(billID);
            orderRefund.setFullName(fullName);
        }
        
        request.setAttribute("listB", listB);
        request.getRequestDispatcher("list-Order-Refund.jsp").forward(request, response);
    }
    } 




