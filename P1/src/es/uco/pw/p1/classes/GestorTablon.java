package es.uco.pw.p1.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;
import java.util.Iterator;

/**
 * Concret factory que realiza la gestión de los anuncios
 * @author Sofía Salas Ruiz
 * @author Jaime Garcia Arjona
 */

public class GestorTablon extends Tablon {

	protected ArrayList<GeneralAnuncio> anunciosG = new ArrayList<GeneralAnuncio>();
	protected ArrayList<TematicoAnuncio> anunciosT = new ArrayList<TematicoAnuncio>();
	protected ArrayList<IndivAnuncio> anunciosI = new ArrayList<IndivAnuncio>();
	protected ArrayList<FlashAnuncio> anunciosF = new ArrayList<FlashAnuncio>();
	protected ArrayList <Anuncio> tablon = new ArrayList<Anuncio>();
	
	@Override
	public void createAnuncioGeneral(int ID, String titulo, Contacto userProp, String cuerpo) {
		
		GeneralAnuncio anuncio = new GeneralAnuncio(ID,titulo,userProp,cuerpo);
		anunciosG.add(anuncio);
	}

	@Override
	public void createAnuncioTematico(int ID, String titulo, Contacto userProp, String cuerpo,
			ArrayList<Interes> intereses) {
		ArrayList<Contacto> userDest = obtenerDestinatarios(intereses);
		TematicoAnuncio anuncio = new TematicoAnuncio(ID,titulo,userProp,cuerpo,userDest,intereses);
		anunciosT.add(anuncio);
	}

	@Override
	public void createAnuncioIndiv(int ID, String titulo, Contacto userProp, String cuerpo,
			ArrayList<Contacto> userDest) {
		IndivAnuncio anuncio = new IndivAnuncio(ID,titulo,userProp,cuerpo,userDest);
		anunciosI.add(anuncio);
	}

	@Override
	public void createAnuncioFlash(int ID, String titulo, Contacto userProp, String cuerpo, Date fechainicio,
			Date fechafin) {
		FlashAnuncio anuncio = new FlashAnuncio(ID,titulo,userProp,cuerpo,fechainicio,fechafin);
		anunciosF.add(anuncio);
	}
	
	/**
	 * Devuelve un ArrayList con los destinatarios cuyos intereses coinciden con los temas de interés asignados al anuncio
	 * @param intereses ArrayList de los temas de interés asignados al anuncio
	 * @return ArrayList de Contacto con los destinatarios que han coincidido los temas de interés
	 */
	public ArrayList<Contacto> obtenerDestinatarios(ArrayList<Interes> intereses){
		ArrayList<Contacto> userDest = new ArrayList<Contacto>();
		Scanner scan = new Scanner(System.in);
		GestorUsuarios Gestor = GestorUsuarios.getInstance();
		for(Interes i : intereses) {
		userDest = Gestor.search(i);
		}
		scan.close();
		return userDest;
	}
	
	/**
	 * Transforma el String fecha recibido en variable tipo Date
	 * @param fecha String fecha en formato dd/MM/yyyy HH:mm
	 * @return La fecha pasada como param pero en tipo Date
	 */
	public Date cambioFecha(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date fechaDate = null;
		try {
			fechaDate = formato.parse(fecha);
		}
		catch (ParseException ex)
		{
			System.out.println(ex);
		}
		return fechaDate;
	}
	
	/**
	 * Busca en el ArrayList anunciosG el anuncio cuyo ID coincide con el pasado como parámetro
	 * @param ID El ID del anuncio que se va a buscar
	 * @return Objeto GeneralAnuncio cuyo ID es el indicado, o null si no lo encuentra
	 */
	public GeneralAnuncio buscarGAnuncio(int ID) {
		if (anunciosG.size() == 0) {
			return null;
		}
		for (GeneralAnuncio g : anunciosG) {
			if (g.getID() == ID) {
				return g;
			}
		}
		return null;
	}
	
