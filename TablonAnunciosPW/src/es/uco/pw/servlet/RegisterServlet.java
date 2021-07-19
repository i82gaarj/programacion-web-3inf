package es.uco.pw.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.business.user.InterestDTO;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.user.UserDAO;
//import es.uco.pw.p3.display.CustomerBean;
//import java.util.ArrayList;
import es.uco.pw.display.CustomerBean;

/**
 * Servlet implementation class Register
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/MVC/View/register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean != null) {
			if (userBean.getEmail() == null) {
				ServletContext context = getServletContext();
				String email = request.getParameter("email");
				
				String password = request.getParameter("password");
				String firstname = request.getParameter("firstname");
				String lastname = request.getParameter("lastname");
				String birthdateStr = request.getParameter("birthdate");
				
				UserDAO userDAO = new UserDAO(context);
				if (userDAO.queryByEmail(email) != null) {
					String errorMsg = "Un usuario con este email ya existe";
					request.setAttribute("errorMsg", errorMsg);
					RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
					rd.forward(request, response);
				}
				else if (firstname == null || lastname == null || email == null || password == null || birthdateStr == null || firstname == "" || lastname == "" || email == "" || password == "" || birthdateStr == "") {
					String errorMsg = "Uno o más campos de registro estaban vacíos";
					request.setAttribute("errorMsg", errorMsg);
					RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
					rd.forward(request, response);
				}
				else {
					LocalDate birthdate = LocalDate.parse(birthdateStr);
					
					UserDTO user = new UserDTO(-1, email, password, firstname, lastname, birthdate, new ArrayList<InterestDTO>());
					
					userDAO.insertUser(user);
					String msg = "Registrado correctamente";
					request.setAttribute("msg", msg);
					RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/login.jsp");
					rd.forward(request, response);
				}
			}
			else {
				response.sendRedirect(request.getContextPath());
			}
		}
		else {
			response.sendRedirect(request.getContextPath());
		}
	}

}
