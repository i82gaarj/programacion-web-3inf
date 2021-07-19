<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="es.uco.pw.business.ad.Ad, java.util.ArrayList, es.uco.pw.display.CustomerBean, es.uco.pw.business.ad.AdStatus, java.time.LocalDate, java.time.format.DateTimeFormatter" %>
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
		<title>Tablón de anuncios</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	</head>
	<body>
		<%@ include file="../../include/navbar.jsp" %>
		<div class="container-tablon-main">
			<h1>Mis anuncios</h1>
			<p>Ha iniciado sesión como <%= userBean.getFirstname() + " " + userBean.getLastname() %></p>
			<% ArrayList<Ad> publishedAds = (ArrayList<Ad>)request.getAttribute("publishedAds"); %>
			<% ArrayList<Ad> notPublishedAds = (ArrayList<Ad>)request.getAttribute("notPublishedAds"); %>
			
			<% String msg = (String)request.getAttribute("msg"); // Mensaje de respuesta %>
	    	<% if (msg != null) { %>
	    		<p class="message-success-left"><%= msg %></p>
	    	<% } %>
	    	
			<!-- ANUNCIOS PUBLICADOS -->
			<% if (publishedAds == null || publishedAds.size() == 0) { %>
				<p>No hay anuncios publicados.</p>
			<% } else { %>
				<p>Anuncios publicados:</p>
				<div class="tablon-anuncios">
					<% for (Ad a : publishedAds){ %>
						<div class="anuncio">
							<h3><%= a.getTitle() %></h3>
							<p><%= a.getContent() %></p>
							<p>Publicado el d&iacute;a
							<% DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); %>
							<% String dateFormatted = a.getPublishDate().format(formatter); %>
							<%= dateFormatted %>
						    </p>
							<p class="small-text">Anuncio <%= a.getTypeStr() %></p>
							<button type="button" class="small-button" onclick="window.location.href='<%= request.getContextPath() %>/archiveAdServlet?id=<%=a.getID()%>'">Archivar</button>
						</div>
					<% } %>
				</div>
			<% } %>
			
			<!-- ANUNCIOS EN EDICIÓN -->
			<% if (notPublishedAds == null || notPublishedAds.size() == 0) { %>
				<p>No hay anuncios sin publicar.</p>
			<% } else { %>
				<p>Anuncios sin publicar:</p>
				<div class="tablon-anuncios">
					<% for (Ad a : notPublishedAds){ %>
						<div class="anuncio">
							<h3><%= a.getTitle() %></h3>
							<p><%= a.getContent() %></p>
							<p class="small-text">Anuncio <%= a.getTypeStr() %></p>
							<% if (a.getStatus() == AdStatus.EDITING) { %>
								<button type="button" class="small-button" onclick="window.location.href='<%= request.getContextPath() %>/publishAdServlet?id=<%=a.getID()%>'">Publicar</button>
								<button type="button" class="small-button" onclick="window.location.href='<%= request.getContextPath() %>/editAdServlet?id=<%=a.getID()%>'">Editar</button>
							<% } else if (a.getStatus() == AdStatus.ARCHIVED) { %>
								<button type="button" class="small-button" id="delete-ad-button" onclick="deleteAd(<%=a.getID()%>);">Eliminar</button>
								<button type="button" class="small-button" onclick="window.location.href='<%= request.getContextPath() %>/restoreAdServlet?id=<%=a.getID()%>'">Restaurar</button>
							<% } %>
						</div>
					<% } %>
				</div>
			<% } %>
			
		</div>
		<script>
		function deleteAd(adID){
			let a = confirm("¿Desea eliminar el anuncio?");
			if (a == true){
				window.location.href='<%= request.getContextPath() %>/deleteAdServlet?id=' + adID;
			}
		}
		</script>
	</body>
</html>