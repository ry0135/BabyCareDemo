/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.UserRepository;

@WebServlet(name="CancelRegistrationServlet", urlPatterns={"/cancelRegistration"})
public class CancelRegistrationServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = (String) request.getSession().getAttribute("userID");
        
        // Xóa người dùng khỏi cơ sở dữ liệu
        UserRepository.deleteCustomer(userID);
        
        // Xóa session
        request.getSession().invalidate();
        
        // Chuyển hướng đến trang đăng ký
        response.sendRedirect("register.jsp?message=Session expired, please register again");
    }
}
