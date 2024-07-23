package controller;

import entity.Brand;
import entity.CommentProduct;
import entity.Product;
import entity.User;
import repository1.ProductRepository;
import repository1.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repository1.FollowRepository;
import static repository1.ProductRepository.getCountProductByCTV;

@WebServlet(name = "GetProductDetailServlet", value = "/getProductDetail")
public class GetProductDetailServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetProductDetailServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userr = (User) session.getAttribute("user");


        String userId = null;
        if (userr != null) {
            userId = userr.getUserId();
        }

        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            Product product = ProductRepository.getProductById(id);

            List<CommentProduct> comments = ProductRepository.listCommentsByProductId(id);
            Brand brand = ProductRepository.getBrandByCTVId(product.getCTVID());
            for (CommentProduct comment : comments) {
                User user = UserRepository.getUserDetailsByCommentId(comment.getCommentID());
                comment.setUser(user); // 
            }
             boolean isFollowing = false;
            if (userId != null) {
                isFollowing = FollowRepository.isFollowing(userId, brand.getBrandID());
            }

            int TotalRating = ProductRepository.getTotalRatingForProduct(product.getProductId());
            double getAverageRating = ProductRepository.getAverageRatingForProduct(product.getProductId());
            int TotalComment = ProductRepository.getTotalCommentsForProduct(product.getProductId());
            int productCount = getCountProductByCTV(product.getCTVID());
            request.setAttribute("listComments", comments);
            request.setAttribute("TotalRating", getAverageRating);
            request.setAttribute("TotalComment", TotalComment);
            request.setAttribute("product", product);
            request.setAttribute("brand", brand);
            request.setAttribute("productCount", productCount);
            request.setAttribute("isFollowing", isFollowing);
            String message = (String) session.getAttribute("message");
            if (message != null) {
                request.setAttribute("message", message);
                session.removeAttribute("message"); // Xóa thông báo sau khi hiển thị
            }
            request.getRequestDispatcher("product-detail.jsp").forward(request, response);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error occurred while getting product details: ", ex);
            response.sendRedirect("index.jsp");
        }
    }
}
