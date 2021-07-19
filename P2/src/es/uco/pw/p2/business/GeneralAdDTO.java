package es.uco.pw.p2.business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase que representa un anuncio general
 * @author Jaime Garc�a Arjona
 * @author Sof�a Salas Ruiz
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
	 * @param title T�tulo del anuncio
	 * @param owner Usuario propietario del anuncio
	 * @param content Cuerpo del anuncio
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicaci�n del anuncio
	 */
	public GeneralAdDTO(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date) {
		super(title, owner, content, id, status, publish_date);
	}


}
