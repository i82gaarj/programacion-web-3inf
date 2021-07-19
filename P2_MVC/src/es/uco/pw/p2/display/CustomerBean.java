package es.uco.pw.p2.display;

import java.io.Serializable;

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
	
	private int loginAttempts = 0;

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
	 * Devuelve el número de intentos utilizados para iniciar sesión
	 * @return El número de intentos utilizados para iniciar sesión
	 */
	public int getLoginAttempts() {
		return loginAttempts;
	}
	/**
	 * Asigna el número de intentos utilizados para iniciar sesión
	 * @param loginAttempts El número de intentos utilizados para iniciar sesión
	 */
	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}
		
}
