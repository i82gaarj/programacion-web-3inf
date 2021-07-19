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
import es.uco.pw.business.ad.GeneralAdDTO;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.user.UserDAO;
/**
 * Clase que accede a la base de datos (Anuncios generales)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class GeneralAdDAO extends AdDAO{

	public GeneralAdDAO(ServletContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Inserta un anuncio general
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public  int insertGeneralAd(GeneralAdDTO a) {
		int status = 0;
		int id = insertAd(a);
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("insert-general-ad"));
			ps.setInt(1, id);
			status = ps.executeUpdate();
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	
	/**
	 * Obtiene los anuncios generales ordenados por fecha
	 * @return Los anuncios generales ordenados por fecha
	 */
	public  ArrayList<GeneralAdDTO> getPublishedGeneralAdsSortedByDate(){
		ArrayList<GeneralAdDTO> ads = new ArrayList<GeneralAdDTO>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-general-ads"));
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int ad_id = rs.getInt(1);
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
					GeneralAdDTO a = AdFactory.createGeneralAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date);
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
	 * Edita un anuncio general
	 * @param ad_id ID del anuncio
	 * @param new_title Nuevo título
	 * @param new_content Nuevo contenido
	 * @return Status de la BD
	 */
	public int updateGeneralAd(int ad_id, String new_title, String new_content) {
		return (new AdDAO(context)).updateAd(ad_id, new_title, new_content);
	}
	
}
