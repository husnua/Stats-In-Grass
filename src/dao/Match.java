package dao;

public class Match {
    private int matchId;
    private int team1Id;
    private int team2Id;
    private int team1Score;
    private int team2Score;
    private String matchDate;

    public Match(int matchId, int team1Id, int team2Id, int team1Score, int team2Score, String matchDate) {
        this.matchId = matchId;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.matchDate = matchDate;
    }

    // Getters
    public int getMatchId() { return matchId; }
    public int getTeam1Id() { return team1Id; }
    public int getTeam2Id() { return team2Id; }
    public int getTeam1Score() { return team1Score; }
    public int getTeam2Score() { return team2Score; }
    public String getMatchDate() { return matchDate; }
}
