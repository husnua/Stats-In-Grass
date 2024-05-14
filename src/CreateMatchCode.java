import dao.DatabaseInitializer;
import dao.MatchDAO;
import dao.PlayerDAO;
import dao.PlayerStatsDAO;
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
    private PlayerDAO playerDAO;
    private MatchDAO matchDAO;
    private PlayerStatsDAO playerStatsDAO;
    private DatabaseInitializer databaseInitializer;
    private static String nameA;
    private static String nameB;
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }
    
    @FXML
    private TextField teamANameField;

    @FXML
    private TextField teamBNameField;

    @FXML
    void addPlayer(ActionEvent event) throws Exception {
        String nameTeamA = teamANameField.getText();
        String nameTeamB = teamBNameField.getText();
        
        if (nameTeamA.isEmpty() || nameTeamB.isEmpty()) {
            showAlert("Error", "Both team names must be entered.");
            return;
        }

        this.teamDAO = new TeamDAO();
        teamDAO.addTeam( nameTeamA, "path/to/logo1.jpg");
        teamDAO.addTeam( nameTeamB, "path/to/logo2.jpg");
        nameA = nameTeamA;
        nameB = nameTeamB;
        Stage addPlayerButtonClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddPlayer.fxml"));
        addPlayerButtonClickedStage.setScene(new Scene(root, 950, 600));
        addPlayerButtonClickedStage.setTitle("Add Player");
        addPlayerButtonClickedStage.show();
    }
    public static String getNameA() {
        return nameA;
    }
    public static String getNameB() {
        return nameB;
    }
    @FXML
    void startMatch(ActionEvent event) {
        System.out.println("MATCH STARTING");
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
        Parent root = FXMLLoader.load(getClass().getResource("CreateMatch.fxml"));
        primaryStage.setTitle("Create Match");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }
}

