package es.uco.pw.p2.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import es.uco.pw.p2.business.AdFactory;
import es.uco.pw.p2.business.AdStatus;
import es.uco.pw.p2.business.FlashAdDTO;
import es.uco.pw.p2.business.UserDTO;
/**
 * Clase que accede a la base de datos (Anuncios flash)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class FlashAdDAO extends AdDAO{
	
	/**
	 * Inserta un anuncio de tipo Flash en la base de datos
	 * @param a El anuncio a insertar
	 * @return Status de la base de datos
	 */
	public int insertFlashAd(FlashAdDTO a) {
		int status = 0;
		int id = insertAd(a);
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("insert-flash-ad"));
			ps.setInt(1, id);
			ps.setDate(2, Date.valueOf(a.getStartDate()));
			ps.setDate(3, Date.valueOf(a.getEndDate()));
			status = ps.executeUpdate();
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	
	/**
	 * Obtiene los anuncios de tipo flash ordenados por fecha
	 * @return Los anuncios de tipo flash ordenados por fecha
	 */
	public ArrayList<FlashAdDTO> getPublishedFlashAdsSortedByDate(){
		ArrayList<FlashAdDTO> ads = new ArrayList<FlashAdDTO>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-published-flash-ads-by-date"));
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int ad_id = rs.getInt(1);
				LocalDate start_date = rs.getDate(2).toLocalDate();
				LocalDate end_date = rs.getDate(3).toLocalDate();
				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("get-published-flash-ads-by-date-2"));
				ps2.setInt(1, ad_id);
				ps2.setInt(2, AdStatus.PUBLISHED.ordinal());
				ResultSet rs2 = ps2.executeQuery();

				while (rs2.next()) {
					UserDTO owner = (new UserDAO()).queryByID(rs2.getInt(5));
					String title = rs2.getString(2);
					String content = rs2.getString(3);
					int status = rs2.getInt(4);
					LocalDate publish_date = rs.getDate(5).toLocalDate();
					FlashAdDTO a = AdFactory.createFlashAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date, start_date, end_date);
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
	 * Edita un anuncio de tipo flash
	 * @param ad_id El ID del anuncio
	 * @param new_title Nuevo título
	 * @param new_content Nuevo contenido
	 * @param new_start_date Nueva fecha de inicio
	 * @param new_end_date Nueva fecha de fin
	 * @return Status de la BD
	 */
	public int editAd(int ad_id, String new_title, String new_content, LocalDate new_start_date, LocalDate new_end_date) {
		(new AdDAO()).editAd(ad_id, new_title, new_content);
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("edit-flash-ad"));
			ps.setDate(1, Date.valueOf(new_start_date));
			ps.setDate(2, Date.valueOf(new_end_date));
			ps.setInt(3, ad_id);
			status = ps.executeUpdate();
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
}
