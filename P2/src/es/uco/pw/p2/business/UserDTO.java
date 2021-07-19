package es.uco.pw.p2.business;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Clase que gestiona la información de un usuario
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
public class UserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private ArrayList<InterestDTO> interests;
	
	/**
	 * Constructor
	 * @param id El ID del usuario
	 * @param firstname Nombre del contacto
	 * @param lastname Apellidos del contacto
	 * @param email Email del contacto
	 * @param interests Temas de interés del contacto
	 */
	public UserDTO(int id, String firstname, String lastname, String email, ArrayList<InterestDTO> interests) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.interests = interests;
	}
	
	/**
	 * Constructor sin intereses
	 * @param id El ID del usuario
	 * @param firstname Nombre del usuario
	 * @param lastname Apellidos del usuario
	 * @param email Email del usuario
	 */
	public UserDTO(int id, String firstname, String lastname, String email) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.interests = null;
	}
	
	/**
	 * Devuelve el ID del usuario
	 * @return El ID del usuario
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Asigna el ID al usuario
	 * @param id El ID que se va a asignar al usuario
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Devuelve el nombre del contacto
	 * @return El nombre del contacto
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * Devuelve los apellidos del contacto
	 * @return Los apellidos del contacto
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Devuelve el email del contacto
	 * @return El email del contacto
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Devuelve los temas de interés del contacto
	 * @return Los temas de interés del contacto
	 */
	public ArrayList<InterestDTO> getInterests() {
		return interests;
	}
	
	/**
	 * Asigna un nombre al contacto
	 * @param firstname El nombre del contacto
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * Asigna los apellidos al contacto
	 * @param lastname Los apellidos del contacto
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Asigna el email al contacto
	 * @param email El email del contacto
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Asigna una lista de intereses al contacto
	 * @param interests Lista de temas de interés
	 */
	public void setInterests(ArrayList<InterestDTO> interests) {
		this.interests = interests;
	}
	
}
