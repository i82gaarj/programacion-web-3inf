package es.uco.pw.p1.classes;

import java.util.Date;
/**
 * Concret product que define el tipo de anuncio flash
 * @author Sofía Salas Ruiz
 * @author Jaime García Arjona
 */

public class FlashAnuncio extends Anuncio {
	
	/**Fecha a partir de la cual el anuncio aparecerá como publicado*/
	protected Date fechainicio;
	
	/**
	 * Devuelve la fecha de inicio asignada al anuncio
	 * @return Fecha de inicio del anuncio
	 */
	public Date getFechainicio() {
		return fechainicio;
	}

	/**
	 * Asigna la fecha de inicio al anuncio
	 * @param fechainicio La fecha de inicio del anuncio
	 */
	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	/**Fecha a partir de la cual el anuncio ya no aparecerá como publicado*/
	protected Date fechafin;
	
	/**
	 * Devuelve la fecha de fin asignada al anuncio
	 * @return Fecha de fin del anuncio
	 */
	public Date getFechafin() {
		return fechafin;
	}

	/**
	 * Asigna la fecha de fin al anuncio
	 * @param fechafin La fecha de fin del anuncio
	 */
	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	
	/**
	 * Constructor
	 * @param ID El ID del anuncio
	 * @param titulo Título del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 * @param fechainicio Fecha de inicio
	 * @param fechafin Fecha de fin
	 */
	public FlashAnuncio(int ID, String titulo, Contacto userProp, String cuerpo, Date fechainicio, Date fechafin) {
		super(ID,titulo,userProp,cuerpo);
		GestorUsuarios Gestor = GestorUsuarios.getInstance();
		this.userDest= Gestor.getContactos();
		this.fechainicio = fechainicio;
		this.fechafin = fechafin;
		this.tipo="flash";
	}
}
