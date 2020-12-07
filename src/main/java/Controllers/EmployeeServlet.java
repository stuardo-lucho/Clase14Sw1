package Controllers;

import Beans.Department;
import Beans.Employee;
import Beans.Job;
import Daos.DepartmentDao;
import Daos.EmployeeDao;
import Daos.JobDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "EmployeeServlet", urlPatterns = {"/EmployeeServlet"})
public class EmployeeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        if (request.getSession().getAttribute("employeeSession") == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

            RequestDispatcher view;
            EmployeeDao employeeDao = new EmployeeDao();
            JobDao jobDao = new JobDao();
            DepartmentDao departmentDao = new DepartmentDao();

            switch (action) {
                case "lista":
                    request.setAttribute("listaEmpleados", employeeDao.listarEmpleados());
                    view = request.getRequestDispatcher("employees/lista.jsp");
                    view.forward(request, response);
                    break;
                case "agregar":
                    request.setAttribute("listaTrabajos", jobDao.listarTrabajos());
                    request.setAttribute("listaDepartamentos", departmentDao.listaDepartamentos());
                    request.setAttribute("listaJefes", employeeDao.listarEmpleados());

                    view = request.getRequestDispatcher("employees/formularioNuevo.jsp");
                    view.forward(request, response);
                    break;
                case "editar":
                    if (request.getParameter("id") != null) {
                        String employeeIdString = request.getParameter("id");
                        int employeeId = 0;
                        try {
                            employeeId = Integer.parseInt(employeeIdString);
                        } catch (NumberFormatException ex) {
                            response.sendRedirect("EmployeeServlet");
                        }

                        Employee emp = employeeDao.obtenerEmpleado(employeeId);

                        if (emp != null) {
                            request.setAttribute("empleado", emp);
                            request.setAttribute("listaTrabajos", jobDao.listarTrabajos());
                            request.setAttribute("listaDepartamentos", departmentDao.listaDepartamentos());
                            request.setAttribute("listaJefes", employeeDao.listarEmpleados());
                            view = request.getRequestDispatcher("employees/formularioEditar.jsp");
                            view.forward(request, response);
                        } else {
                            response.sendRedirect("EmployeeServlet");
                        }

                    } else {
                        response.sendRedirect("EmployeeServlet");
                    }

                    break;
                case "borrar":
                    if (request.getParameter("id") != null) {
                        String employeeIdString = request.getParameter("id");
                        int employeeId = 0;
                        try {
                            employeeId = Integer.parseInt(employeeIdString);
                        } catch (NumberFormatException ex) {
                            response.sendRedirect("EmployeeServlet");
                        }

                        Employee emp = employeeDao.obtenerEmpleado(employeeId);

                        if (emp != null) {
                            employeeDao.borrarEmpleado(employeeId);
                        }
                    }
                    response.sendRedirect("EmployeeServlet");

                    break;
                case "est":
                    request.setAttribute("listaSalarioPorDepa", departmentDao.listaSalarioPorDepartamento());
                    request.setAttribute("listaEmpleadPorRegion", employeeDao.listaEmpleadosPorRegion());
                    view = request.getRequestDispatcher("employees/estadisticas.jsp");
                    view.forward(request, response);
                    break;
                default:
                    response.sendRedirect("EmployeeServlet");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        switch (action) {
            case "guardar":
                Employee e = new Employee();
                e.setFirstName(request.getParameter("first_name"));
                e.setLastName(request.getParameter("last_name"));
                e.setEmail(request.getParameter("email"));
                e.setPhoneNumber(request.getParameter("phone"));
                e.setHireDate(request.getParameter("hire_date"));
                e.setSalary(new BigDecimal(request.getParameter("salary")));
                e.setCommissionPct(request.getParameter("commission").equals("") ? null : new BigDecimal(request.getParameter("commission")));

                String jobId = request.getParameter("job_id");
                Job job = new Job(jobId);
                e.setJob(job);

                String managerId = request.getParameter("manager_id");
                if (!managerId.equals("sin-jefe")) {
                    Employee manager = new Employee(Integer.parseInt(managerId));
                    e.setManager(manager);
                }

                String departmentId = request.getParameter("department_id");
                Department department = new Department(Integer.parseInt(departmentId));
                e.setDepartment(department);

                EmployeeDao employeeDao = new EmployeeDao();

                if (request.getParameter("employee_id") == null) {
                    employeeDao.guardarEmpleado(e);
                } else {
                    e.setEmployeeId(Integer.parseInt(request.getParameter("employee_id")));
                    employeeDao.actualizarEmpleado(e);
                }
                break;
        }
        response.sendRedirect("EmployeeServlet");
    }

}
