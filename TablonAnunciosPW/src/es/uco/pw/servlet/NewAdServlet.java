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

import es.uco.pw.business.ad.AdFactory;
import es.uco.pw.business.ad.AdStatus;
import es.uco.pw.business.ad.AdType;
import es.uco.pw.business.ad.FlashAdDTO;
import es.uco.pw.business.ad.GeneralAdDTO;
import es.uco.pw.business.ad.IndividualAdDTO;
import es.uco.pw.business.ad.ThematicAdDTO;
import es.uco.pw.business.user.InterestDTO;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.ad.FlashAdDAO;
import es.uco.pw.data.ad.GeneralAdDAO;
import es.uco.pw.data.ad.IndividualAdDAO;
import es.uco.pw.data.ad.ThematicAdDAO;
import es.uco.pw.data.user.InterestDAO;
import es.uco.pw.data.user.UserDAO;
import es.uco.pw.display.CustomerBean;

/**
 * Servlet implementation class NewAd
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
@WebServlet("/newAdServlet")
public class NewAdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAdServlet() {
        super();
    }

    public void sendCreationSuccessMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String msg = "Anuncio creado";
		request.setAttribute("msg", msg);
		RequestDispatcher rd = request.getRequestDispatcher("/myAdsServlet");
		rd.forward(request, response);
    }
    
    public void sendCreationErrorMsg(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws ServletException, IOException {
		request.setAttribute("errorMsg", errorMsg);
		RequestDispatcher rd = request.getRequestDispatcher("/errorPage.jsp");
		rd.forward(request, response);	
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CustomerBean userBean = (CustomerBean) request.getSession().getAttribute("userBean");
		if (userBean != null) {
			if (userBean.getEmail() != null) {
				InterestDAO interestDAO = new InterestDAO(getServletContext());
				ArrayList<InterestDTO> allInterests = interestDAO.getInterests();
				request.setAttribute("interests", allInterests);
				RequestDispatcher rd = request.getRequestDispatcher("/MVC/View/newAd.jsp");
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
				ServletContext context = getServletContext();
				String adTypeStr = request.getParameter("adType");
				try {
					int adTypeInt = Integer.parseInt(adTypeStr);
					AdType adType = AdType.values()[adTypeInt];
					String title = request.getParameter("title");
					String content = request.getParameter("content");
					if (title != "" && content != "") {
						
						if (adType.equals(AdType.FLASH)) {
							String startDateStr = request.getParameter("startDate");
							String endDateStr = request.getParameter("endDate");
							if (startDateStr.equals("") || endDateStr.equals("")) {
								String errorMsg = "Complete las fechas de inicio y fin del anuncio flash";
								sendCreationErrorMsg(request, response, errorMsg);
							}
							else {
								FlashAdDAO adDAO = new FlashAdDAO(context);
								UserDAO userDAO = new UserDAO(context);
								UserDTO loggedUser = userDAO.queryByEmail(userBean.getEmail());
								FlashAdDTO a = AdFactory.createFlashAd(
										title, loggedUser, content,	-1,
										AdStatus.EDITING, LocalDate.of(1970, 1, 1), LocalDate.parse(startDateStr), LocalDate.parse(endDateStr)
										);
								adDAO.insertFlashAd(a);
								
								sendCreationSuccessMsg(request, response);
							}
							
						}
						
						else if (adType.equals(AdType.INDIVIDUAL)) {
							String destUsersStr = request.getParameter("destUsers");
							if (destUsersStr.equals("")) {
								String errorMsg = "Complete los usuarios destinatarios del anuncio individualizado";
								sendCreationErrorMsg(request, response, errorMsg);
							}
							else {
								String[] destUsersSeparated = destUsersStr.split(",");
								UserDAO userDAO = new UserDAO(context);
								UserDTO loggedUser = userDAO.queryByEmail(userBean.getEmail());
								ArrayList<UserDTO> destUsers = new ArrayList<UserDTO>();
								for (String destUserStr : destUsersSeparated) {
									UserDTO destUser = userDAO.queryByEmail(destUserStr);
									if (destUser != null) {
										destUsers.add(destUser);
									}
								}
								if (destUsers.size() == 0) {
									String errorMsg = "No se pudo encontrar ningún usuario destinatario";
									sendCreationErrorMsg(request, response, errorMsg);
								}
								else {
									IndividualAdDAO adDAO = new IndividualAdDAO(context);
									IndividualAdDTO a = AdFactory.createIndividualAd(title, loggedUser, content, -1, AdStatus.EDITING, LocalDate.of(1970, 1, 1), destUsers);
									adDAO.insertIndividualAd(a);
									
									sendCreationSuccessMsg(request, response);
								}
							}
							
						}
						
						else if (adType.equals(AdType.THEMATIC)) {
							String[] interestsStr = request.getParameterValues("interest[]");
							if (interestsStr.length == 0) {
								String errorMsg = "Asigne al menos un tema de interés al anuncio temático";
								sendCreationErrorMsg(request, response, errorMsg);	
							}
							else {
								InterestDAO interestDAO = new InterestDAO(context);
								UserDAO userDAO = new UserDAO(context);
								UserDTO loggedUser = userDAO.queryByEmail(userBean.getEmail());
								ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
								for (String interestStr : interestsStr) {
									InterestDTO interest = interestDAO.queryByID(Integer.parseInt(interestStr));
									if (interest != null) {
										interests.add(interest);
									}
								}
								if (interests.size() == 0) {
									String errorMsg = "No se ha seleccionado ningún tema de interés válido";
									sendCreationErrorMsg(request, response, errorMsg);
								}
								else {
									ThematicAdDAO adDAO = new ThematicAdDAO(context);
									ThematicAdDTO a = AdFactory.createThematicAd(title, loggedUser, content, -1, AdStatus.EDITING, LocalDate.of(1970, 1, 1), interests);
									adDAO.insertThematicAd(a);
									
									sendCreationSuccessMsg(request, response);
								}
								
							}
							
						}
						
						else if (adType.equals(AdType.GENERAL)) {
							GeneralAdDAO adDAO = new GeneralAdDAO(context);
							UserDAO userDAO = new UserDAO(context);
							UserDTO loggedUser = userDAO.queryByEmail(userBean.getEmail());
							GeneralAdDTO a = new GeneralAdDTO(title, loggedUser, content, -1, AdStatus.EDITING, LocalDate.of(1970, 1, 1));
							adDAO.insertGeneralAd(a);
							
							sendCreationSuccessMsg(request, response);
						}
						
						else {
							String errorMsg = "Tipo de anuncio no válido";
							sendCreationErrorMsg(request, response, errorMsg);
						}
						
					}
					
					else {
						String errorMsg = "El anuncio debe tener un título y un contenido";
						sendCreationErrorMsg(request, response, errorMsg);
					}
					
				}
				catch (NumberFormatException e) {
					String errorMsg = "Tipo de anuncio no válido";
					sendCreationErrorMsg(request, response, errorMsg);
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
