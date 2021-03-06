<%@page import="Beans.Department"%>
<%@ page import="Beans.Employee" %>
<%@ page import="Beans.Location" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listaLocations" scope="request" type="java.util.ArrayList<Beans.Location>"/>
<jsp:useBean id="listaEmpleados" scope="request" type="java.util.ArrayList<Beans.Employee>"/>
<jsp:useBean id="department" scope="request" type="Department" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='resources/css/bootstrap.min.css'/>
        <title>Editar un Departamento</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="../includes/navbar.jsp">
                <jsp:param name="currentPage" value="dep" />
            </jsp:include>
            <div class="row mb-4">
                <div class="col"></div>
                <div class="col-md-6">
                    <h1 class='mb-3'>Editar un departamento</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/DepartmentServlet?action=crear">
                        <input type="hidden" class="form-control" name="id" value="<%=department.getDepartmentId()%>">
                        <div class="form-group">
                            <label>Department name</label>
                            <input type="text" class="form-control" name="departmentName" value="<%=department.getDepartmentName()%>">
                        </div>
                        <div class="form-group">
                            <label for="managerId">Manager</label>
                            <select name="managerId" id="managerId" class="form-control">
                                <option value="0" <%= department.getManager() == null?"Selected":"" %>>-- Sin Jefe --</option>
                                <% for (Employee e : listaEmpleados) {%>
                                <option value="<%=e.getEmployeeId()%>" <%= (department.getManager() != null && e.getEmployeeId() == department.getManager().getEmployeeId())?"Selected":"" %>>
                                    <%=e.getFirstName() + " " + e.getLastName()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="locationId">Location</label>
                            <select name="locationId" id="locationId" class="form-control">
                                <option value="0" <%= department.getLocation() == null?"Selected":"" %>>-- Sin Ubicación --</option>
                                <% for (Location l : listaLocations) {%>
                                <option value="<%=l.getLocationId()%>" <%= (department.getLocation() != null && l.getLocationId() == department.getLocation().getLocationId())?"Selected":"" %>>
                                    <%=l.getCity()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                        <a href="<%= request.getContextPath()%>/DepartmentServlet" class="btn btn-danger">Cancelar</a>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>
                <div class="col"></div>
            </div>
        </div>
        <jsp:include page="../includes/footer.jsp"/>
    </body>
</html>
