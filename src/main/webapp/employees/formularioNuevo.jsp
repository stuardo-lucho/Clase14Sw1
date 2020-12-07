<%@page import="Beans.Job" %>
<%@page import="java.util.ArrayList" %>
<%@ page import="Beans.Employee" %>
<%@ page import="Beans.Department" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean scope="request" id="listaTrabajos" type="ArrayList<Job>"/>
<jsp:useBean id="listaDepartamentos" type="ArrayList<Department>" scope="request"/>
<jsp:useBean id="listaJefes" type="ArrayList<Employee>" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='resources/css/bootstrap.min.css'/>
        <title>Nuevo empleado</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="../includes/navbar.jsp">
                <jsp:param name="currentPage" value="emp"/>
            </jsp:include>
            <div class="row mb-4">
                <div class="col"></div>
                <div class="col-md-6">
                    <h1 class='mb-3'>Nuevo usuario</h1>
                    <hr>
                    <form method="POST" action="EmployeeServlet?action=guardar">
                        <div class="form-group">
                            <label for="first_name">First Name</label>
                            <input type="text" class="form-control form-control-sm" id="first_name" name="first_name">
                        </div>
                        <div class="form-group">
                            <label for="last_name">Last Name</label>
                            <input type="text" class="form-control form-control-sm" id="last_name" name="last_name">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="text" class="form-control form-control-sm" id="email" name="email">
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone number</label>
                            <input type="text" class="form-control form-control-sm" id="phone" name="phone">
                        </div>
                        <div class="form-group">
                            <label for="hire_date">Hire date</label>
                            <input type="text" class="form-control form-control-sm" id="hire_date" name="hire_date">
                        </div>
                        <div class="form-group">
                            <label for="job_id">Job ID</label>
                            <select name="job_id" id="job_id" class="form-control">
                                <% for (Job job : listaTrabajos) {%>
                                <option value="<%=job.getJobId()%>"><%=job.getJobTitle()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="salary">Salary</label>
                            <input type="text" class="form-control form-control-sm" id="salary" name="salary">
                        </div>
                        <div class="form-group">
                            <label for="commission">Commision PCT</label>
                            <input type="text" class="form-control form-control-sm" id="commission" name="commission">
                        </div>
                        <div class="form-group">
                            <label for="manager_id">Manager</label>
                            <select name="manager_id" id="manager_id" class="form-control">
                                <option value="sin-jefe">--Sin jefe--</option>
                                <% for (Employee employee : listaJefes) {%>
                                <option value="<%=employee.getEmployeeId()%>"><%=employee.getFirstName()%> <%=employee.getLastName()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="department_id">Department</label>
                            <select name="department_id" id="department_id" class="form-control">
                                <% for (Department department : listaDepartamentos) {%>
                                <option value="<%=department.getDepartmentId()%>"><%=department.getDepartmentName()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                        <a href="<%= request.getContextPath()%>/EmployeeServlet" class="btn btn-danger">Cancelar</a>
                        <input type="submit" value="Guardar" class="btn btn-primary"/>
                    </form>
                </div>
                <div class="col"></div>
            </div>
        </div>
        <jsp:include page="../includes/footer.jsp"/>
    </body>
</html>
