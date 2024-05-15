public class ActionLog {
    
    private static int ID = 0;
    private String type;
    private MatchScreenCode matchScreenCode;
    private Player player;

    public ActionLog( String givenType, Player givenPlayer)
    {
        type = givenType;
        ID++;
        player = givenPlayer;
    }

    //getters
    public String getType()
    {
        return type;
    }

    public Player getPlayer()
    {
        return player;
    }
}
