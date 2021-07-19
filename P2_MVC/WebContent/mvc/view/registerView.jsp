<!DOCTYPE html>
<html lang="es">
    <head>
		<meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro</title>
    </head>
    <body>
	    <form action="../controller/registerController.jsp" method="POST">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email"><br/>
            
            <label for="password">Contrase&ntilde;a:</label>
            <input type="password" id="password" name="password">
            <br/>

            <label for="firstname">Nombre:</label>
            <input type="text" id="firstname" name="firstname">
            <br/>

            <label for="lastname">Apellidos:</label>
            <input type="text" id="lastname" name="lastname">
            <br/>

            <label for="birthdate">Fecha de nacimiento:</label>
            <input type="date" id="birthdate" name="birthdate">
            <br/>

            <input type="submit" value="Registrarse">
		</form> 
    </body>
</html>