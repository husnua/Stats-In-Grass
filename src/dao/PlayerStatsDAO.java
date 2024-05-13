package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerStatsDAO {
    public void addPlayerStats(int playerId, int matchId, int goals, int assists, int minutesPlayed, int yellowCards, int redCards) throws SQLException {
        String sql = "INSERT INTO PlayerStats (PlayerID, MatchID, Goals, Assists, MinutesPlayed, YellowCards, RedCards) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
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
}