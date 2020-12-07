<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel='stylesheet' href='resources/css/bootstrap.min.css'/>
        <title>Crear un Pais</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="../includes/navbar.jsp">
                <jsp:param name="currentPage" value="cou" />
            </jsp:include>
            <div class="row mb-4">
                <div class="col"></div>
                <div class="col-md-6">
                    <h1 class='mb-3'>Crear un Pais</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/CountryServlet?action=crear">
                        <div class="form-group">
                            <label>Country ID</label>
                            <input type="text" class="form-control" name="id">
                        </div>
                        <div class="form-group">
                            <label>Country nameñ</label>
                            <input type="text" class="form-control" name="countryName">
                        </div>
                        <div class="form-group">
                            <label>Region Id</label>
                            <input type="text" class="form-control" name="regionId">
                        </div>
                        <a href="<%= request.getContextPath()%>/CountryServlet" class="btn btn-danger">Cancelar</a>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>
                <div class="col"></div>
            </div>
        </div>
        <jsp:include page="../includes/footer.jsp"/>
    </body>
</html>
