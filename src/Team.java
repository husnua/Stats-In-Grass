import java.util.ArrayList;

public class Team {
    private ArrayList<Player> players;

    public Team() {
        players = new ArrayList<Player>();
        //TODO
    }

    // method for adding a player to this team
    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
