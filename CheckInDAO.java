import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckInDAO {

    // Log a check-in
    public boolean checkIn(int memberID) {
        String sql = "INSERT INTO CheckIn_Logs (Member_ID) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Get all checkins
    public List<String[]> getAllCheckIns() {
        List<String[]> logs = new ArrayList<>();
        String sql = "SELECT cl.Log_ID, m.First_Name, m.Last_Name, cl.CheckIn_Time " +
                     "FROM CheckIn_Logs cl JOIN Members m ON cl.Member_ID = m.Member_ID " +
                     "ORDER BY cl.CheckIn_Time DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] log = {
                    rs.getString("Log_ID"),
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("CheckIn_Time")
                };
                logs.add(log);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return logs;
    }

    // Run archive procedure
    public void runArchive() {
        String sql = "EXEC AutoArchiveCheckIns";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Archive completed!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}