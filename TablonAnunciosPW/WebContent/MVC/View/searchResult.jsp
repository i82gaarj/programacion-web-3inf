<%@ page import="es.uco.pw.business.ad.Ad, java.util.ArrayList, java.time.format.DateTimeFormatter, java.time.LocalDate" %>
<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
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
		<title>Resultados de la b&uacute;squeda</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	</head>
	<body>
		<%@ include file="../../include/navbar.jsp" %>
		<div class="container-tablon-main">
			<h1>Resultados de la búsqueda</h1>
			<% String titleQuery = (String) request.getAttribute("titleQuery"); %>
			
			<% String startDate = (String) request.getAttribute("startDate"); %>
			<% String endDate = (String) request.getAttribute("endDate"); %>
			<% DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); %>
			
			<% if (titleQuery != null) { %>
				<p>Resultados que coinciden con "<%= titleQuery %>"</p>
			<% } else if (startDate != null && endDate != null) { %>
				
				<p>Resultados de publicaciones entre <%= LocalDate.parse(startDate).format(formatter) %> y <%= LocalDate.parse(endDate).format(formatter) %></p>
			<% } %>
			
			<% ArrayList<Ad> ads = (ArrayList<Ad>) request.getAttribute("adsSearch"); %>
			
			<% if (ads == null || ads.size() == 0) { %>
				<p>No hay anuncios que coincidan con la b&uacute;squeda</p>
			<% } else { %>
				<div class="tablon-anuncios">
					<% for (Ad a : ads){ %>
						<div class="anuncio">
							<h4><%= a.getTitle() %></h4>
							<p><%= a.getContent() %></p>
							<% LocalDate publishDate = a.getPublishDate(); %>
							<% if (!publishDate.equals(LocalDate.of(1970, 1, 1))) { %>
								<p class="small-text">Publicado el d&iacute;a
								
								<% String formattedDate = publishDate.format(formatter); %>
								<%= formattedDate %>
								</p>
							<% } else { %>
								<p class="small-text">No publicado nunca</p>
							<% } %>
							<p class="small-text">Autor: <%= a.getOwner().getFirstname() + " " + a.getOwner().getLastname() + " (" + a.getOwner().getEmail() + ")" %></p>
						</div>
					<% } %>
				</div>
			<% } %>
		</div>
	</body>
</html>