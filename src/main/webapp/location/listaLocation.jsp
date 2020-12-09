<%@page import="Beans.Location" %>
<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean type="ArrayList<Location>" scope="request" id="lista"/>
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
                <jsp:param name="currentPage" value="loc"/>
            </jsp:include>
            <div class="row mb-5 mt-4">
                <div class="col-lg-6">
                    <h1 class=''>Lista de Ubicaciones</h1>
                </div>
                <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("AD_VP")) { %>
                <div class="col-lg-6 my-auto text-lg-right">
                    <a href="<%= request.getContextPath()%>/LocationServlet?action=formCrear" class="btn btn-primary">Crear
                        Ubicación</a>
                </div>
                <% } %>
            </div>
            <table class="table">
                <tr>
                    <th>#</th>
                    <th>Location ID</th>
                    <th>Street Address</th>
                    <th>Postal Code</th>
                    <th>City</th>
                    <th>State Province</th>
                    <th>Country</th>
                    <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("AD_VP")) { %>
                    <th></th>
                    <th></th>
                    <% } %>
                </tr>
                <%
                    int i = 1;
                    for (Location location : lista) {
                %>
                <tr>
                    <td><%=i%>
                    </td>
                    <td><%=location.getLocationId()%>
                    </td>
                    <td><%=location.getStreetAddress() %>
                    </td>
                    <td><%=location.getPostalCode() == null ? "--" : location.getPostalCode() %>
                    </td>
                    <td><%=location.getCity() %>
                    </td>
                    <td><%=location.getStateProvince() == null ? "--" : location.getStateProvince() %>
                    </td>
                    <td><%=location.getCountry().getCountryName() %>
                    </td>
                    <% if (employeeSession.getJob().getJobId().equals("AD_PRES") || employeeSession.getJob().getJobId().equals("AD_VP")) { %>
                    <td>
                        <a href="<%=request.getContextPath()%>/LocationServlet?action=editar&id=<%=location.getLocationId()%>">
                            Editar
                        </a>
                    </td>
                    <td>
                        <a href="<%=request.getContextPath()%>/LocationServlet?action=borrar&id=<%=location.getLocationId()%>">
                            Borrar
                        </a>
                    </td>
                    <% } %>
                </tr>
                <%
                        i++;
                    }
                %>
            </table>
        </div>
        <jsp:include page="../includes/footer.jsp"/>
    </body>
</html>