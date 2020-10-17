package es.uco.pw.p1.classes;

import java.util.ArrayList;
import java.util.Date;

/**
 * The abstract class that represent a general product
 * @author Sofia Salas Ruiz
 * @author Jaime Garcia Arjona
 */

public abstract class Anuncio {

	protected int ID;
	protected String titulo;
	protected Contacto userProp;
	protected ArrayList<Contacto> userDest = new ArrayList<Contacto>();
	protected String cuerpo;
	protected String estado;
	protected Date fechapublicacion;
	protected String tipo;
	
	/**
	 * Constructor parametrizado
	 * @param ID El ID del anuncio
	 * @param titulo Título del anuncio
	 * @param userProp Uusario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 */

	public Anuncio(int ID, String titulo, Contacto userProp, String cuerpo) {
		this.ID = ID;
		this.titulo = titulo;
		this.userProp = userProp;
		this.cuerpo = cuerpo;
	}

	/**
	 * Devuelve el ID del anuncio
	 * @return El ID del anuncio
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Asigna un ID al anuncio
	 * @param iD El ID del anuncio
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Devuelve el titulo del anuncio
	 * @return El titulo del anuncio
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Asigna un titulo al anuncio
	 * @param titulo El titulo del anuncio
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Devuelve el usuario propietario del anuncio
	 * @return El propietario del anuncio
	 */
	public Contacto getUserProp() {
		return userProp;
	}

	/**
	 * Asigna el usuario propietario del anuncio
	 * @param userProp El propietario del anuncio
	 */
	public void setUserProp(Contacto userProp) {
		this.userProp = userProp;
	}
	
	/**
	 * Devuelve el array con los usuarios destinatarios del anuncio
	 * @return Los destinatarios del anuncio
	 */
	public ArrayList<Contacto> getUserDest() {
		return userDest;
	}
	
	/**
	 * Asigna los usuarios destinatarios del anuncio
	 * @param userDest El array con los destinatarios del anuncio
	 */
	public void setUserDest(ArrayList<Contacto> userDest) {
		this.userDest = userDest;
	}

	/**
	 * Devuelve el cuerpo del anuncio
	 * @return El cuerpo del anuncio
	 */
	public String getCuerpo() {
		return cuerpo;
	}

	/**
	 * Asigna el cuerpo al anuncio
	 * @param cuerpo El cuerpo del anuncio
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	/**
	 * Devuelve el tipo de anuncio
	 * @return El tipo de anuncio
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Asigna el tipo al anuncio
	 * @param tipo El tipo del anuncio
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Devuelve la fecha de publicación del anuncio
	 * @return La fecha de publicación del anuncio
	 */
	public Date getFechapublicacion() {
		return fechapublicacion;
	}

	/**
	 * Asigna la fecha de publicación del anuncio
	 * @param fechapublicacion La fecha de publicación del anuncio
	 */
	public void setFechapublicacion(Date fechapublicacion) {
		this.fechapublicacion = fechapublicacion;
	}
	
	/**
	 * Devuelve el estado del anuncio
	 * @return El estado del anuncio
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Asigna el estado del anuncio
	 * @param estado El estado del anuncio
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * Devuelve una cadena con toda la información asignada al objeto anuncio
	 * @return La información del objeto anuncio
	 */
	public String toString() {
		String info = "ID: " + this.ID + " Titulo: " + this.titulo + " Propietario: " + this.userProp + " Destinatario(s): " + this.userDest + " Cuerpo: " + this.cuerpo + "Tipo: " + this.tipo + "Fecha publicacion: " + this.fechapublicacion + "Estado: " + this.estado;
		return info;
	}
	
}
