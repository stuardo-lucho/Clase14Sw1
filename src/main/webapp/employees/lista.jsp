<%@page import="Beans.Employee" %>
<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listaEmpleados" type="ArrayList<Employee>" scope="request"/>
<jsp:useBean type="Beans.Employee" scope="session" id="employeeSession"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel='stylesheet' href='resources/css/bootstrap.min.css'/>
        <title>Lista empleados</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="../includes/navbar.jsp">
                <jsp:param name="currentPage" value="emp"/>
            </jsp:include>
            <div class="row mb-5 mt-4">
                <div class="col-lg-6">
                    <h1 class=''>Lista de empleados</h1>
                </div>
                <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("ST_CLERK")) { %>
                <div class="col-lg-6 my-auto text-lg-right">
                    <a href="<%= request.getContextPath()%>/EmployeeServlet?action=agregar" class="btn btn-primary">Agregar
                        nuevo empleado</a>
                </div>
                <% } %>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Employee</th>
                        <th>Email</th>
                        <th>Job</th>
                        <th>Salary</th>
                        <th>Commision</th>
                        <th>Manager</th>
                        <th>Department</th>
                        <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("ST_CLERK")) { %>
                        <th></th>
                        <th></th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int i = 1;
                        for (Employee e : listaEmpleados) {
                    %>
                    <tr>
                        <td><%= i%>
                        </td>
                        <td><%= e.getFirstName() + " " + e.getLastName()%>
                        </td>
                        <td><%= e.getEmail()%>
                        </td>
                        <td><%= e.getJob().getJobTitle()%>
                        </td>
                        <td><%= e.getSalary()%>
                        </td>
                        <td><%= e.getCommissionPct() == null ? "--" : e.getCommissionPct()%>
                        </td>
                        <td><%= e.getManager() == null ? "--" : (e.getManager().getFirstName() + " " + e.getManager().getLastName())%>
                        </td>
                        <td><%= e.getDepartment() == null ? "--" : e.getDepartment().getDepartmentName()%>
                        </td>
                        <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("ST_CLERK")) { %>
                        <td><a href="EmployeeServlet?action=editar&id=<%= e.getEmployeeId()%>">Editar</a></td>
                        <td><a href="EmployeeServlet?action=borrar&id=<%= e.getEmployeeId()%>">Borrar</a></td>
                        <% } %>
                    </tr>
                    <%
                            i++;
                        }
                    %>
                </tbody>


            </table>
            <jsp:include page="../includes/footer.jsp"/>
        </div>
    </body>
</html>
