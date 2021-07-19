<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	if (userBean.getEmail() != null){
		response.sendRedirect(request.getContextPath() + "/MVC/View/board.jsp");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <title>Iniciar sesión</title>
</head>
<body>
	<div class="cuadro">
		<div class="container-center">
			<div class="header">
				<h1 class="page-title">Iniciar sesi&oacute;n</h1>
				<p class="subtitle">Introduzca su email y contrase&ntilde;a</p>
		    </div>
		    <% String msg = (String)request.getAttribute("msg"); // Mensaje de respuesta %>
	    	<% if (msg != null) { %>
	    		<p class="message-success"><%= msg %></p>
	    	<% } %>
	        <form class="form-login" action="<%= request.getContextPath() %>/loginServlet" method="post">
	            <label for="email">Email:</label>
	            <input type="text" class="input-form" name="email">
	            <br/>
	            <label for="password">Contraseña:</label>
	            <input type="password" class="input-form" name="password">
	            <br/>
	            <input type="submit" class="small-button" id="button-submit-login" value="Iniciar sesión">
	        </form>
		</div>
	</div>
</body>
</html>