<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	if (userBean.getEmail() == null){
		response.sendRedirect(request.getContextPath());
	}
%>
<!DOCTYPE html>
<html lang="es">
    <head>
		<meta charset="ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
        <title>Modificar usuario</title>
    </head>
    <body>
    	<%@ include file="../../include/navbar.jsp" %>
		<div class="header">
			<h1 class="page-title">Modificar perfil de <%= userBean.getEmail() %></h1>
	    </div>
	    <div class="text-center">
	    	<% String msg = (String)request.getAttribute("msg"); %>
	    	<% if (msg != null) { %>
	    		<p class="message-success"><%= msg %></p>
	    	<% } %>
		    <form class="modify-form" action="<%= request.getContextPath() %>/editProfileServlet" method="POST">
	            <label for="firstname">Nombre:</label>
	            <input type="text" class="input-form" id="name" name="firstname" value="<%= userBean.getFirstname() %>">
	            <br/>
	
	            <label for="lastname">Apellidos:</label>
	            <input type="text" class="input-form" id="lastname" name="lastname" value="<%= userBean.getLastname() %>">
	            <br/>
	
	            <label for="birthdate">Fecha de nacimiento:</label>
	            <input type="date" class="input-form" id="birthdate" name="birthdate" value="<%= userBean.getBirthdate() %>">
	            <br/>
	
	            <input type="submit" class="small-button" value="Modificar">
			</form>
			<br/>
			<button type="button" class="small-button" style="width: 200px;" onclick="window.location.href='<%= request.getContextPath() %>/MVC/View/changePassword.jsp'">Cambiar contrase&ntilde;a</button>
			<br/>
			<button type="button" class="small-button" style="width: 200px;" onclick="window.location.href='<%= request.getContextPath() %>/editInterestsServlet'">A&ntilde;adir intereses</button>
		</div>
    </body>
</html>