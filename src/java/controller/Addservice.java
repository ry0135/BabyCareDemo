/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import entity.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository1.ServiceRespository;
import service.MyRandom;


/**
 *
 * @author ADMIN
 */
@WebServlet(name = "Addservice", urlPatterns = {"/addservice"})
public class Addservice extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Addservice</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Addservice at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        String serviceName = request.getParameter("serviceName");
        double servicePrice = 0.0;
        if (!request.getParameter("servicePrice").isEmpty()) {
            try {
                servicePrice = Double.parseDouble(request.getParameter("servicePrice"));
            } catch (NumberFormatException e) {
                System.out.println("Error: servicePrice must be a valid number.");
                e.printStackTrace();
                request.setAttribute("thongbao", "servicePrice must be a valid number.");
                request.getRequestDispatcher("service-add.jsp").forward(request, response);
                return;
            }
        }
        String serviceUrlImg = request.getParameter("serviceUrlImg");
        String description = request.getParameter("Description");

        System.out.println("Adding Service: "  + serviceName + " " + servicePrice + " " + serviceUrlImg + " " + description);

        boolean isAdded = false;
        try {
            isAdded = ServiceRespository.addService(serviceName, serviceUrlImg, description, servicePrice);
        } catch (SQLException ex) {
            Logger.getLogger(Addservice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Addservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (isAdded) {
            request.setAttribute("thongbao", "Thêm thành công");
        } else {
            request.setAttribute("thongbao", "Thêm thất bại");
        }
        request.getRequestDispatcher("service-add.jsp").forward(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
