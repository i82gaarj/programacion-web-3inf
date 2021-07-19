package es.uco.pw.p2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.uco.pw.p2.business.InterestDTO;
import es.uco.pw.p2.business.UserDTO;
/**
 * Clase que accede a la base de datos (Anuncios)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class UserDAO extends DAO{
	
	/**
	 * Devuelve un usuario por ID
	 * @param id El ID
	 * @return El usuario con ese ID
	 */
	public  UserDTO queryByID(int id) {
		UserDTO u = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-user-by-id"));
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				String email = rs.getString(1);
				String firstname = rs.getString(2);
				String lastname = rs.getString(3);
				ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("query-user-by-id-2"));
				ps2.setInt(1, id);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					int interest_id = rs2.getInt(1);
					InterestDTO i = (new InterestDAO()).queryByID(interest_id);
					interests.add(i);
				}
				u = new UserDTO(id, firstname, lastname, email, interests);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return u;
	}
	
	/**
	 * Devuelve todos los usuarios
	 * @return ArrayList con todos los usuarios de la BD
	 */
	public  ArrayList<UserDTO> getUsers(){
		ArrayList<UserDTO> users = new ArrayList<UserDTO>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-users"));
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String email = rs.getString(2);
				String firstname = rs.getString(3);
				String lastname = rs.getString(4);
				
				UserDTO u = new UserDTO(id, email, firstname, lastname);
				users.add(u);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return users;
	}
	
	/**
	 * Devuelve un usuario por email
	 * @param email El email
	 * @return El usuario con ese email
	 */
	public  UserDTO queryByEmail(String email) {
		UserDTO u = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-user-by-email"));
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int user_id = rs.getInt(1);
				String firstname = rs.getString(2);
				String lastname = rs.getString(3);
				ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("query-user-by-email-2"));
				ps2.setInt(1, user_id);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					int interest_id = rs2.getInt(1);
					InterestDTO i = (new InterestDAO()).queryByID(interest_id);
					interests.add(i);
				}
				u = new UserDTO(user_id, firstname, lastname, email, interests);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return u;
	}
	
	/**
	 * Devuelve un usuario según su nombre y apellidos
	 * @param firstname El nombre
	 * @param lastname Los apellidos
	 * @return El usuario con ese nombre y apellidos
	 */
	public  UserDTO queryByName(String firstname, String lastname) {
		UserDTO u = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-user-by-name"));
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int id = rs.getInt(1);
				String email = rs.getString(2);
				u = new UserDTO(id, firstname, lastname, email);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return u;
	}
}
