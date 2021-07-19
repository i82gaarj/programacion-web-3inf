package es.uco.pw.business.ad;

import java.io.Serializable;
import java.time.LocalDate;

import es.uco.pw.business.user.UserDTO;

/**
 * Clase que representa un anuncio general
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class GeneralAdDTO extends Ad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id El ID del anuncio
	 * @param title Título del anuncio
	 * @param owner Usuario propietario del anuncio
	 * @param content Cuerpo del anuncio
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicación del anuncio
	 */
	public GeneralAdDTO(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date) {
		super(title, owner, content, id, status, publish_date);
	}

	@Override
	public String getTypeStr() {
		return "general";
	}
	
	@Override
	public AdType getType() {
		return AdType.GENERAL;
	}


}
