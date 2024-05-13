package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MatchDAO {
    public void addMatch(int team1Id, int team2Id, String scores, String venue, String date) throws SQLException {
        String sql = "INSERT INTO Matches (Team1ID, Team2ID, Scores, Venue, Date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, team1Id);
            stmt.setInt(2, team2Id);
            stmt.setString(3, scores);
            stmt.setString(4, venue);
            stmt.setString(5, date);
            stmt.executeUpdate();
        }
    }
}