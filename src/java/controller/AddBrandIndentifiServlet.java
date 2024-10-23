package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entity.Brand;
import entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import repository1.OrderRepository;
import repository1.ProductRepository;
import repository1.UserRepository;
import service.MyRandom;

@MultipartConfig // Annotation để hỗ trợ việc upload file
@WebServlet(name = "AddBrandIndentifiServlet", urlPatterns = {"/addBrandIndentifi"})
public class AddBrandIndentifiServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AddBrandIndentifiServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String thongbao = (String) session.getAttribute("thongbao");
        if (thongbao != null) {
            request.setAttribute("thongbao", thongbao);
            session.removeAttribute("thongbao"); // Xóa thông báo sau khi hiển thị
        }

        String userID = user.getUserId();
        Brand brand = ProductRepository.getBrandByCTVId(userID);
        request.setAttribute("brand", brand);
        boolean hasPending = UserRepository.hasPendingRegistration(userID);
        request.setAttribute("hasPending", hasPending);

        request.getRequestDispatcher("addBrand/information_definition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.warning("User is not logged in, redirecting to login page.");
            response.sendRedirect("login.jsp");
            return;
        }

        String userID = user.getUserId();
        String CCCDNumber = request.getParameter("CCCDNumber");
        logger.info("Received CCCDNumber: " + CCCDNumber + " from userID: " + userID);

        // Xử lý part1 (Ảnh CCCD)
        Part part1 = request.getPart("img_CCCD");
        Part part2 = request.getPart("img_CCCD_Face");

        if (part1 == null || part1.getSize() == 0 || part2 == null || part2.getSize() == 0) {
            session.setAttribute("thongbao", "Vui lòng chọn cả hai hình ảnh.");
            logger.warning("Image parts are missing or empty.");
            response.sendRedirect(request.getContextPath() + "/addBrandIndentifi");
            return;
        }

        String filename1 = part1.getSubmittedFileName();
        String filename2 = part2.getSubmittedFileName();
        logger.info("Uploaded filenames: " + filename1 + ", " + filename2);

        // (các đoạn mã khác...)
        try {
            // Cập nhật thông tin CCCD
            Brand existingBrand = ProductRepository.getBrandByCTVId(userID);
            if (existingBrand != null ) {
                logger.info("Brand found for userID: " + userID);
                if (UserRepository.checkIdentifiNumberExist(CCCDNumber)) {
                    session.setAttribute("thongbao", "Số CCCD/CMND đã tồn tại.");
                    logger.warning("CCCDNumber already exists: " + CCCDNumber);
                    response.sendRedirect(request.getContextPath() + "/addBrandIndentifi");
                    return;
                }

                boolean updated = UserRepository.updateBrandIdentifiNumber(existingBrand.getBrandID(), CCCDNumber, filename1, filename2);
                logger.info("Attempting to update brand identifi number: " + existingBrand.getBrandID() + ", CCCDNumber: " + CCCDNumber);

                if (updated) {
                    session.setAttribute("thongbao", "Lưu thông tin thành công.");
                    logger.info("Brand identifi number updated successfully for brandID: " + existingBrand.getBrandID());
                } else {
                    session.setAttribute("thongbao", "Có lỗi xảy ra khi cập nhật cửa hàng. Vui lòng thử lại.");
                    logger.warning("Failed to update brand identifi number for brandID: " + existingBrand.getBrandID());
                }
            } else {
                logger.warning("No brand found or identifiNumber already set for userID: " + userID);
            }
            response.sendRedirect(request.getContextPath() + "/addBrandIndentifi");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in processing CCCD upload", e);
            session.setAttribute("thongbao", "Có lỗi xảy ra. Vui lòng thử lại.");
            response.sendRedirect(request.getContextPath() + "/addBrandIndentifi");
        }
    }

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
            logger.info("File uploaded successfully to: " + path);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to upload file to: " + path, e);
        }
        return test;
    }

}
