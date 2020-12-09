package Filters;

import Beans.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "EmployeeFilter", servletNames = {"EmployeeServlet"})
public class EmployeeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        Employee em = (Employee) session.getAttribute("employeeSession");

        String jobId = em.getJob().getJobId();
        String action = request.getParameter("action");

        switch(jobId){
            case "AD_PRES":
                chain.doFilter(req, resp);
                break;
            case "ST_CLERK":
                if (action == null || !action.equals("est")) {
                    chain.doFilter(req, resp);
                }else{
                    response.sendRedirect(request.getContextPath());
                }
                break;
            case "IT_PROG":
                if (action == null || action.equals("lista")) {
                    chain.doFilter(req, resp);
                }else{
                    response.sendRedirect(request.getContextPath());
                }
                break;
            default:
                if (action == null || action.equals("lista") || action.equals("est")) {
                    chain.doFilter(req, resp);
                }else{
                    response.sendRedirect(request.getContextPath());
                }
                break;
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
