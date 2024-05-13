package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TeamDAO {
    public void addTeam(String name, String logoPath) throws SQLException {
        String sql = "INSERT INTO Teams (Name, LogoPath) VALUES (?, ?)";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, logoPath);
            stmt.executeUpdate();
        }
    }
    public Team getTeam(int teamId) {
        String sql = "SELECT * FROM Teams WHERE TeamID = ?";
        Team team = null;
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                team = new Team(
                    rs.getInt("TeamID"),
                    rs.getString("Name"),
                    rs.getString("LogoPath")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return team;
    }

    

    
}