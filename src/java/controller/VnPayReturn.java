package controller;

import entity.Booking;
import entity.User;
import repository1.ServiceRespository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "VnPayReturn", urlPatterns = {"/VnPayReturn"})
public class VnPayReturn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        User oldUser = (User) session.getAttribute("user");

        if (oldUser == null) {
            request.setAttribute("errorMessage", "User is not logged in.");
            request.getRequestDispatcher("booking-service.jsp").forward(request, response);
            return;
        }

        String customerID = oldUser.getUserId();
        String serviceID = (String) session.getAttribute("serviceID");
        String servicePriceStr = (String) session.getAttribute("servicePrice");
        String serviceName = (String) session.getAttribute("serviceName");
        String name = (String) session.getAttribute("name");
        String address = (String) session.getAttribute("address");
        String phone = (String) session.getAttribute("phone");
        String sex = (String) session.getAttribute("sex");
        String slot = (String) session.getAttribute("slot");
        String bookingDate = (String) session.getAttribute("bookingDate");
        String note = (String) session.getAttribute("note");
        String email = (String) session.getAttribute("email");

        // Convert service price from string to double
        double servicePrice;
        try {
            servicePrice = Double.parseDouble(servicePriceStr);
        } catch (NumberFormatException e) {
            request.setAttribute("serviceID", serviceID);
            request.setAttribute("servicePrice", servicePriceStr);
            request.setAttribute("serviceName", serviceName);
            request.setAttribute("errorMessage", "Invalid service price. Please enter a valid number.");
            request.getRequestDispatcher("booking-service.jsp").forward(request, response);
            return;
        }

        // Create a new Booking object and set its attributes
        Booking booking = new Booking();
        booking.setCustomerID(customerID);
        booking.setServiceID(serviceID);
        booking.setName(name);
        booking.setAddress(address);
        booking.setPhoneNumber(phone);
        booking.setSex(sex);
        booking.setSlot(slot);
        booking.setBookingDate(bookingDate);
        booking.setBookingStatus(1); // Assuming status is 1 for new bookings
        booking.setNote(note);
        booking.setPrice(servicePrice);
        booking.setEmail(email);
        booking.setServiceName(serviceName);

        // Save the booking to the database using repository
        ServiceRespository serviceRepository = new ServiceRespository();
        int bookingID = serviceRepository.insertBooking(booking); // Adjust to return booking ID

        // Set session attributes for further processing if needed
        session.setAttribute("bookingID", bookingID);
        session.setAttribute("serviceName", serviceName);
        session.setAttribute("servicePrice", servicePrice);

        // Redirect to further processing or confirmation page
        response.sendRedirect("AddBillServiceServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle POST method if needed
        doGet(request, response); // Call doGet for handling
    }

    @Override
    public String getServletInfo() {
        return "VnPayReturn Servlet";
    }
}