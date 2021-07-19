package es.uco.pw.p2.business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Clase que gestiona la información de un usuario
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private LocalDate birthdate;
	
	/**
	 * Constructor
	 * @param id El ID del usuario
	 * @param firstname Nombre del usuario
	 * @param lastname Apellidos del usuario
	 * @param email Email del usuario
	 * @param birthdate Fecha de nacimiento del usuario
	 */
	public UserDTO(int id, String firstname, String lastname, String email, LocalDate birthdate) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthdate = birthdate;
	}
	
	/**
	 * Constructor con password
	 * @param id El ID del usuario
	 * @param firstname Nombre del usuario
	 * @param lastname Apellidos del usuario
	 * @param email Email del usuario
	 * @param birthdate Fecha de nacimiento
	 * @param password Contraseña del usuario
	 */
	public UserDTO(int id, String email, String password, String firstname, String lastname, LocalDate birthdate) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.birthdate = birthdate;
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
	 * Devuelve la contraseña del usuario
	 * @return La contraseña del usuario
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Asigna una contraseña al usuario
	 * @param password La contraseña que se va a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Devuelve la fecha de nacimiento del usuario
	 * @return La fecha de nacimiento del usuario
	 */
	public LocalDate getBirthdate() {
		return birthdate;
	}
	
	/**
	 * Asigna la fecha de nacimiento al usuario
	 * @param birthdate La fecha de nacimiento
	 */
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
}
