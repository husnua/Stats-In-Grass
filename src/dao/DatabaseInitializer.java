package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseInitializer {

    private static final String URL = "jdbc:mysql://localhost:3306/football_stats"; 
    static final String USER = "root";
    static final String PASS = "root";
    private static final String DATABASE_NAME = "football_stats";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)){
            Statement stmt = conn.createStatement();
            
            // Create the database if it doesn't exist
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
            stmt.execute("USE " + DATABASE_NAME);
            
            // Create the 'Teams' table if it doesn't exist
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Teams (" +
                "TeamID INT AUTO_INCREMENT PRIMARY KEY, " +
                "Name VARCHAR(255), " +
                "LogoPath VARCHAR(255))"
            );
            
            // Create the 'Players' table if it doesn't exist
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Players (" +
                "PlayerID INT AUTO_INCREMENT PRIMARY KEY, " +
                "TeamID INT, " +
                "Name VARCHAR(255), " +
                "Position VARCHAR(50), " +
                "FOREIGN KEY (TeamID) REFERENCES Teams(TeamID))"
            );
            
            // Create the 'Matches' table if it doesn't exist
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Matches (" +
                "MatchID INT AUTO_INCREMENT PRIMARY KEY, " +
                "Team1ID INT, " +
                "Team2ID INT, " +
                "Team1Score INT DEFAULT 0, " +
                "Team2Score INT DEFAULT 0, " +
                "MatchDate DATE, " +
                "FOREIGN KEY (Team1ID) REFERENCES Teams(TeamID), " +
                "FOREIGN KEY (Team2ID) REFERENCES Teams(TeamID))"
            );
            
            // Create the 'PlayerStats' table if it doesn't exist
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS PlayerStats (" +
                "StatsID INT AUTO_INCREMENT PRIMARY KEY, " +
                "PlayerID INT, " +
                "MatchID INT, " +
                "Goals INT, " +
                "Assists INT, " +
                "MinutesPlayed INT, " +
                "YellowCards INT, " +
                "RedCards INT, " +
                "FOREIGN KEY (PlayerID) REFERENCES Players(PlayerID), " +
                "FOREIGN KEY (MatchID) REFERENCES Matches(MatchID))"
            );
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
