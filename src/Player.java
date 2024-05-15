public class Player {
    private String name;
    private int jerseyNumber;
    PlayerStats stats;
    public Player( dao.Player p){
        name=p.getName();
        jerseyNumber=Integer.parseInt(p.getPosition());
        stats = new PlayerStats();

        
    }
    public Player ( String namee, int jersey) {
        name = namee;
        jerseyNumber = jersey;
        stats = new PlayerStats();
    }

    public String getName () {
        return this.name;
    }

    public int getJerseyNumber() {
        return this.jerseyNumber;
    }

    public PlayerStats getStats ()
    {
        return this.stats;
    }
    
}
