package es.uco.pw.p2.business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que representa un anuncio tem�tico
 * @author Sof�a Salas Ruiz
 * @author Jaime Garc�a Arjona
 */

public class ThematicAdDTO extends Ad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<InterestDTO> interests;
	
	
	/**
	 * Constructor
	 * @param id El ID del anuncio
	 * @param title T�tulo del anuncio
	 * @param owner Usuario propietario del anuncio
	 * @param content Cuerpo del anuncio
	 * @param interests Temas de inter�s
	 * @param publish_date Fecha de publicaci�n del anuncio
	 * @param status Estado del anuncio
	 */
	public ThematicAdDTO(String title, UserDTO owner, String content, ArrayList<InterestDTO> interests, int id, AdStatus status, LocalDate publish_date) {
		super(title, owner, content, id, status, publish_date);
		this.interests = interests;
	}
	
	/**
	 * Devuelve un ArrayList con los temas de inter�s asignados al anuncio
	 * @return Temas de inter�s del anuncio
	 */
	public ArrayList<InterestDTO> getInterests() {
		return interests;
	}
	
	/**
	 * Asigna al anuncio una serie de temas de inter�s
	 * @param interests Temas de inter�s del anuncio
	 */
	public void setInterests(ArrayList<InterestDTO> interests) {
		this.interests = interests;
	}

}
