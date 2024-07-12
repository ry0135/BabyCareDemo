package controller;

import entity.Feedback;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.ServiceRespository;

@WebServlet(name = "FeedBackServlet", urlPatterns = {"/FeedBackServlet"})
public class FeedBackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerID = request.getParameter("CustomerID");        
        String serviceID = request.getParameter("ServiceID");
        String bookingDate = request.getParameter("BookingDate");
        String name = request.getParameter("name");

        request.setAttribute("CustomerID", customerID);
        request.setAttribute("ServiceID", serviceID);
        request.setAttribute("BookingDate", bookingDate);
        request.setAttribute("name", name);
        request.getRequestDispatcher("feedback.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        String customerID = request.getParameter("CustomerID");
        String serviceIDString = request.getParameter("ServiceID");
        String testimonial = request.getParameter("testimonial");
        String satisfactionLevelString = request.getParameter("satisfactionLevel");
        String name = request.getParameter("name");

        int serviceID;
        int satisfactionLevel;
        Date experienceDate;

        try {
            // Phân tích và kiểm tra các tham số đầu vào
            serviceID = Integer.parseInt(serviceIDString);
            satisfactionLevel = Integer.parseInt(satisfactionLevelString);

            // Kiểm tra và phân tích experienceDate
            String bookingDate = request.getParameter("BookingDate");
            if (bookingDate != null && !bookingDate.isEmpty()) {
                experienceDate = Date.valueOf(bookingDate); // Convert từ chuỗi sang java.sql.Date
            } else {
                experienceDate = new Date(System.currentTimeMillis()); // Sử dụng ngày hiện tại nếu không có ngày nào được truyền
            }

            // Tạo đối tượng Feedback
            Feedback feedback = new Feedback(customerID, serviceID, testimonial, experienceDate, satisfactionLevel, name);

            // Lưu phản hồi vào cơ sở dữ liệu
            ServiceRespository.saveFeedback(feedback);

            // Chuyển hướng sang trang cảm ơn
            response.sendRedirect("thankyou.jsp");

        } catch (NumberFormatException e) {
            // Xử lý ngoại lệ khi số không hợp lệ
            request.setAttribute("errorMessage", "Định dạng số không hợp lệ. Vui lòng nhập số hợp lệ.");
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
        } catch (IOException e) {
            // Xử lý ngoại lệ IO (ví dụ: chuyển hướng không thành công)
            request.setAttribute("errorMessage", "Lỗi IO xảy ra trong khi xử lý yêu cầu của bạn. Vui lòng thử lại sau.");
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Feedback Servlet";
    }
}
