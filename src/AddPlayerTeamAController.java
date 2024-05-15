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


public class AddPlayerTeamAController {

    private TeamDAO teamDAO;
    private PlayerDAO playerDAO;

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
    private final ArrayList<String> teamAJerseyNumbers = new ArrayList<>();
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
        String nameA = CreateMatchCode.getNameA();
        int aID;
        try {
            aID = teamDAO.searchTeamByName(nameA).getTeamId();
        } catch (Exception e) {
            System.out.println("Error in search players db");
            return;
        }
        if (teamAPlayers.size() <=12  && teamAPlayers.size() >=6) {
            try {
                for(int i = 0;i< teamAJerseyNumbers.size();i++){
                    playerDAO.addPlayer(aID, teamAPlayers.get(i), teamAJerseyNumbers.get(i));
                }
            } catch (Exception e) {
                System.out.println("Error in adding players to db");
            }
            System.out.println("Save button");
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
            CreateMatchCode.isReadyA = true;
        }
    }
}

