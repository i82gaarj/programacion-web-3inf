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
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <title>Registro</title>
</head>
<body>
	<div class="cuadro">
		<div class="container-center">
		    <div class="header">
				<h1 class="page-title">Registro</h1>
				<p class="subtitle">Rellene el formulario</p>
		    </div>
		    <div class="form-register">
		        <form action="<%= request.getContextPath() %>/registerServlet" method="POST">
		            <label for="email">Email:</label>
		            <input type="email" class="input-form" id="email" name="email">
		            <br/>
		            
		            <label for="password">Contrase&ntilde;a:</label>
		            <input type="password" class="input-form" id="password" name="password">
		            <br/>
		
		            <label for="firstname">Nombre:</label>
		            <input type="text" class="input-form" id="firstname" name="firstname">
		            <br/>
		
		            <label for="lastname">Apellidos:</label>
		            <input type="text" class="input-form" id="lastname" name="lastname">
		            <br/>
		
		            <label for="birthdate">Fecha de nacimiento:</label>
		            <input type="date" class="input-form" id="birthdate" name="birthdate">
		            <br/>
		
		            <input type="submit" class="small-button" value="Registrarse">
		        </form>
		    </div>
		</div>
	</div>
</body>
</html>