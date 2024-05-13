import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;


public class MatchScreenCode extends Application{

    //Instance Variables of MatchScreenCode
    
    @FXML
    private Text teamAScore;
  

    @FXML
    void teamAScoreDown(ActionEvent event) {
        System.out.println( teamAScore.getText());
        teamAScore.setText("" + (Integer.parseInt(teamAScore.getText()) - 1));
        
    }

    @FXML
    void teamAScoreUp(ActionEvent event) {
        System.out.println( teamAScore.getText());
        teamAScore.setText("" + (Integer.parseInt(teamAScore.getText()) + 1));
        
    }

    @FXML
    void matchScreenSubButtonClicked(ActionEvent event) throws Exception {
        
        Stage MatchScreenSubButtonClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenSubButtonClicked.fxml"));
        MatchScreenSubButtonClickedStage.setScene( new Scene( root, 600, 600));
        MatchScreenSubButtonClickedStage.show();
    }

    @FXML
    void matchScreenPitchClicked(MouseEvent event) throws Exception {

        MatchScreenPitchClickedCode matchScreenPitchClickedCode = new MatchScreenPitchClickedCode( this, event.getX() + 100, event.getY() + 100);
        
    }

    
    @FXML
    void matchScreenPitchClickedGoalClicked(ActionEvent event) throws Exception {

        MatchScreenPitchClickedGoalClickedCode matchScreenPitchClickedGoalClicked = new MatchScreenPitchClickedGoalClickedCode(this);
       
       
    }
    
   
    //getters
    public Text getTextScoreOfTeamA()
    {
        return teamAScore;
    }
   

    public static void main(String[] args)  {
        System.out.println("Hello, World!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        primaryStage.setTitle("Match Screen");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }
}
