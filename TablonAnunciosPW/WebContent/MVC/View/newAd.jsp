<%@ page import="es.uco.pw.business.ad.Ad, java.util.ArrayList, es.uco.pw.business.user.InterestDTO" %>
<jsp:useBean id="userBean" scope="session" class="es.uco.pw.display.CustomerBean"></jsp:useBean>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="es.uco.pw.business.ad.AdType" %>
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
	    <title>Nuevo anuncio</title>
	</head>
	<body>
		<%@ include file="../../include/navbar.jsp" %>
		<div class="container-tablon-main">
			<h1>Crear anuncio</h1>
			<form action="<%= request.getContextPath() %>/newAdServlet" method="post">
				<label for="title">Título:</label>
				<input type="text" class="input-form" name="title">
				<br/>
				<label for="adType">Tipo</label>
				<select id="adType" class="select-type" name="adType">
					<option value="<%=AdType.GENERAL.ordinal()%>">General</option>
				 	<option value="<%=AdType.FLASH.ordinal()%>">Flash</option>
					<option value="<%=AdType.THEMATIC.ordinal()%>">Tem&aacute;tico</option>
					<option value="<%=AdType.INDIVIDUAL.ordinal()%>">Individualizado</option>
				</select>
				<br/>
				
				<!-- ANUNCIO FLASH -->
				<div style="display: none;" id="flash-data">
					<label for="startDate">Fecha de inicio:</label>
					<input type="date" class="input-form" name="startDate">
					<br/>
					
					<label for="endDate">Fecha de fin:</label>
					<input type="date" class="input-form" name="endDate">
				</div>
				
				<!-- ANUNCIO TEMATICO -->
				<div style="display: none;" id="thematic-data">
					<p>Temas de inter&eacute;s del anuncio</p>
					<% ArrayList<InterestDTO> interests = (ArrayList<InterestDTO>) request.getAttribute("interests"); %>
					<% for (InterestDTO i : interests){ %>
						<label>
							<input type="checkbox" name="interest[]" value="<%=i.getID()%>"><%= i.getName() %>
						</label>
						<br/>
					<% } %>
				</div>
				
				<!-- ANUNCIO INDIVIDUALIZADO -->
				<div style="display: none;" id="individual-data">
					<label for="destUsers">Destinatarios</label>
					<input type="text" class="input-form" name="destUsers">
					<br/>
					<p>Separe los emails de los destinatarios por comas y sin espacios</p>
				</div>
				<br/>
				<p>Contenido:</p>
				<textarea name="content" cols="40" rows="5"></textarea>
				<br/>
				<input type="submit" class="small-button" value="Guardar">
			</form>
		</div>
		<script>
			document.getElementById("adType").addEventListener("change", function(e) {
				let tipo = e.target.value;
				if (tipo == <%=AdType.GENERAL.ordinal()%>) {
					document.getElementById("flash-data").style.display = "none";
					document.getElementById("thematic-data").style.display = "none";
					document.getElementById("individual-data").style.display = "none";
				}
				else if (tipo == <%=AdType.THEMATIC.ordinal()%>) {
					document.getElementById("flash-data").style.display = "none";
					document.getElementById("thematic-data").style.display = "block";
					document.getElementById("individual-data").style.display = "none";
				}
				else if (tipo == <%=AdType.FLASH.ordinal()%>) {
					document.getElementById("flash-data").style.display = "block";
					document.getElementById("thematic-data").style.display = "none";
					document.getElementById("individual-data").style.display = "none";
				}
				else if (tipo == <%=AdType.INDIVIDUAL.ordinal()%>){
					document.getElementById("flash-data").style.display = "none";
					document.getElementById("thematic-data").style.display = "none";
					document.getElementById("individual-data").style.display = "block";
				}
			});
		</script>
	</body>
</html>