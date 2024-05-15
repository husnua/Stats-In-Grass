import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.Timer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import dao.Match;
import dao.MatchDAO;
//db related conns
import dao.PlayerDAO;
import dao.PlayerStatsDAO;




public class MatchScreenCode extends Application{

    private PlayerDAO playerDAO;
    private static Stage stage;

    //Instance Variables of MatchScreenCode
    static Parent mainRoot;
    static Parent goalClickedRoot;
    static Parent lostClickedRoot;
    static Parent foulClickedRoot;
    static Parent missedClickedRoot;
    static Parent subClickedRoot;
    static Parent actionsClickedRoot;

    private static String teamAShortName;
    private static String teamBShortName;
    private static dao.Team teamAdb, teamBdb;
    private Timer timer = new Timer();
    private TimerHelper timerHelper = new TimerHelper( this);
    private Stage matchScreenPitchClickedGoalClickedStage;
    private Stage matchScreenPitchClickedStage;

    private static ArrayList<ActionLog> actions = new ArrayList<ActionLog>();
    private static Player[] teamAPlayers = new Player[12];
    private static Player[] teamBPlayers = new Player[12];
    private static int teamAsize;
    private static int teamBsize;
    private static int goalCountTeamA = 0;
    private static int goalCountTeamB = 0;

    //shotChart
    private static int[][] teamAShotChart = new int[100][2];
    private static int[][] teamBShotChart = new int[100][2];
    private static int shotCountA = 0;
    private static int shotCountB = 0;
    private static ArrayList<dao.Player> aPlayers = null, bPlayers = null;



    @FXML
    private Text teamAScore;
    private Text teamBScore;
    private Text matchHour;
    private Text matchSecond;

    public Text getTeamBScore() {
        return teamBScore;
    }
    public static Stage getStage() {
        return stage;
    }

    //alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    

