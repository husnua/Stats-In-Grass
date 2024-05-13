package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PlayerDAO {
    public void addPlayer(int teamId, String name, String position) throws SQLException {
        String sql = "INSERT INTO Players (TeamID, Name, Position) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setString(2, name);
            stmt.setString(3, position);
            stmt.executeUpdate();
        }
    }
    public Player getPlayer(int playerId) {
        String sql = "SELECT * FROM Players WHERE PlayerID = ?";
        Player player = null;
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                player = new Player(
                    rs.getInt("PlayerID"),
                    rs.getInt("TeamID"),
                    rs.getString("Name"),
                    rs.getString("Position")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return player;
    }
}