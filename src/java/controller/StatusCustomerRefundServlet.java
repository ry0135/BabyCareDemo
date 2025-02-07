/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.ServiceRespository;

/**
 *
 * @author ACER
 */
@WebServlet(name="StatusCustomerRefundServlet", urlPatterns={"/StatusCustomerRefundServlet"})
public class StatusCustomerRefundServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StatusCustomerRefundServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatusCustomerRefundServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
       String refundID = request.getParameter("refundID");
       String email = request.getParameter("email");
       String name = request.getParameter("name");
        String serviceName = request.getParameter("servicename");
        String price = request.getParameter("price");
        String note = request.getParameter("note");
        if (refundID != null) {
            int ID = Integer.parseInt(refundID);
            
            
            ServiceRespository serviceRepository = new ServiceRespository();
            boolean updateSuccess = serviceRepository.updateStatusCustomerRefund(ID, 2);
             String subject = "Xác nhận hoàn tiền";
                String body = "Kính gửi " + name + ",\n\n"
                        + "Bạn đã hủy dịch vụ của chúng tôi. Dưới đây là chi tiết:\n\n"
                        + "Tên dịch vụ: " + serviceName + "\n"
                      
                        + "Số tiền hoàn lại :" + price + "\n"
                        + "Ghi chú: " + note + "\n\n"
                        + "Trân trọng,\n"
                        + "BabyNature";

                ServiceRespository.sendEmail(email, subject, body);
            if (updateSuccess) {
                response.sendRedirect("ListCustomerRefundServlet"); // Redirect to a success page
            } else {
                response.sendRedirect("error.jsp"); // Redirect to an error page
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid refund ID or status.");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
