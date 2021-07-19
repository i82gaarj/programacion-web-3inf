<jsp:useBean id="customerBean" scope="session" class="es.uco.pw.p2.display.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html lang="es">
    <head>
		<meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>P2 MVC Login</title>
    </head>
    <body class="style_body">
		<% if (customerBean.getEmail() != null) { %>
			<button type="button" onclick="window.location.href='mvc/controller/logoutController.jsp';">Salir</button>	
			<button type="button" onclick="window.location.href='mvc/view/modifyView.jsp';">Configuraci&oacute;n</button>
	 	<% } else { %>
			<button type="button" onclick="window.location.href='mvc/view/loginView.jsp';">Iniciar sesi&oacute;n</button>
			<button type="button" onclick="window.location.href='mvc/view/registerView.jsp';">Registrarse</button>
	  	<% } %>        
    </body>
</html>