<%-- 
    Document   : newJob
    Created on : 13/08/2019, 12:38:09 PM
    Author     : DTI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='resources/css/bootstrap.min.css'/>
        <title>Crear un nuevo trabajo</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="../includes/navbar.jsp">
                <jsp:param name="currentPage" value="job"/>
            </jsp:include>
            <div class="row mb-4">
                <div class="col"></div>
                <div class="col-md-6">
                    <h1 class='mb-3'>Crear un nuevo trabajo</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/JobServlet?action=crear">
                        <div class="form-group">
                            <label for="jobId">Job ID</label>
                            <input type="text" class="form-control" name="id">
                        </div>
                        <div class="form-group">
                            <label for="jobTitle">Job Title</label>
                            <input type="text" class="form-control" name="jobTitle">
                        </div>
                        <div class="form-group">
                            <label for="minSalary">Min Salary</label>
                            <input type="text" class="form-control" name="minSalary">
                        </div>
                        <div class="form-group">
                            <label for="maxSalary">Max Salary</label>
                            <input type="text" class="form-control" name="maxSalary">
                        </div>
                        <a href="<%= request.getContextPath()%>/JobServlet" class="btn btn-danger">Cancelar</a>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>
                <div class="col"></div>
            </div>
        </div>
        <jsp:include page="../includes/footer.jsp"/>
    </body>
</html>
