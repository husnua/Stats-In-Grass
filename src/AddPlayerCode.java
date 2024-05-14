import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import dao.DatabaseInitializer;
import dao.MatchDAO;
import dao.PlayerDAO;
import dao.PlayerStatsDAO;
import dao.TeamDAO;

public class AddPlayerCode {
    
    private TeamDAO teamDAO;
    private PlayerDAO playerDAO;
    private MatchDAO matchDAO;
    private PlayerStatsDAO playerStatsDAO;
    private DatabaseInitializer databaseInitializer;

    @FXML
    private TextField teamAPlayerNameField;

    @FXML
    private TextField teamAPlayerNumberField;

    @FXML
    private Button teamAAddPlayerButton;

    @FXML
    private ListView<String> teamAListView;

    @FXML
    private TextField teamBPlayerNameField;

    @FXML
    private TextField teamBPlayerNumberField;

    @FXML
    private Button teamBAddPlayerButton;

    @FXML
    private ListView<String> teamBListView;

    @FXML
    private Button saveButton;

    private final ObservableList<String> teamAPlayers = FXCollections.observableArrayList();
    private final ObservableList<String> teamBPlayers = FXCollections.observableArrayList();

    private final ArrayList<String> teamAJerseyNumbers = new ArrayList<String>();
    private final ArrayList<String> teamBJerseyNumbers = new ArrayList<String>();

    @FXML
    public void initialize() {
        teamAListView.setItems(teamAPlayers);
        teamBListView.setItems(teamBPlayers);

        teamAAddPlayerButton.setOnAction(e -> addPlayerToTeamA());
        teamBAddPlayerButton.setOnAction(e -> addPlayerToTeamB());
        saveButton.setOnAction(e -> savePlayerData());
    }

    private void addPlayerToTeamA() {
        String playerName = teamAPlayerNameField.getText();
        String playerNumber = teamAPlayerNumberField.getText();

        if (isValidInput(playerName, playerNumber, teamAPlayers, teamAJerseyNumbers)) {
            teamAPlayers.add(playerName + " - #" + playerNumber);
            teamAJerseyNumbers.add(playerNumber);
            teamAPlayerNameField.clear();
            teamAPlayerNumberField.clear();
        }
    }

    private void addPlayerToTeamB() {
        String playerName = teamBPlayerNameField.getText();
        String playerNumber = teamBPlayerNumberField.getText();

        if (isValidInput(playerName, playerNumber, teamBPlayers, teamBJerseyNumbers)) {
            teamBPlayers.add(playerName + " - #" + playerNumber);
            teamBJerseyNumbers.add(playerNumber);
            teamBPlayerNameField.clear();
            teamBPlayerNumberField.clear();
        }
    }

    private boolean isValidInput(String playerName, String playerNumber, ObservableList<String> teamPlayers, ArrayList<String> jerseyNumbers) {
        if (playerName.isEmpty() || playerNumber.isEmpty()) {
            showAlert("Error", "Player name and jersey number cannot be empty.");
            return false;
        }
        if (!playerNumber.matches("\\d+")) {
            showAlert("Error", "Jersey number must be a number.");
            return false;
        }
        if (jerseyNumbers.contains(playerNumber)) {
            showAlert("Error", "Duplicate jersey number for the team.");
            return false;
        }
        if (teamPlayers.size() >= 11) {
            showAlert("Error", "A team cannot have more than 11 players.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private Parent replaceSceneContent(String fxml) throws Exception {
        Stage stage = CreateMatchCode.getStage();
        Parent page = (Parent) FXMLLoader.load(CreateMatchCode.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, 700, 450);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }

    private void savePlayerData() {
        playerDAO = new PlayerDAO();
        teamDAO = new TeamDAO();
        String nameA = CreateMatchCode.getNameA();
        String nameB = CreateMatchCode.getNameB();
        int aID,bID;
        try {
            aID = teamDAO.searchTeamsByName(nameA).get(0).getTeamId(); bID = teamDAO.searchTeamsByName(nameB).get(0).getTeamId();
        } catch (Exception e) {
            System.out.println("Error in search players db");
            return;
        }
        if (teamAPlayers.size() <=12 && teamBPlayers.size() <=12 && teamAPlayers.size() >=0 && teamBPlayers.size() >= 0) {
            try {
                for(int i = 0;i< teamAJerseyNumbers.size();i++){
                    playerDAO.addPlayer(aID, teamAPlayers.get(i), teamAJerseyNumbers.get(i));
                }
                for(int i = 0;i< teamBJerseyNumbers.size();i++){
                    playerDAO.addPlayer(bID, teamBPlayers.get(i), teamBJerseyNumbers.get(i));
                }
            } catch (Exception e) {
                System.out.println("Error in adding players to db");
            }
            System.out.println("Save button");
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
            try {
                
                replaceSceneContent("MatchScreen.fxml");
            } catch (Exception e) {
                System.out.println("couldnt change screen");
            }
        } else {
            showAlert("Error", "Each team must have at least 6 at most 12 players.");
        }
    }
}

