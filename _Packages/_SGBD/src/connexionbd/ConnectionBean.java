package connexionbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class ConnectionBean implements HttpSessionBindingListener {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String dbURL = "jdbc:mysql://127.0.0.1:8889/Ecommerce";
	private static final String login = "root";        // -- A CHANGER --
	private static final String password = "root";     // -- A CHANGER --

	private Connection connection;
	private Statement statement;

	public ConnectionBean() {
		try {
			Class.forName(driver);
			connection=DriverManager.getConnection(dbURL,login,password);
			statement=connection.createStatement();
		}
		catch (ClassNotFoundException e) {
			System.err.println("ConnectionBean : pilote non disponible");
			connection = null;
		}
		catch (SQLException e) {
			System.err.println("ConnectionBean : pilote non charge");
			System.err.println(e);
			connection = null;
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}

	public void setAutoCommit(boolean autoCommit)
	throws SQLException {
		connection.setAutoCommit(autoCommit );
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}

	public int executeUpdate(String sql) throws SQLException {
		return statement.executeUpdate(sql);
	}

	public void valueBound(HttpSessionBindingEvent event) {
		System.err.println("ConnectionBean : Erreur dans la methode valueBound");
		try {
			if (connection == null || connection.isClosed()) {
				connection =
					DriverManager.getConnection(dbURL,login,password);
				statement = connection.createStatement();
			}
		}
		catch (SQLException e) { connection = null; }
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		try {
			connection.close();
		}
		catch (SQLException e) { }
		finally { connection = null; }
	}

	protected void finalize() {
		try {
			connection.close();
		}
		catch (SQLException e) { }
	}
}