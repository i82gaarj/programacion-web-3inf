<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
<%@ page import="es.uco.pw.business.user.InterestDTO, java.util.ArrayList" %>
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
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
		<title>Suscribirse a temas de inter&eacute;s</title>
	</head>
	<body>
		<%@ include file="../../include/navbar.jsp" %>
		<div class="container-tablon-main">
			<h1>Suscribirse a temas de inter&eacute;s</h1>
			<form class="interests-form" action="<%= request.getContextPath() %>/editInterestsServlet" method="post">
			
			<% ArrayList<InterestDTO> interests = (ArrayList<InterestDTO>) request.getAttribute("interests"); %>
			<% ArrayList<InterestDTO> interestsOfUser = (ArrayList<InterestDTO>) request.getAttribute("interestsOfUser"); %>
			<% if (interestsOfUser == null || interestsOfUser.size() == 0) { %>
				<p>Actualmente no se encuentra suscrito a ning&uacute;n tema de inter&eacute;s</p>
			<% } else { %>
				<p>Actualmente se encuentra suscrito a los siguientes temas de inter&eacute;s</p>
				<ul>

				<% for (InterestDTO interest : interestsOfUser) { %>
					<li><%=interest.getName() %></li>
				<% } %>
				</ul>
			<% } %>
			<p>Seleccione uno o más temas de interés (reemplazan a los anteriores)</p>
			<% for (InterestDTO i : interests){ %>
				<label>
					<input type="checkbox" name="interest[]" value="<%= i.getID() %>"><%= i.getName() %>
				</label>
				<br/>
			<% } %>
			<input type="submit" class="small-button" value="Guardar">
		</form>
		</div>
		
	</body>
</html>