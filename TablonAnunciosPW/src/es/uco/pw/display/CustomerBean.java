package es.uco.pw.display;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * CustomerBean del usuario
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id = -1;
	private String email = null;
	private String firstname = null;
	private String lastname = null;
	private LocalDate birthdate = null;

	/**
	 * Devuelve el ID del usuario
	 * @return El ID del usuario
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Asigna un ID al usuario
	 * @param id El ID
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Devuelve el email del usuario
	 * @return El email del usuario
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Asigna un email al usuario
	 * @param email El email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Devuelve el nombre del usuario
	 * @return El nombre del usuario
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * Asigna un nombre al usuario
	 * @param firstname El nombre del usuario
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * Devuelve los apellidos del usuario
	 * @return Los apellidos del usuario
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * Asigna los apellidos al usuario
	 * @param lastname Los apellidos
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Devuelve la fecha de nacimiento
	 * @return La fecha de nacimiento
	 */
	public LocalDate getBirthdate() {
		return birthdate;
	}

	/**
	 * Asigna una fecha de nacimiento al usuario
	 * @param birthdate La fecha de nacimiento
	 */
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

}
