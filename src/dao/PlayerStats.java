package dao;

public class PlayerStats {
    private int statsId;
    private int playerId;
    private int matchId;
    private int goals;
    private int assists;
    private int minutesPlayed;
    private int yellowCards;
    private int redCards;

    public PlayerStats(int statsId, int playerId, int matchId, int goals, int assists, int minutesPlayed, int yellowCards, int redCards) {
        this.statsId = statsId;
        this.playerId = playerId;
        this.matchId = matchId;
        this.goals = goals;
        this.assists = assists;
        this.minutesPlayed = minutesPlayed;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }

    // Getters
    public int getStatsId() { return statsId; }
    public int getPlayerId() { return playerId; }
    public int getMatchId() { return matchId; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }
    public int getMinutesPlayed() { return minutesPlayed; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }
}
