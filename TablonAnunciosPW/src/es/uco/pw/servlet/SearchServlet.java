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

import es.uco.pw.business.ad.Ad;
import es.uco.pw.data.ad.AdDAO;
import es.uco.pw.display.CustomerBean;

/**
 * Servlet implementation class Search
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/searchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
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
				String searchType = request.getParameter("type");
				if (searchType == null) {
					response.sendRedirect(request.getContextPath() + "/MVC/View/search.jsp");
				}
				else if (searchType.equals("title")) {
					String titleQuery = request.getParameter("titleQuery");
					if (titleQuery == null || titleQuery.equals("")) {
						String errorMsg = "Introduzca un título";
						request.setAttribute("errorMsg", errorMsg);
						RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/search.jsp");
						rd.forward(request, response);
					}
					else {
						ArrayList<Ad> adsSearch = (new AdDAO(context)).queryByTitle(titleQuery);
						request.setAttribute("adsSearch", adsSearch);
						request.setAttribute("titleQuery", titleQuery);
						RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/searchResult.jsp");
						rd.forward(request, response);
					}
				}
				else if (searchType.equals("dateRange")){
					String startDateStr = request.getParameter("startDate");
					String endDateStr = request.getParameter("endDate");
					LocalDate startDate = LocalDate.parse(startDateStr);
					LocalDate endDate = LocalDate.parse(endDateStr);
					if (startDate != null && endDate != null) {
						ArrayList<Ad> adsSearch = (new AdDAO(context)).queryByDateRange(startDate, endDate);
						request.setAttribute("adsSearch", adsSearch);
						request.setAttribute("startDate", startDateStr);
						request.setAttribute("endDate", endDateStr);
						RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/searchResult.jsp");
						rd.forward(request, response);
					}
					else {
						String errorMsg = "Especifique una fecha de inicio y una fecha de fin";
						request.setAttribute("errorMsg", errorMsg);
						RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/search.jsp");
						rd.forward(request, response);
					}
				}
				else {
					response.sendRedirect(request.getContextPath() + "/MVC/View/search.jsp");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
