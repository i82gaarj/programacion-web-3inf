package es.uco.pw.p2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import es.uco.pw.p2.business.AdFactory;
import es.uco.pw.p2.business.AdStatus;
import es.uco.pw.p2.business.InterestDTO;
import es.uco.pw.p2.business.ThematicAdDTO;
import es.uco.pw.p2.business.UserDTO;
import es.uco.pw.p2.business.UserManager;

/**
 * Clase que accede a la base de datos (Anuncios generales)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class ThematicAdDAO extends AdDAO{
	
	/**
	 * Inserta un anuncio temático en la BD
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public  int insertThematicAd(ThematicAdDTO a) {
		int status = 0;
		int id = insertAd(a);
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("insert-thematic-ad"));
			ps.setInt(1, id);
			ArrayList <InterestDTO> interests = a.getInterests();
			for (InterestDTO i : interests) {
				ps.setInt(2, i.getID());
				status = ps.executeUpdate();
			}
			
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
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
					PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-published-thematic-ads-by-date"));
					ps.setInt(1, i.getID());
					ResultSet rs = ps.executeQuery();
					
					while(rs.next()) {
						int ad_id = rs.getInt(1);
						PreparedStatement ps1 = con.prepareStatement(getProps().getProperty("get-published-thematic-ads-by-date-1"));
						ps1.setInt(1, ad_id);
						ResultSet rs1 = ps1.executeQuery();
						ArrayList<InterestDTO> adInterests = new ArrayList<InterestDTO>();
						while(rs1.next()) {
							UserManager um = UserManager.getInstance();
							adInterests.add(um.getInterestByID(rs1.getInt(2)));
						}
						PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("get-published-thematic-ads-by-date-2"));
						ps2.setInt(1, ad_id);
						ps2.setInt(2, AdStatus.PUBLISHED.ordinal());
						ResultSet rs2 = ps2.executeQuery();
						while (rs2.next()) {
							UserDTO owner = (new UserDAO()).queryByID(rs2.getInt(4));
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
					System.out.println(e);
				}
			}
		}
		close();
		return ads;
	}
	
	/**
	 * Edita un anuncio temático
	 * @param ad_id El ID del anuncio
	 * @param new_title Nuevo título
	 * @param new_content Nuevo contenido
	 * @param new_interests Nuevos temas de interés
	 * @return Status de la BD
	 */
	public  int editAd(int ad_id, String new_title, String new_content, ArrayList<InterestDTO> new_interests) {
		int status = 0;
		(new AdDAO()).editAd(ad_id, new_title, new_content);
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("edit-thematic-ad"));
			ps.setInt(2, ad_id);
			for (InterestDTO i : new_interests) {
				ps.setInt(1, i.getID());
				status = ps.executeUpdate();
			}
			
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
}
