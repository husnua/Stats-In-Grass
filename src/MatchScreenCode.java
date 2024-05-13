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
import java.util.ArrayList;



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
import dao.Match;


public class MatchScreenCode extends Application{
    private TeamDAO teamDAO;
    private PlayerDAO playerDAO;
    private MatchDAO matchDAO;
    private PlayerStatsDAO playerStatsDAO;
    private DatabaseInitializer databaseInitializer;

    

    //Instance Variables of MatchScreenCode
    static Parent mainRoot;
    @FXML
    private Text teamAScore;
    private Text teamBScore;
  


    //team Score adjustment by arrows
    @FXML
    void teamAScoreDown(ActionEvent event) {
        Text score = (Text)mainRoot.lookup("#teamAScore");
        if(score!=null)
        score.setText("" + (Integer.parseInt(score.getText()) - 1));
    }
    @FXML
    void teamAScoreUp(ActionEvent event) {
        Text score = (Text)mainRoot.lookup("#teamAScore");
        if(score!=null)
        score.setText("" + (Integer.parseInt(score.getText()) + 1));
    }
    @FXML
    void teamBScoreDown(ActionEvent event) {
        Text score = (Text)mainRoot.lookup("#teamBScore");
        if(score!=null)
        score.setText("" + (Integer.parseInt(score.getText()) - 1));
    }
    @FXML
    void teamBScoreUp(ActionEvent event) {
        Text score = (Text)mainRoot.lookup("#teamBScore");
        if(score!=null)
        score.setText("" + (Integer.parseInt(score.getText()) + 1));
    }
    

    //SUB
    @FXML
    void matchScreenSubButtonClicked(ActionEvent event) throws Exception {
        
        Stage MatchScreenSubButtonClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenSubButtonClicked.fxml"));
        MatchScreenSubButtonClickedStage.setScene( new Scene( root, 600, 600));
        MatchScreenSubButtonClickedStage.show();
    }



    //PitchClickRelatedCodes
    @FXML
    void matchScreenPitchClicked(MouseEvent event) throws Exception {
        
        MatchScreenPitchClickedCode matchScreenPitchClickedCode = new MatchScreenPitchClickedCode( this, event.getX() + 100, event.getY() + 100);
    }

    @FXML
    void matchScreenPitchClickedGoalClicked(ActionEvent event) throws Exception {

        Text score = (Text)mainRoot.lookup("#teamAScore");
        if(score!=null)
        score.setText("" + (Integer.parseInt(score.getText()) + 1));
        MatchScreenPitchClickedGoalClickedCode matchScreenPitchClickedGoalClickedCode = new MatchScreenPitchClickedGoalClickedCode(this);
    }
    
    @FXML
    void matchScreenPitchClickedMissedClicked(ActionEvent event) throws Exception {
        //MatchScreenPitchClickedMissedClickedCode matchScreenPitchClickedMissedClickedCode = new MatchScreenPitchClickedMissedClickedCode(this);
        Stage matchScreenPitchClickedMissedClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedMissedClicked.fxml"));
        matchScreenPitchClickedMissedClickedStage.setScene( new Scene( root, 600, 400));
        matchScreenPitchClickedMissedClickedStage.show();   
    }

    @FXML
    void matchScreenPitchClickedLostClicked(MouseEvent event) throws Exception {
        Stage MatchScreenPitchClickedLostClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedLostClicked.fxml"));
        MatchScreenPitchClickedLostClickedStage.setScene( new Scene( root, 600, 500));
        MatchScreenPitchClickedLostClickedStage.show();
    }

    @FXML
    void matchScreenPitchClickedFoulClicked(MouseEvent event) throws Exception{
        Stage MatchScreenPitchClickedFoulClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedFoulClicked.fxml"));
        MatchScreenPitchClickedFoulClickedStage.setScene( new Scene( root, 600, 500));
        MatchScreenPitchClickedFoulClickedStage.show();
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
        /*DatabaseInitializer.initializeDatabase();
        this.teamDAO = new TeamDAO();
        this.playerDAO = new PlayerDAO();
        this.matchDAO = new MatchDAO();
        this.playerStatsDAO = new PlayerStatsDAO();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));

        
        teamDAO.addTeam("Team A", "path/to/logo1.jpg");
        teamDAO.addTeam("Team B", "path/to/logo2.jpg");
        matchDAO.addMatch(1, 2, 3, 2, "2024-05-13");  
        matchDAO.printAllMatches();*/  // Printing all matches
        //System.out.println(matchDAO.getMatch(5).getMatchDate());
        
        mainRoot = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        primaryStage.setTitle("Match Screen");
        primaryStage.setScene(new Scene(mainRoot, 950, 600));
        primaryStage.show();
    }
}
