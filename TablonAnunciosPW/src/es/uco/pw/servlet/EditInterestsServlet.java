package es.uco.pw.servlet;

import java.io.IOException;
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
import es.uco.pw.data.user.InterestDAO;
import es.uco.pw.data.user.UserDAO;
import es.uco.pw.display.CustomerBean;

/**
 * Servlet implementation class EditInterests
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/editInterestsServlet")
public class EditInterestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditInterestsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean != null) {
			if (userBean.getEmail() != null) {
				ServletContext context = getServletContext();
				
				InterestDAO interestDAO = new InterestDAO(context);
				UserDAO userDAO = new UserDAO(context);
				UserDTO loggedUser = userDAO.queryByEmail(userBean.getEmail());
				
				ArrayList<InterestDTO> allInterests = interestDAO.getInterests();
				request.setAttribute("interests", allInterests);
				
				ArrayList<InterestDTO> interestsOfUser = interestDAO.queryByUser(loggedUser);
				request.setAttribute("interestsOfUser", interestsOfUser);
				
				RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/editInterests.jsp");
				rd.forward(request, response);
			}
			else {
				response.sendRedirect(request.getContextPath());
			}
		}
		else {
			response.sendRedirect(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean != null) {
			if (userBean.getEmail() != null) {
				String[] interestsStr = request.getParameterValues("interest[]");
				
				ServletContext context = getServletContext();
				
				UserDAO userDAO = new UserDAO(context);
				UserDTO user = userDAO.queryByEmail(userBean.getEmail());
				
				InterestDAO interestDAO = new InterestDAO(context);
				if (interestsStr == null) {
					interestDAO.deleteAllInterestsFromUser(user);
					String msg = "Intereses actualizados correctamente";
					request.setAttribute("msg", msg);
					RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/editProfile.jsp");
					rd.forward(request, response);
				}
				else {
					ArrayList<Integer> interestIDs = new ArrayList<Integer>();
					for (String interestStr : interestsStr) {
						try {
							int interestID = Integer.parseInt(interestStr);
							interestIDs.add(interestID);
						}
						catch (NumberFormatException e) {
							String errorMsg = "Uno o más temas de interés especificados no eran válidos";
							request.setAttribute("errorMsg", errorMsg);
							RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
							rd.forward(request, response);
						}
						
					}
					interestDAO.deleteAllInterestsFromUser(user);
					for (Integer interestID : interestIDs) {
						InterestDTO interest = interestDAO.queryByID(interestID);
						if (interest != null) {
							interestDAO.addInterestToUser(user, interest);
						}
					}
					String msg = "Intereses actualizados correctamente";
					request.setAttribute("msg", msg);
					RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/editProfile.jsp");
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
