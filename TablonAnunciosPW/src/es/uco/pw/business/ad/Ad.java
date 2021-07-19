package es.uco.pw.business.ad;

import java.time.LocalDate;

import es.uco.pw.business.user.UserDTO;

/**
 * Clase abstracta que representa un anuncio
 * @author Sofía Salas Ruiz
 * @author Jaime García Arjona
 */

public abstract class Ad {

	protected int id;
	protected String title;
	protected UserDTO owner;
	protected String content;
	protected AdStatus status;
	protected LocalDate publish_date;
	
	/**
	 * Constructor
	 * @param title Título del anuncio
	 * @param owner Usuario propietario del anuncio
	 * @param content Contenido del anuncio
	 * @param id El ID del anuncio
	 * @param status El estado del anuncio
	 * @param publish_date La fecha de publicación del anuncio
	 */
	public Ad(String title, UserDTO owner, String content, int id, AdStatus status, LocalDate publish_date) {
		this.title = title;
		this.owner = owner;
		this.content = content;
		this.id = id;
		this.status = status;
		this.publish_date = publish_date;
	}

	/**
	 * Devuelve el ID del anuncio
	 * @return El ID del anuncio
	 */
	public int getID() {
		return id;
	}

	/**
	 * Asigna un ID al anuncio
	 * @param id El ID del anuncio
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Devuelve el título del anuncio
	 * @return El título del anuncio
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Asigna un título al anuncio
	 * @param title El título del anuncio
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Devuelve el usuario propietario del anuncio
	 * @return El propietario del anuncio
	 */
	public UserDTO getOwner() {
		return owner;
	}

	/**
	 * Asigna el usuario propietario del anuncio
	 * @param owner El propietario del anuncio
	 */
	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	/**
	 * Devuelve el contenido del anuncio
	 * @return El contenido del anuncio
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Asigna el contenido al anuncio
	 * @param content El contenido del anuncio
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Devuelve la fecha de publicación del anuncio
	 * @return La fecha de publicación del anuncio
	 */
	public LocalDate getPublishDate() {
		return publish_date;
	}

	/**
	 * Asigna la fecha de publicación del anuncio
	 * @param publish_date La fecha de publicación del anuncio
	 */
	public void setPublishDate(LocalDate publish_date) {
		this.publish_date = publish_date;
	}
	
	/**
	 * Devuelve el estado del anuncio
	 * @return El estado del anuncio
	 */
	public AdStatus getStatus() {
		return status;
	}

	/**
	 * Asigna el estado del anuncio
	 * @param status El estado del anuncio
	 */
	public void setStatus(AdStatus status) {
		this.status = status;
	}
	
	/**
	 * Devuelve una cadena con toda la información asignada al objeto anuncio
	 * @return La información del objeto anuncio
	 */
	public String toString() {
		String info = "ID: " + this.id + "\nTitulo: " + this.title + "\nPropietario: " + this.owner.getFirstname() + " " + this.owner.getLastname() + "\nContenido: " + this.content + "\nFecha publicacion: " + this.publish_date;
		return info;
	}
	
	/**
	 * Devuelve una cadena con parte de la información asignada al anuncio
	 * @return La información del objeto anuncio
	 */
	public String toStringReduced() {
		String info = "ID: " + this.id + "\nTitulo: " + this.title + "\nFecha publicacion: " + this.publish_date + "\n";
		return info;
	}

	/**
	 * Devuelve el tipo de anuncio
	 * @return Una string indicando el tipo de anuncio
	 */
	public abstract String getTypeStr();
	
	/**
	 * Devuelve el tipo de anuncio
	 * @return El tipo de anuncio
	 */
	public abstract AdType getType();
	
}
