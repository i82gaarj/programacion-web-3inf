<jsp:useBean id="CustomerBean" scope="session" class="es.uco.pw.p2.display.CustomerBean"></jsp:useBean>
<%
	if (CustomerBean.getEmail() == null){
		response.sendRedirect(request.getContextPath());
	}
%>
<!DOCTYPE html>
<html lang="es">
    <head>
		<meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Modificar usuario</title>
    </head>
    <body>
	    <form action="../controller/modifyController.jsp" method="POST">
            <label for="firstname">Nuevo nombre:</label>
            <input type="text" id="name" name="firstname">
            <br/>

            <label for="lastname">Nuevos apellidos:</label>
            <input type="text" id="lastname" name="lastname">
            <br/>

            <label for="birthdate">Nueva fecha de nacimiento:</label>
            <input type="date" id="birthdate" name="birthdate">
            <br/>
            
            <label for="password">Nueva contrase&ntilde;a:</label>
            <input type="password" id="password" name="password">
            <br/>

            <input type="submit" value="Modificar">
		</form>
    </body>
</html>