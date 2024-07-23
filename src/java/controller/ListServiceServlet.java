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
import entity.ServiceType;

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
    ArrayList<ServiceType> listc = ServiceRespository.getServiceTypeAll();
    int page, numberElementsInPage = 6;
    int size = allServices.size();

    int numberPage = (size % numberElementsInPage == 0) ? (size / numberElementsInPage) : (size / numberElementsInPage + 1);
    String xpage = request.getParameter("page");
    if (xpage == null) {
        page = 1;
    } else {
        page = Integer.parseInt(xpage);
    }

    int start = (page - 1) * numberElementsInPage;
    int end = Math.min(page * numberElementsInPage, size);

    ArrayList<Service> list = new ArrayList<>(allServices.subList(start, end));

    request.setAttribute("ListS", list);
    request.setAttribute("listC", listc); // Ensure this is set correctly
    request.setAttribute("numberPage", numberPage);
    request.setAttribute("currentPage", page);
    System.out.println("Service Types: " + listc);

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
