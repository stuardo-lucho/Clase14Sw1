package Controllers;

import Beans.Employee;
import Beans.Location;
import Daos.CountryDao;
import Daos.LocationDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LocationServlet", urlPatterns = {"/LocationServlet"})
public class LocationServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Employee em = (Employee)  request.getSession().getAttribute("employeeSession");

        if (em == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

            LocationDao locationDao = new LocationDao();
            CountryDao countryDao = new CountryDao();
            RequestDispatcher view;
            Location location;
            int locationId;

            switch (action) {
                case "formCrear":
                    request.setAttribute("listaPaises", countryDao.listar());
                    view = request.getRequestDispatcher("location/newLocation.jsp");
                    view.forward(request, response);
                    break;
                case "crear":
                    locationId = Integer.parseInt(request.getParameter("id"));
                    String streetAddress = request.getParameter("streetAddress");
                    String postalCode = request.getParameter("postalCode");
                    String city = request.getParameter("city");
                    String stateProvince = request.getParameter("stateProvince");
                    String countryId = request.getParameter("countryId");

                    location = locationDao.obtener(locationId);

                    if (location == null) {
                        locationDao.crear(locationId, streetAddress, postalCode, city, stateProvince, countryId);
                    } else {
                        locationDao.actualizar(locationId, streetAddress, postalCode, city, stateProvince, countryId);
                    }
                    response.sendRedirect(request.getContextPath() + "/LocationServlet");
                    break;
                case "lista":
                    ArrayList<Location> lista = locationDao.listar();

                    request.setAttribute("lista", lista);

                    view = request.getRequestDispatcher("location/listaLocation.jsp");
                    view.forward(request, response);
                    break;

                case "editar":
                    locationId = Integer.parseInt(request.getParameter("id"));
                    location = locationDao.obtener(locationId);
                    if (location == null) {
                        response.sendRedirect(request.getContextPath() + "/LocationServlet");
                    } else {
                        request.setAttribute("listaPaises", countryDao.listar());
                        request.setAttribute("location", location);
                        view = request.getRequestDispatcher("location/updateLocation.jsp");
                        view.forward(request, response);
                    }
                    break;
                case "borrar":
                    locationId = Integer.parseInt(request.getParameter("id"));
                    if (locationDao.obtener(locationId) != null) {
                        locationDao.borrar(locationId);
                    }
                    response.sendRedirect(request.getContextPath() + "/LocationServlet");
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
