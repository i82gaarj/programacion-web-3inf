package es.uco.pw.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.display.CustomerBean;
import es.uco.pw.business.ad.AdStatus;
import es.uco.pw.data.ad.AdDAO;

/**
 * Servlet implementation class ArchiveAd
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/archiveAdServlet")
public class ArchiveAdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArchiveAdServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//recortar el nombre según se llame el botón para quedarnos solo con el id del anuncio
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean != null) {
			if (userBean.getEmail() != null) {
				try {
					ServletContext context = getServletContext();
					
					int id = Integer.parseInt(request.getParameter("id"));
					
					AdDAO adDAO = new AdDAO(context);
					adDAO.setAdStatus(id, AdStatus.ARCHIVED);
					
					String msg = "Anuncio archivado";
					request.setAttribute("msg", msg);
					RequestDispatcher rd = request.getRequestDispatcher("/myAdsServlet");
					rd.forward(request, response);
				}
				catch(NumberFormatException e) {
					e.printStackTrace();
					String errorMsg = "Archivar anuncio: ID del anuncio no válido";
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
