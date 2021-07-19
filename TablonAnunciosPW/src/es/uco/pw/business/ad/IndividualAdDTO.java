package es.uco.pw.business.ad;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import es.uco.pw.business.user.UserDTO;

/**
 * Clase que representa un anuncio individualizado
 * @author Sofía Salas Ruiz
 * @author Jaime García Arjona
 */

public class IndividualAdDTO extends Ad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ArrayList<UserDTO> dest_users = new ArrayList<UserDTO>();
	
	/**
	 * Constructor
	 * @param id El ID del anuncio
	 * @param title El título del anuncio
	 * @param owner Usuario propietario del anuncio
	 * @param content Contenido del anuncio
	 * @param dest_users Usuarios destinatarios
	 * @param status Estado del anuncio
	 * @param publish_date Fecha de publicación del anuncio
	 */
	public IndividualAdDTO(String title, UserDTO owner, String content, ArrayList<UserDTO> dest_users, int id, AdStatus status, LocalDate publish_date) {
		super(title, owner, content, id, status, publish_date);
		this.dest_users = dest_users;
	}
	
	/**
	 * Devuelve los usuarios destinatarios
	 * @return Los usuarios destinatarios
	 */
	public ArrayList<UserDTO> getDestinationUsers(){
		return dest_users;
	}
	/**
	 * Asigna los usuarios destinatarios
	 * @param dest_users Los usuarios destinatarios
	 */
	public void setDestinationUsers(ArrayList<UserDTO> dest_users) {
		this.dest_users = dest_users;
	}
	
	@Override
	public String getTypeStr() {
		return "individualizado";
	}
	
	@Override
	public AdType getType() {
		return AdType.INDIVIDUAL;
	}


}
