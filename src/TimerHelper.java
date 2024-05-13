import java.util.Timer;
import java.util.TimerTask;
 
public class TimerHelper extends TimerTask
{

    private MatchScreenCode matchScreenCode;

    public TimerHelper( MatchScreenCode matchScreenCode)
    {
        this.matchScreenCode = matchScreenCode;
    }

    public void run()
    {
        matchScreenCode.runMatchTime();
    }
}