    //constructor
    public void con(dao.Team teamA, dao.Team teamB){
        teamAdb = teamA;
        teamBdb = teamB;
        if(teamA.getName().length()>=3)
            teamAShortName = teamA.getName().substring(0, 3).toUpperCase();
        else 
            teamAShortName = teamA.getName().toUpperCase();
        

        if(teamB.getName().length()>=3)
            teamBShortName = teamB.getName().substring(0, 3).toUpperCase();
        else 
            teamBShortName = teamB.getName().toUpperCase();

        playerDAO = new PlayerDAO();
    
        try {
            aPlayers = playerDAO.getTeamPlayers( teamA.getTeamId() ); 
        } catch (Exception e) {
            System.out.println("An error occured when accesing the team members");
        }

        try {
            bPlayers = playerDAO.getTeamPlayers( teamB.getTeamId() ); 
        } catch (Exception e) {
            System.out.println("An error occured when accesing the team members");
        }
        teamAsize = aPlayers.size();
        teamBsize = bPlayers.size();
        teamAPlayers = new Player[teamAsize];
        teamBPlayers = new Player[teamBsize];
        int i;
        for( i = 0; i < aPlayers.size(); i++){
            teamAPlayers[i] = new Player( aPlayers.get(i) );
        }
        /*for (; i < 12; i++){
            teamAPlayers[i] = null;
        }*/

        for( i = 0; i < bPlayers.size(); i++){
            teamBPlayers[i] = new Player( bPlayers.get(i) );
        }
        /*for (; i < 12; i++){
            teamBPlayers[i] = null;
        }*/
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
                if(teamAPlayers[i]!=null )
                    buttonTeamA.setText("" + teamAPlayers[i].getJerseyNumber());
                else
                    buttonTeamA.setText("");

            }
            if( buttonTeamB != null)
            {   
                if(teamBPlayers[i]!=null )
                    buttonTeamB.setText("" + teamBPlayers[i].getJerseyNumber());
                else
                    buttonTeamB.setText("");

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



    //finishingMatch
    @FXML
    void matchScreenFinishClicked(ActionEvent event) {
        MatchDAO m = new MatchDAO();
        PlayerStatsDAO ps = new PlayerStatsDAO();
        try {
            m.addMatch(teamAdb.getTeamId(), teamBdb.getTeamId(), goalCountTeamA, goalCountTeamB, "2024-05-14");
        } catch (Exception e) {
            System.out.println("Match could not added" + e);
        }
        dao.Match ml = new Match();
        try {
            
            ml = m.searchMatchByTeamsName(teamAdb.getTeamId(), teamBdb.getTeamId());
            System.out.println(ml);
        } catch (Exception e) {
            System.out.println("Match could not found" + e);
        }
        
        // TODO: define a correct match
        for(int i = 0; i< teamAPlayers.length;i++){
            try {
                ps.addPlayerStats(aPlayers.get(i).getPlayerId(), ml.getMatchId(), teamAPlayers[i].getStats().getGoal(), teamAPlayers[i].getStats().getAssist(), (int)teamAPlayers[i].getStats().getPlayedTime(), teamAPlayers[i].getStats().getYellow(), teamAPlayers[i].getStats().getRed());
            } catch (Exception e) {
                System.out.println("PlayerStats could not added " + e);
            }
        }
        for(int i = 0; i< teamBPlayers.length;i++){
            try {
                ps.addPlayerStats(bPlayers.get(i).getPlayerId(), ml.getMatchId(), teamBPlayers[i].getStats().getGoal(), teamBPlayers[i].getStats().getAssist(), (int)teamBPlayers[i].getStats().getPlayedTime(), teamBPlayers[i].getStats().getYellow(), teamBPlayers[i].getStats().getRed());
            } catch (Exception e) {
                System.out.println("PlayerStats could not added " + e);
            }
        }
        MainScreenCode msc = new MainScreenCode();
        try {
            msc.start(getStage());
        } catch (Exception e) {
            System.out.println("Returning back to main");
        }
    }


    //SUB
    @FXML
    void matchScreenSubButtonClicked(ActionEvent event) throws Exception {
     
        Stage MatchScreenSubButtonClickedStage = new Stage();
        subClickedRoot = FXMLLoader.load(getClass().getResource("MatchScreenSubButtonClicked.fxml"));
        MatchScreenSubButtonClickedStage.setScene( new Scene( subClickedRoot, 600, 600));
        MatchScreenSubButtonClickedStage.show();

        for ( int i = 0; i < teamAsize; i++){
            Button buttonOfTeamAPlayer = (Button)subClickedRoot.lookup("#subScreenTeamAButton" + i );
            buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());

        }
        for ( int i = 0; i < teamBsize; i++){
            Button buttonOfTeamBPlayer = (Button)subClickedRoot.lookup("#subScreenTeamBButton" + i );
            buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
        }
    }

    @FXML
    void subScreenTeamAPlayerClicked(ActionEvent event) {

        Button firstSelectedPlayer = null;
        boolean dis = false;
        for ( int i = 0; i < teamAsize; i++)
        {
            Button button = (Button)subClickedRoot.lookup("#subScreenTeamAButton" + i);
            if ( button.isDisabled())
            {
                dis = true;
                firstSelectedPlayer = button;
            }
        }
        if ( !dis )
        {
            firstSelectedPlayer = (Button)event.getSource();
            firstSelectedPlayer.setDisable(true);
        }
        else
        {
            String temp = firstSelectedPlayer.getText();
            Button seconSelectedPlayer = (Button)event.getSource();
            seconSelectedPlayer.setDisable( true );
            firstSelectedPlayer.setText( seconSelectedPlayer.getText() );
            seconSelectedPlayer.setText( temp );

            for( int c = 0; c < teamAsize; c++){
                if ( firstSelectedPlayer.getText().equals(((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + c)).getText()))
                {
                    Button firstMainRootButton = (Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + c);
                    firstMainRootButton.setText("" + seconSelectedPlayer.getText());
                }
                else if ( seconSelectedPlayer.getText().equals(((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + c)).getText()))
                {
                    Button secondMainRootButton = (Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + c);
                    secondMainRootButton.setText("" + firstSelectedPlayer.getText());
                }
            }
            firstSelectedPlayer.setDisable(false);
            seconSelectedPlayer.setDisable(false);
            
        }
    }

