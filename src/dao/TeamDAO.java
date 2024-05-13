package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeamDAO {
    public void addTeam(String name, String logoPath) throws SQLException {
        String sql = "INSERT INTO Teams (Name, LogoPath) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, logoPath);
            stmt.executeUpdate();
        }
    }

    
}