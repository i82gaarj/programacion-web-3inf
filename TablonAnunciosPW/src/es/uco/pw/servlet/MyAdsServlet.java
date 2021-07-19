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
 * Servlet implementation class MyAds
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/myAdsServlet")
public class MyAdsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyAdsServlet() {
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
				
				UserDAO userDAO = new UserDAO(context);
				UserDTO user = userDAO.queryByEmail(userBean.getEmail());
				
				AdDAO adDAO = new AdDAO(context);
				ArrayList<Ad> publishedAds =  adDAO.getPublishedAdsOfUser(user);
				ArrayList<Ad> notPublishedAds =  adDAO.getNotPublishedAdsOfUser(user);

				request.setAttribute("publishedAds", publishedAds);
				request.setAttribute("notPublishedAds", notPublishedAds);
				RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/myAds.jsp");
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
		doGet(request, response);
	}

}
