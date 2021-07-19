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
 * Servlet implementation class ChangePassword
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/changePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/MVC/View/changepassword.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean != null) {
			if (userBean.getEmail() != null) {
				ServletContext context = getServletContext();
				String oldPass = request.getParameter("oldPassword");
				String newPass = request.getParameter("newPassword");
				if (newPass.equals("")) {
					String errorMsg = "La contraseña no puede quedar vacía";
					request.setAttribute("errorMsg", errorMsg);
					RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
					rd.forward(request, response);
				}
				UserDAO userDAO = new UserDAO(context);
				UserDTO user = userDAO.queryByEmail(userBean.getEmail());
				if (user.getPassword().equals(oldPass)) {
					userDAO.setPassword(user, newPass);
					String msg = "Contraseña actualizada";
					request.setAttribute("msg", msg);
					RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/changePassword.jsp");
					rd.forward(request, response);
				}
				else {
					String errorMsg = "La contraseña actual es incorrecta";
					request.setAttribute("errorMsg", errorMsg);
					RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
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
