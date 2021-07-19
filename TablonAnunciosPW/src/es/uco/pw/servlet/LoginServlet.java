package es.uco.pw.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.user.UserDAO;
import es.uco.pw.display.CustomerBean;


/**
 * Servlet implementation class Login
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet(name="LoginServlet", urlPatterns="/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean != null && userBean.getEmail() == null) {
			response.sendRedirect("MVC/View/board.jsp");
		}
		else {
			response.sendRedirect("MVC/View/board.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email.equals("") || password.equals("")) {
			String errorMsg = "Complete los campos del login";
			request.setAttribute("errorMsg", errorMsg);
			RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
			rd.forward(request, response);
		}
		else {
			UserDAO userDAO = new UserDAO(context);
			UserDTO user = userDAO.queryByEmail(email);
			
			CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
			
			if(user != null) {
				if (user.getPassword().equals(password)) {
									
					userBean.setEmail(user.getEmail());
					userBean.setFirstname(user.getFirstname());
					userBean.setLastname(user.getLastname());
					userBean.setBirthdate(user.getBirthdate());
					userBean.setID(user.getID());
					
					response.sendRedirect("boardServlet");
				}
				else {
					String errorMsg = "El usuario y/o la contraseña no coinciden con nuestros registros";
					request.setAttribute("errorMsg", errorMsg);
					RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
					rd.forward(request, response);
				}
			}
			else {
				String errorMsg = "El usuario y/o la contraseña no coinciden con nuestros registros";
				request.setAttribute("errorMsg", errorMsg);
				RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
				rd.forward(request, response);
			}
		}
		
	}
}
