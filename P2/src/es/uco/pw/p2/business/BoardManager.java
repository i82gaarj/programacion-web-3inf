package es.uco.pw.p2.business;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import es.uco.pw.p2.data.AdDAO;
import es.uco.pw.p2.data.FlashAdDAO;
import es.uco.pw.p2.data.GeneralAdDAO;
import es.uco.pw.p2.data.IndividualAdDAO;
import es.uco.pw.p2.data.InterestDAO;
import es.uco.pw.p2.data.ThematicAdDAO;

/**
 * Clase que realiza la gestión de los anuncios
 * @author Sofía Salas Ruiz
 * @author Jaime Garcia Arjona
 */

public class BoardManager{

	/** Instancia singleton */
	private static BoardManager instance = null;
	
	/**
	 * Devuelve la instancia de la clase
	 * @return La instancia
	 */
	public static BoardManager getInstance() {
		if (instance == null) {
			instance = new BoardManager();
		}
		return instance;
	}

	/**
	 * Publica un anuncio
	 * @param id El ID del anuncio
	 * @return Status de la BD
	 */
	public int publishAd(int id) {
		return (new AdDAO()).publishAd(id);
	}
	
	/**
	 * Archiva un anuncio
	 * @param id El ID del anuncio
	 * @return Status de la BD
	 */
	public int archiveAd(int id) {
		return (new AdDAO()).archiveAd(id);
	}
	
	/**
	 * Comprueba si el usuario es propietario de ese anuncio
	 * @param u El usuario
	 * @param ad_id El ID del anuncio
	 * @return true si es propietario / false si no lo es
	 */
	public boolean isOwner(UserDTO u, int ad_id) {
		return (new AdDAO()).isOwner(u, ad_id);
	}

