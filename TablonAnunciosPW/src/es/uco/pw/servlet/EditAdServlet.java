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
import es.uco.pw.business.ad.AdType;
import es.uco.pw.business.user.InterestDTO;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.ad.AdDAO;
import es.uco.pw.data.ad.FlashAdDAO;
import es.uco.pw.data.ad.GeneralAdDAO;
import es.uco.pw.data.ad.IndividualAdDAO;
import es.uco.pw.data.ad.ThematicAdDAO;
import es.uco.pw.data.user.InterestDAO;
import es.uco.pw.data.user.UserDAO;

/**
 * Servlet implementation class EditAd
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/editAdServlet")
public class EditAdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAdServlet() {
        super();
    }
    
    public void sendEditSuccessMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String msg = "Anuncio editado";
		request.setAttribute("msg", msg);
		RequestDispatcher rd = request.getRequestDispatcher("/myAdsServlet");
		rd.forward(request, response);
    }
    
    public void sendEditErrorMsg(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws ServletException, IOException {
		request.setAttribute("errorMsg", errorMsg);
		RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
		rd.forward(request, response);	
    }
    
    public void updateGeneralAd(HttpServletRequest request, HttpServletResponse response, ServletContext context, int id, String title, String content) throws ServletException, IOException {
    	GeneralAdDAO generalDAO = new GeneralAdDAO(context);
		generalDAO.updateGeneralAd(id, title, content);
	
		sendEditSuccessMsg(request, response);
    }
    
    public void updateFlashAd(HttpServletRequest request, HttpServletResponse response, ServletContext context, int id, String title, String content) throws ServletException, IOException {
    	String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		
		if (startDateStr.equals("") || endDateStr.equals("")) {
			String errorMsg = "Complete las fechas de inicio y fin del anuncio flash";
			sendEditErrorMsg(request, response, errorMsg);
		}
		else {
			LocalDate startDate = LocalDate.parse(startDateStr);
			LocalDate endDate = LocalDate.parse(endDateStr);
			
			FlashAdDAO flashDAO = new FlashAdDAO(context);
			flashDAO.updateFlashAd(id, title, content, startDate, endDate);
			
			sendEditSuccessMsg(request, response);
		}
    }
    
    public void updateThematicAd(HttpServletRequest request, HttpServletResponse response, ServletContext context, int id, String title, String content) throws ServletException, IOException {
    	String[] interestsStr = request.getParameterValues("interest[]");
		if (interestsStr != null) {
			InterestDAO interestDAO = new InterestDAO(context);
			ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
			
			for (String interestStr : interestsStr) {
				InterestDTO interest = interestDAO.queryByID(Integer.parseInt(interestStr));
				if (interest != null) {
					interests.add(interest);
				}
			}
			
			ThematicAdDAO themDAO = new ThematicAdDAO(context);
			themDAO.updateThematicAd(id, title, content, interests);
			
			sendEditSuccessMsg(request, response);
		}
		else {
			String errorMsg = "Asigne al menos un tema de interés al anuncio temático";
			sendEditErrorMsg(request, response, errorMsg);
		}
    }
    
    public void updateIndividualAd(HttpServletRequest request, HttpServletResponse response, ServletContext context, int id, String title, String content) throws ServletException, IOException {
    	String destUsersStr = request.getParameter("destUsers");
		if (destUsersStr.equals("")) {
			String errorMsg = "Complete los usuarios destinatarios del anuncio individualizado";
			sendEditErrorMsg(request, response, errorMsg);
		}
		else {
			String [] destUsersSeparated = destUsersStr.split(",");
			
			ArrayList<UserDTO> destUsers = new ArrayList<UserDTO>();
			UserDAO userDAO = new UserDAO(context);
			for (String destUserStr : destUsersSeparated) {
				UserDTO destUser = userDAO.queryByEmail(destUserStr);
				if (destUser != null) {
					destUsers.add(destUser);
				}
			}
			if (destUsers.size() == 0) {
				String errorMsg = "No se pudo encontrar ningún usuario destinatario";
				sendEditErrorMsg(request, response, errorMsg);
			}
			else {
				IndividualAdDAO indivDAO = new IndividualAdDAO(context);
				indivDAO.updateIndividualAd(id, title, content, destUsers);

				sendEditSuccessMsg(request, response);
			}
		}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		if (title != "" && content != "") { // Comprobamos que el titulo y el contenido no están vacíos
			ServletContext context = getServletContext();
			try{
				int id = Integer.parseInt(idStr);
				
				AdDAO adDAO = new AdDAO(context);
				AdType adType = adDAO.getTypeOfAdFromID(id);
				if (adType != null) { // Esto significa que el anuncio con el ID especificado existe
					if(adType.equals(AdType.GENERAL)) {
						updateGeneralAd(request, response, context, id, title, content);
						
					}else if(adType.equals(AdType.FLASH)) {
						updateFlashAd(request, response, context, id, title, content);
						
					}else if(adType.equals(AdType.THEMATIC)) {
						updateThematicAd(request, response, context, id, title, content);
						
					}else if(adType.equals(AdType.INDIVIDUAL)) {
						updateIndividualAd(request, response, context, id, title, content);
					}
				}
				else {
					String errorMsg = "ID de anuncio inválido";
					sendEditErrorMsg(request, response, errorMsg);
				}
			}
			catch(NumberFormatException e) {
				String errorMsg = "ID de anuncio inválido";
				sendEditErrorMsg(request, response, errorMsg);
			}
		}
		else {
			String errorMsg = "El anuncio debe tener un título y un contenido";
			sendEditErrorMsg(request, response, errorMsg);
		}
		request.getRequestDispatcher("myAdsServlet").forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		try{
			int id = Integer.parseInt(idStr);
			ServletContext context = getServletContext();
			
			AdDAO adDAO = new AdDAO(context);
			AdType adType = adDAO.getTypeOfAdFromID(id);
			Ad ad = adDAO.queryByID(id, adType);
			
			InterestDAO interestDAO = new InterestDAO(context);
			ArrayList<InterestDTO> allInterests = interestDAO.getInterests();
			
			request.setAttribute("interests", allInterests);
			request.setAttribute("ad", ad);
			request.getRequestDispatcher("MVC/View/editAd.jsp").forward(request, response);
		}
		catch (NumberFormatException e) {
			String errorMsg = "ID de anuncio no válido";
			sendEditErrorMsg(request, response, errorMsg);
		}
	}

}
