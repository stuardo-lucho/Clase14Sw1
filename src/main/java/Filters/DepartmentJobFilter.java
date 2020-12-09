package Filters;

import Beans.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "DepartmentJobFilter", servletNames = {"DepartmentServlet", "JobServlet"})
public class DepartmentJobFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        Employee em = (Employee) session.getAttribute("employeeSession");

        String jobId = em.getJob().getJobId();

        if (jobId.equals("AD_PRES") || jobId.equals("IT_PROG")) {
            chain.doFilter(req, resp);
        } else {
            String action = request.getParameter("action");
            if ((!jobId.equals("AD_VP") && !jobId.equals("ST_CLERK")) && action == null) {
                chain.doFilter(req, resp);
            }else{
                response.sendRedirect(request.getContextPath());
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
