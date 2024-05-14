import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
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
    static Parent goalClickedRoot;
    private String teamAShortName;
    private String teamBShortName;
    private Timer timer = new Timer();
    private TimerHelper timerHelper = new TimerHelper( this);
    private Stage matchScreenPitchClickedGoalClickedStage;
    private Stage matchScreenPitchClickedStage;


    @FXML
    private Text teamAScore;
    private Text teamBScore;
    private Text matchHour;
    private Text matchSecond;
    private Text matchScreenTeamAShortName;
    private Text matchScreenTeamBShortName;




    //deneme
    private Team teamA = new Team();
    private Team teamB = new Team();
    private Player[] teamAPlayers = new Player[12];
    private Player[] teamBPlayers = new Player[12];
    


    //constructor
    public MatchScreenCode()
    {
        teamAShortName = "GS";
        teamBShortName = "FB";
    }
     
    //consturcting teams
    public void constructTeam()
    {   
        Text textTeamA = (Text)mainRoot.lookup("#matchScreenTeamAShortName");
        Text textTeamB = (Text)mainRoot.lookup("#matchScreenTeamBShortName");

        textTeamA.setText(teamAShortName);
        textTeamB.setText(teamBShortName);



        for ( int i = 0; i < teamAPlayers.length; i ++)
        {
            Button buttonTeamA = (Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i);
            Button buttonTeamB = (Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i);
            if ( buttonTeamA != null)
            {
                buttonTeamA.setText("" + i);
                teamAPlayers[i] = new Player("Hüsnü Akseli" + i, i, teamA);
            }
            if( buttonTeamB != null)
            {   
                buttonTeamB.setText("" + (i + 1));
                teamBPlayers[i] = new Player("Yunus Karamatov" + i , i, teamB);
            }
        }
    }

       
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
        {
            score.setText("" + (Integer.parseInt(score.getText()) + 1));
        }
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
    

    //Timer matchTime setter
    public void runMatchTime()
    {
        
        Text second = (Text)mainRoot.lookup("#matchSecond");
        Text hour = (Text)mainRoot.lookup("#matchHour");

        if( Integer.parseInt(second.getText()) < 59)
        {
            
            if(second!=null)
            {
                second.setText( ((Integer.parseInt(second.getText())) + 1)  + "");
            }
        }
        else
        {
            if(second!=null)
            {
                second.setText( 0 + "");
            }
            if(hour!=null)
            {
                hour.setText( ((Integer.parseInt(hour.getText())) + 1)  + "");
            }
        }
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
        
        //MatchScreenPitchClickedCode matchScreenPitchClickedCode = new MatchScreenPitchClickedCode( this, event.getX() + 100, event.getY()
        matchScreenPitchClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClicked.fxml"));
        matchScreenPitchClickedStage.setScene( new Scene( root, 300, 300));
        matchScreenPitchClickedStage.setX( event.getX() + 100);
        matchScreenPitchClickedStage.setY( event.getY() + 100);
        matchScreenPitchClickedStage.show();   
    }

    @FXML
    void matchScreenPitchClickedGoalClicked(ActionEvent event) throws Exception {

        goalClickedRoot = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedGoalClicked.fxml")); 
        
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        //MatchScreenPitchClickedGoalClickedCode matchScreenPitchClickedGoalClickedCode = new MatchScreenPitchClickedGoalClickedCode(this);
        matchScreenPitchClickedGoalClickedStage = new Stage();
        matchScreenPitchClickedGoalClickedStage.setScene( new Scene( goalClickedRoot, 600, 491));
        matchScreenPitchClickedGoalClickedStage.show();   
    }

    @FXML 
    void matchScreenPitchClickedGoalClickedTeamAClicked ( ActionEvent event) throws Exception{
        
        Text score = (Text)mainRoot.lookup("#teamAScore");
        if(score!=null)
        {
            score.setText("" + (Integer.parseInt(score.getText()) + 1));
        }

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamAPlayer = (Button)goalClickedRoot.lookup("#matchScreenTeamPlayerButtonGoal" + i );
            buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
        }
    }



    @FXML 
    void matchScreenPitchClickedGoalClickedTeamBClicked ( ActionEvent event) throws Exception{
        Text score = (Text)mainRoot.lookup("#teamBScore");
        if(score!=null)
        {
            score.setText("" + (Integer.parseInt(score.getText()) + 1));
        }

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamBPlayer = (Button)goalClickedRoot.lookup("#matchScreenTeamPlayerButtonGoal" + i );
            buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
        }

    }

    @FXML
    void matchScreenPitchClickedGoalClickedExit(ActionEvent event) {
        //closing goalScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }   
    
    @FXML
    void matchScreenPitchClickedMissedClicked(ActionEvent event) throws Exception {
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        //MatchScreenPitchClickedMissedClickedCode matchScreenPitchClickedMissedClickedCode = new MatchScreenPitchClickedMissedClickedCode(this);
        Stage matchScreenPitchClickedMissedClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedMissedClicked.fxml"));
        matchScreenPitchClickedMissedClickedStage.setScene( new Scene( root, 600, 400));
        matchScreenPitchClickedMissedClickedStage.show();   
    }

    @FXML
    void matchScreenPitchClickedLostClicked(MouseEvent event) throws Exception {
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        Stage MatchScreenPitchClickedLostClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedLostClicked.fxml"));
        MatchScreenPitchClickedLostClickedStage.setScene( new Scene( root, 600, 500));
        MatchScreenPitchClickedLostClickedStage.show();
    }

    @FXML
    void matchScreenPitchClickedLostClickedExit( ActionEvent event) throws Exception{
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void matchScreenPitchClickedFoulClicked(MouseEvent event) throws Exception{
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        Stage MatchScreenPitchClickedFoulClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedFoulClicked.fxml"));
        MatchScreenPitchClickedFoulClickedStage.setScene( new Scene( root, 600, 500));
        MatchScreenPitchClickedFoulClickedStage.show();
    }   

    @FXML
    void matchScreenPitchClickedFoulClickedExit( ActionEvent event) throws Exception{
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
   
    //getters
    public Text getTextScoreOfTeamA()
    {
        return teamAScore;
    }
    public Text getMatchHour()
    {
        return matchHour;
    }
    public Text getMatchSecond()
    {
        return matchSecond;
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
        
        timer.schedule(timerHelper, 1000, 1000);
        mainRoot = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        primaryStage.setTitle("Match Screen");
        primaryStage.setScene(new Scene(mainRoot, 950, 600));
        primaryStage.setMaximized(true);
        primaryStage.show();
        constructTeam();
       
        
       
        
        
        
    }
}
