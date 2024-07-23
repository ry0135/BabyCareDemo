package controller;

import entity.CustomerRefund;
import repository1.ServiceRespository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "CustomerRefundServlet", urlPatterns = {"/CustomerRefund"})
public class CustomerRefundServlet extends HttpServlet {
private static final DecimalFormat decimalFormat = new DecimalFormat("###0");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        String bookingID = request.getParameter("bookingID");
        String customerID = request.getParameter("customerID");
        String nameUser = request.getParameter("name");
        String servicePriceStr = request.getParameter("servicePrice");
        String billID = request.getParameter("billID");
        double servicePrice = Double.parseDouble(servicePriceStr);
        String serviceName = request.getParameter("serviceName");
        String email = request.getParameter("t");
        // Calculate 80% of servicePrice
        double refundAmount = servicePrice * 0.80;

        // Set attributes
        request.setAttribute("bookingID", bookingID);
        request.setAttribute("customerID", customerID);
        request.setAttribute("nameUser", nameUser);
        request.setAttribute("refundAmount", decimalFormat.format(refundAmount));
        request.setAttribute("billID", billID);
        request.setAttribute("serviceName", serviceName);
        request.setAttribute("t", email);

        request.getRequestDispatcher("customerRefund.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        try {
            // Extract parameters from the request
            int bookingID = Integer.parseInt(request.getParameter("bookingID"));
            String customerID = request.getParameter("customerID");
            String nameUser = request.getParameter("nameUser");
            String serviceName = request.getParameter("serviceName");
            String accountNumber = request.getParameter("accountNumber");
            String bankName = request.getParameter("bankName");
            String accountName = request.getParameter("accountName");
            double refundAmount = Double.parseDouble(request.getParameter("refundAmount"));
            String note = request.getParameter("note");
            String email = request.getParameter("email");

            // Get current date for refundDate
            String refundDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // Default refund status to 1 (assuming 1 means pending or not processed)
            int refundStatus = 1;

            // Create CustomerRefund object
            CustomerRefund customerRefund = new CustomerRefund();
            customerRefund.setBookingID(bookingID);
        customerRefund.setCustomerID(customerID);
        customerRefund.setName(nameUser);
        customerRefund.setServiceName(serviceName);
        customerRefund.setAccountNumber(accountNumber);
        customerRefund.setBankName(bankName);
        customerRefund.setRefundAmount(refundAmount);
        customerRefund.setRefundDate(refundDate); // Use the appropriate date format
        customerRefund.setRefundStatus(1);
        customerRefund.setNote(note);
        customerRefund.setAccountName(accountName);
        customerRefund.setEmail(email);

            // Call the insertCustomerRefund method
         boolean isInserted = ServiceRespository.insertCustomerRefund(customerRefund);

            if (isInserted) {
                // Delete associated records if needed
                ServiceRespository serviceRepository = new ServiceRespository();
                serviceRepository.deleteBillService(Integer.parseInt(request.getParameter("billID")));
                serviceRepository.deleteBooking(bookingID);

                
                
                response.sendRedirect("ListBookingCustomerIDServlet");
            } else {
                // Redirect to an error page or set error message
                request.setAttribute("errorMessage", "Failed to submit refund request.");
            }
           
                
            
        } catch (Exception e) {
            // Handle exceptions and display an error message
            request.setAttribute("errorMessage", "An error occurred while processing the refund request: " + e.getMessage());
            request.getRequestDispatcher("customerRefund.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "CustomerRefundServlet handles refund requests.";
    }
}
