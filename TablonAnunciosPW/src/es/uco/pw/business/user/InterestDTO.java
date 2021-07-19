package es.uco.pw.business.user;

import java.io.Serializable;

/**
 * Clase que representa un tema de inter�s
 * @author Jaime Garc�a Arjona
 * @author Sof�a Salas Ruiz
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
	 * @param id ID del inter�s
	 * @param name Nombre del inter�s
	 */
	public InterestDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Devuelve el ID del inter�s
	 * @return El ID del inter�s
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Devuelve el nombre del inter�s
	 * @return El nombre del inter�s
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Asigna el ID del inter�s
	 * @param id ID del inter�s
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Asigna el nombre del inter�s
	 * @param name El nombre del inter�s
	 */
	public void setName(String name) {
		this.name = name;
	}
}
