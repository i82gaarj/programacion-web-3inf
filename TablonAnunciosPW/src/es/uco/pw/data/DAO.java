package es.uco.pw.data;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * Clase que gestiona la conexión a la base de datos
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public abstract class DAO {
	
	protected static Connection con = null;
	protected static ServletContext context = null;
	
	public DAO(ServletContext context) {
		DAO.context = context;
	}
	
	public static Properties getProps() {
		Properties prop = new Properties();
		
		InputStream inputStream;
		try {
			inputStream = context.getResourceAsStream(context.getInitParameter("propertiesPath"));
			prop.load(inputStream);
		}
		catch(FileNotFoundException e1){
			e1.printStackTrace();
		}
		catch (Exception e2){
			e2.printStackTrace();
		}
		return prop;
    }
	
	protected static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(context.getInitParameter("jdbc"), context.getInitParameter("db-user"), context.getInitParameter("db-pass"));
		} catch(SQLException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
