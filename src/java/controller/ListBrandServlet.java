/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.Brand;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repository1.ProductRepository;
import repository1.UserRepository;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="ListBrandServlet", urlPatterns={"/listBrand"})
public class ListBrandServlet extends HttpServlet {
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Brand> listBrands= (ArrayList<Brand>) ProductRepository.getAllBrands();
        request.setAttribute("listBrands",listBrands);
        request.getRequestDispatcher("listBrand.jsp").forward(request,response);
    }
}
