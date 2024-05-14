import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MatchDetailsController {

    @FXML
    private Label teamALabel;

    @FXML
    private Label teamBLabel;

    @FXML
    private TableView<PlayerStats> teamATableView;

    @FXML
    private TableColumn<PlayerStats, String> playerNameAColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> shotsAColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> missedShotsAColumn;

    @FXML
    private TableColumn<PlayerStats, Double> accuracyAColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> ballLossesAColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> foulsAColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> yellowCardsAColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> redCardsAColumn;

    @FXML
    private TableView<PlayerStats> teamBTableView;

    @FXML
    private TableColumn<PlayerStats, String> playerNameBColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> shotsBColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> missedShotsBColumn;

    @FXML
    private TableColumn<PlayerStats, Double> accuracyBColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> ballLossesBColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> foulsBColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> yellowCardsBColumn;

    @FXML
    private TableColumn<PlayerStats, Integer> redCardsBColumn;

    private ObservableList<PlayerStats> teamAStats = FXCollections.observableArrayList();
    private ObservableList<PlayerStats> teamBStats = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        playerNameAColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        shotsAColumn.setCellValueFactory(new PropertyValueFactory<>("shots"));
        missedShotsAColumn.setCellValueFactory(new PropertyValueFactory<>("missedShots"));
        accuracyAColumn.setCellValueFactory(new PropertyValueFactory<>("accuracy"));
        ballLossesAColumn.setCellValueFactory(new PropertyValueFactory<>("ballLosses"));
        foulsAColumn.setCellValueFactory(new PropertyValueFactory<>("fouls"));
        yellowCardsAColumn.setCellValueFactory(new PropertyValueFactory<>("yellowCards"));
        redCardsAColumn.setCellValueFactory(new PropertyValueFactory<>("redCards"));

        playerNameBColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        shotsBColumn.setCellValueFactory(new PropertyValueFactory<>("shots"));
        missedShotsBColumn.setCellValueFactory(new PropertyValueFactory<>("missedShots"));
        accuracyBColumn.setCellValueFactory(new PropertyValueFactory<>("accuracy"));
        ballLossesBColumn.setCellValueFactory(new PropertyValueFactory<>("ballLosses"));
        foulsBColumn.setCellValueFactory(new PropertyValueFactory<>("fouls"));
        yellowCardsBColumn.setCellValueFactory(new PropertyValueFactory<>("yellowCards"));
        redCardsBColumn.setCellValueFactory(new PropertyValueFactory<>("redCards"));

        teamATableView.setItems(teamAStats);
        teamBTableView.setItems(teamBStats);
    }

    public void loadMatchDetails(String match) {
        // Set team names dynamically based on the match data
        teamALabel.setText("Dynamic Team A Name"); // TODO: Replace with actual team A name from the match data
        teamBLabel.setText("Dynamic Team B Name"); // TODO: Replace with actual team B name from the match data

        // TODO: Load match details from the database based on the match identifier
        // Example data to test
        teamAStats.addAll(
            new PlayerStats("Player 1", 3, 1, 75.0, 2, 0, 1, 0),
            new PlayerStats("Player 2", 1, 0, 100.0, 1, 2, 0, 0)
        );
        teamBStats.addAll(
            new PlayerStats("Player 3", 2, 1, 50.0, 1, 1, 1, 0),
            new PlayerStats("Player 4", 4, 2, 50.0, 3, 3, 0, 0)
        );
    }

    public static class PlayerStats {
        private final String playerName;
        private final int shots;
        private final int missedShots;
        private final double accuracy;
        private final int ballLosses;
        private final int fouls;
        private final int yellowCards;
        private final int redCards;

        public PlayerStats(String playerName, int shots, int missedShots, double accuracy, int ballLosses, int fouls, int yellowCards, int redCards) {
            this.playerName = playerName;
            this.shots = shots;
            this.missedShots = missedShots;
            this.accuracy = accuracy;
            this.ballLosses = ballLosses;
            this.fouls = fouls;
            this.yellowCards = yellowCards;
            this.redCards = redCards;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getShots() {
            return shots;
        }

        public int getMissedShots() {
            return missedShots;
        }

        public double getAccuracy() {
            return accuracy;
        }

        public int getBallLosses() {
            return ballLosses;
        }

        public int getFouls() {
            return fouls;
        }

        public int getYellowCards() {
            return yellowCards;
        }

        public int getRedCards() {
            return redCards;
        }
    }
}

