package es.uco.pw.p1.classes;

public class Interes{
	private int id;
	private String nombre;
	
	/**
	 * Constructor parametrizado
	 * @param id ID del inter�s
	 * @param nombre Nombre del inter�s
	 */
	public Interes(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve el ID del inter�s
	 * @return El ID del inter�s
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Devuelve el nombre del inter�s
	 * @return El nombre del inter�s
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Asigna el ID del inter�s
	 * @param id ID del inter�s
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Asigna el nombre del inter�s
	 * @param nombre El nombre del inter�s
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
