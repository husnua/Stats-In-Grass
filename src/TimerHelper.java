
import java.util.TimerTask;
 
public class TimerHelper extends TimerTask
{
    int second;

    private MatchScreenCode matchScreenCode;

    public TimerHelper( MatchScreenCode matchScreenCode)
    {
        second = 0;
        this.matchScreenCode = matchScreenCode;
    }

    public void run()
    {
        second++;
        matchScreenCode.runMatchTime();
    }

    public int getTime(){
        return second;
    }
}