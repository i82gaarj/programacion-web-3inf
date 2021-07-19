<%@ page import="es.uco.pw.business.ad.Ad, java.util.ArrayList, es.uco.pw.business.user.InterestDTO" %>
<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="es.uco.pw.business.ad.AdType, es.uco.pw.business.ad.FlashAdDTO, es.uco.pw.business.ad.IndividualAdDTO, es.uco.pw.business.ad.ThematicAdDTO, es.uco.pw.business.user.UserDTO" %>
<%
	if (userBean.getEmail() == null){
		response.sendRedirect(request.getContextPath());
	}
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	    <meta charset="ISO-8859-1">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	    <title>Editar anuncio</title>
	</head>
	<body>
		<%@ include file="../../include/navbar.jsp" %>
		<% Ad a = (Ad) request.getAttribute("ad"); %>
		<div class="container-tablon-main">
			<h1>Editar anuncio</h1>
			<form action="<%= request.getContextPath() %>/editAdServlet" method="post">
				<label for="title">Título:</label>
				<input type="text" name="title" class="input-form" value="<%= a.getTitle() %>">
				<br/>
				<% if (a instanceof FlashAdDTO) { %>
					<% FlashAdDTO flashAd = (FlashAdDTO) a; %>
					<!-- ANUNCIO FLASH -->
					<div id="flash-data">
						<label for="startDate">Fecha de inicio:</label>
						<input type="date" name="startDate" value="<%= flashAd.getStartDate() %>">
						<br/>
						
						<label for="endDate">Fecha de fin:</label>
						<input type="date" name="endDate" value="<%= flashAd.getEndDate() %>">
					</div>
				<% } %>
				
				<% if (a instanceof ThematicAdDTO) { %>
					<!-- ANUNCIO TEMATICO -->
					<div id="thematic-data">
						<p>Temas de inter&eacute;s del anuncio</p>
						<% ThematicAdDTO thematicAd = (ThematicAdDTO) a; %>
						<% ArrayList<InterestDTO> interests = (ArrayList<InterestDTO>) request.getAttribute("interests"); %>
						<% for (InterestDTO i : interests){ %>
							<label>
								<input type="checkbox" name="interest[]" value="<%=i.getID()%>"><%= i.getName() %>
							</label>
							<br/>
						<% } %>
					</div>
				<% } %>
				
				<% if (a instanceof IndividualAdDTO) { %>
					<!-- ANUNCIO INDIVIDUALIZADO -->
					<div id="individual-data">
						<% IndividualAdDTO indivAd = (IndividualAdDTO) a; %>
						<label for="destUsers">Destinatarios</label>
						<% ArrayList<UserDTO> destUsers = indivAd.getDestinationUsers();
						String destUsersSeparatedComma = "";
						for (UserDTO destUser : destUsers){
							destUsersSeparatedComma.concat(destUser.getEmail());
							if (!destUser.equals(destUsers.get(destUsers.size() - 1))) {
								destUsersSeparatedComma.concat(",");
							}
						}%>
						<input type="text" name="destUsers" value="<%= destUsersSeparatedComma %>">
						<br/>
						<p>Separe los destinatarios por comas y sin espacios</p>
					</div>
				<% } %>
				<br/>
				<p>Contenido:</p>
				<textarea name="content" cols="40" rows="5"><%= a.getContent() %></textarea>
				<br/>
				<input type="hidden" name="adType" value="<%=a.getType().ordinal()%>">
				<input type="hidden" name="id" value="<%= a.getID() %>">
				<input type="submit" class="small-button" value="Guardar">
			</form>
		</div>
	</body>
</html>