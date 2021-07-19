<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		   <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>ERROR</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	</head>
	<body>
		<div class="container-tablon-main">
			<h1>ERROR: <%= (String)request.getAttribute("errorMsg") %></h1>
			<button type="button" class="small-button" onclick="window.location.href='<%=request.getContextPath() %>'">Volver al inicio</button>
			<button type="button" class="small-button" onclick="goBack()">Volver atr&aacute;s</button>
		</div>
	</body>
	<script>
	function goBack() {
		window.history.back();
	}
	</script>
</html>