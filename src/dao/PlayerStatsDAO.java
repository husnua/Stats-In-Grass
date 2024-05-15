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
    public void updatePlayerStats(PlayerStats playerStats) throws SQLException {
        String sql = "UPDATE PlayerStats SET PlayerID = ?, MatchID = ?, Goals = ?, Assists = ?, MinutesPlayed = ?, YellowCards = ?, RedCards = ? WHERE StatsID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerStats.getPlayerId());
            stmt.setInt(2, playerStats.getMatchId());
            stmt.setInt(3, playerStats.getGoals());
            stmt.setInt(4, playerStats.getAssists());
            stmt.setInt(5, playerStats.getMinutesPlayed());
            stmt.setInt(6, playerStats.getYellowCards());
            stmt.setInt(7, playerStats.getRedCards());
            stmt.setInt(8, playerStats.getStatsId());
            stmt.executeUpdate();
        }
    }

    public void deletePlayerStats(int statsId) throws SQLException {
        String sql = "DELETE FROM PlayerStats WHERE StatsID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, statsId);
            stmt.executeUpdate();
        }
    }

    public int countPlayerStats() throws SQLException {
        String sql = "SELECT COUNT(*) FROM PlayerStats";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public ArrayList<PlayerStats> getMatchPlayerStats(int matchId) throws SQLException {
        ArrayList<PlayerStats> playerStats = new ArrayList<>();
        String sql = "SELECT StatsID, PlayerID, MatchID, Goals, Assists, MinutesPlayed, YellowCards, RedCards " +
                     "FROM PlayerStats " +
                     "WHERE MatchID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                playerStats.add(new PlayerStats(
                    rs.getInt("StatsID"),
                    rs.getInt("PlayerID"),
                    rs.getInt("MatchID"),
                    rs.getInt("Goals"),
                    rs.getInt("Assists"),
                    rs.getInt("MinutesPlayed"),
                    rs.getInt("YellowCards"),
                    rs.getInt("RedCards")
                ));
            }
        }
        return playerStats;
    }
    
}


    


}