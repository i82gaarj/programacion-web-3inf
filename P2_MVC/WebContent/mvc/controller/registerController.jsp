<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate, java.time.format.DateTimeFormatter,es.uco.pw.p2.business.UserDTO,es.uco.pw.p2.data.DAO,es.uco.pw.p2.data.UserDAO" %>
<jsp:useBean id="CustomerBean" scope="session" class="es.uco.pw.p2.display.CustomerBean"></jsp:useBean>

<%
	String firstname = request.getParameter("firstname");
	String lastname = request.getParameter("lastname");
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String birthdateStr = request.getParameter("birthdate");
	
	if (firstname == null || lastname == null || email == null || password == null || birthdateStr == null || firstname == "" || lastname == "" || email == "" || password == "" || birthdateStr == "") {
		response.sendRedirect("/P2_MVC/errorPage.jsp?msg=Uno o mas campos de registro estaban incompletos");
	}
	else{
		UserDAO userDAO = new UserDAO(request.getServletContext());
		UserDTO user = userDAO.queryByEmail(request.getParameter("email"));
		
		if (CustomerBean.getEmail() != null) {
			response.sendRedirect("/P2_MVC/errorPage.jsp?msg=Debe cerrar la sesion antes de registrar un usuario");
		}
		else {		
			if (user != null) {
				response.sendRedirect("/P2_MVC/errorPage.jsp?msg=Ya existe un usuario con ese email");
			}
			else {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				try {
					LocalDate birthdate = LocalDate.parse(birthdateStr, formatter);
					user = new UserDTO(-1, email, password, firstname, lastname, birthdate);
					userDAO.insertUser(user);
				} catch(Exception e){
					e.printStackTrace();
				}
				
				
				response.sendRedirect("/P2_MVC/");
			}
		}
	}
	
%>
		