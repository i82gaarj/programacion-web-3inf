package es.uco.pw.p1.classes;

import java.util.Date;
import java.util.ArrayList;
import java.util.Properties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.io.*;

/**
 * Clase que realiza la gestión de los usuarios
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 * 
 */
public class GestorUsuarios {
	
	private static GestorUsuarios instance = null;
	private ArrayList<Contacto> contactos;
	private ArrayList<Interes> interesesDisponibles = null;
	
	/**
	 * Constructor privado singleton
	 */
	private GestorUsuarios() {
		this.loadInterests();
		this.loadContacts();
	}
	
	/**
	 * Devuelve la instancia de la clase
	 * @return La instancia
	 */
	public static GestorUsuarios getInstance() {
		if (instance == null) {
			instance = new GestorUsuarios();
		}
		return instance;
	}

	/**
	 * Devuelve la ruta del fichero de datos
	 * @return La ruta del fichero de datos
	 */
	public String getFilePath() {
		Properties prop = new Properties();
		
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream("./config.properties");
			prop.load(inputStream);
			String filePath = prop.getProperty("filePath");
			return filePath;
		}
		catch(FileNotFoundException e1){
			return null;
		}
		catch (Exception e2){
			e2.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Carga los contactos en memoria
	 */
	public void loadContacts() {
		String filePath = this.getFilePath();
		File f = null;
		FileReader fr = null;
		BufferedReader br = null;
		this.contactos = new ArrayList<Contacto>();
		try {
			f = new File(filePath);
			fr = new FileReader (f);
			br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				String[] contactData = line.split(";");
				String nombre = contactData[0];
				String apellidos = contactData[1];
				
				String fechaNacimiento = contactData[2];
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				Date fechaNacimientoDate = null;
				try {
					fechaNacimientoDate = formato.parse(fechaNacimiento);
				} catch(ParseException e){
					return;
				}
				
				String email = contactData[3];
				
				String aux = contactData[4].substring(1);
				String interesesStr = aux.substring(0, aux.length() - 1);
				String[] interesesVec = interesesStr.split(",");
				ArrayList<Interes> intereses = new ArrayList<Interes>();
				for (int i = 0; i < interesesVec.length; i++) {
					intereses.add(this.getInteresByID(Integer.parseInt(interesesVec[i])));
				}
				Contacto c = new Contacto(nombre, apellidos, fechaNacimientoDate, email, intereses);
				contactos.add(c);
				line = br.readLine();
			}
		} catch (Exception e) {
			try{
				FileWriter fw = new FileWriter(filePath);
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Carga los temas de interés en memoria
	 */
	public void loadInterests() {
		this.interesesDisponibles = new ArrayList<Interes>();
		Properties prop = new Properties();
		
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("./config.properties");
			prop.load(inputStream);
			String intereses = prop.getProperty("intereses");
			String[] interesesVec = intereses.split(",");
			for (int i = 0; i < interesesVec.length; i++) {
				Interes interes = new Interes(i + 1, interesesVec[i]);
				interesesDisponibles.add(interes);
			}
			
		}
		catch (FileNotFoundException e1) {
			return;
		}
		catch (Exception e2){
			e2.printStackTrace();
		}
	}
	
	/**
	 * Devuelve el array de contactos
	 * @return El array de contactos
	 */
	public ArrayList<Contacto> getContactos() {
		return contactos;
	}
	
	/**
	 * Da de alta un contacto, guardándolo en el fichero de datos
	 * @param c El contacto que hay que dar de alta
	 */
	public void altaContacto(Contacto c) {
		String filePath = getFilePath();
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(filePath, true);
			bw = new BufferedWriter(fw);
			Date fechaNacimiento = c.getFechaNacimiento(); 
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
            String strDate = dateFormat.format(fechaNacimiento);  
			bw.write(c.getNombre() + ";" + c.getApellidos() + ";" + strDate + ";" + c.getEmail() + ";[");
			ArrayList<Interes> intereses = c.getIntereses();
			if (intereses.size() != 0 && intereses != null) {
				for (Interes i : intereses) {
					if (intereses.get(intereses.size() - 1) == i) {
						bw.write(i.getID() + "");
					}
					else {
						bw.write(i.getID() + ",");
					}
	
				}
			}
			else {
				bw.write("");
			}
			bw.write("]\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
					this.loadContacts(); // carga de nuevo en memoria los contactos
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * Borra un contacto del fichero de datos
	 * @param email El email que identifica el contacto
	 */
	public void bajaContacto(String email) {
		String filePath = getFilePath();
		
		File inputFile = null;
		File tempFile = null;
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		this.contactos = new ArrayList<Contacto>();
		
		try {
			inputFile = new File(filePath);
			tempFile = new File("temp.txt");
			br = new BufferedReader(new FileReader (inputFile));
			bw = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				String[] contactData = currentLine.split(";");
				String emailSearch = contactData[3];
				if (!email.equals(emailSearch)) {
					bw.write(currentLine + "\n");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					bw.close();
					inputFile.delete();
					tempFile.renameTo(inputFile);
					this.loadContacts();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Actualiza un contacto
	 * @param email El email que identifica al contacto
	 * @param c El contacto actualizado
	 */
	public void actualizarContacto(String email, Contacto c) {
		this.bajaContacto(email);
		this.altaContacto(c);
	}
	
	/**
	 * Busca un contacto por email
	 * @param email El email del contacto a buscar
	 * @return El contacto encontrado, o null si no se encuentra
	 */
	public Contacto search(String email) {
		if (contactos.size() == 0) {
			return null;
		}
		for (Contacto c : contactos) {
			if (c.getEmail().equals(email)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Busca un contacto por nombre y apellidos
	 * @param nombre El nombre del contacto
	 * @param apellidos El/los apellido(s) del contacto
	 * @return El contacto encontrado, o null si no lo ha encontrado
	 */
	public Contacto search(String nombre, String apellidos) {
		for (Contacto c : contactos) {
			if (c.getNombre().equalsIgnoreCase(nombre) && c.getApellidos().equalsIgnoreCase(apellidos)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Busca un contacto por edad
	 * @param edad La edad de los contactos a buscar
	 * @return Un array de contactos
	 */
	public ArrayList<Contacto> search(int edad) {
		ArrayList<Contacto> ret = new ArrayList<Contacto>();
		LocalDate today = LocalDate.now();
		for (Contacto c : contactos) {
			LocalDate birthday = c.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Period p = Period.between(birthday, today);
			int edadSearch = p.getYears();
			if (edad == edadSearch) {
				ret.add(c);
			}
		}
		return ret;
	}
	
	/**
	 * Busca un contacto por interés
	 * @param interes Tema de interés
	 * @return Un array de contactos
	 */
	public ArrayList<Contacto> search(Interes interes){
		ArrayList<Contacto> ret = new ArrayList<Contacto>();
		for (Contacto c : contactos) {
			if (c.getIntereses().contains(interes)) {
				ret.add(c);
			}
		}
		return ret;
	}
	
	/**
	 * Imprime los intereses disponibles
	 */
	public void printIntereses() {
		for (Interes i : interesesDisponibles) {
			System.out.println(i.getID() + " - " + i.getNombre());
		}
	}
	
	/**
	 * Devuelve un objeto del tipo Interés cuyo ID coincide con el que se pasa como parámetro
	 * @param id El ID del tema de interés
	 * @return El objeto Interés con ese ID o null si no existe
	 */
	public Interes getInteresByID(int id) {
		for (Interes i : interesesDisponibles) {
			if (i.getID() == id) {
				return i;
			}
		}
		return null;
	}
	
	public Contacto getContactoByEmail(String email) {
		for (Contacto c : contactos) {
			if (c.getEmail().equals(email)) {
				return c;
			}
		}
		return null;
	}
}
