package es.uco.pw.servlet;

import java.io.IOException;
import java.time.LocalDate;

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
 * Servlet implementation class EditProfile
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/editProfileServlet")
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/MVC/View/board.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean == null || userBean.getEmail() != null) {
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");

			String birthdateStr = request.getParameter("birthdate");
			LocalDate birthdate = LocalDate.parse(birthdateStr);
			
			ServletContext context = getServletContext();
			
			UserDAO userDAO = new UserDAO(context);
			UserDTO user = userDAO.queryByEmail(userBean.getEmail());
			
			user.setBirthdate(birthdate);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			
			userDAO.updateUser(user);
			
			userBean.setFirstname(firstname);
			userBean.setLastname(lastname);
			userBean.setBirthdate(birthdate);
			
			String msg = "Perfil actualizado correctamente";
			request.setAttribute("msg", msg);
			RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/editProfile.jsp");
			rd.forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath());
		}
	}

}
