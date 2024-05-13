package dao;

public class Team {
    private int teamId;
    private String name;
    private String logoPath;

    public Team(int teamId, String name, String logoPath) {
        this.teamId = teamId;
        this.name = name;
        this.logoPath = logoPath;
    }

    // Getters
    public int getTeamId() { return teamId; }
    public String getName() { return name; }
    public String getLogoPath() { return logoPath; }
}
