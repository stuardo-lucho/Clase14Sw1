/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Beans.Department;
import Beans.Employee;
import Daos.DepartmentDao;
import Daos.EmployeeDao;
import Daos.LocationDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "DepartmentServlet", urlPatterns = {"/DepartmentServlet"})
public class DepartmentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Employee em = (Employee) request.getSession().getAttribute("employeeSession");

        if (em == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

            DepartmentDao departmentDao = new DepartmentDao();
            LocationDao locationDao = new LocationDao();
            EmployeeDao employeeDao = new EmployeeDao();
            RequestDispatcher view;
            Department department;
            int departmentId;

            switch (action) {
                case "formCrear":
                    request.setAttribute("listaLocations",locationDao.listar());
                    request.setAttribute("listaEmpleados",employeeDao.listarEmpleados());
                    view = request.getRequestDispatcher("department/newDepartment.jsp");
                    view.forward(request, response);
                    break;
                case "crear":
                    departmentId = Integer.parseInt(request.getParameter("id"));
                    String departmentName = request.getParameter("departmentName");
                    int managerId = Integer.parseInt(request.getParameter("managerId"));
                    int locationId = Integer.parseInt(request.getParameter("locationId"));

                    department = departmentDao.obtener(departmentId);

                    if (department == null) {
                        departmentDao.crear(departmentId, departmentName, managerId, locationId);
                    } else {
                        departmentDao.actualizar(departmentId, departmentName, managerId, locationId);
                    }
                    response.sendRedirect(request.getContextPath() + "/DepartmentServlet");
                    break;
                case "lista":
                    ArrayList<Department> lista = departmentDao.listaDepartamentos();

                    request.setAttribute("lista", lista);

                    view = request.getRequestDispatcher("department/listaDepartment.jsp");
                    view.forward(request, response);
                    break;

                case "editar":
                    departmentId = Integer.parseInt(request.getParameter("id"));
                    department = departmentDao.obtener(departmentId);
                    if (department == null) {
                        response.sendRedirect(request.getContextPath() + "/DepartmentServlet");
                    } else {
                        request.setAttribute("listaLocations",locationDao.listar());
                        request.setAttribute("listaEmpleados",employeeDao.listarEmpleados());
                        request.setAttribute("department", department);
                        view = request.getRequestDispatcher("department/updateDepartment.jsp");
                        view.forward(request, response);
                    }
                    break;
                case "borrar":
                    departmentId = Integer.parseInt(request.getParameter("id"));
                    if (departmentDao.obtener(departmentId) != null) {
                        departmentDao.borrar(departmentId);
                    }
                    response.sendRedirect(request.getContextPath() + "/DepartmentServlet");
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
