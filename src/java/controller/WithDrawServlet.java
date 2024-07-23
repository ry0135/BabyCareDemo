package controller;

import entity.Brand;
import entity.User;
import entity.WithDraw;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository1.OrderRepository;
import repository1.ProductRepository;
import repository1.TransactionRepository;

@WebServlet(name = "WithDrawServlet", urlPatterns = {"/withdraw"})
public class WithDrawServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if user is not logged in
            return;
        }
        String CTVID = user.getUserId();
        String id = request.getParameter("id");
        System.out.println(id);
        
   
        double revenue = 0;
       
        try {
            revenue = TransactionRepository.getRevenueByCTVID(CTVID);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WithDrawServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
            request.setAttribute("revenue", revenue);
            
       Brand brand = ProductRepository.getBrandByCTVId(CTVID);

        try {
            List<WithDraw> listWithDraw = TransactionRepository.listWithdraw(brand.getBrandID());
            request.setAttribute("listWithDraw", listWithDraw);
        } catch (SQLException ex) {
            Logger.getLogger(WithDrawServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WithDrawServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String message = (String) session.getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
        String thongbao = (String) session.getAttribute("thongbao");
        if (thongbao != null) {
            request.setAttribute("thongbao", thongbao);
            session.removeAttribute("thongbao"); // Remove message after displaying
        }

        String brandID = request.getParameter("brandID");
        request.setAttribute("revenue", revenue);
        request.setAttribute("brandID", brandID);

        request.getRequestDispatcher("withdraw.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if user is not logged in
            return;
        }

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            request.setCharacterEncoding("utf-8");

            String amountStr = request.getParameter("amount");
            
            String revenueStr = request.getParameter("revenue");
            System.out.println("Revenue parameter: " + revenueStr);
            double amount = 0;
            String CTVID = user.getUserId();
             Brand brand = ProductRepository.getBrandByCTVId(CTVID);
      
            if (amountStr != null && !amountStr.isEmpty()) {
                try {
                    amount = Double.parseDouble(amountStr);
            
                      double  revenue = Double.parseDouble(revenueStr);
                    
                      
                    if (amount > revenue) {
                        session.setAttribute("message", "Số tiền yêu cầu vượt quá số tiền trong ví của bạn");
                        
                        response.sendRedirect("withdraw");
                        return;
                    }

                    if (amount < 100000) {
                        session.setAttribute("message", "Số tiền yêu cầu rút tối thiểu là 100,000 VND");
                        response.sendRedirect("withdraw");
                        return;
                    }

                } catch (NumberFormatException e) {
                    request.setAttribute("thongbao1", "Invalid amount format.");
                    request.getRequestDispatcher("withdraw.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("thongbao1", "Amount is required.");
                response.sendRedirect("withdraw");
                return;
            }

            boolean isAdded = TransactionRepository.addWithdraw(brand.getBrandID(), amount);
            boolean deductRevenue = OrderRepository.deductRevenue(CTVID, amount);    

            // Set success or failure message
            if (isAdded && deductRevenue) {
                session.setAttribute("thongbao", "Gửi yêu cầu thành công");     
            } else {
                session.setAttribute("thongbao", "Gửi yêu cầu thất bại");
            }
            session.removeAttribute("message"); // Xóa thông báo cũ nếu cần
            response.sendRedirect("withdraw");
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(WithDrawServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("thongbao1", "An error occurred: " + ex.getMessage());
            request.getRequestDispatcher("withdraw.jsp").forward(request, response);
        }
    }

}
