package es.uco.pw.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.data.ad.AdDAO;
import es.uco.pw.display.CustomerBean;

/**
 * Servlet implementation class DeleteAd
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/deleteAdServlet")
public class DeleteAdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAdServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean != null) {
			if (userBean.getEmail() != null) {
				try {
					int id = Integer.parseInt(request.getParameter("id"));
					ServletContext context = getServletContext();
					AdDAO adDAO = new AdDAO(context);
					if (adDAO.queryByID(id, adDAO.getTypeOfAdFromID(id)) != null) {
						adDAO.deleteAd(id);
						String msg = "Anuncio eliminado";
						request.setAttribute("msg", msg);
						RequestDispatcher rd = request.getRequestDispatcher("/myAdsServlet");
						rd.forward(request, response);
					}
					else {
						String errorMsg = "Eliminar anuncio: ID del anuncio no válido";
						request.setAttribute("errorMsg", errorMsg);
						RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
						rd.forward(request, response);
					}
					
				}
				catch(NumberFormatException e) {
					e.printStackTrace();
					String errorMsg = "Eliminar anuncio: ID del anuncio no válido";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
