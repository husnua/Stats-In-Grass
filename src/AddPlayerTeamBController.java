import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import dao.PlayerDAO;
import dao.TeamDAO;


public class AddPlayerTeamBController {

    private TeamDAO teamDAO;
    private PlayerDAO playerDAO;
    
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

    private final ObservableList<String> teamBPlayers = FXCollections.observableArrayList();
    private final ArrayList<String> teamBJerseyNumbers = new ArrayList<>();

    @FXML
    public void initialize() {
        teamBListView.setItems(teamBPlayers);

        teamBAddPlayerButton.setOnAction(e -> addPlayerToTeamB());
        saveButton.setOnAction(e -> savePlayerData());
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

    private void savePlayerData() {
        playerDAO = new PlayerDAO();
        teamDAO = new TeamDAO();
        String nameB = CreateMatchCode.getNameB();
        int bID;
        try {
            bID = teamDAO.searchTeamByName(nameB).getTeamId();
        } catch (Exception e) {
            System.out.println("Error in search players db");
            return;
        }
        if (teamBPlayers.size() <=12  && teamBPlayers.size() >=6) {
            try {
                for(int i = 0;i< teamBJerseyNumbers.size();i++){
                    playerDAO.addPlayer(bID, teamBPlayers.get(i), teamBJerseyNumbers.get(i));
                }
            } catch (Exception e) {
                System.out.println("Error in adding players to db");
            }
            System.out.println("Save button");
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
            CreateMatchCode.isReadyB = true;

        }
        else{
            showAlert("Error", "Team must have at least 6 at most 12 players");

        }
    }
}
