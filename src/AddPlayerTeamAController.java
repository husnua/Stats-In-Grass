import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.Set;


public class AddPlayerTeamAController {

    @FXML
    private TextField teamAPlayerNameField;

    @FXML
    private TextField teamAPlayerNumberField;

    @FXML
    private Button teamAAddPlayerButton;

    @FXML
    private ListView<String> teamAListView;

    @FXML
    private Button saveButton;

    private final ObservableList<String> teamAPlayers = FXCollections.observableArrayList();
    private final Set<String> teamAJerseyNumbers = new HashSet<>();

    @FXML
    public void initialize() {
        teamAListView.setItems(teamAPlayers);

        teamAAddPlayerButton.setOnAction(e -> addPlayerToTeamA());
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

    private boolean isValidInput(String playerName, String playerNumber, ObservableList<String> teamPlayers, Set<String> jerseyNumbers) {
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
        if (teamAPlayers.size() == 11) {
            // Save data and close the interface
            // TODO: Implement save functionality
        } else {
            showAlert("Error", "Team A must have exactly 11 players.");
        }
    }
}

