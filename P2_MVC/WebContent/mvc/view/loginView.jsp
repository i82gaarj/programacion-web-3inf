<jsp:useBean id="CustomerBean" scope="session" class="es.uco.pw.p2.display.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html lang="es">
    <head>
		<meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
    </head>
    <body>
    	<% if (CustomerBean.getLoginAttempts() >= 3) {
    		response.sendRedirect("http://www.uco.es/");
    	}%>
    	<% if (CustomerBean.getEmail() != null) { %>
    		<p>Bienvenido <jsp:getProperty property="firstname" name="CustomerBean"/> <jsp:getProperty property="lastname" name="CustomerBean"/>
    		(<jsp:getProperty property="email" name="CustomerBean"/>)
    		</p>
    		<br/>
    		<button type="button" onclick="window.location.href='../controller/logoutController.jsp';">Cerrar sesi&oacute;n</button>
    		<button type="button" onclick="window.location.href='../view/modifyView.jsp';">Modificar</button>
    	<% } else { %>
			<form action="../controller/loginController.jsp" method="post">
	            <label for="email">Email:</label>
	            <input type="text" name="email">
	            <br/>
	            <label for="password">Contraseña:</label>
	            <input type="password" name="password">
	            <br/>
	            <input type="submit" value="Login">
	        </form>
	     <% } %>
	     <br/>
	     <% if (request.getParameter("msg") != null) { %>
	  		<p><%= request.getParameter("msg") %>. Le quedan <%= 3 - CustomerBean.getLoginAttempts() %> intentos. </p>
	     <% } %>
	    
    </body>
</html>