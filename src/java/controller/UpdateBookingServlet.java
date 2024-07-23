package controller;

import entity.Booking;
import repository1.ServiceRespository;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateBookingServlet", urlPatterns = {"/UpdateBookingServlet"})
public class UpdateBookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Not used
    }

   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
    int bookingID = Integer.parseInt(request.getParameter("bookingID"));
    ServiceRespository serviceRepository = new ServiceRespository();
    Booking booking = serviceRepository.getBookingById(bookingID);
    request.setAttribute("booking", booking);
    request.getRequestDispatcher("updateBooking.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        int bookingID = Integer.parseInt(request.getParameter("bookingID"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String sex = request.getParameter("sex");
        String bookingDate = request.getParameter("bookingDate");
        String slot = request.getParameter("slot");
        String note = request.getParameter("note");
        String serviceID = request.getParameter("serviceID");

      
         int bookedCount = 0;
        try {
            bookedCount = ServiceRespository.countBookingsForDateAndSlot(bookingDate, slot,serviceID);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateBookingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

       if (bookedCount >=5  ) {
        request.setAttribute("errorMessage", slot + " ngày " + bookingDate + " đã đầy, bạn vui lòng chọn slot khác.");
        
    ServiceRespository serviceRepository = new ServiceRespository();
    Booking booking = serviceRepository.getBookingById(bookingID);
    request.setAttribute("booking", booking);
        
        request.getRequestDispatcher("updateBooking.jsp").forward(request, response);
           
            return;
        }

        Booking booking = new Booking();
        booking.setBookingID(bookingID);
        booking.setName(name);
        booking.setAddress(address);
        booking.setSex(sex);
        booking.setBookingDate(bookingDate);
        booking.setSlot(slot);
        booking.setNote(note);
        
     
        ServiceRespository serviceRepository = new ServiceRespository();
        serviceRepository.updateBooking(booking);

        response.sendRedirect("ListBookingCustomerIDServlet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}