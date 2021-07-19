<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate,es.uco.pw.p2.business.UserDTO,es.uco.pw.p2.data.UserDAO" %>
<jsp:useBean id="CustomerBean" scope="session" class="es.uco.pw.p2.display.CustomerBean"></jsp:useBean>
<%

	String firstname = request.getParameter("firstname");
	String lastname = request.getParameter("lastname");
	String password = request.getParameter("password");
	String birthdateStr = request.getParameter("birthdate");
	
	if (firstname == null || lastname == null || password == null || birthdateStr == null || firstname == "" || lastname == "" || password == "" || birthdateStr == ""){
		response.sendRedirect("/P2_MVC/errorPage.jsp?msg=Uno o mas campos estaban incompletos");
	}
	else{
		UserDAO userDAO = new UserDAO(request.getServletContext());
		
		if (CustomerBean.getEmail() != null) {
			int id = CustomerBean.getID();
			try{
				LocalDate birthdate = LocalDate.parse(birthdateStr);
				UserDTO user = new UserDTO(id, CustomerBean.getEmail(), password, firstname, lastname, birthdate);
				userDAO.updateUser(user);
			} catch (Exception e){
				e.printStackTrace();
			}
			
		}
		response.sendRedirect("/P2_MVC/");
	}
%>