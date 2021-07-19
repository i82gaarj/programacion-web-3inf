package es.uco.pw.p2.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletContext;

import es.uco.pw.p2.business.UserDTO;
/**
 * Clase que accede a la base de datos (Usuarios)
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public class UserDAO extends DAO{
	
	/**
	 * Constructor con servletcontext
	 * @param context ServletContext
	 */
	public UserDAO(ServletContext context) {
		super(context);
	}

	/**
	 * Devuelve un usuario por email
	 * @param email El email
	 * @return El usuario con ese email
	 */
	public  UserDTO queryByEmail(String email) {
		UserDTO u = null;
		try {
			Connection con = getConnection(getContext());
			PreparedStatement ps = con.prepareStatement(getProps(context).getProperty("query-user-by-email"));
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int user_id = rs.getInt(1);
				String firstname = rs.getString(2);
				String lastname = rs.getString(3);
				LocalDate birthdate = rs.getDate(4).toLocalDate();
				String password = rs.getString(5);
				u = new UserDTO(user_id, email, password, firstname, lastname, birthdate);
			}
		} catch (SQLException e) {
			close();
			System.out.println(e);
		}
		close();
		return u;
	}
	
	/**
	 * Inserta un usuario en la base de datos
	 * @param user El usuario que se va a insertar
	 * @return status Status de la BD
	 */
	public int insertUser (UserDTO user) {
		int status = 0;
		try {
			Connection con = getConnection(getContext());
			PreparedStatement ps = con.prepareStatement(getProps(context).getProperty("insert-user"));

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
			Connection con = getConnection(getContext());
			PreparedStatement ps = con.prepareStatement(getProps(context).getProperty("update-user"));
			
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
}
