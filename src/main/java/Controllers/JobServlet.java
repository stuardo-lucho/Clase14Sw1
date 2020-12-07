package Controllers;

import Beans.Employee;
import Beans.Job;
import Daos.JobDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "JobServlet", urlPatterns = {"/JobServlet"})
public class JobServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Employee em = (Employee) request.getSession().getAttribute("employeeSession");

        if (em == null) {
            response.sendRedirect(request.getContextPath());
        } else {

            String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

            JobDao jobDao = new JobDao();
            RequestDispatcher view;
            Job job;
            String jobId;

            switch (action) {
                case "formCrear":
                    view = request.getRequestDispatcher("jobs/newJob.jsp");
                    view.forward(request, response);
                    break;
                case "crear":
                    jobId = request.getParameter("id");
                    String jobTitle = request.getParameter("jobTitle");
                    int minSalary = Integer.parseInt(request.getParameter("minSalary"));
                    int maxSalary = Integer.parseInt(request.getParameter("maxSalary"));

                    job = jobDao.obtenerTrabajo(jobId);

                    if (job == null) {
                        jobDao.crearTrabajo(jobId, jobTitle, minSalary, maxSalary);
                    } else {
                        jobDao.actualizarTrabajo(jobId, jobTitle, minSalary, maxSalary);
                    }
                    response.sendRedirect(request.getContextPath() + "/JobServlet");
                    break;
                case "lista":
                    ArrayList<Job> listaTrabajos = jobDao.listarTrabajos();

                    request.setAttribute("lista", listaTrabajos);

                    view = request.getRequestDispatcher("jobs/listaJobs.jsp");
                    view.forward(request, response);
                    break;

                case "editar":
                    jobId = request.getParameter("id");
                    job = jobDao.obtenerTrabajo(jobId);
                    if (job == null) {
                        response.sendRedirect(request.getContextPath() + "/JobServlet");
                    } else {
                        request.setAttribute("job", job);
                        view = request.getRequestDispatcher("jobs/updateJob.jsp");
                        view.forward(request, response);
                    }
                    break;
                case "borrar":
                    jobId = request.getParameter("id");
                    if (jobDao.obtenerTrabajo(jobId) != null) {
                        jobDao.borrarTrabajo(jobId);
                    }
                    request.getSession().setAttribute("info", "Trabajo borrado existosamente");
                    response.sendRedirect(request.getContextPath() + "/JobServlet");
                    break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


}
