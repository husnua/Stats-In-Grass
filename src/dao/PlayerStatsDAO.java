package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PlayerStatsDAO {
    public void addPlayerStats(int playerId, int matchId, int goals, int assists, int minutesPlayed, int yellowCards, int redCards) throws SQLException {
        String sql = "INSERT INTO PlayerStats (PlayerID, MatchID, Goals, Assists, MinutesPlayed, YellowCards, RedCards) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.setInt(2, matchId);
            stmt.setInt(3, goals);
            stmt.setInt(4, assists);
            stmt.setInt(5, minutesPlayed);
            stmt.setInt(6, yellowCards);
            stmt.setInt(7, redCards);
            stmt.executeUpdate();
        }
    }

     public PlayerStats getPlayerStats(int statsId) {
        String sql = "SELECT * FROM PlayerStats WHERE StatsID = ?";
        PlayerStats playerStats = null;
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, statsId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                playerStats = new PlayerStats(
                    rs.getInt("StatsID"),
                    rs.getInt("PlayerID"),
                    rs.getInt("MatchID"),
                    rs.getInt("Goals"),
                    rs.getInt("Assists"),
                    rs.getInt("MinutesPlayed"),
                    rs.getInt("YellowCards"),
                    rs.getInt("RedCards")
                );
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return playerStats;
    }

    


}