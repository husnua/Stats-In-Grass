import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;


public class MatchScreenCode extends Application{

    //Instance Variables of MatchScreenCode
    static Parent mainRoot;
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

        Stage matchScreenPitchClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClicked.fxml"));
        matchScreenPitchClickedStage.setScene( new Scene( root, 300, 300));
        matchScreenPitchClickedStage.setX( event.getX() + 100 + 100);
        matchScreenPitchClickedStage.setY( event.getY() + 100);
        matchScreenPitchClickedStage.show();  
        //MatchScreenPitchClickedCode matchScreenPitchClickedCode = new MatchScreenPitchClickedCode( this, event.getX() + 100, event.getY() + 100);
    }

    @FXML
    void matchScreenPitchClickedGoalClicked(ActionEvent event) throws Exception {

        //MatchScreenPitchClickedGoalClickedCode matchScreenPitchClickedGoalClickedCode = new MatchScreenPitchClickedGoalClickedCode(this);
        Text score = (Text)mainRoot.lookup("#teamAScore");
        if(score!=null)
        score.setText("" + (Integer.parseInt(score.getText()) + 1));
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

        mainRoot = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        primaryStage.setTitle("Match Screen");
        primaryStage.setScene(new Scene(mainRoot, 950, 600));
        primaryStage.show();
    }
}
