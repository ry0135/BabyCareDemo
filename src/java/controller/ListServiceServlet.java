package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.ServiceRespository;
import entity.Service;

/**
 * Servlet implementation class ListServiceServlet
 */
@WebServlet(name = "ListServiceServlet", urlPatterns = {"/listService"})
public class ListServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListServiceServlet() {
        super();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ArrayList<Service> allServices = ServiceRespository.getALLService();
        int page, numberElementsInPage = 6;
        int size = allServices.size();
        
        // Calculate the total number of pages
        int numberPage = (size % numberElementsInPage == 0) ? (size / numberElementsInPage) : (size / numberElementsInPage + 1);
        
        // Get the current page number from the request
        String xpage = request.getParameter("page");
        if (xpage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(xpage);
        }
        
        // Determine the start and end indices for the sublist
        int start = (page - 1) * numberElementsInPage;
        int end = Math.min(page * numberElementsInPage, size);
        
        // Get the sublist of services for the current page
        ArrayList<Service> list = new ArrayList<>(allServices.subList(start, end));
        
        // Set the attributes for the request
        request.setAttribute("ListS", list);
        request.setAttribute("numberPage", numberPage);
        request.setAttribute("currentPage", page);
        
        // Forward to the JSP page
        request.getRequestDispatcher("service.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}