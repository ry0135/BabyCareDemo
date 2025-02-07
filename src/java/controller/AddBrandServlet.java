package controller;

import entity.Brand;
import entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import repository1.OrderRepository;
import repository1.ProductRepository;
import repository1.UserRepository;
import service.MyRandom;

@WebServlet(name = "AddBrandServlet", urlPatterns = {"/addBrand"})
@MultipartConfig // Annotation để hỗ trợ việc upload file
public class AddBrandServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AddBrandServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is not logged in
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String userID = user.getUserId();
        Brand brand = ProductRepository.getBrandByCTVId(userID);
        request.setAttribute("brand", brand);
        boolean hasPending = UserRepository.hasPendingRegistration(userID);
        System.out.println(hasPending);

        request.setAttribute("hasPending", hasPending);
        request.getRequestDispatcher("registerctv.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userID = user.getUserId();
        String accountNumber = request.getParameter("accountNumber");
        String bankName = request.getParameter("bankName");
        String brandName = request.getParameter("brandName");
        String brandDescription = request.getParameter("brandDescription");
        String newAddress = request.getParameter("newAddress");
        String brandAddress = request.getParameter("brandAddress");
        System.out.println(newAddress);

        String CCCDNumber = request.getParameter("CCCDNumber");
        String name = request.getParameter("name");

        try {

            // Kiểm tra xem thương hiệu đã tồn tại hay chưa
            Brand existingBrand = ProductRepository.getBrandByCTVId(userID);

            if (existingBrand != null) {

                if (UserRepository.checkBrandNameExist(brandName, existingBrand.getBrandID())) {
                    request.setAttribute("thongbao1", "Tên cửa hàng đã tồn tại.");
                } else {
                    // Nếu thương hiệu đã tồn tại, tiến hành cập nhật
                    boolean updated = UserRepository.updateBrand(existingBrand.getBrandID(), brandName, (newAddress != null && !newAddress.isEmpty()) ? newAddress : brandAddress, bankName, accountNumber);

                    if (updated) {
                        request.setAttribute("thongbao", "Lưu thông tin thành công.");
                    } else {
                        request.setAttribute("thongbao", "Có lỗi xảy ra khi cập nhật cửa hàng. Vui lòng thử lại.");
                    }
                }
            } else {
                // Nếu thương hiệu chưa tồn tại, thêm mới
                if (UserRepository.checkBrandNameExist(brandName)) {
                    request.setAttribute("thongbao1", "Tên cửa hàng đã tồn tại.");
                } else {
                    String brandID = MyRandom.getRandomBrandID();
                    boolean added = UserRepository.addBrand(brandID, brandName, (newAddress != null && !newAddress.isEmpty()) ? newAddress : brandAddress, userID, bankName, accountNumber);

                    if (added) {
                        request.setAttribute("thongbao", "Lưu thông tin thành công");
                    } else {
                        request.setAttribute("thongbao", "Có lỗi xảy ra khi đăng ký cửa hàng. Vui lòng thử lại.");
                    }
                }
            }

            doGet(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in processing add/update brand", e);
            request.setAttribute("thongbao", "Có lỗi xảy ra. Vui lòng thử lại.");
            doGet(request, response);
        }

    }

//  
    public boolean uploadFile(InputStream is, String path) {
        boolean test = false;
        try {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(bytes);
                fos.flush();
            }
            test = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }
}
