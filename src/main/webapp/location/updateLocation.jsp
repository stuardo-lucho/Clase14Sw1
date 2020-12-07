<%@ page import="Beans.Country" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listaPaises" scope="request" type="java.util.ArrayList<Beans.Country>"/>
<jsp:useBean id="location" scope="request" type="Beans.Location"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' href='resources/css/bootstrap.min.css'/>
        <title>Editar una ubicación</title>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="../includes/navbar.jsp">
                <jsp:param name="currentPage" value="loc"/>
            </jsp:include>
            <div class="row mb-4">
                <div class="col"></div>
                <div class="col-md-6">
                    <h1 class='mb-3'>Editar una ubicación</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/LocationServlet?action=crear">
                        <input type="hidden" class="form-control" name="id" value="<%=location.getLocationId()%>">
                        <div class="form-group">
                            <label>Street Address</label>
                            <input type="text" class="form-control" name="streetAddress"
                                   value="<%=location.getStreetAddress() %>">
                        </div>
                        <div class="form-group">
                            <label>Postal Code</label>
                            <input type="text" class="form-control" name="postalCode"
                                   value="<%=location.getPostalCode() %>">
                        </div>
                        <div class="form-group">
                            <label>City</label>
                            <input type="text" class="form-control" name="city" value="<%=location.getCity() %>">
                        </div>
                        <div class="form-group">
                            <label>State Province</label>
                            <input type="text" class="form-control" name="stateProvince"
                                   value="<%=location.getStateProvince() %>">
                        </div>
                        <div class="form-group">
                            <label for="countryId">Country</label>
                            <select name="countryId" id="countryId" class="form-control">
                                <% for (Country country : listaPaises) {%>
                                <option value="<%=country.getCountryId()%>" <%=country.getCountryId().equals(location.getCountry().getCountryId())?"SELECTED":""%>>
                                    <%=country.getCountryName()%>
                                </option>
                                <% }%>
                            </select>
                        </div>
                        <a href="<%= request.getContextPath()%>/LocationServlet" class="btn btn-danger">Cancelar</a>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>
                <div class="col"></div>
            </div>
        </div>
        <jsp:include page="../includes/footer.jsp"/>
    </body>
</html>
