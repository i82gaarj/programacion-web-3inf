package es.uco.pw.p1.classes;

import java.util.ArrayList;

/**
 * Concret product que define el tipo de anuncio individualizado
 * @author Sof�a Salas Ruiz
 * @author Jaime Garc�a Arjona
 */

public class IndivAnuncio extends Anuncio {
	
	/**
	 * Constructor
	 * @param ID El ID del anuncio
	 * @param titulo El t�tulo del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 * @param userDest Usuarios destinatarios
	 */
	public IndivAnuncio(int ID, String titulo, Contacto userProp, String cuerpo, ArrayList<Contacto> userDest) {
		super(ID,titulo,userProp,cuerpo);
		this.userDest=userDest;
		this.estado = "editado";
		this.tipo = "indiv";
	}

}
