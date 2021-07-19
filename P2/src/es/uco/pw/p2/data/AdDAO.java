package es.uco.pw.p2.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import es.uco.pw.p2.business.Ad;
import es.uco.pw.p2.business.AdDTO;
import es.uco.pw.p2.business.AdFactory;
import es.uco.pw.p2.business.AdType;
import es.uco.pw.p2.business.FlashAdDTO;
import es.uco.pw.p2.business.GeneralAdDTO;
import es.uco.pw.p2.business.IndividualAdDTO;
import es.uco.pw.p2.business.AdStatus;
import es.uco.pw.p2.business.InterestDTO;
import es.uco.pw.p2.business.OwnerSorter;
import es.uco.pw.p2.business.ThematicAdDTO;
import es.uco.pw.p2.business.UserDTO;

/**
 * Clase que accede a la base de datos (Anuncios)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class AdDAO extends DAO {
	
	/**
	 * Inserta un anuncio en la base de datos
	 * @param a El anuncio a insertar
	 * @return El ID del nuevo anuncio insertado
	 */
	public int insertAd(Ad a) {
		int id = 0;
		
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("insert-ad"), PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1,a.getTitle());
			ps.setString(2,a.getContent());
			ps.setInt(3, a.getStatus().ordinal());
			ps.setDate(4, Date.valueOf(a.getPublishDate()));
			ps.setInt(5, a.getOwner().getID());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			
			while (rs.next()) {
				id = rs.getInt(1);
			}
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return id;
	}
	
	/**
	 * Devuelve el número de todos los anuncios publicados
	 * @return El número de todos los anuncios publicados
	 */
	public int getNumberOfPublishedAds() {
		int n_ads = 0;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("number-published-ads"));
			ps.setInt(1, AdStatus.PUBLISHED.ordinal());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				n_ads = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return n_ads;
	}
	
	/**
	 * Devuelve los anuncios publicados que debe ver el usuario logeado ordenados por fecha
	 * @param logged_user El usuario que ha iniciado sesión
	 * @return El tablón de anuncios que debe ver el usuario
	 */
	public ArrayList<Ad> getPublishedAdsSortedByDate(UserDTO logged_user){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		ArrayList<GeneralAdDTO> general_ads = (new GeneralAdDAO()).getPublishedGeneralAdsSortedByDate();
		ArrayList<FlashAdDTO> flash_ads = (new FlashAdDAO()).getPublishedFlashAdsSortedByDate();
		ArrayList<IndividualAdDTO> individual_ads = (new IndividualAdDAO()).getPublishedIndividualAdsSortedByDate(logged_user);
		ArrayList<ThematicAdDTO> thematic_ads = (new ThematicAdDAO()).getPublishedThematicAdsSortedByDate(logged_user);
		ads.addAll(general_ads);
		ads.addAll(flash_ads);
		ads.addAll(individual_ads);
		ads.addAll(thematic_ads);
		return ads;
	}
	
	/**
	 * Devuelve los anuncios publicados que debe ver el usuario logeado ordenados por fecha
	 * @param logged_user El usuario que ha iniciado sesión
	 * @return El tablón de anuncios que debe ver el usuario
	 */
	public ArrayList<Ad> getPublishedAdsSortedByOwner(UserDTO logged_user){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		ArrayList<GeneralAdDTO> general_ads = (new GeneralAdDAO()).getPublishedGeneralAdsSortedByDate();
		ArrayList<FlashAdDTO> flash_ads = (new FlashAdDAO()).getPublishedFlashAdsSortedByDate();
		ArrayList<IndividualAdDTO> individual_ads = (new IndividualAdDAO()).getPublishedIndividualAdsSortedByDate(logged_user);
		ArrayList<ThematicAdDTO> thematic_ads = (new ThematicAdDAO()).getPublishedThematicAdsSortedByDate(logged_user);
		ads.addAll(general_ads);
		ads.addAll(flash_ads);
		ads.addAll(individual_ads);
		ads.addAll(thematic_ads);
		ads.sort(new OwnerSorter());
		return ads;
	}
	
	/**
	 * Devuelve los anuncios publicados que debe ver el usuario logeado ordenados por nombre de usuario
	 * @param logged_user El usuario que ha iniciado sesión
	 * @return El tablón de anuncios que debe ver el usuario
	 */
	public ArrayList<Ad> getNotPublishedAdsOfUser(UserDTO logged_user){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("not-published-ads"));
			ps.setInt(1, AdStatus.PUBLISHED.ordinal());
			ps.setInt(2, logged_user.getID());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				AdStatus status = AdStatus.values()[rs.getInt(6)];
				LocalDate publish_date = rs.getDate(5).toLocalDate();
				AdDTO a = AdFactory.createNonSpecifiedAd(title, logged_user, content, id, status, publish_date);
				ads.add(a);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return ads;
	}
	
	/**
	 * Devuelve un ArrayList con los anuncios publicados por el usuario
	 * @param logged_user El usuario que ha iniciado sesión
	 * @return Los anuncios publicados por el usuario
	 */
	public ArrayList<Ad> getPublishedAdsOfUser(UserDTO logged_user){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("published-ads"));
			ps.setInt(1, AdStatus.PUBLISHED.ordinal());
			ps.setInt(2, logged_user.getID());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				AdStatus status = AdStatus.values()[rs.getInt(5)];
				LocalDate publish_date = rs.getDate(4).toLocalDate();
				AdDTO a = AdFactory.createNonSpecifiedAd(title, logged_user, content, id, status, publish_date);
				ads.add(a);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return ads;
	}
	
	/**
	 * Devuelve verdadero si el usuario NO tiene publicado ningún anuncio (puede tener algunos en edición o archivados)
	 * @param u El usuario
	 * @return true si no tiene anuncios publicados / false si sí los tiene
	 */
	public boolean hasNotPublishedAds(UserDTO u) {
		boolean hasNotPublishedAds = false;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("has-not-published-ads"));
			ps.setInt(1, u.getID());
			ps.setInt(2, AdStatus.PUBLISHED.ordinal());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					hasNotPublishedAds = true;
				}
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return hasNotPublishedAds;
	}
	
	/**
	 * Devuelve un ArrayList con los anuncios publicados en una fecha concreta
	 * @param date La fecha
	 * @return Los anuncios publicados en esa fecha
	 */
	public ArrayList<Ad> queryByDate(LocalDate date){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-by-date"));
			Date date_sql = Date.valueOf(date);
			ps.setDate(1, date_sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int ad_id = rs.getInt(1);
				UserDTO owner = (new UserDAO()).queryByID(rs.getInt(5));
				String title = rs.getString(2);
				String content = rs.getString(3);
				int status = rs.getInt(4);
				Date publish_date_sql = rs.getDate(6);
				LocalDate publish_date = publish_date_sql.toLocalDate();
				AdDTO ad = AdFactory.createNonSpecifiedAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date);
				ads.add(ad);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return ads;
	}
	
	/**
	 * Devuelve un ArrayList de anuncios de un usuario
	 * @param owner El usuario propietario
	 * @return Los anuncios de ese usuario
	 */
	public ArrayList<Ad> queryByOwner(UserDTO owner){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-by-owner"));
			ps.setInt(1, owner.getID());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int ad_id = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				int status = rs.getInt(4);
				Date publish_date_sql = rs.getDate(6);
				LocalDate publish_date = publish_date_sql.toLocalDate();
				AdDTO ad = AdFactory.createNonSpecifiedAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date);
				ads.add(ad);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return ads;
	}
	
	/**
	 * Devuelve los anuncios que tengan asignados los temas de interés que se le pasen como parámetro
	 * @param interests Los temas de interés
	 * @return Los anuncios que tienen asignados esos temas
	 */
	public ArrayList<Ad> queryByInterests(ArrayList<InterestDTO> interests){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		for (InterestDTO i : interests) {
			try {
				Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-by-interests"));
				ps.setInt(1, i.getID());
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					int ad_id = rs.getInt(1);
					PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("query-by-interests-2"));
					ps2.setInt(1, ad_id);
					ResultSet rs2 = ps2.executeQuery();
					while (rs2.next()) {
						String title = rs2.getString(1);
						String content = rs2.getString(2);
						int status = rs2.getInt(4);
						UserDTO owner = (new UserDAO()).queryByID(rs2.getInt(3));
						LocalDate publish_date = rs2.getDate(5).toLocalDate();
						AdDTO ad = AdFactory.createNonSpecifiedAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date);
						ads.add(ad);
					}
					
				}
				
			} catch (SQLException e) {
				close();
				System.out.println(e);
			}
		}
		close();
		return ads;
	}
	
	/**
	 * Archiva un anuncio
	 * @param id El ID del anuncio a archivar
	 * @return Status de la base de datos
	 */
	public int archiveAd(int id) {
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("archive-ad"));
			ps.setInt(1, AdStatus.ARCHIVED.ordinal());
			ps.setInt(2, id);
			status = ps.executeUpdate();
			
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	
	/**
	 * Publica un anuncio
	 * @param id El ID del anuncio a publicar
	 * @return Status de la base de datos
	 */
	public int publishAd(int id) {
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("publish-ad"));
			ps.setInt(1, AdStatus.PUBLISHED.ordinal());
			ps.setInt(2, id);
			status = ps.executeUpdate();
			
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	
	/**
	 * Comprueba si el usuario es propietario de ese anuncio
	 * @param u El usuario
	 * @param ad_id El ID del anuncio
	 * @return true si es propietario / false si no lo es
	 */
	public boolean isOwner(UserDTO u, int ad_id) {
		boolean isOwner = false;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("is-owner"));
			ps.setInt(1, ad_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int user_id = rs.getInt(1);
				if (user_id == u.getID()) {
					isOwner = true;
				}
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return isOwner;
	}
	
	/**
	 * Devuelve los anuncios que tienen como destinatario el usuario que se le pasa como parámetro
	 * @param dest_user El usuario destinatario
	 * @return Los anuncios que van dirigidos a ese usuario
	 */
	public ArrayList<Ad> queryByDestUser(UserDTO dest_user){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-by-dest-user"));
			ps.setInt(1, dest_user.getID());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int ad_id = rs.getInt(1);
				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("query-by-dest-user-2"));
				ps2.setInt(1, ad_id);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					String title = rs2.getString(1);
					String content = rs2.getString(2);
					int status = rs2.getInt(3);
					UserDTO owner = (new UserDAO()).queryByID(rs2.getInt(4));
					LocalDate publish_date = rs2.getDate(5).toLocalDate();
					AdDTO ad = AdFactory.createNonSpecifiedAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date);
					ads.add(ad);
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
	 * Devuelve el anuncio con el ID que se le pasa
	 * @param ad_id El ID del anuncio que se quiere buscar
	 * @return El anuncio que tiene ese ID / null si no existe
	 */
	public Ad queryByID(int ad_id) {
		AdDTO ad = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-ad-by-id"));
			ps.setInt(1, ad_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				int status = rs.getInt(3);
				UserDTO owner = (new UserDAO()).queryByID(rs.getInt(4));
				Date publish_date_sql = rs.getDate(5);
				LocalDate publish_date = publish_date_sql.toLocalDate();
				ad = AdFactory.createNonSpecifiedAd(title, owner, content, ad_id, AdStatus.values()[status], publish_date);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return ad;
	}
	
	/**
	 * Devuelve el tipo de anuncio que es según el ID que se le pasa
	 * @param ad_id El ID del anuncio
	 * @return El tipo de anuncio / null si no existe
	 */
	public AdType getTypeOfAdFromID(int ad_id) {
		AdType type = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-type-of-ad-by-id"));
			ps.setInt(1, ad_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == true) {
				type = AdType.GENERAL;
				return type;
			}
			
			ps = con.prepareStatement(getProps().getProperty("get-type-of-ad-by-id-indiv"));
			ps.setInt(1, ad_id);
			rs = ps.executeQuery();
			if (rs.next() == true) {
				type = AdType.INDIVIDUAL;
				return type;
			}
			
			ps = con.prepareStatement(getProps().getProperty("get-type-of-ad-by-id-thematic"));
			ps.setInt(1, ad_id);
			rs = ps.executeQuery();
			if (rs.next() == true) {
				type = AdType.THEMATIC;
				return type;
			}
			
			ps = con.prepareStatement(getProps().getProperty("get-type-of-ad-by-id-flash"));
			ps.setInt(1, ad_id);
			rs = ps.executeQuery();
			if (rs.next() == true) {
				type = AdType.FLASH;
				return type;
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return type;
	}
	
	/**
	 * Edita un anuncio
	 * @param ad_id ID del anuncio
	 * @param title Nuevo título
	 * @param content Nuevo contenido
	 * @return Status de la BD
	 */
	public int editAd(int ad_id, String title, String content) {
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("edit-ad"));
			ps.setString(1, title);
			ps.setString(2, content);
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