    @FXML
    void subScreenTeamBPlayerClicked(ActionEvent event) {

        Button firstSelectedPlayer = null;
        boolean dis = false;
        for ( int i = 0; i < teamBsize; i++)
        {
            Button button = (Button)subClickedRoot.lookup("#subScreenTeamBButton" + i);
            if ( button.isDisabled())
            {
                dis = true;
                firstSelectedPlayer = button;
            }
        }
        if ( !dis )
        {
            firstSelectedPlayer = (Button)event.getSource();
            firstSelectedPlayer.setDisable(true);
        }
        else
        {
            String temp = firstSelectedPlayer.getText();
            Button seconSelectedPlayer = (Button)event.getSource();
            seconSelectedPlayer.setDisable( true );
            firstSelectedPlayer.setText( seconSelectedPlayer.getText() );
            seconSelectedPlayer.setText( temp );

            for( int c = 0; c < teamBsize; c++){
                if ( firstSelectedPlayer.getText().equals(((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + c)).getText()))
                {
                    Button firstMainRootButton = (Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + c);
                    firstMainRootButton.setText("" + seconSelectedPlayer.getText());
                }
                else if ( seconSelectedPlayer.getText().equals(((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + c)).getText()))
                {
                    Button secondMainRootButton = (Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + c);
                    secondMainRootButton.setText("" + firstSelectedPlayer.getText());
                }
            }
            firstSelectedPlayer.setDisable(false);
            seconSelectedPlayer.setDisable(false);
            
        }
    }

    @FXML
    void subScreenDoneClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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

        teamAShotChart[shotCountA][0] = (int) event.getX();
        teamAShotChart[shotCountA][1] = (int) event.getY();
        teamBShotChart[shotCountB][0] = (int) event.getX();
        teamBShotChart[shotCountB][1] = (int) event.getY();
        shotCountA++;
        shotCountB++;

