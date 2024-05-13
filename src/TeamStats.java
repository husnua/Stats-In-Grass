public class TeamStats {
    private int totalGoal = 0;
    private int totalConceded = 0;
    private int win = 0;
    private int loss = 0;
    private int draw = 0;
    private int totalMatch = 0;
    private int yellows = 0;
    private int reds = 0;
    private int fouls = 0;
    private int ballLoss = 0;
    private int missedGoal = 0;

    public TeamStats(){}

    public int getTotalGoal() {
        return totalGoal;
    }
    public int getTotalConceded() {
        return totalConceded;
    }
    public int getWin() {
        return win;
    }
    public int getLoss() {
        return loss;
    }
    public int getDraw() {
        return draw;
    }
    public int getTotalMatch() {
        return totalMatch;
    }
    public int getYellows() {
        return yellows;
    }
    public int getReds() {
        return reds;
    }
    public int getFouls() {
        return fouls;
    }
    public int getBallLoss() {
        return ballLoss;
    }
    public int getMissed() {
        return missedGoal;
    }


    public void increaseGoal(){
        totalGoal++;
    }
    public void decreaseGoal(){
        if( totalGoal > 0)
            totalGoal--;
    }

    public void increaseConcededGoal(){
        totalConceded++;
    }
    public void decreaseConcededGoal(){
        if( totalConceded > 0)
            totalConceded--;
    }
    public void addFoul(){
        fouls++;
    }

    public void increaseFoul(){
        fouls++;
    }
    public void decreaseFoul(){
        if( fouls > 0)
            fouls--;
    }
    public void increaseLoss(){
        ballLoss++;
    }
    public void decreaseLoss(){
        if( ballLoss > 0)
            ballLoss--;
    }
    public void increaseMissed(){
        missedGoal++;
    }
    public void decreaseMissed(){
        if( missedGoal > 0)
            missedGoal--;
    }
}
