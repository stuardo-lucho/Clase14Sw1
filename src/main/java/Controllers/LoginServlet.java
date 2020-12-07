package Controllers;

import Beans.Employee;
import Daos.EmployeeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action") == null ? "loginform" : request.getParameter("action");

        switch (action) {
            case "loginform":
                if (request.getSession().getAttribute("employeeSession") != null) {
                    response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
                } else {
                    RequestDispatcher view = request.getRequestDispatcher("login/loginForm.jsp");
                    view.forward(request, response);
                }
                break;
            case "logout":
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect(request.getContextPath());
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EmployeeDao employeeDao = new EmployeeDao();

        String username = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");

        Employee employee = employeeDao.validarUsuarioPasswordHashed(username, password);

        if (employee != null) {
            HttpSession session = request.getSession();
            session.setAttribute("employeeSession", employee);

            session.setMaxInactiveInterval(10 * 60); // 10 minutos

            response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
        } else {
            response.sendRedirect(request.getContextPath() + "/LoginServlet?error");
        }

    }
}