	/**
	 * Obtiene los anuncios con la fecha de publicación que se le pasa como parámetro
	 * @param date La fecha
	 * @return Los anuncios que fueron publicados en esa fecha
	 */
	public ArrayList<Ad> findAdsByDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		return (new AdDAO()).queryByDate(LocalDate.parse(date, formatter));
	}

	/**
	 * Obtiene los anuncios cuyo propietario coincide con el que se pasa como parámetro
	 * @param owner El usuario
	 * @return Los anuncios cuyo propietario coincide con el que se pasa como parámetro
	 */
	public ArrayList<Ad> findAdsByOwner(UserDTO owner) {
		return (new AdDAO()).queryByOwner(owner);
	}
	
	/**
	 * Obtiene los anuncios por tema de interés
	 * @param interests Temas de interés
	 * @return Los anuncios que son de esos temas
	 */
	public ArrayList<Ad> findAdsByInterest(ArrayList<InterestDTO> interests) {
		return (new AdDAO()).queryByInterests(interests);
	}
	
	/**
	 * Obtiene los anuncios que tienen como destinatario el que se pasa como parámetro
	 * @param dest_user El usuario destinatario
	 * @return Los anuncios que van dirigidos a ese usuario
	 */
	public ArrayList<Ad> findAdsByDestinationUser(UserDTO dest_user) {
		return (new AdDAO()).queryByDestUser(dest_user);
	}
	
	/**
	 * Devuelve verdadero si el usuario NO tiene publicado ningún anuncio (puede tener algunos en edición o archivados)
	 * @param logged El usuario
	 * @return true si no tiene anuncios publicados / false si sí los tiene
	 */
	public boolean hasNotPublishedAds(UserDTO logged) {
		return (new AdDAO()).hasNotPublishedAds(logged);
	}
	
	/**
	 * Añade un tema de interés al usuario
	 * @param u El usuario
	 * @param i El tema de interés
	 * @return Status de la BD
	 */
	public int addInterestToUser(UserDTO u, InterestDTO i) {
		return (new InterestDAO()).addInterestToUser(u, i);
	}
	
	/**
	 * Borra todos los temas de interés de un usuario
	 * @param u El usuario
	 * @return Status de la BD
	 */
	public int deleteAllInterestsFromUser(UserDTO u) {
		return (new InterestDAO()).deleteAllInterestsFromUser(u);
	}
	
	/**
	 * Guarda un anuncio general en la BD
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public int saveGeneralAd(GeneralAdDTO a) {
		return (new GeneralAdDAO()).insertGeneralAd(a);
	}
	
	/**
	 * Guarda un anuncio individual en la BD
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public int saveIndividualAd(IndividualAdDTO a) {
		return (new IndividualAdDAO()).insertIndividualAd(a);
	}
	
	/**
	 * Guarda un anuncio flash en la BD
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public int saveFlashAd(FlashAdDTO a) {
		return (new FlashAdDAO()).insertFlashAd(a);
	}
	
	/**
	 * Guarda un anuncio temático en la BD
	 * @param a El anuncio
	 * @return Status de la BD
	 */
	public int saveThematicAd(ThematicAdDTO a) {
		return (new ThematicAdDAO()).insertThematicAd(a);
	}
	
	/**
	 * Devuelve el anuncio con el ID que se le pasa
	 * @param ad_id El ID del anuncio que se quiere buscar
	 * @return El anuncio que tiene ese ID / null si no existe
	 */
	public Ad search(int ad_id) {
		return (new AdDAO()).queryByID(ad_id);
	}
	
	/**
	 * Devuelve el tipo de anuncio que es según el ID que se le pasa
	 * @param ad_id El ID del anuncio
	 * @return El tipo de anuncio / null si no existe
	 */
	public AdType getTypeOfAdByID(int ad_id) {
		return (new AdDAO()).getTypeOfAdFromID(ad_id);
	}
	
	/**
	 * Edita un anuncio general
	 * @param ad_id ID del anuncio
	 * @param new_title Nuevo título
	 * @param new_content Nuevo contenido
	 * @return Status de la BD
	 */
	public int editAd(int ad_id, String new_title, String new_content) {
		return (new GeneralAdDAO()).editAd(ad_id, new_title, new_content);
	}
	
	/**
	 * Edita un anuncio individual
	 * @param ad_id ID del anuncio
	 * @param new_title Título del anuncio
	 * @param new_content Contenido del anuncio
	 * @param new_dest_users Usuarios destinatarios
	 * @return Status de la BD
	 */
	public int editAd(int ad_id, String new_title, String new_content, ArrayList<UserDTO> new_dest_users) {
		return (new IndividualAdDAO()).editAd(ad_id, new_title, new_content, new_dest_users);
	}
	
	/**
	 * Edita un anuncio temático
	 * @param ad_id El ID del anuncio
	 * @param new_title Nuevo título
	 * @param new_content Nuevo contenido
	 * @param new_interests Nuevos temas de interés
	 * @return Status de la BD
	 */
	public int editThematicAd(int ad_id, String new_title, String new_content, ArrayList<InterestDTO> new_interests) {
		return (new ThematicAdDAO()).editAd(ad_id, new_title, new_content, new_interests);
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
		return (new FlashAdDAO()).editAd(ad_id, new_title, new_content, new_start_date, new_end_date);
	}

	/**
	 * Imprime los anuncios en orden descendiente de fecha de publicación
	 * @param loggedUser Usuario al que se le muestran los anuncios
	 */
	public void showAdsByDate(UserDTO loggedUser) {
		if((new AdDAO()).getNumberOfPublishedAds() != 0) {
			ArrayList<Ad> ads = (new AdDAO()).getPublishedAdsSortedByDate(loggedUser);
			for(Ad a : ads) {
				AdStatus status = a.getStatus();
				if (status != AdStatus.ARCHIVED && status != AdStatus.EDITING) {
					if (a instanceof IndividualAdDTO && ((IndividualAdDTO) a).getDestinationUsers().contains(loggedUser)) {
						System.out.println(a.toString());
					}
					else if (a instanceof ThematicAdDTO && !Collections.disjoint(((ThematicAdDTO) a).getInterests(), loggedUser.getInterests())) {
						System.out.println(a.toString());
					}
					else if (a instanceof FlashAdDTO && ( ((FlashAdDTO) a).getStartDate().isBefore(LocalDate.now()) && ((FlashAdDTO) a).getEndDate().isAfter(LocalDate.now()) )) {
						System.out.println(a.toString());
					}
					else {
						System.out.println(a.toString());
					}
					System.out.println("\n----------------\n");
				}
			}
			System.out.println("\n----------------\n");
		}
		else {
			System.out.println("No hay anuncios publicados");
		}
	}

	/**
	 * Imprime los anuncios en orden ascendente de nombre de usuario
	 * @param loggedUser Usuario al que se le muestran los anuncios
	 */
	public void showAdsByOwner(UserDTO loggedUser) {
		if((new AdDAO()).getNumberOfPublishedAds() != 0) {
			ArrayList<Ad> ads = (new AdDAO()).getPublishedAdsSortedByOwner(loggedUser);
			for(Ad a : ads) {
				AdStatus status = a.getStatus();
				if (status != AdStatus.ARCHIVED && status != AdStatus.EDITING) {
					if (a instanceof IndividualAdDTO && ((IndividualAdDTO) a).getDestinationUsers().contains(loggedUser)) {
						System.out.println(a.toString());
					}
					else if (a instanceof ThematicAdDTO && !Collections.disjoint(((ThematicAdDTO) a).getInterests(), loggedUser.getInterests())) {
						System.out.println(a.toString());
					}
					else if (a instanceof FlashAdDTO && ( ((FlashAdDTO) a).getStartDate().isBefore(LocalDate.now()) && ((FlashAdDTO) a).getEndDate().isAfter(LocalDate.now()) )) {
						System.out.println(a.toString());
					}
					else {
						System.out.println(a.toString());
					}

				}
				System.out.println("\n----------------\n");
			}
		}
		else {
			System.out.println("No hay anuncios publicados");
		}
		System.out.println("\n----------------\n");
	}
	
	/**
	 * Muestra los anuncios del usuario que no se han publicado
	 * @param logged El usuario
	 */
	public void showNotPublishedAds(UserDTO logged) {
		ArrayList<Ad> ads = (new AdDAO()).getNotPublishedAdsOfUser(logged);
		if (ads != null) {
			for(Ad a : ads) {
				System.out.println(a.toStringReduced());
			}
		}
	}
	
	/**
	 * Muestra los anuncios del usuario que se han publicado
	 * @param logged El usuario
	 */
	public void showPublishedAds(UserDTO logged) {
		ArrayList<Ad> ads = (new AdDAO()).getPublishedAdsOfUser(logged);
		if (ads != null) {
			for(Ad a : ads) {
				System.out.println(a.toStringReduced());
			}
		}
	}
}
