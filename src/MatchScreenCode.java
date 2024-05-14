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
                buttonTeamB.setText("" + i);
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
        
        MatchScreenPitchClickedCode matchScreenPitchClickedCode = new MatchScreenPitchClickedCode( this, event.getX() + 100, event.getY() + 100);
    }

    @FXML
    void matchScreenPitchClickedGoalClicked(ActionEvent event) throws Exception {

        MatchScreenPitchClickedGoalClickedCode matchScreenPitchClickedGoalClickedCode = new MatchScreenPitchClickedGoalClickedCode(this);
        
        Text score = (Text)mainRoot.lookup("#teamAScore");
        
        //now working 
        if(score!=null)
        {
            score.setText("" + (Integer.parseInt(score.getText()) + 1));
        }

        Button assist = (Button)goalClickedRoot.lookup("#goalClickedAssistButton");
        assist.setText("A");

        for ( int i = 0; i < 12; i++)
        {
            System.out.println( "sa");
            Button button = (Button)goalClickedRoot.lookup("#matchScreenTeamAPlayerButtonGoal" + i);
            button.setText( i + 1 + "");
           
        }
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
        goalClickedRoot = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedGoalClicked.fxml")); 
        primaryStage.setTitle("Match Screen");
        primaryStage.setScene(new Scene(mainRoot, 950, 600));
        primaryStage.show();
        constructTeam();
       
        
       
        
        
        
    }
}
