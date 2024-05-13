import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MatchScreenPitchClickedMissedClickedCode {
    
    private MatchScreenCode matchScreenCode;
    private Stage matchScreenPitchClickedMissedClickedStage; 

    public MatchScreenPitchClickedMissedClickedCode( MatchScreenCode matchScreenCode) throws Exception
    {
        this.matchScreenCode = matchScreenCode;
        Stage matchScreenPitchClickedMissedClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedMissedClicked.fxml"));
        matchScreenPitchClickedMissedClickedStage.setScene( new Scene( root, 600, 400));
        matchScreenPitchClickedMissedClickedStage.show();   
    }
}
