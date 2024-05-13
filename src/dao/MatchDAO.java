package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MatchDAO {
    public void addMatch(int team1Id, int team2Id, int team1Score, int team2Score, String date) throws SQLException {
        String sql = "INSERT INTO Matches (Team1ID, Team2ID, Team1Score, Team2Score, MatchDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, team1Id);      // Team1ID
                stmt.setInt(2, team2Id);      // Team2ID
                stmt.setInt(3, team1Score);   // Team1Score
                stmt.setInt(4, team2Score);   // Team2Score
                stmt.setString(5, date);      // MatchDate
                stmt.executeUpdate();
        }
    }

    public void printAllMatches() throws SQLException {
        String sql = "SELECT * FROM Matches";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Match ID: " + rs.getInt("MatchID") + ", Team1 ID: " + rs.getInt("Team1ID")
                    + ", Team2 ID: " + rs.getInt("Team2ID") + ", Team1 Score: " + rs.getInt("Team1Score")
                    + ", Team2 Score: " + rs.getInt("Team2Score") + ", Date: " + rs.getString("MatchDate"));
            }
        }
    }

    public Match getMatch(int matchId) throws SQLException {
        String sql = "SELECT * FROM Matches WHERE MatchID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Match(
                    rs.getInt("MatchID"),
                    rs.getInt("Team1ID"),
                    rs.getInt("Team2ID"),
                    rs.getInt("Team1Score"),
                    rs.getInt("Team2Score"),
                    rs.getString("MatchDate")
                );
            }
        }
        return null;  
    }

    public ArrayList<Match> getAllMatches() throws SQLException {
        ArrayList<Match> matches = new ArrayList<>();
        String sql = "SELECT * FROM Matches";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                matches.add(new Match(
                    rs.getInt("MatchID"),
                    rs.getInt("Team1ID"),
                    rs.getInt("Team2ID"),
                    rs.getInt("Team1Score"),
                    rs.getInt("Team2Score"),
                    rs.getString("MatchDate")
                ));
            }
        }
        return matches;  
    }

    public void updateMatch(Match match) throws SQLException {
        String sql = "UPDATE Matches SET Team1ID = ?, Team2ID = ?, Team1Score = ?, Team2Score = ?, MatchDate = ? WHERE MatchID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, match.getTeam1Id());
            stmt.setInt(2, match.getTeam2Id());
            stmt.setInt(3, match.getTeam1Score());
            stmt.setInt(4, match.getTeam2Score());
            stmt.setString(5, match.getMatchDate());
            stmt.setInt(6, match.getMatchId());
            stmt.executeUpdate();
        }
    }

    public void deleteMatch(int matchId) throws SQLException {
        String sql = "DELETE FROM Matches WHERE MatchID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matchId);
            stmt.executeUpdate();
        }
    }

    public int countMatches() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Matches";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    
}

