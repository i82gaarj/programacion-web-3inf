package es.uco.pw.p1.program;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

import es.uco.pw.p1.classes.*;

/**
 * Programa que prueba el funcionamiento del tablon de anuncios y su gestor
 * @author Sofía Salas Ruiz
 * @author Jaime García Arjona
 */

public class EJ2{
	
	public static void main(String[] args) {
		
	GestorTablon factoryGestor = new GestorTablon();
	Scanner scan = new Scanner(System.in);
	GestorUsuarios Gestor = GestorUsuarios.getInstance();
	Gestor.loadContacts();
	
	System.out.println("Introduzca su email para identificarse:");
	String email = scan.nextLine();
	if(Gestor.search(email) == null) {
		System.out.println("El usuario no esta registrado en el sistema.");
		System.out.println("Ejecute el primer programa para registrarse.");
		System.out.println("FIN");
		System.exit(0);
	}
	Contacto usuario = Gestor.search(email);
	
	
	int num = 0;
	while(true) {
		
		System.out.println("TABLON DE ANUNCIOS");
		System.out.println("1. Suscribirse a temas de interes");
		System.out.println("2. Crear anuncio");
		System.out.println("3. Editar anuncio");
		System.out.println("4. Publicar anuncio");
		System.out.println("5. Archivar anuncio");
		System.out.println("6. Mostrar anuncios por fecha de publicacion");
		System.out.println("7. Mostrar anuncios por usuario propietario");
		System.out.println("8. Buscar anuncios por fecha");
		System.out.println("9. Buscar anuncios por usuario propietario");
		System.out.println("10. Buscar anuncios por usuario destinatario");
		System.out.println("11. Buscar anuncios por tema de interes");
		System.out.println("12. Salir");
		String aux = scan.nextLine();
		try{
			num = Integer.parseInt(aux);
		}
		catch (NumberFormatException e) {
			System.out.print("Formato no valido");
		}
		switch(num) {
		
		case 1:{
			System.out.println("Introduzca los temas de interés a los que suscribirse (REEMPLAZA LOS TEMAS ANTERIORES):");
			System.out.println("Lista de temas:");
			Gestor.printIntereses();
			System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea añadir a este contacto:");
			System.out.println("Ejemplo: 1,3,8");
			String interesesStr = scan.nextLine();
			String[] interesesVec = interesesStr.split(",");
			ArrayList<Interes> intereses = new ArrayList<Interes>();
			for (int i = 0; i < interesesVec.length; i++) {
				try{
					Interes interes = Gestor.getInteresByID(Integer.parseInt(interesesVec[i]));
					if (Gestor.getInteresByID(Integer.parseInt(interesesVec[i])) != null) {
						intereses.add(interes);
						Contacto c = Gestor.getContactoByEmail(email);
						Gestor.bajaContacto(email);
						Contacto c2 = new Contacto(c.getNombre(), c.getApellidos(), c.getFechaNacimiento(), c.getEmail(), intereses);
						Gestor.altaContacto(c2);
					}
					else {
						System.out.println("No existe ese tema de interés. Operación cancelada.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Formato no valido");
				}
			}
			
		}break;
		
		case 2:{
			System.out.println("Seleccione el tipo de anuncio que desea crear");
			System.out.println("1. General");
			System.out.println("2. Tematico");
			System.out.println("3. Individualizado");
			System.out.println("4. Flash");
			String aux1 = scan.nextLine();
			int opc = Integer.parseInt(aux1);
			switch(opc) {
			case 1:{
				System.out.println("Introduzca el ID del anuncio: ");
				int ID = Integer.parseInt(scan.nextLine());
				System.out.println("Introduzca el titulo del anuncio: ");
				String titulo = scan.nextLine();
				System.out.println("Nombre del propietario del anuncio: ");
				String strPropN = scan.nextLine();
				System.out.println("Apellidos propietario: ");
				String srtPropA = scan.nextLine();
				Contacto userProp = Gestor.search(strPropN,srtPropA);
				System.out.println("Introduzca el cuerpo del anuncio: ");
				String cuerpo = scan.nextLine();
				factoryGestor.createAnuncioGeneral(ID,titulo,userProp,cuerpo);
			}break;
			case 2:{
				System.out.println("Introduzca el ID del anuncio: ");
				int ID = Integer.parseInt(scan.nextLine());
				System.out.println("Introduzca el titulo del anuncio: ");
				String titulo = scan.nextLine();
				System.out.println("Nombre del propietario del anuncio: ");
				String strPropN = scan.nextLine();
				System.out.println("Apellidos propietario: ");
				String srtPropA = scan.nextLine();
				Contacto userProp = Gestor.search(strPropN,srtPropA);
				System.out.println("Introduzca el cuerpo del anuncio: ");
				String cuerpo = scan.nextLine();
				System.out.println("Introduce los temas de interes del anuncio: ");
				//
				System.out.println("Lista de temas:");
				Gestor.printIntereses();
				System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea añadir a este contacto:");
				System.out.println("Ejemplo: 1,3,8");
				String interesesStr = scan.next();
				String[] interesesVec = interesesStr.split(",");
				ArrayList<Interes> intereses = new ArrayList<Interes>();
				for (int i = 0; i < interesesVec.length; i++) {
					System.out.println(interesesVec[i]);
					Interes interes = Gestor.getInteresByID(Integer.parseInt(interesesVec[i]));
					intereses.add(interes);
				}
				//
				factoryGestor.createAnuncioTematico(ID,titulo,userProp,cuerpo,intereses);
			}break;
			case 3:{
				System.out.println("Introduzca el ID del anuncio: ");
				int ID = Integer.parseInt(scan.nextLine());
				System.out.println("Introduzca el titulo del anuncio: ");
				String titulo = scan.nextLine();
				System.out.println("Nombre del propietario del anuncio: ");
				String strPropN = scan.nextLine();
				System.out.println("Apellidos propietario: ");
				String srtPropA = scan.nextLine();
				Contacto userProp = Gestor.search(strPropN,srtPropA);
				System.out.println("Introduzca el cuerpo del anuncio: ");
				String cuerpo = scan.nextLine();
				ArrayList<Contacto> userDest = new ArrayList<Contacto>();
				System.out.println("Numero de destinatarios: ");
				String aux2 = scan.nextLine();
				int n = Integer.parseInt(aux2);
				for(int i=0;i<n;i++)
				{
					System.out.println("Email del destinatario: ");
					String emailDest = scan.nextLine();
					userDest.add(Gestor.search(emailDest));
				}
				factoryGestor.createAnuncioIndiv(ID,titulo,userProp,cuerpo,userDest);
			}break;
			case 4:{
				System.out.println("Introduzca el ID del anuncio: ");
				int ID = Integer.parseInt(scan.nextLine());
				System.out.println("Introduzca el titulo del anuncio: ");
				String titulo = scan.nextLine();
				System.out.println("Nombre del propietario del anuncio: ");
				String strPropN = scan.nextLine();
				System.out.println("Apellidos propietario: ");
				String srtPropA = scan.nextLine();
				Contacto userProp = Gestor.search(strPropN,srtPropA);
				System.out.println("Introduzca el cuerpo del anuncio: ");
				String cuerpo = scan.nextLine();
				System.out.println("Introduza la fecha de inicio y la fecha de fin del anuncio (dd/MM/yyyy HH:mm): ");
				String strinicio = scan.nextLine();
				String strfin = scan.nextLine();
				Date fechainicio = factoryGestor.cambioFecha(strinicio);
				Date fechafin = factoryGestor.cambioFecha(strfin);
				factoryGestor.createAnuncioFlash(ID,titulo,userProp,cuerpo,fechainicio,fechafin);
			}break;
			}
		}break;
		
		case 3:{
			System.out.println("Seleccione el tipo de anuncio que desea editar:");
			System.out.println("1. General");
			System.out.println("2. Tematico");
			System.out.println("3. Individualizado");
			System.out.println("4. Flash");
			String aux2= scan.nextLine();
			int opc = Integer.parseInt(aux2);
			if(opc == 1) {
				System.out.println("Introduzca el ID del anuncio que se va a editar (no se cambia el ID): ");
				int ID = Integer.parseInt(scan.nextLine());
				if(factoryGestor.buscarGAnuncio(ID) == null) {
					System.out.println("No existe ningún anuncio general con ese ID");
					break;
				}
				System.out.println("Introduzca el nuevo titulo: ");
				String titulo = scan.nextLine();
				System.out.println("Nombre del nuevo propietario: ");
				String strPropN = scan.nextLine();
				System.out.println("Apellidos propietario: ");
				String srtPropA = scan.nextLine();
				Contacto userProp = Gestor.search(strPropN,srtPropA);
				System.out.println("Introduzca el nuevo cuerpo: ");
				String cuerpo = scan.nextLine();
				GeneralAnuncio g = new GeneralAnuncio(ID,titulo,userProp,cuerpo);
				factoryGestor.editAnuncioGeneral(ID, g);
			}else if(opc == 2) {
				System.out.println("Introduzca el ID del anuncio que se va a editar (no se cambia el ID): ");
				int ID = Integer.parseInt(scan.nextLine());
				if(factoryGestor.buscarTAnuncio(ID) == null) {
					System.out.println("No existe ningún anuncio general con ese ID");
					break;
				}
				System.out.println("Introduzca el nuevo titulo: ");
				String titulo = scan.nextLine();
				System.out.println("Nombre del nuevo propietario: ");
				String strPropN = scan.nextLine();
				System.out.println("Apellidos propietario: ");
				String srtPropA = scan.nextLine();
				Contacto userProp = Gestor.search(strPropN,srtPropA);
				System.out.println("Introduzca el nuevo cuerpo: ");
				String cuerpo = scan.nextLine();
				System.out.println("Introduce los nuevos temas de interes: ");
				//
				System.out.println("Lista de temas:");
				Gestor.printIntereses();
				System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea añadir a este contacto:");
				System.out.println("Ejemplo: 1,3,8");
				String interesesStr = scan.next();
				String[] interesesVec = interesesStr.split(",");
				ArrayList<Interes> intereses = new ArrayList<Interes>();
				for (int i = 0; i < interesesVec.length; i++) {
					System.out.println(interesesVec[i]);
					Interes interes = Gestor.getInteresByID(Integer.parseInt(interesesVec[i]));
					intereses.add(interes);
				}
				//
				ArrayList<Contacto> userDest = factoryGestor.obtenerDestinatarios(intereses);
				TematicoAnuncio t = new TematicoAnuncio(ID,titulo,userProp,cuerpo,userDest,intereses);
				factoryGestor.editAnuncioTematico(ID, t);
			}else if(opc == 3) {
				System.out.println("Introduzca el ID del anuncio que se va a editar (no se cambia el ID): ");
				int ID = Integer.parseInt(scan.nextLine());
				if(factoryGestor.buscarIAnuncio(ID) == null) {
					System.out.println("No existe ningún anuncio general con ese ID");
					break;
				}
				System.out.println("Introduzca el titulo del anuncio: ");
				String titulo = scan.nextLine();
				System.out.println("Nombre del propietario del anuncio: ");
				String strPropN = scan.nextLine();
				System.out.println("Apellidos propietario: ");
				String srtPropA = scan.nextLine();
				Contacto userProp = Gestor.search(strPropN,srtPropA);
				System.out.println("Introduzca el cuerpo del anuncio: ");
				String cuerpo = scan.nextLine();
				ArrayList<Contacto> userDest = new ArrayList<Contacto>();
				System.out.println("Numero de destinatarios: ");
				String aux3 = scan.nextLine();
				int n = Integer.parseInt(aux3);
				for(int i=0;i<n;i++)
				{
					System.out.println("Email del destinatario: ");
					String emailDest = scan.nextLine();
					userDest.add(Gestor.search(emailDest));
				}
				IndivAnuncio i = new IndivAnuncio(ID,titulo,userProp,cuerpo,userDest);
				factoryGestor.editAnuncioIndiv(ID, i);
			}else if(opc == 4) {
				System.out.println("Introduzca el ID del anuncio que se va a editar (no se cambia el ID): ");
				int ID = Integer.parseInt(scan.nextLine());
				if(factoryGestor.buscarFAnuncio(ID) == null) {
					System.out.println("No existe ningún anuncio general con ese ID");
					break;
				}
				System.out.println("Introduzca el titulo del anuncio: ");
				String titulo = scan.nextLine();
				System.out.println("Nombre del propietario del anuncio: ");
				String strPropN = scan.nextLine();
				System.out.println("Apellidos propietario: ");
				String srtPropA = scan.nextLine();
				Contacto userProp = Gestor.search(strPropN,srtPropA);
				System.out.println("Introduzca el cuerpo del anuncio: ");
				String cuerpo = scan.nextLine();
				System.out.println("Introduza la fecha de inicio y la fecha de fin del anuncio (dd/MM/yyyy HH:mm): ");
				String strinicio = scan.nextLine();
				String strfin = scan.nextLine();
				Date fechainicio = factoryGestor.cambioFecha(strinicio);
				Date fechafin = factoryGestor.cambioFecha(strfin);
				FlashAnuncio f = new FlashAnuncio(ID,titulo,userProp,cuerpo,fechainicio,fechafin);
				factoryGestor.editAnuncioFlash(ID, f);
			}
			
		}break;
		
		case 4:{
			System.out.println("Introduce el ID del anuncio que se va a publicar:");
			int ID = Integer.parseInt(scan.nextLine());
			System.out.println("Tipo del anuncio (general, tematico, individualizado, flash): ");
			String tipo = scan.nextLine();
			factoryGestor.postAnuncio(ID,tipo);
			
		}break;
		
		case 5:{
			System.out.println("Introduce el ID del anuncio que se va a archivar:");
			int ID = Integer.parseInt(scan.nextLine());
			System.out.println("Tipo del anuncio (general, tematico, individualizado, flash): ");
			String tipo = scan.nextLine();
			factoryGestor.fileAnuncio(ID,tipo);
			
		}break;
		
		case 6:{
			
			factoryGestor.showAnunciosOrderDate(usuario);
			
		}break;
		
		case 7:{
			
			factoryGestor.showAnunciosOrderOwner(usuario);
			
			}break;
		
		case 8: {
			System.out.println("Introduce la fecha(dd/MM/yyyy HH:mm) para buscar los anuncios: ");
			String fecha = scan.nextLine();
			ArrayList<Anuncio> anuncios = factoryGestor.findDateAnuncio(fecha);
			for(Anuncio a : anuncios) {
				System.out.println("ID:" + a.getID());
				System.out.println("Titulo: " + a.getTitulo());
				Contacto propietario = a.getUserProp();
				System.out.println("Propietario: " + propietario.getNombre() + propietario.getApellidos());
				System.out.println("Cuerpo: " + a.getCuerpo());
			}
		}break;
		
		case 9:{
			System.out.println("Introduce el nombre del propietario y sus apellidos para buscar los anuncios: ");
			System.out.println("Nombre: ");
			String nombre = scan.nextLine();
			System.out.println("Apellidos: ");
			String apellidos = scan.nextLine();
			Contacto propietario = Gestor.search(nombre, apellidos);
			ArrayList<Anuncio> anuncios = factoryGestor.findOwnerAnuncio(propietario);
			for(Anuncio a : anuncios) {
				System.out.println("ID:" + a.getID());
				System.out.println("Titulo: " + a.getTitulo());
				System.out.println("Propietario: " + propietario.getNombre() + propietario.getApellidos());
				System.out.println("Cuerpo: " + a.getCuerpo());
			}
			
		}break;
		
		case 10:{
			System.out.println("Introduce nombre del destinatario y sus apellidos para buscar los anuncios: ");
			System.out.println("Nombre:");
			String nombre = scan.nextLine();
			System.out.println("Apellidos:");
			String apellidos = scan.nextLine();
			Contacto destinatario = Gestor.search(nombre, apellidos);
			ArrayList<Anuncio> anuncios = factoryGestor.findDestAnuncio(destinatario);
			for(Anuncio a : anuncios) {
				System.out.println("ID:" + a.getID());
				System.out.println("Titulo: " + a.getTitulo());
				Contacto propietario = a.getUserProp();
				System.out.println("Propietario: " + propietario.getNombre() + propietario.getApellidos());
				System.out.println("Cuerpo: " + a.getCuerpo());
			}
		}break;
		
		case 11:{
			ArrayList<Interes> intereses = usuario.getIntereses();
			ArrayList<Anuncio> anuncios = factoryGestor.findInteresAnuncio(intereses);
			for(Anuncio a : anuncios) {
				System.out.println("ID:" + a.getID());
				System.out.println("Titulo: " + a.getTitulo());
				Contacto propietario = a.getUserProp();
				System.out.println("Propietario: " + propietario.getNombre() + propietario.getApellidos());
				System.out.println("Cuerpo: " + a.getCuerpo());
			}
			
		}break;
		case 12:{
			scan.close();
			System.exit(0);
		}
		default:{
			System.out.println("Opcion no valida");
		}
		
	}
	}
	}
}