	/**
	 * Busca en el ArrayList anunciosI el anuncio cuyo ID coincide con el pasado como parámetro
	 * @param ID El ID del anuncio que se va a buscar
	 * @return Objeto IndivAnuncio cuyo ID es el indicado, o null si no lo encuentra
	 */
	public IndivAnuncio buscarIAnuncio(int ID) {
		if (anunciosI.size() == 0) {
			return null;
		}
		for (IndivAnuncio a : anunciosI) {
			if (a.getID() == ID) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Busca en el ArrayList anunciosF el anuncio cuyo ID coincide con el pasado como parámetro
	 * @param ID El ID del anuncio que se va a buscar
	 * @return Objeto FlashAnuncio cuyo ID es el indicado, o null si no lo encuentra
	 */
	public FlashAnuncio buscarFAnuncio(int ID) {
		if (anunciosF.size() == 0) {
			return null;
		}
		for (FlashAnuncio a : anunciosF) {
			if (a.getID() == ID) {
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Busca en el ArrayList anunciosT el anuncio cuyo ID coincide con el pasado como parámetro
	 * @param ID El ID del anuncio que se va a buscar
	 * @return Objeto TematicoAnuncio cuyo ID es el indicado, o null si no lo encuentra
	 */
	public TematicoAnuncio buscarTAnuncio(int ID) {
		if (anunciosT.size() == 0) {
			return null;
		}
		for (TematicoAnuncio a : anunciosT) {
			if (a.getID() == ID) {
				return a;
			}
		}
		return null;
	}

	@Override
	public void editAnuncioGeneral(int ID, GeneralAnuncio g) {
		GeneralAnuncio aux = buscarGAnuncio(ID);
		aux.setTitulo(g.getTitulo());
		aux.setUserProp(g.getUserProp());
		aux.setCuerpo(g.getCuerpo());
		aux.setEstado("editado");
	}

	@Override
	public void editAnuncioTematico(int ID, TematicoAnuncio t) {
		TematicoAnuncio aux = buscarTAnuncio(ID);
		aux.setTitulo(t.getTitulo());
		aux.setUserProp(t.getUserProp());
		aux.setCuerpo(t.getCuerpo());
		aux.setIntereses(t.getIntereses());
		aux.setEstado("editado");
	}

	@Override
	public void editAnuncioIndiv(int ID, IndivAnuncio i) {
		IndivAnuncio aux = buscarIAnuncio(ID);
		aux.setTitulo(i.getTitulo());
		aux.setUserProp(i.getUserProp());
		aux.setCuerpo(i.getCuerpo());
		aux.setUserDest(i.getUserDest());
		aux.setEstado("editado");
	}

	@Override
	public void editAnuncioFlash(int ID, FlashAnuncio f) {
		FlashAnuncio aux = buscarFAnuncio(ID);
		aux.setTitulo(f.getTitulo());
		aux.setUserProp(f.getUserProp());
		aux.setCuerpo(f.getCuerpo());
		aux.setFechainicio(f.getFechainicio());
		aux.setFechafin(f.getFechafin());
		aux.setEstado("editado");
	}

	@Override
	public void postAnuncio(int ID, String tipo) {
		if(tipo.equals("general")) {
			if(buscarGAnuncio(ID) == null) {
				System.out.println("No existe ningún anuncio general con ese ID");
			}else {
			GeneralAnuncio g = buscarGAnuncio(ID);
			g.setEstado("publicado");
			Date fechalocal = new Date();
			g.setFechapublicacion(fechalocal);
			tablon.add(g);
			}
		}else if(tipo.equals("tematico")) {
			if(buscarTAnuncio(ID) == null) {
				System.out.println("No existe ningún anuncio tematico con ese ID");
			}else {
			TematicoAnuncio t = buscarTAnuncio(ID);
			t.setEstado("publicado");
			Date fechalocal = new Date();
			t.setFechapublicacion(fechalocal);
			tablon.add(t);
			}
		}else if(tipo.equals("individualizado")) {
			if(buscarIAnuncio(ID) == null) {
				System.out.println("No existe ningún anuncio individulizado con ese ID");
			}else {
			IndivAnuncio i = buscarIAnuncio(ID);
			i.setEstado("publicado");
			Date fechalocal = new Date();
			i.setFechapublicacion(fechalocal);
			tablon.add(i);
			}
		}else if(tipo.equals("flash")) {
			if(buscarFAnuncio(ID) == null) {
				System.out.println("No existe ningún anuncio flash con ese ID");
			}else {
			FlashAnuncio f = buscarFAnuncio(ID);
			Date fechalocal = new Date();
			if(f.getFechainicio().after(fechalocal))
			{
				f.setEstado("en-espera");
			}else
			{
				f.setEstado("publicado");
			}
			f.setFechapublicacion(fechalocal);
			tablon.add(f);
			}
		}else {
			System.out.println("Error al introducir el tipo de anuncio.");
		}

	}

	@Override
	public void fileAnuncio(int ID, String tipo) {
		if(tipo.equals("general")) {
			if(buscarGAnuncio(ID) == null) {
				System.out.println("No existe ningún anuncio general con ese ID");
			}else {
			GeneralAnuncio g = buscarGAnuncio(ID);
			g.setEstado("archivado");
			tablon = eliminaAnuncio(tablon,ID);
			}
		}
		else if(tipo.equals("tematico")) {
			if(buscarTAnuncio(ID) == null) {
				System.out.println("No existe ningún anuncio temático con ese ID");
			}else {
			TematicoAnuncio t = buscarTAnuncio(ID);
			t.setEstado("archivado");
			tablon = eliminaAnuncio(tablon,ID);
			}
		}
		else if(tipo.equals("individualizado")) {
			if(buscarIAnuncio(ID) == null) {
				System.out.println("No existe ningún anuncio general con ese ID");
			}else {
			IndivAnuncio i = buscarIAnuncio(ID);
			i.setEstado("archivado");
			tablon = eliminaAnuncio(tablon,ID);
			}
		}
		else if(tipo.equals("flash")) {
			if(buscarFAnuncio(ID) == null) {
				System.out.println("No existe ningún anuncio flash con ese ID");
			}else {
			FlashAnuncio f = buscarFAnuncio(ID);
			f.setEstado("archivado");
			tablon = eliminaAnuncio(tablon,ID);
			}
		}
		else {
			System.out.println("Error al introducir el tipo de anuncio");
		}

	}
	
	/**
	 * Elimina del ArrayList tablon el anuncio indicado por el ID
	 * @param tablon ArrayList del que se borrará el anuncio
	 * @param ID El ID del anuncio que se va a borrar
	 * @return El ArrayList tablon pero ya sin el anuncio indicado
	 */
	public ArrayList<Anuncio> eliminaAnuncio(ArrayList<Anuncio> tablon, int ID){
		for(Iterator<Anuncio> iterator = tablon.iterator(); iterator.hasNext();) {
			Anuncio aux = iterator.next();
			if (aux.getID() == ID) {
				iterator.remove();
			}
		}
		return tablon;
	}

	@Override
	public ArrayList<Anuncio> findDateAnuncio(String fechastr) {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		Date fecha = cambioFecha(fechastr);
		for(GeneralAnuncio g : anunciosG) {
			if(g.getFechapublicacion() == fecha) {
				anuncios.add(g);
			}
		}
		for(TematicoAnuncio t : anunciosT) {
			if(t.getFechapublicacion() == fecha) {
				anuncios.add(t);
			}
		}
		for(IndivAnuncio i : anunciosI) {
			if(i.getFechapublicacion() == fecha) {
				anuncios.add(i);
			}
		}
		for(FlashAnuncio f : anunciosF) {
			if(f.getFechapublicacion() == fecha) {
				anuncios.add(f);
			}
		}
		return anuncios;

	}

	@Override
	public ArrayList<Anuncio> findOwnerAnuncio(Contacto propietario) {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		for(GeneralAnuncio g : anunciosG) {
			if(g.getUserProp() == propietario) {
				anuncios.add(g);
			}
		}
		for(TematicoAnuncio t : anunciosT) {
			if(t.getUserProp() == propietario) {
				anuncios.add(t);
			}
		}
		for(IndivAnuncio i : anunciosI) {
			if(i.getUserProp() == propietario) {
				anuncios.add(i);
			}
		}
		for(FlashAnuncio f : anunciosF) {
			if(f.getUserProp() == propietario) {
				anuncios.add(f);
			}
		}
		return anuncios;
	}
	
	@Override
	public ArrayList<Anuncio> findInteresAnuncio(ArrayList<Interes> intereses) {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		for(Interes i1 : intereses) {			
			for(TematicoAnuncio t : anunciosT) {
				for(Interes i2 : t.getIntereses()) {
					if(i1 == i2) {
						anuncios.add(t);
					}
				}
			}
		}
		return anuncios;
	}
	
	@Override
	public ArrayList<Anuncio> findDestAnuncio(Contacto destinatario) {
		ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
		for(GeneralAnuncio g : anunciosG) {
			for(Contacto dest : g.getUserDest()) {
				if(dest == destinatario) {
					anuncios.add(g);
				}
			}
		}
		for(TematicoAnuncio t : anunciosT) {
			for(Contacto dest : t.getUserDest()) {
				if(dest == destinatario) {
					anuncios.add(t);
				}
			}
		}
		for(IndivAnuncio i : anunciosI) {
			for(Contacto dest : i.getUserDest()) {
				if(dest == destinatario) {
					anuncios.add(i);
				}
			}
		}
		for(FlashAnuncio f : anunciosF) {
			for(Contacto dest : f.getUserDest()) {
				if(dest == destinatario) {
					anuncios.add(f);
				}
			}
		}
		return null;
	}

	@Override
	public void showAnunciosOrderDate(Contacto usuario) {
		if( tablon.size() != 0) {
		tablon = ordenarPorFecha(tablon);
		for(Anuncio a : tablon) {
			for(Contacto destinatario : a.getUserDest()) {
				if(usuario == destinatario) {
			System.out.println("ID:" + a.getID());
			System.out.println("Titulo: " + a.getTitulo());
			Contacto propietario = a.getUserProp();
			System.out.println("Propietario: " + propietario.getNombre() + propietario.getApellidos());
			System.out.println("Cuerpo: " + a.getCuerpo());
				}
			}
		}
		}
		else {
			System.out.println("No hay anuncios publicados");
		}
	}

	@Override
	public void showAnunciosOrderOwner(Contacto usuario) {
		if(tablon.size() != 0) {
		tablon = ordenarPorNombreProp(tablon);
		for(Anuncio a : tablon) {
			for(Contacto destinatario : a.getUserDest()) {
				if(usuario == destinatario) {
					System.out.println("ID:" + a.getID());
					System.out.println("Titulo: " + a.getTitulo());
					Contacto propietario = a.getUserProp();
					System.out.println("Propietario: " + propietario.getNombre() + propietario.getApellidos());
					System.out.println("Cuerpo: " + a.getCuerpo());
				}
			}
		}
		}

	}
	
	/**
	 * Ordena el ArrayList tablon en función de la fecha de publicación de cada anuncio
	 * @param tablon El ArrayList donde se encuentran los anuncios cuyo estado es "publicado"
	 * @return El ArrayList pasado como parámetro pero ya ordenado
	 */
	public static ArrayList<Anuncio> ordenarPorFecha(ArrayList<Anuncio> tablon){
		Collections.sort(tablon, new Comparator<Anuncio>(){
			@Override
			public int compare(Anuncio a1, Anuncio a2) {
				return a1.getFechapublicacion().compareTo(a2.getFechapublicacion());
			}
		});
		return tablon;
	}
	
	/**
	 * Ordena el ArrayList tablon alfabéticamente según el nombre del userProp
	 * @param tablon El ArrayList donde se encuentran los anuncios cuyo estado es "publicado"
	 * @return El ArrayList pasado como parámetro pero ya ordenado
	 */
	public static ArrayList<Anuncio> ordenarPorNombreProp(ArrayList<Anuncio> tablon){
		Collections.sort(tablon, new Comparator<Anuncio>(){
			public int compare(Anuncio a1, Anuncio a2) {
				Contacto prop1 = a1.getUserProp();
				Contacto prop2 = a2.getUserProp();
				return prop1.getNombre().compareTo(prop2.getNombre());
			}
		});
		return tablon;
	}

}
