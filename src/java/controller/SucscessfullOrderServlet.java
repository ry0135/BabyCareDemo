/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
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
import repository1.UserRepository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="SucscessfullOrderServlet", urlPatterns={"/succsessfull"})
public class SucscessfullOrderServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            System.out.println(id);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Cập nhật trạng thái đơn hàng
            OrderRepository.SuccsessOrder(id, user.getUserId());
            
            OrderRepository orderRepository = new OrderRepository();
            String name = orderRepository.getNameByOrderId(id);
            String email = orderRepository.getEmailByOrderId(id);
            UserRepository userRepository = new UserRepository();
            UserRepository.sendCodeToEmailSuccsessOrder(email, id, name);
  
            // Cập nhật doanh thu từ đơn hàng
            orderRepository.addRevenueFromOrder(id, user.getUserId());

            // Cập nhật session với thông tin revenue mới
            double revenue = TransactionRepository.getRevenueByCTVID(user.getUserId());
            session.setAttribute("revenue", revenue);

            response.sendRedirect("order-list-paid?thongbao=1");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SucscessfullOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

