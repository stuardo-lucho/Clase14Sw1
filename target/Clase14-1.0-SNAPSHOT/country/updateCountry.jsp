<%@page import="Beans.Country"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="country" scope="request" type="Beans.Country" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='resources/css/bootstrap.min.css'/>
        <title>Editar un Pais</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="../includes/navbar.jsp">
                <jsp:param name="currentPage" value="cou" />
            </jsp:include>
            <div class="row mb-4">
                <div class="col"></div>
                <div class="col-md-6">
                    <h1 class='mb-3'>Editar un Pais</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/CountryServlet?action=crear">
                        <input type="hidden" class="form-control" name="id" value="<%=country.getCountryId()%>">
                        <div class="form-group">
                            <label>Country name</label>
                            <input type="text" class="form-control" name="countryName" value="<%=country.getCountryName()%>">
                        </div>
                        <div class="form-group">
                            <label>Region Id</label>
                            <input type="text" class="form-control" name="regionId" value="<%=country.getRegionId() %>">
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
