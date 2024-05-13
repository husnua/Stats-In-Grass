import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;


/**
 * MatchScreenPitchClicked
 */
public class MatchScreenPitchClickedCode {

    private MatchScreenCode matchScreenCode;
    private Stage matchScreenPitchClickedStage; 
    Text teamAScore;

   

    public MatchScreenPitchClickedCode( MatchScreenCode matchScreenCode ,double X, double Y) throws Exception
    {
        this.matchScreenCode = matchScreenCode;
        matchScreenPitchClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClicked.fxml"));
        matchScreenPitchClickedStage.setScene( new Scene( root, 300, 300));
        matchScreenPitchClickedStage.setX( X + 100);
        matchScreenPitchClickedStage.setY( Y + 100);
        matchScreenPitchClickedStage.show();   
    }
    
    /*@FXML
    void matchScreenPitchClickedGoalClicked(ActionEvent event) throws Exception {

        //MatchScreenPitchClickedGoalClickedCode matchScreenPitchClickedGoalClickedCode = new MatchScreenPitchClickedGoalClickedCode(this);
        matchScreenCode.teamAScoreUp(null);
        
    }*/

   
   

   
  
   
}