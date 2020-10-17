package es.uco.pw.p1.classes;


/**
 * Concret product ANUNCIO GENERAL
 * @author Salas
 *
 */
public class GeneralAnuncio extends Anuncio{
	
	/**
	 * Constructor
	 * @param ID El ID del anuncio
	 * @param titulo Título del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 */
	public GeneralAnuncio(int ID, String titulo, Contacto userProp, String cuerpo) {
		super(ID,titulo,userProp,cuerpo);
		GestorUsuarios Gestor = GestorUsuarios.getInstance();
		this.userDest = Gestor.getContactos();
		this.tipo = "general";
	}

}
