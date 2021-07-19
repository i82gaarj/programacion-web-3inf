<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
<%
	if (userBean.getEmail() != null){
		response.sendRedirect(request.getContextPath() + "/boardServlet");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/style.css"></head>
	<title>PR&Aacute;CTICA 3</title>
</head>
<body>
		<div class="cuadro">
			<div class="container-center">
				<div class="header">
					<h1 class="page-title">Tabl&oacute;n de anuncios</h1>
					<p id="bienvenida">Bienvenido al tabl&oacute;n de anuncios</p>
				</div>
				<div class="main">
					<button type="button" class="small-button" onclick="window.location.href='<%= request.getContextPath() %>/MVC/View/login.jsp'">Iniciar sesi&oacute;n</button>
					<button type="button" class="small-button" onclick="window.location.href='<%= request.getContextPath() %>/MVC/View/register.jsp'">Registro</button>
				</div>
			</div>
		</div>
</body>
</html>