package Filters;

import Beans.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "JobFilter", servletNames = {"JobServlet"})
public class JobFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        Employee em = (Employee) session.getAttribute("employeeSession");

        System.out.println("--- JobFilter : servletName ---");

        if (em.getJob().getJobId().equals("AD_PRES")) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect(request.getContextPath());
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
