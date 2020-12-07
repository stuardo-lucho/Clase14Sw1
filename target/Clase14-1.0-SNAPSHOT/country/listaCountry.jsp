<%@page import="Beans.Country"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean type="ArrayList<Country>" scope="request" id="lista"  />
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
                <jsp:param name="currentPage" value="cou" />
            </jsp:include>
            <div class="row mb-5 mt-4">
                <div class="col-lg-6">
                    <h1 class=''>Lista de Paises</h1>
                </div>
                <div class="col-lg-6 my-auto text-lg-right">
                    <a href="<%= request.getContextPath()%>/CountryServlet?action=formCrear" class="btn btn-primary">Crear País</a>
                </div>
            </div>
            <table class="table">
                <tr>
                    <th>#</th> 
                    <th>Country ID</th>
                    <th>Country name</th>
                    <th>Region ID</th>
                    <th></th>
                    <th></th>
                </tr>
                <%
                    int i = 1;
                    for (Country country : lista) {
                %>
                <tr>
                    <td><%=i%></td>
                    <td><%=country.getCountryId()%></td>
                    <td><%=country.getCountryName() %></td>
                    <td><%=country.getRegionId()%></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/CountryServlet?action=editar&id=<%=country.getCountryId()%>">
                            Editar
                        </a>
                    </td>
                    <td>
                        <a href="<%=request.getContextPath()%>/CountryServlet?action=borrar&id=<%=country.getCountryId()%>">
                            Borrar
                        </a>
                    </td>
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