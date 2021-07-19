package es.uco.pw.p2.business;

import java.util.ArrayList;

import es.uco.pw.p2.data.InterestDAO;
import es.uco.pw.p2.data.UserDAO;


/**
 * Clase que realiza la gesti�n de los usuarios
 * @author Jaime Garc�a Arjona
 * @author Sof�a Salas Ruiz
 * 
 */
public class UserManager {
	
	private static UserManager instance = null;
	
	private ArrayList<UserDTO> users;
	private ArrayList<InterestDTO> available_interests;
	
	/**
	 * Constructor privado singleton
	 */
	private UserManager() {
		this.loadInterests();
		this.loadUsers();
	}
	
	/**
	 * Devuelve la instancia de la clase
	 * @return La instancia
	 */
	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	/**
	 * Carga los contactos en memoria
	 */
	public void loadUsers() {
		users = (new UserDAO()).getUsers();
	}
	
	/**
	 * Carga los temas de inter�s en memoria
	 */
	public void loadInterests() {
		available_interests = (new InterestDAO()).getInterests();
	}
	
	/**
	 * Devuelve el array de usuarios
	 * @return El array de usuarios
	 */
	public ArrayList<UserDTO> getUsers() {
		return users;
	}

	/**
	 * Busca un usuario por email
	 * @param email El email del contacto a buscar
	 * @return El contacto encontrado, o null si no se encuentra
	 */
	public UserDTO queryByEmail(String email) {
		return (new UserDAO()).queryByEmail(email);
	}
	
	/**
	 * Busca un usuario por nombre y apellidos
	 * @param firstname El nombre del contacto
	 * @param lastname El/los apellido(s) del contacto
	 * @return El contacto encontrado, o null si no lo ha encontrado
	 */
	public UserDTO queryByName(String firstname, String lastname) {
		return (new UserDAO()).queryByName(firstname, lastname);
	}
	
	/**
	 * Busca un usuario por inter�s
	 * @param interest Tema de inter�s
	 * @return Un array de usuarios
	 */
	public ArrayList<UserDTO> queryByInterests(InterestDTO interest){
		ArrayList<UserDTO> users = new ArrayList<UserDTO>();
		for (UserDTO u : users) {
			if (u.getInterests().contains(interest)) {
				users.add(u);
			}
		}
		return users;
	}
	
	/**
	 * Imprime los intereses disponibles
	 */
	public void printInterests() {
		for (InterestDTO i : available_interests) {
			System.out.println(i.getID() + " - " + i.getName());
		}
	}
	
	/**
	 * Devuelve un objeto del tipo Inter�s cuyo ID coincide con el que se pasa como par�metro
	 * @param id El ID del tema de inter�s
	 * @return El objeto Inter�s con ese ID o null si no existe
	 */
	public InterestDTO getInterestByID(int id) {
		for (InterestDTO i : available_interests) {
			if (i.getID() == id) {
				return i;
			}
		}
		return null;
	}
	
	public UserDTO getUserByID(int id) {
		return (new UserDAO()).queryByID(id);
	}

}
