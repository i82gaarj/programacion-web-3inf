package es.uco.pw.p2.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que gestiona la conexión a la base de datos
 * @author Jaime García Arjona
 * @author Sofía Salas Ruiz
 *
 */
public abstract class DAO {
	
	protected static Connection con = null;
	
	public static Properties getProps() {
		Properties prop = new Properties();
		
		InputStream inputStream;
		try {
			inputStream = DAO.class.getResourceAsStream("/resources/sql.properties");
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
	
	public static Properties getLoginProps() {
		Properties prop = new Properties();
		
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream("./config.properties");
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
			con = DriverManager.getConnection(getLoginProps().getProperty("jdbc"), getLoginProps().getProperty("user"), getLoginProps().getProperty("password"));
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
