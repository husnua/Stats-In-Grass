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


//db related conns
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import dao.TeamDAO;
import dao.PlayerDAO;
import dao.MatchDAO;
import dao.PlayerStatsDAO;
import dao.DatabaseInitializer;


public class MatchScreenCode extends Application{
    private TeamDAO teamDAO;
    private PlayerDAO playerDAO;
    private MatchDAO matchDAO;
    private PlayerStatsDAO playerStatsDAO;
    private DatabaseInitializer databaseInitializer;

    

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

     @FXML
    void matchScreenPitchClicked(MouseEvent event) throws Exception {
        Stage matchScreenPitchClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClicked.fxml"));
        matchScreenPitchClickedStage.setScene( new Scene( root, 300, 300));
        matchScreenPitchClickedStage.setX( event.getX() + 100);
        matchScreenPitchClickedStage.setY( event.getY() + 100);
        matchScreenPitchClickedStage.show();


    }
    

    public static void main(String[] args)  {
       
        System.out.println("Hello, World!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseInitializer.initializeDatabase();
        this.teamDAO = new TeamDAO();
        this.playerDAO = new PlayerDAO();
        this.matchDAO = new MatchDAO();
        this.playerStatsDAO = new PlayerStatsDAO();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        primaryStage.setTitle("Match Screen");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();

    
        
    }
}
