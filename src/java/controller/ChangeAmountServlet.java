package controller;

import entity.Cart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "changeAmountServlet", value = "/changeamount")
public class ChangeAmountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            System.err.println("Cart is null");
            response.sendRedirect("cart");
            return;
        }

        String increase = request.getParameter("increase");
        String decrease = request.getParameter("decrease");
        String delete = request.getParameter("delete");

        if (increase != null) {
            System.out.println("Increasing amount for item: " + increase);
            System.out.println(cart.increaseAmount(increase));
        } else if (decrease != null) {
            System.out.println("Decreasing amount for item: " + decrease);
            System.out.println(cart.decreaseAmount(decrease));
        } else if (delete != null) {
            System.out.println("Deleting item: " + delete);
            System.out.println(cart.removeItem(delete));
        } else {
            System.err.println("No action specified");
        }
        
        response.sendRedirect("cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implementation for doPost if needed
    }
}
