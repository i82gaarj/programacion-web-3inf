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
import es.uco.pw.business.ad.IndividualAdDTO;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.user.UserDAO;

/**
 * Clase que accede a la base de datos (Anuncios individuales)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class IndividualAdDAO extends AdDAO{

	public IndividualAdDAO(ServletContext context) {
		super(context);
	}

	/**
	 * Inserta un anuncio individualizado en la BD
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public  int insertIndividualAd(IndividualAdDTO a) {
		int status = 0;
		int id = insertAd(a);
		ArrayList <UserDTO> users = a.getDestinationUsers();
		for (UserDTO user : users) {
			status = addDestUserToIndividualAd(id, user);
		}
		return status;
	}
	
	/**
	 * Obtiene los anuncios individuales ordenados por fecha para el usuario
	 * @param user_logged El usuario
	 * @return Anuncios individuales ordenados por fecha para ese usuario
	 */
	public  ArrayList<IndividualAdDTO> getPublishedIndividualAdsSortedByDate(UserDTO user_logged){
		ArrayList<IndividualAdDTO> ads = new ArrayList<IndividualAdDTO>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-indiv-ads-by-dest-user"));
			ps.setInt(1, user_logged.getID());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int ad_id = rs.getInt(1);
				PreparedStatement ps1 = con.prepareStatement(getProps().getProperty("get-other-dest-users-of-indiv-ad"));
				ps1.setInt(1, ad_id);
				ResultSet rs1 = ps1.executeQuery();
				ArrayList<UserDTO> dest_users = new ArrayList<UserDTO>();
				while(rs1.next()) {
					dest_users.add((new UserDAO(context)).queryByID(rs1.getInt(1)));
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
					IndividualAdDTO a = AdFactory.createIndividualAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date, dest_users);
					ads.add(a);
				}
			}
			
		} catch (SQLException e) {
			close();
			e.printStackTrace();
		}
		close();
		return ads;
	}
	
	/**
	 * Edita un anuncio individualizado
	 * @param adId El ID del anuncio
	 * @param newTitle Nuevo título
	 * @param newContent Nuevo contenido
	 * @param newDestUsers Los nuevos usuarios destinatarios
	 * @return Status de la BD
	 */
	public int updateIndividualAd(int adId, String newTitle, String newContent, ArrayList<UserDTO> newDestUsers) {
		int status = 0;
		(new AdDAO(context)).updateAd(adId, newTitle, newContent);
		deleteAllDestUsersOfIndividualAd(adId);
		for (UserDTO u : newDestUsers) {
			addDestUserToIndividualAd(adId, u);
		}
		close();
		return status;
	}
	
	/**
	 * Borra todos los usuarios destinatarios de un anuncio individual
	 * @param adId El ID del anuncio
	 * @return Status de la BD
	 */
	public int deleteAllDestUsersOfIndividualAd(int adId) {
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("delete-all-dest-users-of-indiv-ad"));
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
	 * Añade un usuario destinatario a un anuncio individual
	 * @param adId El ID del anuncio
	 * @param user El usuario destinatario
	 * @return Status de la BD
	 */
	public int addDestUserToIndividualAd(int adId, UserDTO user) {
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("add-dest-user-indiv-ad"));
			ps.setInt(1, adId);
			ps.setInt(2, user.getID());
			ps.executeUpdate();
			
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	
}
