package es.uco.pw.data.ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import es.uco.pw.business.ad.AdFactory;
import es.uco.pw.business.ad.AdStatus;
import es.uco.pw.business.ad.ThematicAdDTO;
import es.uco.pw.business.user.InterestDTO;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.user.InterestDAO;
import es.uco.pw.data.user.UserDAO;

/**
 * Clase que accede a la base de datos (Anuncios generales)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class ThematicAdDAO extends AdDAO{

	public ThematicAdDAO(ServletContext context) {
		super(context);
	}

	/**
	 * Inserta un anuncio temático en la BD
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public int insertThematicAd(ThematicAdDTO a) {
		int status = 0;
		int id = insertAd(a);
		ArrayList <InterestDTO> interests = a.getInterests();
		for (InterestDTO i : interests) {
			addInterestToThematicAd(id, i);
		}
		return status;
	}
	
	/**
	 * Obtiene los anuncios temáticos publicados ordenados por fecha para el usuario
	 * @param user_logged El usuario
	 * @return Los anuncios temáticos ordenados por fecha
	 */
	public  ArrayList<ThematicAdDTO> getPublishedThematicAdsSortedByDate(UserDTO user_logged){
		ArrayList<ThematicAdDTO> ads = new ArrayList<ThematicAdDTO>();
		ArrayList<InterestDTO> interestsOfUser = user_logged.getInterests();
		if (interestsOfUser.size() != 0) {
			for (InterestDTO i : interestsOfUser) {
				try {
					Connection con = getConnection();
					PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-thematic-ads-by-interest"));
					ps.setInt(1, i.getID());
					ResultSet rs = ps.executeQuery();
					
					while(rs.next()) {
						int ad_id = rs.getInt(1);
						PreparedStatement ps1 = con.prepareStatement(getProps().getProperty("get-other-interests-of-thematic-ad"));
						ps1.setInt(1, ad_id);
						ResultSet rs1 = ps1.executeQuery();
						ArrayList<InterestDTO> adInterests = new ArrayList<InterestDTO>();
						while(rs1.next()) {
							adInterests.add((new InterestDAO(context)).queryByID(rs1.getInt(1)));
						}
						PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("get-ads-order-publish-date-step-2"));
						ps2.setInt(1, ad_id);
						ps2.setInt(2, AdStatus.PUBLISHED.ordinal());
						ResultSet rs2 = ps2.executeQuery();
						while (rs2.next()) {
							UserDTO owner = (new UserDAO(context)).queryByID(rs2.getInt(4));
							String title = rs2.getString(1);
							String content = rs2.getString(2);
							int status = rs2.getInt(3);
							LocalDate publish_date = rs2.getDate(5).toLocalDate();
							ThematicAdDTO a = AdFactory.createThematicAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date, adInterests);
							ads.add(a);
						}
					}
					
				} catch (SQLException e) {
					close();
					e.printStackTrace();
				}
			}
		}
		close();
		return ads;
	}
	
	/**
	 * Edita un anuncio temático
	 * @param adId El ID del anuncio
	 * @param newTitle Nuevo título
	 * @param newContent Nuevo contenido
	 * @param newInterests Nuevos temas de interés
	 * @return Status de la BD
	 */
	public  int updateThematicAd(int adId, String newTitle, String newContent, ArrayList<InterestDTO> newInterests) {
		int status = 0;
		(new AdDAO(context)).updateAd(adId, newTitle, newContent);
		deleteAllInterestsOfThematicAd(adId);
		for (InterestDTO i : newInterests) {
			addInterestToThematicAd(adId, i);
		}
		close();
		return status;
	}
	
	/**
	 * Borra todos los temas de interés de un anuncio temático
	 * @param adId El ID del anuncio
	 * @return Status de la BD
	 */
	public int deleteAllInterestsOfThematicAd(int adId) {
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("delete-all-interests-of-thematic-ad"));
			ps.setInt(1, adId);
			ps.executeUpdate();
			
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	
	/**
	 * Añade un tema de interés a un anuncio temático
	 * @param adId El ID del anuncio
	 * @param i El tema de interés
	 * @return Status de la BD
	 */
	public int addInterestToThematicAd(int adId, InterestDTO i) {
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("add-interest-thematic-ad"));
			ps.setInt(1, adId);
			ps.setInt(2, i.getID());
			ps.executeUpdate();
			
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	
}
