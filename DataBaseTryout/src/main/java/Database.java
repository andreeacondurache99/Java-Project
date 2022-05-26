import java.lang.module.FindException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/AppUsers";
    private static final String USER = "postgres";
    private static final String PASSWORD = "student";
    private static Connection connection = null;
    private static Database instance;

    private Database(){
        createConnection();
    }

    public static Database getInstance() throws SQLException, FindException {
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        getInstance();
        return connection;}

    private static void createConnection(){
        try{
            Properties props = new Properties();
            props.setProperty("user",USER);
            props.setProperty("password",PASSWORD);
            connection = DriverManager.getConnection(URL, props);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
