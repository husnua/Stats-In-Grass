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

    public void updatePlayer(Player player) throws SQLException {
        String sql = "UPDATE Players SET TeamID = ?, Name = ?, Position = ? WHERE PlayerID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, player.getTeamId());
            stmt.setString(2, player.getName());
            stmt.setString(3, player.getPosition());
            stmt.setInt(4, player.getPlayerId());
            stmt.executeUpdate();
        }
    }

    public void deletePlayer(int playerId) throws SQLException {
        String sql = "DELETE FROM Players WHERE PlayerID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        }
    }

    public int countPlayersByTeam(int teamId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Players WHERE TeamID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    public ArrayList<Player> getTeamPlayers(int id) throws SQLException {
        ArrayList<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM Players WHERE TeamID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                players.add(new Player(rs.getInt("PlayerID"), rs.getInt("TeamID"), rs.getString("Name"), rs.getString("Position")));
            }
        }
        return players;
    }
    public ArrayList<Player> searchPlayersByName(String name) throws SQLException {
        ArrayList<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM Players WHERE Name LIKE ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                players.add(new Player(rs.getInt("PlayerID"), rs.getInt("TeamID"), rs.getString("Name"), rs.getString("Position")));
            }
        }
        return players;
    }
}