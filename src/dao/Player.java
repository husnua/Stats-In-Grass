package dao;

public class Player {
    private int playerId;
    private int teamId;
    private String name;
    private String position;

    public Player(int playerId, int teamId, String name, String position) {
        this.playerId = playerId;
        this.teamId = teamId;
        this.name = name;
        this.position = position;
    }

    // Getters
    public int getPlayerId() { return playerId; }
    public int getTeamId() { return teamId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
}
