package es.uco.pw.business.user;

import java.io.Serializable;

/**
 * Clase que representa un tema de interés
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class InterestDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	
	/**
	 * Constructor
	 * @param id ID del interés
	 * @param name Nombre del interés
	 */
	public InterestDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Devuelve el ID del interés
	 * @return El ID del interés
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Devuelve el nombre del interés
	 * @return El nombre del interés
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Asigna el ID del interés
	 * @param id ID del interés
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Asigna el nombre del interés
	 * @param name El nombre del interés
	 */
	public void setName(String name) {
		this.name = name;
	}
}
