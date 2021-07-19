package es.uco.pw.p2.business;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase Factoría que realiza la creación de los diferentes tipos de anuncios
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class AdFactory {
	
	/**
	 * Crea un anuncio de tipo General
	 * @param title Título del anuncio
	 * @param owner Propietario del anuncio
	 * @param content Contenido del anuncio
	 * @param id ID del anuncio
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicación del anuncio
	 * @return Un anuncio de tipo general
	 */
	public static GeneralAdDTO createGeneralAd(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date) {
		return new GeneralAdDTO(title, owner, content, id, status, publish_date);
	}
	
	/**
	 * Crea un anuncio de tipo Flash
	 * @param title Título del anuncio
	 * @param owner Propietario del anuncio
	 * @param content Contenido del anuncio
	 * @param start_date Fecha de inicio de visibilidad
	 * @param end_date Fecha de fin de visibilidad
	 * @param id ID del anuncio
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicación del anuncio
	 * @return Un anuncio de tipo Flash
	 */
	public static FlashAdDTO createFlashAd(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date, LocalDate start_date, LocalDate end_date) {
		return new FlashAdDTO(title, owner, content, start_date, end_date, id, status, publish_date);
	}
	
	/**
	 * Crea un anuncio de tipo Individual
	 * @param title Título del anuncio
	 * @param owner Propietario del anuncio
	 * @param content Contenido del anuncio
	 * @param dest_users Usuarios destinatarios del anuncio
	 * @param id ID del anuncio
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicación del anuncio
	 * @return Un anuncio de tipo Individual
	 */
	public static IndividualAdDTO createIndividualAd(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date, ArrayList<UserDTO> dest_users) {
		return new IndividualAdDTO(title, owner, content, dest_users, id, status, publish_date);
	}
	
	/**
	 * Crea un anuncio de tipo Temático
	 * @param title Título del anuncio
	 * @param owner Propietario del anuncio
	 * @param content Contenido del anuncio
	 * @param interests Conjunto de temas de interés que se aplican al anuncio
	 * @param id ID del anuncio
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicación del anuncio
	 * @return Un anuncio de tipo Temático
	 */
	public static ThematicAdDTO createThematicAd(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date, ArrayList<InterestDTO> interests) {
		return new ThematicAdDTO(title, owner, content, interests, id, status, publish_date);
	}
	
	/**
	 * Crea un DTO Anuncio que no tiene especificado el tipo
	 * @param title Título del anuncio
	 * @param owner Propietario del anuncio
	 * @param content Contenido del anuncio
	 * @param id ID del anuncio
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicación del anuncio
	 * @return Un DTO Anuncio
	 */
	public static AdDTO createNonSpecifiedAd(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date) {
		return new AdDTO(title, owner, content, id, status, publish_date);
	}
}
