

import dao.TeamDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;

public class CreateMatchCode extends Application {
    private TeamDAO teamDAO;
    private static String nameA;
    private dao.Team teamA, teamB;
    private static String nameB;
    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }
    static boolean isReadyA, isReadyB;
    @FXML
    private TextField teamANameField;

    @FXML
    private TextField teamBNameField;

    @FXML
    void addPlayerForTeamA(ActionEvent event) throws Exception {
        String nameTeamA = teamANameField.getText();
        if (teamANameField.getText().isEmpty()) {
            showAlert("Error", "teamA name must be entered.");
            return;
        }
        this.teamDAO = new TeamDAO();
        teamDAO.addTeam( nameTeamA, "path/to/logo1.jpg");
        nameA = nameTeamA;
        Stage addPlayerButtonClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddPlayerForTeamA.fxml"));
        addPlayerButtonClickedStage.setScene(new Scene(root, 950, 600));
        addPlayerButtonClickedStage.setTitle("Add Player to Team A");
        addPlayerButtonClickedStage.show();
    }

    @FXML
    void addPlayerForTeamB(ActionEvent event) throws Exception {
        String nameTeamB = teamBNameField.getText();
        if ( teamBNameField.getText().isEmpty()) {
            showAlert("Error", "TeamB name must be entered.");
            return;
        }
        
        this.teamDAO = new TeamDAO();
        teamDAO.addTeam( nameTeamB, "path/to/logo2.jpg");
        nameB = nameTeamB;

        Stage addPlayerButtonClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddPlayerForTeamB.fxml"));
        addPlayerButtonClickedStage.setScene(new Scene(root, 950, 600));
        addPlayerButtonClickedStage.setTitle("Add Player to Team B");
        addPlayerButtonClickedStage.show();
    }
    public static String getNameA() {
        return nameA;
    }
    public static String getNameB() {
        return nameB;
    }
    @FXML
    void addPlayerFromDatabaseTeamA(ActionEvent event) {
        teamDAO = new TeamDAO();
        if ( teamANameField.getText().isEmpty()) {
            showAlert("Error", "TeamA name must be entered.");
            return;
        }
        String teamName = teamANameField.getText();
        try {
            teamA = teamDAO.searchTeamByName( teamName );

        } catch (Exception e) {
            System.out.println("Error occured when trying to find a team!");
        }
        if( teamA == null){
            showAlert("Error", "No such team exist in database");
            return;
        }
        isReadyA = true;
        
    }

    @FXML
    void addPlayerFromDatabaseTeamB(ActionEvent event) {
        teamDAO = new TeamDAO();
        if ( teamBNameField.getText().isEmpty()) {
            showAlert("Error", "TeamB name must be entered.");
            return;
        }
        String teamName = teamBNameField.getText();
        try {
            teamB = teamDAO.searchTeamByName( teamName );

        } catch (Exception e) {
            System.out.println("Error occured when trying to find a team!");
        }
        if( teamB == null){
            showAlert("Error", "No such team exist in database");
            return;
        }
        isReadyB = true;
    }
    

    @FXML
    void startMatch(ActionEvent event) throws Exception{
        if(!isReadyA){
            showAlert("Error", "TeamA is not determined! Add players or chose a team from database");
            return;
        }
        else if(!isReadyB){
            showAlert("Error", "TeamB is not determined! Add players or chose a team from database");
            return;
        }
        System.out.println("MATCH STARTING");
        MatchScreenCode msc = new MatchScreenCode();
        msc.con(teamA, teamB);
        msc.start(CreateMatchCode.getStage()); 
        
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        isReadyA = false;
        isReadyB = false;
        Parent root = FXMLLoader.load(getClass().getResource("CreateMatch.fxml"));
        primaryStage.setTitle("Create Match");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }
}
