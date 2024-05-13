package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerDAO {
    public void addPlayer(int teamId, String name, String position) throws SQLException {
        String sql = "INSERT INTO Players (TeamID, Name, Position) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            stmt.setString(2, name);
            stmt.setString(3, position);
            stmt.executeUpdate();
        }
    }
}