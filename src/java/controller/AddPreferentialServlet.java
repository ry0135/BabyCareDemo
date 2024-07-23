
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
import repository1.PreferentialRepository;

@WebServlet(name = "AddPreferentialServlet", urlPatterns = {"/addpreferential"})
@MultipartConfig // Bổ sung để hỗ trợ việc tải lên file
public class AddPreferentialServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddPreferentialServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("preferential-add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String preferentialCode = request.getParameter("preferentialCode");
        if (preferentialCode == null || preferentialCode.isEmpty()) {
            logger.warning("Preferential value is null or empty.");
            request.setAttribute("thongbao", "Vui lòng nhập mã giảm giá.");
            request.getRequestDispatcher("preferential-add.jsp").forward(request, response);
            return;
        }

        String preferentialName = request.getParameter("preferentialName");
        String startDay = request.getParameter("startDay");
        String endDay = request.getParameter("endDay");
        String quantityStr = request.getParameter("quantity");
        double rate = Double.parseDouble(request.getParameter("rate"));
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            logger.warning("Invalid quantity value: " + quantityStr);
            request.setAttribute("thongbao", "Giá trị số lượng không hợp lệ");
            request.getRequestDispatcher("preferential-add.jsp").forward(request, response);
            return;
        }

        String preferentialDescription = request.getParameter("preferentialDescription");
        Part part = request.getPart("preferentialImg");
        String filename = part.getSubmittedFileName();

         File absoluteDir = new File("D:\\FPT_VNI\\Semester 5\\BabyCare\\BabyCare4\\web\\img\\discount");
        if (!absoluteDir.exists()) {
            absoluteDir.mkdirs();
        }

        File relativeDir = new File(getServletContext().getRealPath("/") + "img" + File.separator + "discount");
        if (!relativeDir.exists()) {
            relativeDir.mkdirs();
        }

        if (filename != null && !filename.isEmpty()) {
            String absolutePath = "D:\\FPT_VNI\\Semester 5\\BabyCare\\BabyCare4\\web\\img\\discount" + File.separator + filename;
            String relativePath = getServletContext().getRealPath("/") + "img" + File.separator + "discount" + File.separator + filename;

            // Lưu file vào cả hai vị trí
            try (InputStream is = part.getInputStream()) {
                boolean success1 = uploadFile(is, absolutePath);
                boolean success2 = uploadFile(is, relativePath);
                if (success1 && success2) {
                    logger.info("Uploaded file successfully to both locations: " + filename);
                } else {
                    logger.warning("Failed to upload file to one or both locations: " + filename);
                }
            }
        }

        String preferentialImg = filename;
        User user = (User) session.getAttribute("user");
        String CTVID = user.getUserId();

        try {
            PreferentialRepository.addPreferential(preferentialCode, preferentialName, startDay, endDay, quantity, rate, preferentialDescription, preferentialImg, CTVID);
            request.setAttribute("thongbao", "Thêm thành công");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Error", ex);
            request.setAttribute("thongbao", "Có lỗi xảy ra khi thêm ưu đãi: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Class Not Found Error", ex);
            request.setAttribute("thongbao", "Có lỗi xảy ra khi thêm ưu đãi: " + ex.getMessage());
        }

        request.getRequestDispatcher("preferential-add.jsp").forward(request, response);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }
}
