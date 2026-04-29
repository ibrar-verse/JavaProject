import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // Get all members
    public List<String[]> getAllMembers() {
        List<String[]> members = new ArrayList<>();
        String sql = "SELECT m.Member_ID, m.First_Name, m.Last_Name, m.Email, m.Phone, m.Join_Date, t.Tier_Name " +
                     "FROM Members m JOIN Membership_Tiers t ON m.Tier_ID = t.Tier_ID";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] member = {
                    rs.getString("Member_ID"),
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Join_Date"),
                    rs.getString("Tier_Name")
                };
                members.add(member);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return members;
    }

    // Add new member
    public boolean addMember(String firstName, String lastName, 
                              String email, String phone, int tierID) {
        String sql = "INSERT INTO Members (First_Name, Last_Name, Email, Phone, Tier_ID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setInt(5, tierID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Delete member
    public boolean deleteMember(int memberID) {
        String sql = "DELETE FROM Members WHERE Member_ID = ?";
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

    // Search member by name
    public List<String[]> searchMember(String keyword) {
        List<String[]> members = new ArrayList<>();
        String sql = "SELECT m.Member_ID, m.First_Name, m.Last_Name, m.Email, m.Phone, m.Join_Date, t.Tier_Name " +
                     "FROM Members m JOIN Membership_Tiers t ON m.Tier_ID = t.Tier_ID " +
                     "WHERE m.First_Name LIKE ? OR m.Last_Name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] member = {
                    rs.getString("Member_ID"),
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Join_Date"),
                    rs.getString("Tier_Name")
                };
                members.add(member);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return members;
    }
}
