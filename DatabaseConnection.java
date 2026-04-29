import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = 
        "jdbc:sqlserver://DESKTOP-8604OSL;databaseName=GymDB;" +
        "user=Gymboy;password=gym1234;" +
        "trustServerCertificate=true;";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Connected Successfully!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
            return null;
        }
    }
}