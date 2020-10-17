package es.uco.pw.p1.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Clase que gestiona la informaci�n de un contacto
 * @author Jaime Garc�a Arjona
 * @author Sof�a Salas Ruiz
 */
public class Contacto {

	private String nombre;
	private String apellidos;
	private Date fechaNacimiento;
	private String email;
	private ArrayList<Interes> intereses;
	
	/**
	 * Constructor parametrizado
	 * @param nombre Nombre del contacto
	 * @param apellidos Apellidos del contacto
	 * @param fechaNacimiento Fecha de nacimiento del contacto
	 * @param email Email del contacto
	 * @param intereses Temas de inter�s del contacto
	 */
	public Contacto(String nombre, String apellidos, Date fechaNacimiento, String email, ArrayList<Interes> intereses) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.intereses = intereses;
	}
	
	/**
	 * Devuelve el nombre del contacto
	 * @return El nombre del contacto
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Devuelve los apellidos del contacto
	 * @return Los apellidos del contacto
	 */
	public String getApellidos() {
		return apellidos;
	}
	
	/**
	 * Devuelve la fecha de nacimiento del contacto
	 * @return La fecha de nacimiento del contacto
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Devuelve el email del contacto
	 * @return El email del contacto
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Devuelve los temas de inter�s del contacto
	 * @return Los temas de inter�s del contacto
	 */
	public ArrayList<Interes> getIntereses() {
		return intereses;
	}
	
	/**
	 * Asigna un nombre al contacto
	 * @param nombre El nombre del contacto
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Asigna los apellidos al contacto
	 * @param apellidos Los apellidos del contacto
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	/**
	 * Asigna la fecha de nacimiento al contacto
	 * @param fechaNacimiento La fecha de nacimiento del contacto
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	/**
	 * Asigna el email al contacto
	 * @param email El email del contacto
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * A�ade un tema de inter�s al contacto
	 * @param i El tema de inter�s que se quiere a�adir
	 */
	public void addInteres(Interes i) {
		this.intereses.add(i);
	}
	
	/**
	 * Elimina un tema de inter�s al contacto
	 * @param i El tema de inter�s que se quiere eliminar
	 */
	public void removeInteres(Interes i) {
		for (Interes interes : intereses) {
			if (i.getID() == interes.getID()) {
				intereses.remove(i);
			}
		}
	}
	
	/**
	 * Asigna una lista de intereses al contacto
	 * @param intereses Lista de temas de inter�s
	 */
	public void setIntereses(ArrayList<Interes> intereses) {
		this.intereses = intereses;
	}
	
	/**
	 * M�todo que imprime por pantalla los datos del contacto
	 */
	public void print() {
		System.out.println("Nombre: " + this.getNombre());
		System.out.println("Apellidos: " + this.getApellidos());
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = format.format(this.getFechaNacimiento());
		System.out.println("Fecha de nacimiento: " + dateString);
		
		System.out.println("Email: " + this.getEmail());
		System.out.println("Temas de inter�s:");
		List<Interes> intereses = this.getIntereses();
		for(Interes i : intereses) {
			System.out.println(i.getNombre());
		}
	}
}
