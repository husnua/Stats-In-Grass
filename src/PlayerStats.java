public class PlayerStats {
    private int goal = 0;
    private int steal = 0;
    private int turnover = 0;
    private int assist = 0;
    private double playedTime = 0;
    private int playedMatch = 0;
    private int fouls = 0;
    private int yellow = 0;
    private int red = 0;
    private int shotOnTarget;
    private int totalShot = 0;
    

    public PlayerStats(){}

    public int getGoal() {
        return goal;
    }

    public int getSteal() {
        return steal;
    }

    public int getTurnover() {
        return turnover;
    }

    public int getAssist()
    {
        return assist;
    }

    public double getPlayedTime() {
        return playedTime;
    }

    public int getPlayedMatch() {
        return playedMatch;
    }

    public int getFouls() {
        return fouls;
    }

    public int getYellow() {
        return yellow;
    }

    public int getRed() {
        return red;
    }

    public int getShotOnTarget()
    {
        return shotOnTarget;
    }

    public int getTotalShot()
    {
        return totalShot;
    }

    

    //setters
    public void scoreGoal(){
        goal++;
    }

    public void makeAssist(){
        assist++;
    }

    public void lostBall(){
        turnover++;
    }

    public void stealBall(){
        steal++;
    }
    
    public void foulDone(){
        fouls++;
    }

    public void yellowCardTaken()
    {
        yellow++;
    }

    public void redCardTaken()
    {
        red++;
    }

    public void makeShot()
    {
        totalShot++;
    }

    public void makeShotOnTarget()
    {
        shotOnTarget++;
    }

    public void addPlayedTime( int givenPlayedTime)
    {
        this.playedTime += givenPlayedTime;
    }

    

}
