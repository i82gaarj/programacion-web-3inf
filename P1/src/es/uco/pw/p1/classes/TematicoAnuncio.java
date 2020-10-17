package es.uco.pw.p1.classes;

import java.util.ArrayList;

/**
 * Concret product que define el anuncio de tipo tem�tico
 * @author Sof�a Salas Ruiz
 * @author Jaime Garc�a Arjona
 */

public class TematicoAnuncio extends Anuncio {
	
	protected ArrayList<Interes> intereses;
	
	
	/**
	 * Devuelve un ArrayList con los temas de inter�s asignados al anuncio
	 * @return Temas de inter�s del anuncio
	 */
	public ArrayList<Interes> getIntereses() {
		return intereses;
	}


	/**
	 * Asigna al anuncio una serie de temas de inter�s
	 * @param intereses Temas de inter�s del anuncio
	 */
	public void setIntereses(ArrayList<Interes> intereses) {
		this.intereses = intereses;
	}

	/**
	 * Constructor
	 * @param ID El ID del anuncio
	 * @param titulo T�tulo del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 * @param userDest Usuarios destinatarios
	 * @param intereses Temas de inter�s
	 */
	public TematicoAnuncio(int ID, String titulo, Contacto userProp, String cuerpo, ArrayList<Contacto> userDest, ArrayList<Interes> intereses) {
		super(ID,titulo,userProp,cuerpo);
		this.userDest = userDest;
		this.intereses = intereses;
		this.estado = "editado";
		this.tipo = "tematico";
	}
	
}
