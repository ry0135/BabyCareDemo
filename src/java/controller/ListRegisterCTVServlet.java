
package controller;

import entity.Account;
import entity.Brand;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository1.UserRepository;

@WebServlet(name="ListRegisterCTVServlet", urlPatterns={"/listRegisterCTV"})
public class ListRegisterCTVServlet extends HttpServlet {
   
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Brand> listRegisterCTV= UserRepository.getListBrandsWithStatusZero();
                HttpSession session = request.getSession();

        request.setAttribute("listRegisterCTV",listRegisterCTV);
        String message = (String) session.getAttribute("message");

        if (message != null) {
                request.setAttribute("message", message);
                session.removeAttribute("message"); // Xóa thông báo sau khi hiển thị
            }
        request.getRequestDispatcher("listRegisterCTV.jsp").forward(request,response);
    }
}
