import java.sql.*;
import oracle.jdbc.driver.*;

public class ExamenJdbc {
    
    private final static String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final static String JDBC_URL = "jdbc:oracle:thin:@kirov:1521:NFA011";
    private Connection connexion = null;
    
    public ExamenJdbc() {}
    
    public void initConnection(String login, String password) throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            connexion = DriverManager.getConnection(JDBC_URL, login, password);
        }
        catch (ClassNotFoundException e) {
            throw new SQLException("Driver " + e.getMessage() + " absent !");
        }
    }
    
    public void closeConnection() {
        try {
            if (connexion != null) connexion.close();
        }
        catch (SQLException e) {}
    }
    
    public void nourrirTousPoissons() throws SQLException {
        int numeroPoisson;
        Statement stmt = null;
        CallableStatement callStmt = null;
        ResultSet rSet = null;
        String sStmt = "select distinct nopoisson from Garderie where date_debut <= current_date AND date_fin >= current_date";
        String pCall = "{call NourirPoisson(?,?,?,?)}";
        try {
            stmt = connexion.createStatement();
            rSet = stmt.executeQuery(sStmt);
            callStmt = connexion.prepareCall(pCall);
            
            while (rSet.next()) {
                numeroPoisson = rSet.getInt(1);
                callStmt.setInt(1, numeroPoisson);
                callStmt.execute();
                System.out.println("Poisson " + numeroPoisson + " nouri");
            }
        }
        catch (SQLException e) {
            throw e;
        }
        finally {}
        
        try {
            if (callStmt != null) callStmt.close();
        }
        catch (SQLException e) {}
        
        try {
            if (rSet != null) rSet.close();
        }
        catch (SQLException e) {}
        
        try {
            if (stmt != null) stmt.close();
        }
        catch (SQLException e) {}
    }
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Utilisation : java ExamenJdbc [login] [pass]");
            System.exit(1);
        }
        ExamenJdbc monExamen = new ExamenJdbc();
        try {
            monExamen.initConnection(args[0], args[1]);
            monExamen.nourrirTousPoissons();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            monExamen.closeConnection();
        }
    }
}