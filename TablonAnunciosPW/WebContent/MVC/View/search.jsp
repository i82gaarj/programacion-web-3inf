<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>B&uacute;squeda</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	</head>
	<body>
		<%@ include file="../../include/navbar.jsp" %>
		<div class="container-tablon-main">
			<h1>Buscar anuncio</h1>
			<p>Buscar por t&iacute;tulo</p>
			<% String msg = (String)request.getAttribute("errorMsg"); // Mensaje de respuesta %>
	    	<% if (msg != null) { %>
	    		<p class="message-error-left"><%= msg %></p>
	    	<% } %>
			<form action="<%= request.getContextPath() %>/searchServlet" method="get">
				<input type="hidden" name="type" value="title">
				<input type="text" class="input-form" name="titleQuery">
				<input type="submit" class="small-button" value="Buscar">
			</form>
			<p>Buscar por rango de fechas</p>
			<form action="<%= request.getContextPath() %>/searchServlet" method="get">
				<input type="hidden" name="type" value="dateRange">
				<label for="startDate">Fecha inicio</label>
				<input type="date" class="input-form" name="startDate">
				<br/>
				<label for="endDate">Fecha fin</label>
				<input type="date" class="input-form" name="endDate">
				<input type="submit" class="small-button" value="Buscar">
			</form>
		</div>
	</body>
</html>