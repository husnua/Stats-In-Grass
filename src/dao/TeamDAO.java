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

    public void updateTeam(Team team) throws SQLException {
        String sql = "UPDATE Teams SET Name = ?, LogoPath = ? WHERE TeamID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, team.getName());
            stmt.setString(2, team.getLogoPath());
            stmt.setInt(3, team.getTeamId());
            stmt.executeUpdate();
        }
    }

    public void deleteTeam(int teamId) throws SQLException {
        String sql = "DELETE FROM Teams WHERE TeamID = ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.executeUpdate();
        }
    }

    public int countTeams() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Teams";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public ArrayList<Team> searchTeamsByName(String name) throws SQLException {
        ArrayList<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM Teams WHERE Name LIKE ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teams.add(new Team(rs.getInt("TeamID"), rs.getString("Name"), rs.getString("LogoPath")));
            }
        }
        return teams;
    }
    public Team searchTeamByName(String name) throws SQLException {
        String sql = "SELECT * FROM Teams WHERE Name LIKE ?";
        try (Connection conn = DatabaseInitializer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return (new Team(rs.getInt("TeamID"), rs.getString("Name"), rs.getString("LogoPath")));
            }
        }
        return null;
    }

    

    
}