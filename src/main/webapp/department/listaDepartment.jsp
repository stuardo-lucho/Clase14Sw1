<%@page import="Beans.Department" %>
<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean type="ArrayList<Department>" scope="request" id="lista"/>
<jsp:useBean type="Beans.Employee" scope="session" id="employeeSession"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='resources/css/bootstrap.min.css'/>
        <title>JSP Page</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="../includes/navbar.jsp">
                <jsp:param name="currentPage" value="dep"/>
            </jsp:include>
            <div class="row mb-5 mt-4">
                <div class="col-lg-6">
                    <h1 class=''>Lista de Departamentos</h1>
                </div>
                <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("IT_PROG")) { %>
                <div class="col-lg-6 my-auto text-lg-right">
                    <a href="<%= request.getContextPath()%>/DepartmentServlet?action=formCrear" class="btn btn-primary">Crear
                        Departamento</a>
                </div>
                <% } %>
            </div>
            <table class="table">
                <tr>
                    <th>Department ID</th>
                    <th>Department name</th>
                    <th>Manager</th>
                    <th>Location</th>
                    <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("IT_PROG")) { %>
                    <th></th>
                    <th></th>
                    <% } %>
                </tr>
                <%
                    for (Department department : lista) {
                %>
                <tr>
                    <td><%=department.getDepartmentId()%>
                    </td>
                    <td><%=department.getDepartmentName()%>
                    </td>
                    <td><%=department.getManager() == null ? "--" : (department.getManager().getFirstName() + " " + department.getManager().getLastName()) %>
                    </td>
                    <td><%=department.getLocation() == null ? "--" : department.getLocation().getCity()%>
                    </td>
                    <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("IT_PROG")) { %>
                    <td>
                        <a href="<%=request.getContextPath()%>/DepartmentServlet?action=editar&id=<%=department.getDepartmentId()%>">
                            Editar
                        </a>
                    </td>
                    <td>
                        <a href="<%=request.getContextPath()%>/DepartmentServlet?action=borrar&id=<%=department.getDepartmentId()%>">
                            Borrar
                        </a>
                    </td>
                    <% } %>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <jsp:include page="../includes/footer.jsp"/>
    </body>
</html>