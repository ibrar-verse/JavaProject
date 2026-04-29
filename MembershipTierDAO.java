import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipTierDAO {

    // Get all tiers from database
    public List<String[]> getAllTiers() {
        List<String[]> tiers = new ArrayList<>();
        String sql = "SELECT * FROM Membership_Tiers";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String[] tier = {
                    rs.getString("Tier_ID"),
                    rs.getString("Tier_Name"),
                    rs.getString("Monthly_Price"),
                    rs.getString("Access_Level")
                };
                tiers.add(tier);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return tiers;
    }

    // Add new tier
    public boolean addTier(String name, double price, int accessLevel) {
        String sql = "INSERT INTO Membership_Tiers (Tier_Name, Monthly_Price, Access_Level) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, accessLevel);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Delete tier
    public boolean deleteTier(int tierID) {
        String sql = "DELETE FROM Membership_Tiers WHERE Tier_ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tierID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}