package es.uco.pw.p1.classes;

public class Interes{
	private int id;
	private String nombre;
	
	/**
	 * Constructor parametrizado
	 * @param id ID del interés
	 * @param nombre Nombre del interés
	 */
	public Interes(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve el ID del interés
	 * @return El ID del interés
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Devuelve el nombre del interés
	 * @return El nombre del interés
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Asigna el ID del interés
	 * @param id ID del interés
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Asigna el nombre del interés
	 * @param nombre El nombre del interés
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
