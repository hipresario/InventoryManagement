import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Simple MySQL JDBC Connection using DriverManager
 * @author user
 *
 */
public class MySQLConnection {
	private String username;
	private String password;
	private String url;
	private static final String className = "com.mysql.jdbc.Driver";
	//default
	public MySQLConnection(){
		this.username = "root";
		this.password = "qwaszx";
		this.url = "jdbc:mysql://localhost:9443/imdb";
		System.out.println("Creating MySQL JDBC Connection...");
	}
	public MySQLConnection(String username, String password, String url){
			this.username = username;
			this.password = password;
			this.url = url;
			System.out.println("Creating MySQL JDBC Connection...");
	}
	public Connection getConnection(){
		return this.createConnection();
	}
	
	private  Connection createConnection(){
		Connection connection = null;
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			e.printStackTrace();
			return null;
		}
		try {
			 Properties connectionProps = new Properties();
			 	connectionProps.put("user", this.username);
			 	connectionProps.put("password", this.password);

			connection = DriverManager.getConnection(this.url, connectionProps);
	 
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			SQLExceptionHandler.handleSQLException(e);
			return null;
		}
		if (connection != null) {
			System.out.println("Database OK!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return connection;
	}
	

}
