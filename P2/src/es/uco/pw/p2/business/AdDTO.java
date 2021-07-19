package es.uco.pw.p2.business;

import java.io.Serializable;
import java.time.LocalDate;

public class AdDTO extends Ad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param title Título del anuncio
	 * @param owner Usuario propietario del anuncio
	 * @param content Contenido del anuncio
	 * @param id El ID del anuncio
	 * @param status El estado del anuncio
	 * @param publish_date La fecha de publicación del anuncio
	 */
	public AdDTO(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date) {
		super(title, owner, content, id, status, publish_date);
	}

}
