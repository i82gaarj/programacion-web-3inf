package es.uco.pw.data.ad;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import es.uco.pw.business.ad.Ad;
import es.uco.pw.business.ad.AdFactory;
import es.uco.pw.business.ad.AdStatus;
import es.uco.pw.business.ad.AdType;
import es.uco.pw.business.ad.FlashAdDTO;
import es.uco.pw.business.ad.GeneralAdDTO;
import es.uco.pw.business.ad.IndividualAdDTO;
import es.uco.pw.business.ad.ThematicAdDTO;
import es.uco.pw.business.user.InterestDTO;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.DAO;
import es.uco.pw.data.user.InterestDAO;
import es.uco.pw.data.user.UserDAO;

/**
 * Clase que accede a la base de datos (Anuncios)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class AdDAO extends DAO {
	
	public AdDAO(ServletContext context) {
		super(context);
	}

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
	 * Devuelve los anuncios publicados que debe ver el usuario logeado ordenados por fecha
	 * @param loggedUser El usuario que ha iniciado sesión
	 * @return El tablón de anuncios que debe ver el usuario
	 */
	public ArrayList<Ad> getBoardSortedByDate(UserDTO loggedUser){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		ArrayList<GeneralAdDTO> general_ads = (new GeneralAdDAO(context)).getPublishedGeneralAdsSortedByDate();
		ArrayList<FlashAdDTO> flash_ads = (new FlashAdDAO(context)).getPublishedFlashAdsSortedByDate();
		ArrayList<IndividualAdDTO> individual_ads = (new IndividualAdDAO(context)).getPublishedIndividualAdsSortedByDate(loggedUser);
		ArrayList<ThematicAdDTO> thematic_ads = (new ThematicAdDAO(context)).getPublishedThematicAdsSortedByDate(loggedUser);
		ads.addAll(general_ads);
		ads.addAll(flash_ads);
		ads.addAll(individual_ads);
		ads.addAll(thematic_ads);
		close();
		return ads;
	}
	
	/**
	 * Devuelve los anuncios publicados que debe ver el usuario logeado ordenados por fecha
	 * @param loggedUser El usuario que ha iniciado sesión
	 * @return El tablón de anuncios que debe ver el usuario
	 */
	/*public ArrayList<Ad> getBoardSortedByOwner(UserDTO loggedUser){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		ArrayList<GeneralAdDTO> general_ads = (new GeneralAdDAO(context)).getPublishedGeneralAdsSortedByDate();
		ArrayList<FlashAdDTO> flash_ads = (new FlashAdDAO(context)).getPublishedFlashAdsSortedByDate();
		ArrayList<IndividualAdDTO> individual_ads = (new IndividualAdDAO(context)).getPublishedIndividualAdsSortedByDate(loggedUser);
		ArrayList<ThematicAdDTO> thematic_ads = (new ThematicAdDAO(context)).getPublishedThematicAdsSortedByDate(loggedUser);
		ads.addAll(general_ads);
		ads.addAll(flash_ads);
		ads.addAll(individual_ads);
		ads.addAll(thematic_ads);
		ads.sort(new OwnerSorter());
		return ads;
	}*/
	
	/**
	 * Devuelve los anuncios publicados que debe ver el usuario logeado ordenados por nombre de usuario
	 * @param loggedUser El usuario que ha iniciado sesión
	 * @return El tablón de anuncios que debe ver el usuario
	 */
	public ArrayList<Ad> getNotPublishedAdsOfUser(UserDTO loggedUser){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		try {
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-not-published-ads-of-user"));
	        ps.setInt(1, AdStatus.PUBLISHED.ordinal());
	        ps.setInt(2, loggedUser.getID());
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	int id = rs.getInt(1);
	        	ads.add(queryByID(id, getTypeOfAdFromID(id)));
	        }
	    }catch(SQLException e) {
	        close();
	        System.out.println(e);
	    }
	    close();
		return ads;
	}
	
	/**
	 * Devuelve un ArrayList con los anuncios publicados por el usuario
	 * @param loggedUser El usuario que ha iniciado sesión
	 * @return Los anuncios publicados por el usuario
	 */
	public ArrayList<Ad> getPublishedAdsOfUser(UserDTO loggedUser){
		ArrayList<Ad> ads = new ArrayList<Ad>();
		try {
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-published-ads-of-user"));
	        ps.setInt(1, AdStatus.PUBLISHED.ordinal());
	        ps.setInt(2, loggedUser.getID());
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	int id = rs.getInt(1);
	        	ads.add(queryByID(id, getTypeOfAdFromID(id)));
	        }
	    }catch(SQLException e) {
	        close();
	        System.out.println(e);
	    }
	    close();
		return ads;
	}
	
	/**
	 * Devuelve un ArrayList con los anuncios publicados en un rango de fechas
	 * @param startDate Fecha de inicio
	 * @param endDate Fecha de fin
	 * @return Los anuncios publicados en ese rango de fechas
	 */
	public ArrayList<Ad> queryByDateRange(LocalDate startDate, LocalDate endDate){
		ArrayList<Ad> ads = new ArrayList<Ad>();
	    try {
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-ads-date-range"));
	        ps.setDate(1, Date.valueOf(startDate));
	        ps.setDate(2, Date.valueOf(endDate));
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	int id = rs.getInt(1);
	        	ads.add(queryByID(id, getTypeOfAdFromID(id)));
	        }
	    }catch(SQLException e) {
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
	/*public ArrayList<Ad> queryByOwner(UserDTO owner){
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
	}*/
	
	/**
	 * Devuelve los anuncios que tengan asignados los temas de interés que se le pasen como parámetro
	 * @param interests Los temas de interés
	 * @return Los anuncios que tienen asignados esos temas
	 */
	/*public ArrayList<Ad> queryByInterests(ArrayList<InterestDTO> interests){
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
						String title = rs2.getString(2);
						String content = rs2.getString(3);
						int status = rs2.getInt(4);
						UserDTO owner = (new UserDAO(context)).queryByID(rs.getInt(5));
						LocalDate publish_date = rs2.getDate(6).toLocalDate();
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
	}*/
	
	/**
	 * Asigna un estado a un anuncio
	 * @param id El ID del anuncio
	 * @param status El estado del anuncio
	 * @return Status de la base de datos
	 */
	public int setAdStatus(int id, AdStatus status) {
		int bdstatus = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("set-ad-status"));
			ps.setInt(1, status.ordinal());
			ps.setInt(2, id);
			bdstatus = ps.executeUpdate();
			
		} catch(SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return bdstatus;
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
	/*public ArrayList<Ad> queryByDestUser(UserDTO dest_user){
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
					UserDTO owner = (new UserDAO(context)).queryByID(rs2.getInt(4));
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
	}*/
	
	/**
	 * Devuelve el anuncio con el ID que se le pasa
	 * @param ad_id El ID del anuncio que se quiere buscar
	 * @param adType El tipo de anuncio
	 * @return El anuncio que tiene ese ID / null si no existe
	 */
	public Ad queryByID(int ad_id, AdType adType) {
		Ad ad = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-ad-by-id"));
			ps.setInt(1, ad_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				int status = rs.getInt(3);
				UserDTO owner = (new UserDAO(context)).queryByID(rs.getInt(4));
				Date publishDate_sql = rs.getDate(5);
				LocalDate publishDate = publishDate_sql.toLocalDate();
				if (adType.equals(AdType.GENERAL)) {
					ad = AdFactory.createGeneralAd(title, owner, content, ad_id, AdStatus.values()[status], publishDate);
				}
				else if (adType.equals(AdType.FLASH)) {
					PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("get-dates-of-flash-ad"));
					ps2.setInt(1, ad_id);
					ResultSet rs2 = ps2.executeQuery();
					if (rs2.next()) {
						LocalDate startDate = rs2.getDate(1).toLocalDate();
						LocalDate endDate = rs2.getDate(2).toLocalDate();
						ad = AdFactory.createFlashAd(title, owner, content, ad_id, AdStatus.values()[status], publishDate, startDate, endDate);
					}
					
				}
				else if (adType.equals(AdType.INDIVIDUAL)) {
					PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("get-other-dest-users-of-indiv-ad"));
					ps2.setInt(1, ad_id);
					ResultSet rs2 = ps2.executeQuery();
					ArrayList<UserDTO> destUsers = new ArrayList<UserDTO>();
					while (rs2.next()) {
						int destUserID = rs2.getInt(1);
						UserDAO userDAO = new UserDAO(context);
						UserDTO destUser = userDAO.queryByID(destUserID);
						destUsers.add(destUser);
					}
					ad = AdFactory.createIndividualAd(title, owner, content, ad_id, AdStatus.values()[status], publishDate, destUsers);
				}
				else if (adType.equals(AdType.THEMATIC)) {
					PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("get-other-interests-of-thematic-ad"));
					ps2.setInt(1, ad_id);
					ResultSet rs2 = ps2.executeQuery();
					ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
					while (rs2.next()) {
						int interestID = rs2.getInt(1);
						InterestDAO interestDAO = new InterestDAO(context);
						InterestDTO interest = interestDAO.queryByID(interestID);
						interests.add(interest);
					}
					ad = AdFactory.createThematicAd(title, owner, content, ad_id, AdStatus.values()[status], publishDate, interests);
				}	
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
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-type-of-ad-by-id-general"));
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
	public int updateAd(int ad_id, String title, String content) {
		int status = 0;
		try{
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("update-ad"));
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
	
	/**
	 * Elimina un anuncio de la base de datos
	 * @param id El ID del anuncio
	 * @return Status de la BD
	 */
	public int deleteAd(int id) {
        int status = 0;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(getProps().getProperty("delete-ad"));
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
	 * Pasa un anuncio de estado Archivado a Editando
	 * @param id El ID del anuncio
	 * @return Status de la BD
	 */
	public int archiveToEditingAd(int id) {
	    int status = 0;
	    try {
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(getProps().getProperty("set-ad-status"));
	        ps.setInt(1, AdStatus.EDITING.ordinal());
	        ps.setInt(2, id);
	        status = ps.executeUpdate();
	    }catch(SQLException e) {
	        close();
	        System.out.println(e);
	    }
	    close();
	    return status;
	}
	
	public ArrayList<Ad> queryByTitle(String title){
		ArrayList<Ad> ads = new ArrayList<Ad>();
	    try {
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-ad-by-title"));
	        ps.setString(1, "%" + title + "%");
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	int id = rs.getInt(1);
	        	ads.add(queryByID(id, getTypeOfAdFromID(id)));
	        }
	    }catch(SQLException e) {
	        close();
	        System.out.println(e);
	    }
	    close();
		return ads;
	}
}
