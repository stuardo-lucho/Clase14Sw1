package Controllers;

import Beans.Country;
import Beans.Employee;
import Daos.CountryDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@WebServlet(name = "CountryServlet", urlPatterns = {"/CountryServlet"})
public class CountryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Employee em = (Employee) session.getAttribute("employeeSession");
        if (em == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

            CountryDao countryDao = new CountryDao();
            RequestDispatcher view;
            Country country;
            String countryId;

            switch (action) {
                case "formCrear":
                    view = request.getRequestDispatcher("country/newCountry.jsp");
                    view.forward(request, response);
                    break;
                case "crear":
                    countryId = request.getParameter("id");
                    String countryName = request.getParameter("countryName");
                    System.out.println(countryName);
                    BigDecimal regionId = new BigDecimal(request.getParameter("regionId"));

                    country = countryDao.obtener(countryId);

                    if (country == null) {
                        countryDao.crear(countryId, countryName, regionId);
                    } else {
                        countryDao.actualizar(countryId, countryName, regionId);
                    }
                    response.sendRedirect(request.getContextPath() + "/CountryServlet");
                    break;
                case "lista":
                    ArrayList<Country> lista = countryDao.listar();

                    request.setAttribute("lista", lista);

                    view = request.getRequestDispatcher("country/listaCountry.jsp");
                    view.forward(request, response);
                    break;

                case "editar":
                    countryId = request.getParameter("id");
                    country = countryDao.obtener(countryId);
                    if (country == null) {
                        response.sendRedirect(request.getContextPath() + "/CountryServlet");
                    } else {
                        request.setAttribute("country", country);
                        view = request.getRequestDispatcher("country/updateCountry.jsp");
                        view.forward(request, response);
                    }
                    break;
                case "borrar":
                    countryId = request.getParameter("id");
                    if (countryDao.obtener(countryId) != null) {
                        countryDao.borrar(countryId);
                    }
                    response.sendRedirect(request.getContextPath() + "/CountryServlet");
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
