package es.uco.pw.p2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import es.uco.pw.p2.business.AdFactory;
import es.uco.pw.p2.business.AdStatus;
import es.uco.pw.p2.business.IndividualAdDTO;
import es.uco.pw.p2.business.UserDTO;
import es.uco.pw.p2.business.UserManager;

/**
 * Clase que accede a la base de datos (Anuncios individuales)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class IndividualAdDAO extends AdDAO{

	/**
	 * Inserta un anuncio individualizado en la BD
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public  int insertIndividualAd(IndividualAdDTO a) {
		int status = 0;
		int id = insertAd(a);
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("insert-individual-ad"));
			ps.setInt(1, id);
			ArrayList <UserDTO> users = a.getDestinationUsers();
			for (UserDTO user : users) {
				ps.setInt(2, user.getID());
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
	 * Obtiene los anuncios individuales ordenados por fecha para el usuario
	 * @param user_logged El usuario
	 * @return Anuncios individuales ordenados por fecha para ese usuario
	 */
	public  ArrayList<IndividualAdDTO> getPublishedIndividualAdsSortedByDate(UserDTO user_logged){
		ArrayList<IndividualAdDTO> ads = new ArrayList<IndividualAdDTO>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-published-indiv-ads-by-date"));
			ps.setInt(1, user_logged.getID());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int ad_id = rs.getInt(1);
				PreparedStatement ps1 = con.prepareStatement(getProps().getProperty("get-published-indiv-ads-by-date-2"));
				ps1.setInt(1, ad_id);
				ResultSet rs1 = ps1.executeQuery();
				ArrayList<UserDTO> dest_users = new ArrayList<UserDTO>();
				while(rs1.next()) {
					UserManager um = UserManager.getInstance();
					dest_users.add(um.getUserByID(rs1.getInt(2)));
				}

				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("get-published-indiv-ads-by-date-3"));
				ps2.setInt(1, ad_id);
				ps2.setInt(2, AdStatus.PUBLISHED.ordinal());
				ResultSet rs2 = ps2.executeQuery();

				while (rs2.next()) {
					UserDTO owner = (new UserDAO()).queryByID(rs2.getInt(4));
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
			System.out.println(e);
		}
		close();
		return ads;
	}
	
	/**
	 * Edita un anuncio individual
	 * @param ad_id ID del anuncio
	 * @param title Título del anuncio
	 * @param content Contenido del anuncio
	 * @param dest_users Usuarios destinatarios
	 * @return Status de la BD
	 */
	public  int editAd(int ad_id, String title, String content, ArrayList<UserDTO> dest_users) {
		(new AdDAO()).editAd(ad_id, title, content);
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("edit-indiv-ad"));
			ps.setInt(2, ad_id);
			for (UserDTO user : dest_users) {
				ps.setInt(1, user.getID());
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
