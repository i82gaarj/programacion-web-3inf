package es.uco.pw.p1.classes;

import java.util.ArrayList;
import java.util.Date;

/**
 * Abstract factory del tablón
 * @author Sofía Salas Ruiz
 * @author Jaime García Arjona
 */
public abstract class Tablon {
	
	/**
	 * Crea un objeto Anuncio de tipo General
	 * @param ID El ID del anuncio
	 * @param titulo Título del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 */
	public abstract void createAnuncioGeneral(int ID, String titulo, Contacto userProp, String cuerpo);
	
	/**
	 * Crea un objeto Anuncio de tipo Temático
	 * @param ID El ID del anuncio
	 * @param titulo Título del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 * @param intereses Temas que corresponden al anuncio
	 */
	public abstract void createAnuncioTematico(int ID, String titulo, Contacto userProp, String cuerpo, ArrayList<Interes> intereses);
	
	/**
	 * Crea un objeto Anuncio de tipo Individualizado
	 * @param ID El ID del anuncio
	 * @param titulo Título del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 * @param userDest Usuarios destinatarios
	 */
	public abstract void createAnuncioIndiv(int ID, String titulo, Contacto userProp, String cuerpo, ArrayList<Contacto> userDest);
	
	/**
	 * Crea un objeto Anuncio de tipo Flash
	 * @param ID El ID del anuncio
	 * @param titulo Título del anuncio
	 * @param userProp Usuario propietario del anuncio
	 * @param cuerpo Cuerpo del anuncio
	 * @param fechainicio Fecha de inicio
	 * @param fechafin Fecha de fin
	 */
	public abstract void createAnuncioFlash(int ID, String titulo, Contacto userProp, String cuerpo, Date fechainicio, Date fechafin);
	
	/**
	 * Permite modificar uno o varios atributos de un objeto Anuncio de tipo General
	 * @param ID El ID del anuncio que se va a modificar
	 * @param g Objeto GeneralAnuncio con los atributos actualizados
	 */
	public abstract void editAnuncioGeneral(int ID, GeneralAnuncio g);
	
	/**
	 * Permite modificar uno o varios atributos de un objeto Anuncio de tipo Temático
	 * @param ID El ID del anuncio que se va a modificar
	 * @param t Objeto TematicoAnuncio con los atributos actualizados
	 */
	public abstract void editAnuncioTematico(int ID, TematicoAnuncio t);
	
	/**
	 * Permite modificar uno o varios atributos de un objeto Anuncio de tipo Individualizado
	 * @param ID El ID del anuncio que se va a modificar
	 * @param i Objeto IndivAnuncio con los atributos actualizados
	 */
	public abstract void editAnuncioIndiv(int ID, IndivAnuncio i);
	
	/**
	 * Permite modificar uno o varios atributos de un objeto Anuncio de tipo Flash
	 * @param ID El ID del anuncio que se va a modificar
	 * @param f Objeto FlashAnuncio con los atributos actualizados
	 */
	public abstract void editAnuncioFlash(int ID, FlashAnuncio f);
	
	/**
	 * Cambia el estado del anuncio a "publicado" y lo añade al ArrayList tablon, de tal forma que pueda ser mostrado en el tablón de anuncios
	 * @param ID El ID del anuncio que se va a publicar
	 * @param tipo El tipo del anuncio que se va a publicar
	 */
	public abstract void postAnuncio(int ID, String tipo);
	
	/**
	 * Cambia el estado del anuncio a "archivado" y lo elimina del ArrayList tablon, de tal forma que deja de mostrarse en el tablón de anuncios
	 * @param ID El ID del anuncio que se va a archivar
	 * @param tipo El tipo del anuncio que se va a archivar
	 */
	public abstract void fileAnuncio(int ID, String tipo);
	
	/**
	 * Busca en los ArrayList de cada tipo de anuncio aquellos cuya fecha de publicación sea igual a la fecha indicada por el usuario
	 * @param fechastr Fecha indicada por el usuario para buscar los anuncios
	 * @return ArrayList de Anuncio con los anuncios tales que fechaPublicacion == fechastr
	 */
	public abstract ArrayList<Anuncio> findDateAnuncio(String fechastr);
	
	/**
	 * Busca en los ArrayList de cada tipo de anuncio aquellos cuyo usuario propietario sea el mismo que el indicado por el usuario
	 * @param propietario Contacto propietario indicado por el usuario para buscar los anuncios
	 * @return ArrayList de Anuncio con los anuncios tales que userProp == propietario
	 */
	public abstract ArrayList<Anuncio> findOwnerAnuncio(Contacto propietario);
	
	/**
	 * Busca en los ArrayList de cada tipo de anuncio aquellos que tengan al menos 1 tema de interés que coincida con los intereses del usuario
	 * @param intereses Interés asignados al usuario que está ejecutando el programa
	 * @return ArrayList de Anuncio con los anuncios en los que coincide algún tema de interés con los del usuario
	 */
	public abstract ArrayList<Anuncio> findInteresAnuncio(ArrayList<Interes> intereses);
	
	/**
	 * Busca en los ArrayList de cada tipo de anuncio aquellos que tengan al Contacto indicado por el usuario entre uno de sus usuarios destinatarios
	 * @param destinatario Contacto destinatario indicado por el usuario para buscar los anuncios
	 * @return ArrayList de Anuncio con los anuncios que tengan Contacto destinatario en sus userDest
	 */
	public abstract ArrayList<Anuncio> findDestAnuncio(Contacto destinatario);

	/**
	 * Imprime por pantalla los anuncios que son visibles para el usuario logueado ordenados según su fecha de publicación
	 * @param usuario Contacto usuario logueado en la ejecución del programa
	 */
	public abstract void showAnunciosOrderDate(Contacto usuario);
	
	/**
	 * Imprime por pantalla los anuncios que son visibles para el usuario logueado ordenados alfabéticamente según el nombre del userProp
	 * @param usuario Contacto usuario logueado en la ejecución del programa
	 */
	public abstract void showAnunciosOrderOwner(Contacto usuario);

}
