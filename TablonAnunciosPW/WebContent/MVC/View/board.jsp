<%@ page import="es.uco.pw.business.ad.Ad, java.util.ArrayList, java.time.format.DateTimeFormatter, es.uco.pw.business.ad.AdType" %>
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
		<title>Tablón de anuncios</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	</head>
	<body>
		<%@ include file="../../include/navbar.jsp" %>
		<% ArrayList<Ad> ads = (ArrayList<Ad>) request.getAttribute("board"); %>
		<div class="container-tablon-main">
			<h1>Mi tablón de anuncios</h1>
			<p>Bienvenido/a <%= userBean.getFirstname() + " " + userBean.getLastname() %></p>
			<div id="container-search-button">
				<button type="button" class="big-button right" onclick="window.location.href='<%= request.getContextPath() %>/MVC/View/search.jsp'">Buscar anuncios</button>
			</div>
			<p>Filtrar anuncios del tabl&oacute;n</p>
			<label>Por tipo:</label>
			<select id="adType" class="select-type" name="adType">
				<option value="">Todos</option>
				<option value="<%=AdType.GENERAL.ordinal()%>">General</option>
			 	<option value="<%=AdType.FLASH.ordinal()%>">Flash</option>
				<option value="<%=AdType.THEMATIC.ordinal()%>">Tem&aacute;tico</option>
				<option value="<%=AdType.INDIVIDUAL.ordinal()%>">Individualizado</option>
			</select>
			<br/>
			<label>Por t&iacute;tulo</label>
			<input type="text" class="input-form" id="filter-title" placeholder="Título">
			<br/>
			<div id="paginate-buttons"></div>
			<% if (ads == null || ads.size() == 0) { %>
				<p>No hay anuncios</p>
			<% } else { %>
				<div class="tablon-anuncios">
					<% for (Ad a : ads){ %>
						<div class="anuncio" data-type="<%= a.getType().ordinal() %>">
							<h3><%= a.getTitle() %></h3>
							<p><%= a.getContent() %></p>
							<p class="small-text">Publicado el d&iacute;a
							<% DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); %>
							<% String formattedDate = a.getPublishDate().format(formatter); %>
							<%= formattedDate %>
							</p>
							<p class="small-text">Autor: <%= a.getOwner().getFirstname() + " " + a.getOwner().getLastname() + " (" + a.getOwner().getEmail() + ")" %></p>
						</div>
					<% } %>
				</div>
			<% } %>
		</div>
		<script type="text/javascript">
			let type = "";
			let title = "";
			
			let adsPerPage = 5;
			let paginatedAds;
			
			// Cargamos el filtrado guardado
			type = localStorage.getItem("filtradoPorTipo");
			if (type == null){
				type = "";
			}
			
			title = localStorage.getItem("filtradoPorTitulo");
			if (title == null){
				title = "";
			}
			
			document.getElementById("adType").value = type;
			document.getElementById("filter-title").value = title;
			
			filter();
			
			// Recarga de la página
			function refresh(){
				location.reload(true);
			}
			setInterval("refresh()", 30000);
			
			function filter(){
				let anuncios = Array.from(document.getElementsByClassName("anuncio"));
				anuncios.forEach(function(element){
					element.style.display = "none";
				});
				
				// Filtro por tipo
				if (type != ""){
					anuncios = anuncios.filter(function(element){
						if (element.getAttribute("data-type") == type){
							return true;
						}
						else{
							return false;
						}
					});
				}
				
				// Filtro por título
				if (title != ""){
					anuncios = anuncios.filter(function(element){
						if (element.children[0].textContent.toLowerCase().includes(title.toLowerCase())){
							return true;
						}
						else{
							return false;
						}
					});
				}
				
				anuncios.forEach(function(element){
					element.style.display = "block";
				});
				paginate();
			}
			
			function paginate(){
				paginatedAds = Array.from(document.getElementsByClassName("anuncio"));
				paginatedAds = paginatedAds.filter(function(element){
					if (element.style.display == "block"){
						return true;
					}
					else{
						return false;
					}
				});
				
				let pages = Math.ceil(paginatedAds.length / adsPerPage);
				
				let html = "";
				
				for (let i = 0; i < pages; i++){
					html += '<button type="button" class="small-button paginate-button right">' + (pages - i) + '</button>';
				}
				document.getElementById("paginate-buttons").innerHTML = html;
				let buttons = document.getElementById("paginate-buttons").children;
				for (let i = 0; i < buttons.length; i++){
					buttons[i].onclick = function(){
						showPagination(pages - i - 1);
					}
					console.log(buttons);
				}
				
				showPagination(0);
			}
			
			function showPagination(start){
				for(let i = 0; i < paginatedAds.length; i++){
					paginatedAds[i].style.display = "none";
				}
				
				let startIndex = start * adsPerPage;
				
				for(let i = startIndex; i < startIndex + adsPerPage; i++){
					if (i >= paginatedAds.length){
						break;
					}
					paginatedAds[i].style.display = "block";
				}
			}
			
			document.getElementById("adType").addEventListener("change", function(e){
				type = e.target.value;
				localStorage.setItem("filtradoPorTipo", type);
				filter();
			});
			
			document.getElementById("filter-title").addEventListener("change", function(e){
				title = e.target.value;
				localStorage.setItem("filtradoPorTitulo", title);
				filter();
			});
			
			
		</script>
	</body>
</html>