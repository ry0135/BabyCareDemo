package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import repository1.UserRepository;
import service.MyRandom;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        String username=request.getParameter("username");
        String password = request.getParameter("password");
        String firstname=request.getParameter("firstname")
                , lastname=request.getParameter("lastname")
                ,address=request.getParameter("address")
                ,phone=request.getParameter("phone")
                ,email=request.getParameter("email");
        String code = request.getParameter("authcode");
         password = UserRepository.MaHoa(password);
        //check exist username
        if(UserRepository.checkExistUsername(username)){
            request.setAttribute("thongbao","Tên đăng nhập đã tồn tại");
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }else if(UserRepository.checkExistEmail(email)){
            request.setAttribute("thongbao","Email này đã đăng kí vui lòng nhập email khác");
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }
        else {
            String userID= MyRandom.getRandomUserID();
            request.getSession().setAttribute("userID", userID);
            System.out.println("Register "+ username +" " + password+" "+ firstname+ " "+lastname+" "+address+" "+phone+" "+email);
            //insert table tblCustomer and tblAccount
            UserRepository.addCustomer(userID,firstname,lastname,address,phone,username,password,email);
//            request.setAttribute("thongbao","Đăng kí thành công vui lòng đăng nhập vào hệ thống");
            request.getRequestDispatcher("verificode.jsp").forward(request,response);
        }


    }
}