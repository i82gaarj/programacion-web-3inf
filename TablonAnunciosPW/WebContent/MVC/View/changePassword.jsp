<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	if (userBean.getEmail() == null){
		response.sendRedirect(request.getContextPath());
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Cambiar contrase&ntilde;a</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	</head>
	<body>
		<%@ include file="../../include/navbar.jsp" %>
		<h1 class="page-title">Cambiar contrase&ntilde;a</h1>
		<div class="text-center">
			<% String msg = (String)request.getAttribute("msg"); // Mensaje de respuesta %>
	    	<% if (msg != null) { %>
	    		<p class="message-success"><%= msg %></p>
	    	<% } %>
			<form action="<%= request.getContextPath() %>/changePasswordServlet" method="POST">
				<label for="oldPassword">Contrase&ntilde;a actual</label>
				<input type="password" class="input-form" name="oldPassword">
				<br/>
				<label for="newPassword">Nueva contrase&ntilde;a</label>
				<input type="password" class="input-form" name="newPassword">
				<br/>
				<input type="submit" class="small-button" id="change-password-btn" value="Cambiar contrase&ntilde;a">
			</form>
		</div>
	</body>
</html>