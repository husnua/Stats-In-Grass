import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;


public class MatchScreenCode extends Application{

    @FXML
    private Text teamAScore;

    @FXML
    void teamAScoreDown(ActionEvent event) {
        teamAScore.setText("" + (Integer.parseInt(teamAScore.getText()) - 1));
    }

    @FXML
    void teamAScoreUp(ActionEvent event) {
        teamAScore.setText("" + (Integer.parseInt(teamAScore.getText()) + 1));
    }

    @FXML
    void matchScreenSubButtonClicked(ActionEvent event) throws Exception {
        
        Stage MatchScreenSubButtonClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenSubButtonClicked.fxml"));
        MatchScreenSubButtonClickedStage.setScene( new Scene( root, 600, 600));
        MatchScreenSubButtonClickedStage.show();
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
