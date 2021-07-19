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

import es.uco.pw.business.ad.Ad;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.ad.AdDAO;
import es.uco.pw.data.user.UserDAO;
import es.uco.pw.display.CustomerBean;

/**
 * Servlet implementation class Board
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/boardServlet")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		ServletContext context = getServletContext();
		if (userBean != null) {
			if (userBean.getEmail() != null) {
				UserDAO userDAO = new UserDAO(context);
				UserDTO user = userDAO.queryByEmail(userBean.getEmail());
				
				AdDAO adDAO = new AdDAO(context);
				ArrayList<Ad> board = adDAO.getBoardSortedByDate(user);
				
				request.setAttribute("board", board);
				RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/board.jsp");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
