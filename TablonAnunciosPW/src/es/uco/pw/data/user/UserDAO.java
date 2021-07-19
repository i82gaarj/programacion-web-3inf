package es.uco.pw.data.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import es.uco.pw.business.user.InterestDTO;
import es.uco.pw.business.user.UserDTO;
import es.uco.pw.data.DAO;
/**
 * Clase que accede a la base de datos (Usuarios)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class UserDAO extends DAO{

	public UserDAO(ServletContext context) {
		super(context);
	}

	/**
	 * Devuelve un usuario por email
	 * @param email El email
	 * @return El usuario con ese email
	 */
	public UserDTO queryByEmail(String email) {
		UserDTO u = null;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("query-user-by-email"));
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int user_id = rs.getInt(1);
				String password = rs.getString(2);
				String firstname = rs.getString(3);
				String lastname = rs.getString(4);
				LocalDate birthdate = rs.getDate(5).toLocalDate();
				ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("query-user-interest"));
				InterestDAO interestDAO = new InterestDAO(context);
				ps2.setInt(1, user_id);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					int interest_id = rs2.getInt(1);
					InterestDTO i = interestDAO.queryByID(interest_id);
					interests.add(i);
				}
				u = new UserDTO(user_id, email, password, firstname, lastname, birthdate, interests);
			}
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return u;
	}
	
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
				String password = rs.getString(2);
				String firstname = rs.getString(3);
				String lastname = rs.getString(4);
				LocalDate birthdate = rs.getDate(5).toLocalDate();
				ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("query-user-interest"));
				ps2.setInt(1, id);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					int interest_id = rs2.getInt(1);
					InterestDTO i = (new InterestDAO(context)).queryByID(interest_id);
					interests.add(i);
				}
				u = new UserDTO(id, email, password, firstname, lastname, birthdate, interests);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return u;
	}
	
	/**
	 * Devuelve un usuario por ID
	 * @param id El ID
	 * @return El usuario con ese ID
	 */
	/*public UserDTO queryByName(String firstname, String lastname) {
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
				String password = rs.getString(3);
				LocalDate birthdate = rs.getDate(4).toLocalDate();
				ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("query-user-interest"));
				ps2.setInt(1, id);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					int interest_id = rs2.getInt(1);
					InterestDTO i = (new InterestDAO(context)).queryByID(interest_id);
					interests.add(i);
				}
				u = new UserDTO(id, email, password, firstname, lastname, birthdate, interests);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return u;
	}*/
	
	/**
	 * Devuelve todos los usuarios
	 * @return ArrayList con todos los usuarios de la BD
	 */
	/*public  ArrayList<UserDTO> getUsers(){
		ArrayList<UserDTO> users = new ArrayList<UserDTO>();
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("get-users"));
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				LocalDate birthdate = rs.getDate(6).toLocalDate();
				ArrayList<InterestDTO> interests = new ArrayList<InterestDTO>();
				PreparedStatement ps2 = con.prepareStatement(getProps().getProperty("query-user-interest"));
				ps2.setInt(1, id);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					int interest_id = rs2.getInt(1);
					InterestDTO i = (new InterestDAO(context)).queryByID(interest_id);
					interests.add(i);
				}
				UserDTO u = new UserDTO(id, email, password, firstname, lastname, birthdate, interests);
				users.add(u);
			}
			
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return users;
	}*/
	
	/**
	 * Inserta un usuario en la base de datos
	 * @param user El usuario que se va a insertar
	 * @return status Status de la BD
	 */
	public int insertUser (UserDTO user) {
		int status = 0;
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("insert-user"));

			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setString(3, user.getEmail());
			ps.setDate(4, Date.valueOf(user.getBirthdate()));
			ps.setString(5, user.getPassword());
			
			status = ps.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	/**
	 * Modifica un usuario
	 * @param user El usuario que se va a actualizar
	 * @return status Status de la BD
	 */
	public int updateUser (UserDTO user) {
		int status = 0;
		try {			
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("update-user"));
			
			ps.setString(1, user.getFirstname());
			ps.setString(2, user.getLastname());
			ps.setDate(4, Date.valueOf(user.getBirthdate()));
			ps.setString(3, user.getPassword());
			ps.setInt(5, user.getID());
			
			status = ps.executeUpdate();
			
			con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	/**
	 * Asigna una contraseña a un usuario
	 * @param user El usuario
	 * @param newPassword La contraseña
	 * @return status Status de la BD
	 */
	public int setPassword(UserDTO user, String newPassword) {
		int status = 0;
		try {			
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(getProps().getProperty("set-password"));
			
			ps.setString(1, newPassword);
			ps.setInt(2, user.getID());
			
			status = ps.executeUpdate();
			
			con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
}
