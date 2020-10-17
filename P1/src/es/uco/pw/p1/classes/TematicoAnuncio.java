package es.uco.pw.p1.classes;

import java.util.ArrayList;

/**
 * Concret product que define el anuncio de tipo temático
 * @author Sofía Salas Ruiz
 * @author Jaime García Arjona
 */

public class TematicoAnuncio extends Anuncio {
	
	protected ArrayList<Interes> intereses;
	
	
	/**
	 * Devuelve un ArrayList con los temas de interés asignados al anuncio
	 * @return Temas de interés del anuncio
	 */
	public ArrayList<Interes> getIntereses() {
		return intereses;
	}


	/**
	 * Asigna al anuncio una serie de temas de interés
	 * @param intereses Temas de interés del anuncio
	 */
	public void setIntereses(ArrayList<Interes> intereses) {
		this.intereses = intereses;
	}

	/**
	 * Constructor
	 * @param ID El ID del anuncio
	 * @param titulo Título del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 * @param userDest Usuarios destinatarios
	 * @param intereses Temas de interés
	 */
	public TematicoAnuncio(int ID, String titulo, Contacto userProp, String cuerpo, ArrayList<Contacto> userDest, ArrayList<Interes> intereses) {
		super(ID,titulo,userProp,cuerpo);
		this.userDest = userDest;
		this.intereses = intereses;
		this.estado = "editado";
		this.tipo = "tematico";
	}
	
}
