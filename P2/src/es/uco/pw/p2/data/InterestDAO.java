package es.uco.pw.p2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.uco.pw.p2.business.InterestDTO;
import es.uco.pw.p2.business.UserDTO;
/**
 * Clase que accede a la base de datos (Temas de interés)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class InterestDAO extends DAO {
	
	/**
	 * Constructor
	 */
	public InterestDAO() {
		super();
	}
	
	/**
	 * Obtiene todos los intereses de la BD
	 * @return Los intereses almacenados en la BD
	 */
	public  ArrayList<InterestDTO> getInterests(){
		ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-interests"));
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				
				InterestDTO i = new InterestDTO(id, name);
				interests.add(i);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return interests;
	}
	
	/**
	 * Borra todos los intereses a un usuario
	 * @param u El usuario
	 * @return Status de la BD
	 */
	public  int deleteAllInterestsFromUser(UserDTO u) {
		int status = 0;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("delete-interests-user"));
			ps.setInt(1,u.getID());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	/**
	 * Añade un interés a un usuario
	 * @param u El usuario
	 * @param i El tema de interés
	 * @return Status de la BD
	 */
	public  int addInterestToUser(UserDTO u, InterestDTO i) {
		int status = 0;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("add-interest-user"));
			ps.setInt(1, u.getID());
			ps.setInt(2, i.getID());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return status;
	}
	
	/**
	 * Devuelve un tema de interés según su ID
	 * @param interest_id El ID del tema de interés
	 * @return El tema de interés con ese ID
	 */
	public  InterestDTO queryByID(int interest_id) {
		InterestDTO i = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-interest-by-id"));
			ps.setInt(1, interest_id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String interest_name = rs.getString(1);
				
				i = new InterestDTO(interest_id, interest_name);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return i;
	}
}
