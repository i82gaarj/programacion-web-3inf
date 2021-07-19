package es.uco.pw.p2.business;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * Clase que representa un anuncio flash
 * @author Sofía Salas Ruiz
 * @author Jaime García Arjona
 */

public class FlashAdDTO extends Ad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**Fecha a partir de la cual el anuncio aparecerá como publicado*/
	protected LocalDate start_date;
	
	/**Fecha a partir de la cual el anuncio ya no aparecerá como publicado*/
	protected LocalDate end_date;
	
	/**
	 * Constructor
	 * @param id El ID del anuncio
	 * @param owner Usuario propietario del anuncio
	 * @param title Título del anuncio
	 * @param content Contenido del anuncio
	 * @param start_date Fecha de inicio de visibilidad
	 * @param end_date Fecha de fin de visibilidad
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicación del anuncio
	 */
	public FlashAdDTO(String title, UserDTO owner, String content, LocalDate start_date, LocalDate end_date, int id, AdStatus status, LocalDate publish_date) {
		super(title, owner, content, id, status, publish_date);
		this.start_date = start_date;
		this.end_date = end_date;
	}

	
	/**
	 * Devuelve la fecha de inicio asignada de visibilidad al anuncio
	 * @return Fecha de inicio de visibilidad del anuncio
	 */
	public LocalDate getStartDate() {
		return start_date;
	}

	/**
	 * Asigna la fecha de inicio de visibilidad al anuncio
	 * @param start_date La fecha de inicio de visibilidad del anuncio
	 */
	public void setStartDate(LocalDate start_date) {
		this.start_date = start_date;
	}
	
	/**
	 * Devuelve la fecha de fin de visibilidad asignada al anuncio
	 * @return Fecha de fin de visibilidad del anuncio
	 */
	public LocalDate getEndDate() {
		return end_date;
	}

	/**
	 * Asigna la fecha de fin de visibilidad al anuncio
	 * @param end_date La fecha de fin de visibilidad del anuncio
	 */
	public void setEndDate(LocalDate end_date) {
		this.end_date = end_date;
	}

}
