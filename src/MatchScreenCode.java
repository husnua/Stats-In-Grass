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
    static Parent lostClickedRoot;
    static Parent foulClickedRoot;
    static Parent missedClickedRoot;
    static Parent subClickedRoot;

    private String teamAShortName;
    private String teamBShortName;
    private Timer timer = new Timer();
    private TimerHelper timerHelper = new TimerHelper( this);
    private Stage matchScreenPitchClickedGoalClickedStage;
    private Stage matchScreenPitchClickedStage;
    private static boolean disabledControl;
    private static Team teamA = new Team();
    private static Team teamB = new Team();
    private static Player[] teamAPlayers = new Player[12];
    private static Player[] teamBPlayers = new Player[12];




    @FXML
    private Text teamAScore;
    private Text teamBScore;
    private Text matchHour;
    private Text matchSecond;
    private Text matchScreenTeamAShortName;
    private Text matchScreenTeamBShortName;
    


    

    //constructor
    public MatchScreenCode()
    {
        teamAShortName = "GS";
        teamBShortName = "FB";
        disabledControl = false;
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
                buttonTeamB.setText("" + (i + 20));
                teamBPlayers[i] = new Player("Yunus Karamatov" + i , i + 20, teamB);
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
        subClickedRoot = FXMLLoader.load(getClass().getResource("MatchScreenSubButtonClicked.fxml"));
        MatchScreenSubButtonClickedStage.setScene( new Scene( subClickedRoot, 600, 600));
        MatchScreenSubButtonClickedStage.show();

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamAPlayer = (Button)subClickedRoot.lookup("#subScreenTeamAButton" + i );
            buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
            Button buttonOfTeamBPlayer = (Button)subClickedRoot.lookup("#subScreenTeamBButton" + i );
            buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
        }
    }

    @FXML
    void subScreenTeamAPlayerClicked(ActionEvent event) {

        for ( int i = 0; i < 12; i++)
        {
            Button button = (Button)subClickedRoot.lookup("#subScreenTeamAButton" + i);
            if ( button.isDisabled())
            {
                disabledControl = true;
            }
        }
        if ( !disabledControl)
        {
            Button firstSelectedPlayer = (Button)event.getSource();
            firstSelectedPlayer.setDisable(true);
        }
        else
        {
            System.out.println("sa");
            Button secondSelectedButton = (Button)event.getSource();
            for( int i = 0; i < 12; i++)
            {
                if( ((Button)subClickedRoot.lookup("#subScreenTeamAButton" + i)).isDisabled())
                {   

                    Button firstSelectedButton = (Button)subClickedRoot.lookup("#subScreenTeamAButton" + i);
                    String tempText = secondSelectedButton.getText();
                    

                    for( int c = 0; i < 12; i++)
                    {
                        if ( firstSelectedButton.getText().equals(((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + c)).getText()))
                        {
                            Button mainRootFirstSelectedButton = (Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + c);
                        }
                        if ( secondSelectedButton.getText().equals(((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + c)).getText()))
                        {
                            Button mainRootSecondSelectedButton = (Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + c);
                        }
                        
                        /*Button tempMainRootButton = mainRootSecondSelectedButton;
                        mainRootSecondSelectedButton = mainRootFirstSelectedButton;
                        mainRootFirstSelectedButton = tempMainRootButton;*/
                    }
                    secondSelectedButton.setText(firstSelectedButton.getText());
                    firstSelectedButton.setText(tempText);
                    secondSelectedButton.setDisable(true);
                }
            }
        }
    }

    @FXML
    void subScreenTeamBPlayerClicked(ActionEvent event) {

        for ( int i = 0; i < 12; i++)
        {
            Button button = (Button)subClickedRoot.lookup("#subScreenTeamBButton" + i);
            if ( button.isDisabled())
            {
                disabledControl = true;
            }
        }
        if ( !disabledControl)
        {
            Button firstSelectedPlayer = (Button)event.getSource();
            firstSelectedPlayer.setDisable(true);
        }
        else
        {
            System.out.println("sa");
            Button secondSelectedButton = (Button)event.getSource();
            for( int i = 0; i < 12; i++)
            {
                if( ((Button)subClickedRoot.lookup("#subScreenTeamBButton" + i)).isDisabled())
                {
                    Button firstSelectedButton = (Button)subClickedRoot.lookup("#subScreenTeamBButton" + i);
                    String tempText = secondSelectedButton.getText();
                    secondSelectedButton.setText(firstSelectedButton.getText());
                    firstSelectedButton.setText(tempText);
                    secondSelectedButton.setDisable(true);
                }
            }
        }
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




    //goalScreenMethods
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
    void goalScreenPlayerSelected ( ActionEvent event) throws Exception{

        Button assistButton = (Button)goalClickedRoot.lookup("#goalClickedAssistButton");
        Button teamAButton = (Button)goalClickedRoot.lookup("#goalScreenTeamAButton");
        Button teamBButton = (Button)goalClickedRoot.lookup("#goalScreenTeamBButton");

        int selectedJerseyNumber = Integer.parseInt(((Button)event.getSource()).getText());

        if ( assistButton.isDisabled())
        {   
            if( teamAButton.isDisabled())
            {
                for ( Player player : teamAPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().makeAssist();
                        System.out.println( player.getStats().getAssist());
                    }
                }
            }
            else
            {
                for ( Player player: teamBPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().makeAssist();
                        System.out.println( player.getStats().getAssist());
                    }
                }
            }
        }
        else
        {
            if( teamAButton.isDisabled())
            {
                for ( Player player : teamAPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().scoreGoal();;
                        System.out.println( player.getStats().getGoal());
                    }
                }
            }
            else
            {
                for ( Player player: teamBPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().scoreGoal();
                        System.out.println( player.getStats().getGoal());
                    }
                }
            }
        }
    }

    @FXML
    void goalScreenAssistButtonClicked ( ActionEvent event) throws Exception{
        Button button = (Button)event.getSource();
        button.setDisable(true);
    }

    @FXML
    void matchScreenPitchClickedGoalClickedExit(ActionEvent event) {
        //closing goalScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }   
    


    //missedScreenMethods
    @FXML
    void matchScreenPitchClickedMissedClicked(ActionEvent event) throws Exception {
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        
        missedClickedRoot = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedMissedClicked.fxml"));
        //MatchScreenPitchClickedMissedClickedCode matchScreenPitchClickedMissedClickedCode = new MatchScreenPitchClickedMissedClickedCode(this);
        Stage matchScreenPitchClickedMissedClickedStage = new Stage();
        matchScreenPitchClickedMissedClickedStage.setScene( new Scene( missedClickedRoot, 750, 550));
        matchScreenPitchClickedMissedClickedStage.show();   
    }

    @FXML
    void matchScreenPitchClickedMissedClickedTeamAClicked(ActionEvent event) {

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamAPlayer = (Button)missedClickedRoot.lookup("#matchScreenTeamPlayerButtonMissed" + i );
            buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
        }
    }

    @FXML
    void matchScreenPitchClickedMissedClickedTeamBClicked(ActionEvent event) {

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamBPlayer = (Button)missedClickedRoot.lookup("#matchScreenTeamPlayerButtonMissed" + i );
            buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
        }
    }

    @FXML
    void missedScreenPlayerSelected (ActionEvent event) throws Exception{   

        Button onTargetButton = (Button)missedClickedRoot.lookup("#missedScreenOnTargetButton");
        Button teamAButton = (Button)missedClickedRoot.lookup("#missedScreenTeamAButton");
        Button teamBButton = (Button)missedClickedRoot.lookup("#missedScreenTeamBButton");

        int selectedJerseyNumber = Integer.parseInt(((Button)event.getSource()).getText());
        
        if ( onTargetButton.isDisabled())
        {   
            if( teamAButton.isDisabled())
            {
                for ( Player player: teamAPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().makeShotOnTarget();
                        System.out.println( player.getStats().getShotOnTarget());
                    }
                }
            }
            else
            {
                for ( Player player: teamBPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().makeShotOnTarget();
                        System.out.println( player.getStats().getShotOnTarget());
                    }
                }
            }
        }
    }

    @FXML
    void missedScreenOnTargetClicked(ActionEvent event) {
        Button button = (Button)event.getSource();
        button.setDisable(true);
    }

    @FXML
    void missedScreenSavedButtonClicked( ActionEvent event) throws Exception{
        Button button = (Button)event.getSource();
        button.setDisable(true);
    }


    //lostScreenMethods
    @FXML
    void matchScreenPitchClickedLostClicked(MouseEvent event) throws Exception {
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        lostClickedRoot = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedLostClicked.fxml")); 

        Stage MatchScreenPitchClickedLostClickedStage = new Stage();
        MatchScreenPitchClickedLostClickedStage.setScene( new Scene( lostClickedRoot, 600, 500));
        MatchScreenPitchClickedLostClickedStage.show();
    }

    @FXML 
    void matchScreenPitchClickedLostClickedTeamAClicked ( ActionEvent event) throws Exception{

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamAPlayer = (Button)lostClickedRoot.lookup("#matchScreenTeamPlayerButtonLost" + i );
            buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
        }
    }

    @FXML 
    void matchScreenPitchClickedLostClickedTeamBClicked ( ActionEvent event) throws Exception{

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamBPlayer = (Button)lostClickedRoot.lookup("#matchScreenTeamPlayerButtonLost" + i );
            buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
        }
    }

    @FXML
    void lostScreenStealClicked(ActionEvent event) {

        Button stealButton = (Button)event.getSource();
        Button teamAButton = (Button)lostClickedRoot.lookup("#lostScreenTeamAButton");
        Button teamBButton = (Button)lostClickedRoot.lookup("#lostScreenTeamBButton");

        stealButton.setDisable(true);

        if( teamAButton.isDisabled())
        {
            for ( int i = 0; i < 12; i++)
            {
                Button buttonOfTeamBPlayer = (Button)lostClickedRoot.lookup("#matchScreenTeamPlayerButtonLost" + i );
                buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
            }
        }
        if( teamBButton.isDisabled())
        {
            for ( int i = 0; i < 12; i++)
            {
                Button buttonOfTeamAPlayer = (Button)lostClickedRoot.lookup("#matchScreenTeamPlayerButtonLost" + i );
                buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
            }
        }
    }

    @FXML
    void lostScreenPlayerSelected(ActionEvent event) {

        Button stealButton = (Button)lostClickedRoot.lookup("#lostScreenStealClickedButton");
        Button teamAButton = (Button)lostClickedRoot.lookup("#lostScreenTeamAButton");
        Button teamBButton = (Button)lostClickedRoot.lookup("#lostScreenTeamBButton");

        int selectedJerseyNumber = Integer.parseInt(((Button)event.getSource()).getText());

        if ( stealButton.isDisabled())
        {   
            if( teamAButton.isDisabled())
            {
                for ( Player player: teamBPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().stealBall();;
                        System.out.println( player.getStats().getSteal());
                    }
                }
            }
            else
            {
                for ( Player player: teamAPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().stealBall();
                        System.out.println( player.getStats().getSteal());
                    }
                }
            }
        }
        else
        {
            if( teamAButton.isDisabled())
            {
                for ( Player player: teamAPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().lostBall();
                        System.out.println( player.getStats().getTurnover());
                    }
                }
            }
            else
            {
                for ( Player player: teamBPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().lostBall();
                        System.out.println( player.getStats().getTurnover());
                    }
                }
            }
        }
    }

    @FXML
    void matchScreenPitchClickedLostClickedExit( ActionEvent event) throws Exception{
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }



    //FoulScreenMethods
    @FXML
    void matchScreenPitchClickedFoulClicked(MouseEvent event) throws Exception{
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        foulClickedRoot = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedFoulClicked.fxml"));

        Stage MatchScreenPitchClickedFoulClickedStage = new Stage();
        MatchScreenPitchClickedFoulClickedStage.setScene( new Scene( foulClickedRoot, 600, 500));
        MatchScreenPitchClickedFoulClickedStage.show();
    }   

    @FXML
    void matchScreenPitchClickedFoulClickedExit( ActionEvent event) throws Exception{
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    @FXML 
    void matchScreenPitchClickedFoulClickedTeamAClicked ( ActionEvent event) throws Exception{

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamAPlayer = (Button)foulClickedRoot.lookup("#matchScreenTeamPlayerButtonFoul" + i );
            buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
        }
    }

    @FXML 
    void matchScreenPitchClickedFoulClickedTeamBClicked ( ActionEvent event) throws Exception{

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < 12; i++)
        {
            Button buttonOfTeamBPlayer = (Button)foulClickedRoot.lookup("#matchScreenTeamPlayerButtonFoul" + i );
            buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
        }
    }

    @FXML
    void foulScreenPlayerSelected ( ActionEvent event) throws Exception{

        Button yellowButton = (Button)foulClickedRoot.lookup("#foulScreenYellowButton");
        Button redButton = (Button)foulClickedRoot.lookup("#foulScreenRedButton");
        Button teamAButton = (Button)foulClickedRoot.lookup("#foulScreenTeamAButton");
        Button teamBButton = (Button)foulClickedRoot.lookup("#foulScreenTeamBButton");

        int selectedJerseyNumber = Integer.parseInt(((Button)event.getSource()).getText());

        if ( redButton.isDisabled())
        {
            if( teamAButton.isDisabled())
            {
                for ( Player player: teamAPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().redCardTaken();
                        System.out.println( player.getStats().getRed());
                    }
                }
            }
            else
            {
                for ( Player player: teamBPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().redCardTaken();
                        System.out.println( player.getStats().getRed());
                    }
                }
            }
        }
        else if( yellowButton.isDisabled())
        {
            if( teamAButton.isDisabled())
            {
                for ( Player player: teamAPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().yellowCardTaken();
                        System.out.println( player.getStats().getYellow());
                    }
                }
            }
            else
            {
                for ( Player player: teamBPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().yellowCardTaken();
                        System.out.println( player.getStats().getYellow());
                    }
                }
            }
        }
        else
        {
            if( teamAButton.isDisabled())
            {
                for ( Player player: teamAPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().foulDone();;
                        System.out.println( player.getStats().getFouls());
                    }
                }
            }
            else
            {
                for ( Player player: teamBPlayers)
                {
                    if ( selectedJerseyNumber == player.getJerseyNumber())
                    {
                        player.getStats().foulDone();;
                        System.out.println( player.getStats().getFouls());
                    }
                }
            }
        }
    }

    @FXML
    void foulScreenYellowButtonClicked(ActionEvent event) {

        Button button = (Button)event.getSource();
        button.setDisable(true);
    }

    @FXML
    void foulScreenRedButtonClicked(ActionEvent event) {

        Button button = (Button)event.getSource();
        button.setDisable(true);
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
        
        
        teamDAO.addTeam("Team A", "path/to/logo1.jpg");
        teamDAO.addTeam("Team B", "path/to/logo2.jpg");
        matchDAO.addMatch(1, 2, 3, 2, "2024-05-13");  
        matchDAO.printAllMatches();  // Printing all matches
        //System.out.println(matchDAO.getMatch(5).getMatchDate());*/
        
        Parent root = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        timer.schedule(timerHelper, 1000, 1000);
        mainRoot = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        primaryStage.setTitle("Match Screen");
        primaryStage.setScene(new Scene(mainRoot, 950, 600));
        primaryStage.setMaximized(true);
        primaryStage.show();
        constructTeam();
        
        
        
    }
}