        matchScreenPitchClickedStage.show();   
    }



    //actionLog
    @FXML
    void pitchScreenActionButtonClicked(ActionEvent event) throws Exception{

        
        actionsClickedRoot = FXMLLoader.load(getClass().getResource("MatchScreenPitchClickedActionsClicked.fxml")); 
        System.out.println( actions.size());
        if ( actions.size() > 0)
        {   
            int bound = 0;
            if ( actions.size() >= 6 )
            {
                bound = 6;
            }
            else
            {
                bound = actions.size();
            }
            for( int i = 0; i < bound; i++)
            {
                Text playerName = (Text)actionsClickedRoot.lookup("#actionPlayerName" + i);
                Text actionType = (Text)actionsClickedRoot.lookup("#actionType" + i);
                playerName.setText( actions.get(actions.size() - 1 - i).getPlayer().getName());
                actionType.setText( actions.get(actions.size() - 1 - i).getType());
            }
        }
        Stage actionsClickedStage = new Stage();
        actionsClickedStage.setScene(new Scene(actionsClickedRoot, 600,600));
        actionsClickedStage.show();
    }

    @FXML
    void actionScreenDeleteClicked(ActionEvent event) throws Exception {
        
        Button button = (Button)event.getSource();

        for( int i = 0; i < 6; i++)
        {
            if( button.getId().equals(((Button)actionsClickedRoot.lookup("#actionScreenDeleteButton" + i)).getId()))
            {
                actions.remove( actions.size() - 1 - i);
            }
        }

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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

        Button teamBButton = (Button)goalClickedRoot.lookup("#goalScreenTeamBButton");

        if ( !teamBButton.isDisabled())
        {
            Text score = (Text)mainRoot.lookup("#teamAScore");
            if(score!=null)
            {
                score.setText("" + (Integer.parseInt(score.getText()) + 1));
            }

            Button button = (Button)event.getSource();
            button.setDisable(true);

            for ( int i = 0; i < teamAsize; i++)
            {
                Button buttonOfTeamAPlayer = (Button)goalClickedRoot.lookup("#matchScreenTeamPlayerButtonGoal" + i );
                buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
            }
        }
    }

    @FXML 
    void matchScreenPitchClickedGoalClickedTeamBClicked ( ActionEvent event) throws Exception{

        Button teamAButton = (Button)goalClickedRoot.lookup("#goalScreenTeamAButton");

        if ( !teamAButton.isDisabled())
        {
            Text score = (Text)mainRoot.lookup("#teamBScore");
            if(score!=null)
            {
                score.setText("" + (Integer.parseInt(score.getText()) + 1));
            }

            Button button = (Button)event.getSource();
            button.setDisable(true);

            for ( int i = 0; i < teamBsize; i++)
            {
                Button buttonOfTeamBPlayer = (Button)goalClickedRoot.lookup("#matchScreenTeamPlayerButtonGoal" + i );
                buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
            }
        }
    }

    @FXML
    void goalScreenPlayerSelected ( ActionEvent event) throws Exception{

        Button assistButton = (Button)goalClickedRoot.lookup("#goalClickedAssistButton");
        Button teamAButton = (Button)goalClickedRoot.lookup("#goalScreenTeamAButton");
        Button teamBButton = (Button)goalClickedRoot.lookup("#goalScreenTeamBButton");
    
        if( teamAButton.isDisabled() || teamBButton.isDisabled())
        {
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
                            ActionLog newAction = new ActionLog("assist", player);
                            actions.add(newAction);
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
                            ActionLog newAction = new ActionLog("assist", player);
                            actions.add(newAction);
                        }
                    }
                }
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
            else
            {
                if( teamAButton.isDisabled())
                {
                    for ( Player player : teamAPlayers){
                        if ( selectedJerseyNumber == player.getJerseyNumber())
                        {
                            player.getStats().scoreGoal();
                            player.getStats().makeShotOnTarget();
                            player.getStats().makeShot();
                            System.out.println( player.getStats().getGoal());
                            ActionLog newAction = new ActionLog("goal", player);
                            actions.add(newAction);
                            goalCountTeamA++;
                        }
                    }
                }   
                else
                {
                    for ( Player player : teamBPlayers)
                    {
                        if ( selectedJerseyNumber == player.getJerseyNumber())
                        {
                            player.getStats().scoreGoal();
                            player.getStats().makeShotOnTarget();
                            player.getStats().makeShot();
                            System.out.println( player.getStats().getGoal());
                            ActionLog newAction = new ActionLog("goal", player);
                            actions.add(newAction);
                            goalCountTeamB++;
                        }
                    }
                }
            }
        }
        else
        {
            showAlert("Error", "Please select a team.");
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

    @FXML
    void goalScreenConfirmClicked(ActionEvent event) {
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

        Button teamBButton = (Button)missedClickedRoot.lookup("#missedScreenTeamBButton");

        if( !teamBButton.isDisabled())
        {
            Button button = (Button)event.getSource();
            button.setDisable(true);

            for ( int i = 0; i < teamAsize; i++)
            {
                Button buttonOfTeamAPlayer = (Button)missedClickedRoot.lookup("#matchScreenTeamPlayerButtonMissed" + i );
                buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
            }
        }
    }

    @FXML
    void matchScreenPitchClickedMissedClickedTeamBClicked(ActionEvent event) {

        Button teamAButton = (Button)missedClickedRoot.lookup("#missedScreenTeamAButton");

        if( !teamAButton.isDisabled())
        {   
            Button button = (Button)event.getSource();
            button.setDisable(true);

            for ( int i = 0; i < teamBsize; i++)
            {
                Button buttonOfTeamBPlayer = (Button)missedClickedRoot.lookup("#matchScreenTeamPlayerButtonMissed" + i );
                buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
            }
        }
    }

    @FXML
    void missedScreenPlayerSelected (ActionEvent event) throws Exception{   

        Button onTargetButton = (Button)missedClickedRoot.lookup("#missedScreenOnTargetButton");
        Button teamAButton = (Button)missedClickedRoot.lookup("#missedScreenTeamAButton");
        Button teamBButton = (Button)missedClickedRoot.lookup("#missedScreenTeamBButton");

        if( teamAButton.isDisabled() || teamBButton.isDisabled())
        {
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
                            player.getStats().makeShot();
                            System.out.println( player.getStats().getShotOnTarget());
                            ActionLog newAction = new ActionLog("shotOnTarget", player);
                            actions.add(newAction);
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
                            player.getStats().makeShot();
                            System.out.println( player.getStats().getShotOnTarget());
                            ActionLog newAction = new ActionLog("shotOnTarget", player);
                            actions.add(newAction);
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
                            player.getStats().makeShot();
                            System.out.println( player.getStats().getTotalShot());
                            ActionLog newAction = new ActionLog("shotNotOnTarget", player);
                            actions.add(newAction);
                        }
                    }
                }
                else
                {
                    for ( Player player: teamBPlayers)
                    {
                        if ( selectedJerseyNumber == player.getJerseyNumber())
                        {
                            player.getStats().makeShot();
                            System.out.println( player.getStats().getTotalShot());
                            ActionLog newAction = new ActionLog("shotNotOnTarget", player);
                            actions.add(newAction);
                        }
                    }
                }
            }
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
        else
        {
            showAlert("Error", "Please select a team.");
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

    @FXML
    void missedScreenConfirmClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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

        for ( int i = 0; i < teamAsize; i++)
        {
            Button buttonOfTeamAPlayer = (Button)lostClickedRoot.lookup("#matchScreenTeamPlayerButtonLost" + i );
            buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
        }
    }

    @FXML 
    void matchScreenPitchClickedLostClickedTeamBClicked ( ActionEvent event) throws Exception{

        Button button = (Button)event.getSource();
        button.setDisable(true);

        for ( int i = 0; i < teamBsize; i++)
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
            for ( int i = 0; i < teamBsize; i++)
            {
                Button buttonOfTeamBPlayer = (Button)lostClickedRoot.lookup("#matchScreenTeamPlayerButtonLost" + i );
                buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
            }
        }
        if( teamBButton.isDisabled())
        {
            for ( int i = 0; i < teamAsize; i++)
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

        if ( teamAButton.isDisabled() || teamBButton.isDisabled())
        {
            int selectedJerseyNumber = Integer.parseInt(((Button)event.getSource()).getText());

            if ( stealButton.isDisabled())
            {   
                if( teamAButton.isDisabled())
                {
                    for ( Player player: teamBPlayers)
                    {
                        if ( selectedJerseyNumber == player.getJerseyNumber())
                        {
                            player.getStats().stealBall();
                            System.out.println( player.getStats().getSteal());
                            ActionLog newAction = new ActionLog("steal", player);
                            actions.add(newAction);
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
                            ActionLog newAction = new ActionLog("steal", player);
                            actions.add(newAction);
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
                            ActionLog newAction = new ActionLog("turnover", player);
                            actions.add(newAction);
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
                            ActionLog newAction = new ActionLog("turnover", player);
                            actions.add(newAction);
                        }
                    }
                }
            }
        }
        else
        {
            showAlert("Error", "Please select a team.");
        }
    }

    @FXML
    void matchScreenPitchClickedLostClickedExit( ActionEvent event) throws Exception{
        //closing pitchClickedScreen
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void stealScreenConfirmClicked(ActionEvent event) {
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
        Button teamBButton = (Button)foulClickedRoot.lookup("#foulScreenTeamBButton");
        if( !teamBButton.isDisabled())
        {
            Button button = (Button)event.getSource();
            button.setDisable(true);
    
            for ( int i = 0; i < teamAsize; i++)
            {
                Button buttonOfTeamAPlayer = (Button)foulClickedRoot.lookup("#matchScreenTeamPlayerButtonFoul" + i );
                buttonOfTeamAPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamAPlayerButton" + i)).getText());
            }
        }
    }

    @FXML 
    void matchScreenPitchClickedFoulClickedTeamBClicked ( ActionEvent event) throws Exception{
        Button teamAButton = (Button)foulClickedRoot.lookup("#foulScreenTeamAButton");
        if( !teamAButton.isDisabled())
        {
            Button button = (Button)event.getSource();
            button.setDisable(true);

            for ( int i = 0; i < teamBsize; i++)
            {
                Button buttonOfTeamBPlayer = (Button)foulClickedRoot.lookup("#matchScreenTeamPlayerButtonFoul" + i );
                buttonOfTeamBPlayer.setText( ((Button)mainRoot.lookup("#matchScreenTeamBPlayerButton" + i)).getText());
            }
        }
    }

    @FXML
    void foulScreenPlayerSelected ( ActionEvent event) throws Exception{

        Button yellowButton = (Button)foulClickedRoot.lookup("#foulScreenYellowButton");
        Button redButton = (Button)foulClickedRoot.lookup("#foulScreenRedButton");
        Button teamAButton = (Button)foulClickedRoot.lookup("#foulScreenTeamAButton");
        Button teamBButton = (Button)foulClickedRoot.lookup("#foulScreenTeamBButton");

        if( teamAButton.isDisabled() || teamBButton.isDisabled())
        {

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
                            ActionLog newAction = new ActionLog("redCard", player);
                            actions.add(newAction);
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
                            ActionLog newAction = new ActionLog("redCard", player);
                            actions.add(newAction);
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
                            ActionLog newAction = new ActionLog("yellowCard", player);
                            actions.add(newAction);
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
                            ActionLog newAction = new ActionLog("yellowCard", player);
                            actions.add(newAction);
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
                            player.getStats().foulDone();
                            System.out.println( player.getStats().getFouls());
                            ActionLog newAction = new ActionLog("foulDone", player);
                            actions.add(newAction);
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
                            ActionLog newAction = new ActionLog("foulDone", player);
                            actions.add(newAction);
                        }
                    }
                }
            }
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
        else
        {
            showAlert("Error", "Please select a team.");
        }
    }

    @FXML
    void foulScreenYellowButtonClicked(ActionEvent event) {
        Button redButton = (Button)foulClickedRoot.lookup("#foulScreenRedButton");
        if( !redButton.isDisabled())
        {
            Button button = (Button)event.getSource();
            button.setDisable(true);
        }
    }

    @FXML
    void foulScreenRedButtonClicked(ActionEvent event) {
        Button yellowButton = (Button)foulClickedRoot.lookup("#foulScreenYellowButton");
        if( !yellowButton.isDisabled())
        {
            Button button = (Button)event.getSource();
            button.setDisable(true);
        }
    }


    //getters
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
        stage = primaryStage;
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
        
        //Parent root = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        mainRoot = FXMLLoader.load(getClass().getResource("MatchScreen.fxml"));
        timer.schedule(timerHelper, 1000, 1000);
        primaryStage.setTitle("Match Screen");
        primaryStage.setScene(new Scene(mainRoot, 950, 600));
        primaryStage.show();
        primaryStage.setResizable(false);
        constructTeam();
        
        
    }
}
