import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MatchScreenPitchClickedGoalClickedCode{
    
    private MatchScreenCode matchScreenCode;
    private Stage matchScreenPitchClickedGoalClickedStage; 
    

    public MatchScreenPitchClickedGoalClickedCode( MatchScreenCode matchScreenCode) throws Exception
    {
        this.matchScreenCode = matchScreenCode;
        Stage matchScreenPitchClickedGoalClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedGoalClicked.fxml"));
        matchScreenPitchClickedGoalClickedStage.setScene( new Scene( root, 300, 300));
        matchScreenPitchClickedGoalClickedStage.show();   
        
    }
   

}
