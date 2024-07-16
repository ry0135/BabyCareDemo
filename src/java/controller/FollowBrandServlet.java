package controller;

import entity.User;
import repository1.FollowRepository;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FollowBrandServlet", urlPatterns = {"/followBrand"})
public class FollowBrandServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is not logged in
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userID = user.getUserId();  // Assuming there is a getUserId method in your User class
        String brandID = request.getParameter("brandID");

        if (brandID == null || brandID.trim().isEmpty()) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            boolean isFollowing = FollowRepository.isFollowing(userID, brandID);
            if (isFollowing) {
                FollowRepository.removeFollow(userID, brandID);
            } else {
                FollowRepository.addFollow(userID, brandID);
            }
            request.setAttribute("isFollowing", !isFollowing);
            request.setAttribute("brandID", brandID);
            request.getRequestDispatcher("brandDetails.jsp").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException("Database error while following/unfollowing the brand", ex);
        }
    }
}
