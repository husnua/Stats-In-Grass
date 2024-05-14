public class Player {
    private String name;
    private int jerseyNumber;
    private Team team;
    PlayerStats stats;
    
    public Player ( String namee, int jersey, Team t) {
        name = namee;
        jerseyNumber = jersey;
        team = t;
        t.addPlayer( this );
    }

    public String getName () {
        return this.name;
    }

    public int getJerseyNumber() {
        return this.jerseyNumber;
    }

    public Team getTeam () {
        return this.team;
    }
    
}
