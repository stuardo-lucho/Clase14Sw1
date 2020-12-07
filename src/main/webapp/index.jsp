<%@ page import="Beans.Employee" %>
<%
    Employee emp = (Employee) session.getAttribute("employeeSession");
    if (emp != null) {
        response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="resources/css/bootstrap.min.css" rel="stylesheet">
        <title>Gestión HR</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="includes/navbar.jsp">
                <jsp:param name="currentPage" value="emp"/>
            </jsp:include>
            <div class="row mt-4">
                <div class="col"><img width="100%" src="resources/images/hr.jpg"></div>
            </div>
            <jsp:include page="includes/footer.jsp"/>
        </div>
    </body>
</html>
