package controller;

import entity.Brand;
import entity.CommentProduct;
import entity.Product;
import entity.User;
import repository1.FollowRepository;

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
import repository1.ProductRepository;
import static repository1.ProductRepository.getCountProductByCTV;
import repository1.UserRepository;

@WebServlet(name = "FollowBrandServlet", urlPatterns = {"/followBrand"})
public class FollowBrandServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetProductDetailServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         HttpSession session = request.getSession();
        User userr = (User) session.getAttribute("user");

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (userr == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String userId = userr.getUserId();
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }
        String brandId = request.getParameter("brandId");
        try {
            Product product = ProductRepository.getProductById(id);
            if (product == null) {
                response.sendRedirect("index.jsp");
                return;
            }
            List<CommentProduct> comments = ProductRepository.listCommentsByProductId(id);
            Brand brand = ProductRepository.getBrandByCTVId(product.getCTVID());
            for (CommentProduct comment : comments) {
                User user = UserRepository.getUserDetailsByCommentId(comment.getCommentID());
                comment.setUser(user); 
            }
            boolean isFollowing = FollowRepository.isFollowing(userId, brandId);
            int TotalRating = ProductRepository.getTotalRatingForProduct(product.getProductId());
            double getAverageRating = ProductRepository.getAverageRatingForProduct(product.getProductId());
            int TotalComment = ProductRepository.getTotalCommentsForProduct(product.getProductId());
            int productCount = getCountProductByCTV(product.getCTVID());
            FollowRepository.addFollow(userId, brand.getBrandID());
            
            request.setAttribute("listComments", comments);
            request.setAttribute("TotalRating", getAverageRating);
            request.setAttribute("TotalComment", TotalComment);
            request.setAttribute("product", product);
            request.setAttribute("brand", brand);
            request.setAttribute("productCount", productCount);
            request.setAttribute("isFollowing", isFollowing);
            
            response.sendRedirect("getProductDetail?id=" + id);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error occurred while getting product details: ", ex);
            response.sendRedirect("index.jsp");
        }
       
    }
}
    

