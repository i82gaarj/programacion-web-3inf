package es.uco.pw.p2.display;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import es.uco.pw.p2.business.*;

/**
 * Programa que prueba el funcionamiento del tablon de anuncios y su gestor
 * @author Sofía Salas Ruiz
 * @author Jaime García Arjona
 */

public class TablonDeAnuncios{
	
	public static void main(String[] args) {	
		BoardManager boardManager = BoardManager.getInstance();
		Scanner scan = new Scanner(System.in);
		UserManager userManager = UserManager.getInstance();
		
		System.out.println("Introduzca su email para identificarse:");
		String email = scan.nextLine();
		UserDTO loggedUser = userManager.queryByEmail(email);
		if(loggedUser == null) {
			System.out.println("El usuario no esta registrado en el sistema.");
			System.out.println("FIN");
			System.exit(0);
		}
		
		int opt = 0;
		while(opt != 12) {
			
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
				opt = Integer.parseInt(aux);
			}
			catch (NumberFormatException e) {
				System.out.print("Formato no valido");
			}
		
			if (opt == 1) {
				System.out.println("Introduzca los temas de interés a los que suscribirse (REEMPLAZA LOS TEMAS ANTERIORES):");
				System.out.println("Lista de temas:");
				userManager.printInterests();
				System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea añadir a este contacto:");
				System.out.println("Ejemplo: 1,3,8");
				String interestsStr = scan.nextLine();
				String[] interestsVec = interestsStr.split(",");
				ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
				for (int i = 0; i < interestsVec.length; i++) {
					try{
						InterestDTO interest = userManager.getInterestByID(Integer.parseInt(interestsVec[i]));
						if (userManager.getInterestByID(Integer.parseInt(interestsVec[i])) != null) {
							interests.add(interest);
							UserDTO u = userManager.queryByEmail(email);
							boardManager.deleteAllInterestsFromUser(u);
							for (InterestDTO interest_loop : interests) {
								boardManager.addInterestToUser(u, interest_loop);
							}
						}
						else {
							System.out.println("No existe ese tema de interés. Operación cancelada.");
						}
					} catch (NumberFormatException e) {
						System.out.println("Formato no valido");
					}
				}
				
			} else if (opt == 2){
				System.out.println("Seleccione el tipo de anuncio que desea crear");
				System.out.println("1. General");
				System.out.println("2. Tematico");
				System.out.println("3. Individualizado");
				System.out.println("4. Flash");
				String aux1 = scan.nextLine();
				int opt2 = Integer.parseInt(aux1);
				if (opt2 == 1){
					System.out.println("Introduzca el titulo del anuncio: ");
					String title = scan.nextLine();
					System.out.println("Introduzca el contenido del anuncio: ");
					String content = scan.nextLine();
					GeneralAdDTO ad = AdFactory.createGeneralAd(title, loggedUser, content, 0, AdStatus.EDITING, LocalDate.of(1970, 1, 1));
					boardManager.saveGeneralAd(ad);
				} else if (opt2 == 2){
					System.out.println("Introduzca el titulo del anuncio: ");
					String title = scan.nextLine();
					System.out.println("Introduzca el contenido del anuncio: ");
					String content = scan.nextLine();
					System.out.println("Introduce los temas de interes del anuncio: ");
					System.out.println("Lista de temas:");
					userManager.printInterests();
					System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea añadir a este anuncio:");
					System.out.println("Ejemplo: 1,3,8");
					String interestsStr = scan.next();
					String[] interestsVec = interestsStr.split(",");
					ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
					for (int i = 0; i < interestsVec.length; i++) {
						InterestDTO interest = userManager.getInterestByID(Integer.parseInt(interestsVec[i]));
						interests.add(interest);
					}
					ThematicAdDTO ad = AdFactory.createThematicAd(title, loggedUser, content, 0, AdStatus.EDITING, LocalDate.of(1970, 1, 1), interests);
					boardManager.saveThematicAd(ad);
				} else if (opt2 == 3){
					System.out.println("Introduzca el titulo del anuncio: ");
					String title = scan.nextLine();
					System.out.println("Introduzca el contenido del anuncio: ");
					String content = scan.nextLine();
					ArrayList<UserDTO> dest_users = new ArrayList<UserDTO>();
					System.out.println("Numero de destinatarios: ");
					String aux2 = scan.nextLine();
					int n = Integer.parseInt(aux2);
					for(int i = 0; i < n; i++)
					{
						System.out.println("Email del destinatario: ");
						String dest_email = scan.nextLine();
						dest_users.add(userManager.queryByEmail(dest_email));
					}
					IndividualAdDTO ad = AdFactory.createIndividualAd(title, loggedUser, content, 0, AdStatus.EDITING, LocalDate.of(1970, 1, 1), dest_users);
					boardManager.saveIndividualAd(ad);
				} else if (opt2 == 4){
					System.out.println("Introduzca el titulo del anuncio: ");
					String title = scan.nextLine();
					System.out.println("Introduzca el contenido del anuncio: ");
					String content = scan.nextLine();
					System.out.println("Introduza la fecha de inicio y la fecha de fin del anuncio (dd/MM/yyyy): ");
					String start_date_str = scan.nextLine();
					String end_date_str = scan.nextLine();
					LocalDate start_date = LocalDate.parse(start_date_str);
					LocalDate end_date = LocalDate.parse(end_date_str);
					FlashAdDTO ad = AdFactory.createFlashAd(title, loggedUser, content, 0, AdStatus.EDITING, LocalDate.of(1970, 1, 1), start_date, end_date);
					boardManager.saveFlashAd(ad);
				}
			} else if (opt == 3){
				System.out.println("Sus anuncios:\n");
				boardManager.showPublishedAds(loggedUser);
				boardManager.showNotPublishedAds(loggedUser);
				System.out.println("Introduzca el ID del anuncio que va a editar: ");
				int ad_id = Integer.parseInt(scan.nextLine());
				if(boardManager.search(ad_id) == null) {
					System.out.println("No existe ningún anuncio con ese ID");
				}
				else if (boardManager.isOwner(loggedUser, ad_id) == false) {
					System.out.println("No tiene permisos para editar ese anuncio");
				}
				else {
					AdType ad_type = boardManager.getTypeOfAdByID(ad_id);
					if (ad_type == AdType.GENERAL) {
						System.out.println("Introduzca el nuevo titulo: ");
						String title = scan.nextLine();
						System.out.println("Introduzca el nuevo contenido: ");
						String content = scan.nextLine();
						boardManager.editAd(ad_id, title, content);
					}
					else if (ad_type == AdType.THEMATIC) {
						System.out.println("Introduzca el nuevo titulo: ");
						String title = scan.nextLine();
						System.out.println("Introduzca el nuevo contenido: ");
						String content = scan.nextLine();
						System.out.println("Introduce los nuevos temas de interes: ");
						System.out.println("Lista de temas:");
						userManager.printInterests();
						System.out.println("--------------------\nIntroduzca, separados por comas, los NÚMEROS correspondientes a los temas de interés que desea añadir a este contacto:");
						System.out.println("Ejemplo: 1,3,8");
						String interesesStr = scan.next();
						String[] interesesVec = interesesStr.split(",");
						ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
						for (int i = 0; i < interesesVec.length; i++) {
							System.out.println(interesesVec[i]);
							InterestDTO interest = userManager.getInterestByID(Integer.parseInt(interesesVec[i]));
							interests.add(interest);
						}
						boardManager.editThematicAd(ad_id, title, content, interests);
					}
					else if (ad_type == AdType.INDIVIDUAL) {
						System.out.println("Introduzca el titulo del anuncio: ");
						String title = scan.nextLine();
						System.out.println("Introduzca el cuerpo del anuncio: ");
						String content = scan.nextLine();
						ArrayList<UserDTO> dest_users = new ArrayList<UserDTO>();
						System.out.println("Numero de destinatarios: ");
						String aux3 = scan.nextLine();
						int n = Integer.parseInt(aux3);
						for(int i = 0; i < n; i++)
						{
							System.out.println("Email del destinatario: ");
							String dest_email = scan.nextLine();
							dest_users.add(userManager.queryByEmail(dest_email));
						}
						boardManager.editAd(ad_id, title, content, dest_users);
					}
					else if (ad_type == AdType.FLASH) {
						System.out.println("Introduzca el titulo del anuncio: ");
						String title = scan.nextLine();
						System.out.println("Introduzca el cuerpo del anuncio: ");
						String content = scan.nextLine();
						System.out.println("Introduza la fecha de inicio y la fecha de fin del anuncio (dd/MM/yyyy): ");
						String start_date_str = scan.nextLine();
						String end_date_str = scan.nextLine();
						LocalDate start_date = LocalDate.parse(start_date_str);
						LocalDate end_date = LocalDate.parse(end_date_str);
						boardManager.editAd(ad_id, title, content, start_date, end_date);
					}
				}
				
			} else if (opt == 4){
				if (boardManager.hasNotPublishedAds(loggedUser)) {
					System.out.println("Anuncios sin publicar:\n");
					boardManager.showNotPublishedAds(loggedUser);
					System.out.println("Introduce el ID del anuncio que se va a publicar:");
					int ad_id = Integer.parseInt(scan.nextLine());
					if (boardManager.isOwner(loggedUser, ad_id)) {
						boardManager.publishAd(ad_id);
					}
					else {
						System.out.println("No posee permisos para publicar este anuncio\n\n");
					}
				}
				else {
					System.out.println("No tienes anuncios sin publicar");
				}
			} else if (opt == 5){
				if (!boardManager.hasNotPublishedAds(loggedUser)) {
					System.out.println("Anuncios publicados:\n");
					boardManager.showPublishedAds(loggedUser);
					System.out.println("Introduce el ID del anuncio que se va a archivar:");
					int ad_id = Integer.parseInt(scan.nextLine());
					if (boardManager.isOwner(loggedUser, ad_id)) {
						boardManager.archiveAd(ad_id);
					}
					else {
						System.out.println("No posee permisos para archivar este anuncio\n\n");
					}
				}
			} else if (opt == 6){
				boardManager.showAdsByDate(loggedUser);
			} else if (opt == 7){
				boardManager.showAdsByOwner(loggedUser);
			} else if (opt == 8){
				System.out.println("Introduce la fecha(dd/MM/yyyy) para buscar los anuncios: ");
				String date = scan.nextLine();
				ArrayList<Ad> ads = boardManager.findAdsByDate(date);
				for(Ad a : ads) {
					System.out.println(a.toString());
				}
			} else if (opt == 9){
				System.out.println("Introduce el nombre del propietario y sus apellidos para buscar los anuncios: ");
				System.out.println("Nombre: ");
				String firstname = scan.nextLine();
				System.out.println("Apellidos: ");
				String lastname = scan.nextLine();
				UserDTO owner = userManager.queryByName(firstname, lastname);
				if (owner == null){
					System.out.println("No existe este usuario\n\n");
				}
				else {
					ArrayList<Ad> ads = boardManager.findAdsByOwner(owner);
					for(Ad a : ads) {
						System.out.println(a.toString());
					}
				}
			} else if (opt == 10){
				System.out.println("Introduce nombre del destinatario y sus apellidos para buscar los anuncios: ");
				System.out.println("Nombre:");
				String firstname = scan.nextLine();
				System.out.println("Apellidos:");
				String lastname = scan.nextLine();
				UserDTO dest_user = userManager.queryByName(firstname, lastname);
				ArrayList<Ad> ads = boardManager.findAdsByDestinationUser(dest_user);
				for(Ad a : ads) {
					System.out.println(a.toString());
				}
			} else if (opt == 11){
				ArrayList<InterestDTO> interests = loggedUser.getInterests();
				ArrayList<Ad> ads = boardManager.findAdsByInterest(interests);
				for(Ad a : ads) {
					System.out.println(a.toString());
				}
			} else if (opt == 12){
				scan.close();
				System.exit(0);
			} else{
				System.out.println("Opcion no valida");
			}
		}
	}
}