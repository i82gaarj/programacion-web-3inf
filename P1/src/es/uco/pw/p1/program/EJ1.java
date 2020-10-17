package es.uco.pw.p1.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import es.uco.pw.p1.classes.Contacto;
import es.uco.pw.p1.classes.GestorUsuarios;
import es.uco.pw.p1.classes.Interes;

/**
 * Programa que prueba la clase Contacto y GestorUsuarios
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 */
public class EJ1{
	
	/**
	 * Función que pide un nuevo contacto y lo da de alta
	 * @param sc Scanner de entrada por teclado (System.in)
	 */
	public static void pedirNuevoContacto(Scanner sc) {
		GestorUsuarios Gestor = GestorUsuarios.getInstance();
		System.out.println("Introduzca el email del contacto:");
		String email = sc.nextLine();
		if (Gestor.search(email) != null) {
			System.out.println("Ya existe un contacto con ese email");
			return;
		}
		System.out.println("Introduzca el nombre del contacto:");
		String nombre = sc.nextLine();
		System.out.println("Introduzca los apellidos:");
		String apellidos = sc.nextLine();
		System.out.println("Introduzca la fecha de nacimiento en el formato 'dd/MM/yyyy':");
		String fechaNacimiento = sc.nextLine();
		try {
			Date fechaNacimientoDate = new SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento);
			System.out.println("Introduzca los intereses del nuevo contacto:");
			System.out.println("Lista de temas:");
			Gestor.printIntereses();
			System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea añadir a este contacto:");
			System.out.println("Ejemplo: 1,3,8");
			String interesesStr = sc.nextLine();
			String[] interesesVec = interesesStr.split(",");
			ArrayList<Interes> intereses = new ArrayList<Interes>();
			for (int i = 0; i < interesesVec.length; i++) {
				Interes interes = Gestor.getInteresByID(Integer.parseInt(interesesVec[i]));
				if (interes != null) {
					intereses.add(interes);
				}
				else {
					System.out.println("Tema de interés con ID " + interesesVec[i] + " no existe");
					return;
				}
			}
			Contacto c = new Contacto(nombre, apellidos, fechaNacimientoDate, email, intereses);
			Gestor.altaContacto(c);
		} catch (ParseException e){
			System.out.println("Formato de fecha incorrecto");
			return;
		}
	}
	
	/**
	 * Función que pide los datos de un contacto existente y los actualiza
	 * @param email Email del contacto existente
	 * @param sc Scanner de entrada por teclado (System.in)
	 */
	public static void pedirContactoActualizar(String email, Scanner sc) {
		GestorUsuarios Gestor = GestorUsuarios.getInstance();
		System.out.println("Introduzca el nuevo email del contacto:");
		String emailNuevo = sc.nextLine();
		System.out.println("Introduzca el nuevo nombre del contacto:");
		String nombre = sc.nextLine();
		System.out.println("Introduzca los apellidos:");
		String apellidos = sc.nextLine();
		System.out.println("Introduzca la fecha de nacimiento en el formato 'dd/MM/yyyy':");
		String fechaNacimiento = sc.nextLine();
		try {
			Date fechaNacimientoDate = new SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento);
			System.out.println("Introduzca los intereses del nuevo contacto:");
			System.out.println("Lista de temas:");
			Gestor.printIntereses();
			System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea añadir a este contacto:");
			System.out.println("Ejemplo: 1,3,8");
			String interesesStr = sc.nextLine();
			String[] interesesVec = interesesStr.split(",");
			ArrayList<Interes> intereses = new ArrayList<Interes>();
			for (int i = 0; i < interesesVec.length; i++) {
				Interes interes = Gestor.getInteresByID(Integer.parseInt(interesesVec[i]));
				if (interes != null) {
					intereses.add(interes);
				}
			}
			Contacto c = new Contacto(nombre, apellidos, fechaNacimientoDate, emailNuevo, intereses);
			Gestor.actualizarContacto(email, c);
		} catch (ParseException e){
			System.out.println("Formato de fecha incorrecto");
			return;
		}
	}
	
	public static void main(String[] args) {
		
		GestorUsuarios Gestor = GestorUsuarios.getInstance();
		int opcion = 0;
		Scanner sc = new Scanner(System.in);
		
		while(true){
			
			System.out.println("GESTOR DE CONTACTOS");
			System.out.println("1. Añadir contacto");
			System.out.println("2. Eliminar contacto");
			System.out.println("3. Actualizar contacto");
			System.out.println("4. Buscar contacto por email");
			System.out.println("5. Buscar contacto por nombre y apellidos");
			System.out.println("6. Buscar contacto por interes");
			System.out.println("7. Buscar contacto por edad");
			System.out.println("8. Salir");
			String opcionStr = sc.nextLine();
			opcion = Integer.parseInt(opcionStr);
			if (opcion == 1) {
				pedirNuevoContacto(sc);
			}
			else if (opcion == 2){
				System.out.println("Introduzca el email del contacto que desea borrar:");
				String email = sc.nextLine();
				if (Gestor.search(email) != null) {
					Gestor.bajaContacto(email);
					System.out.println("Contacto eliminado");
				}
				else {
					System.out.println("No existe un contacto con ese email");
				}
			}
			else if (opcion == 3){
				System.out.println("Introduzca el email del contacto que desea actualizar:");
				String email = sc.nextLine();
				if (Gestor.search(email) != null) {
					pedirContactoActualizar(email, sc);
				}
				else {
					System.out.println("El contacto no existe");
				}
			}
			else if (opcion == 4){
				System.out.println("Introduzca el email:");
				String email = sc.nextLine();
				Contacto c = Gestor.search(email);
				if (c != null) {
					System.out.println("Contacto encontrado:");
					c.print();
					System.out.println("-------------------------\n");
				}
				else {
					System.out.println("Contacto no encontrado");
				}
			}
			else if (opcion == 5){
				System.out.println("Introduzca el nombre:");
				String nombre = sc.nextLine();
				System.out.println("Introduzca los apellidos:");
				String apellidos = sc.nextLine();
				Contacto c = Gestor.search(nombre, apellidos);
				if (c != null) {
					System.out.println("Contacto encontrado:");
					c.print();
					System.out.println("-------------------------\n");
				}
				else {
					System.out.println("No se encontró ningún contacto llamado " + nombre + " " + apellidos);
				}
			}
			else if (opcion == 6){
				Gestor.printIntereses();
				System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea buscar:");
				System.out.println("Ejemplo: 1,3,8");
				String interesesStr = sc.nextLine();
				String[] interesesVec = interesesStr.split(",");
				ArrayList<Contacto> contactos = null;
				for (int i = 0; i < interesesVec.length; i++) {
					Interes interes = Gestor.getInteresByID(Integer.parseInt(interesesVec[i]));
					if (interes != null) {
						contactos = Gestor.search(interes);
					}
				}
				if (contactos.size() != 0 && contactos != null) {
					System.out.println("Se encontró uno o más contactos:");
					for (Contacto c : contactos) {
						c.print();
						System.out.println("-------\n");
					}
					System.out.println("-------------------------\n");
				}
				else {
					System.out.println("No se encontró ningún contacto con los temas de interés indicados");
				}
			}
			else if (opcion == 7){
				System.out.println("Introduzca la edad en años:");
				int edad = Integer.parseInt(sc.nextLine());
				ArrayList<Contacto> contactos = Gestor.search(edad);
				if (contactos.size() != 0) {
					System.out.println("Se encontró uno o más contactos:");
					for (Contacto c : contactos) {
						c.print();
						System.out.println("-------\n");
					}
				}
				else {
					System.out.println("No se encontró ningún contacto con " + edad + " años");
				}
			}
			else if (opcion == 8){
				sc.close();
				System.exit(0);
			}
			else{
				System.out.println("Opcion no valida");
			}
		}
	}
}