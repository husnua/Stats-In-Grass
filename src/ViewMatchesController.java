import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dao.MatchDAO;

public class ViewMatchesController extends Application{

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> recentMatchesListView;

    private ObservableList<String> recentMatches = FXCollections.observableArrayList();
    private List<String> allMatches = new ArrayList<>();

    @FXML
    public void initialize() {
        loadSampleMatches();
        recentMatchesListView.setItems(recentMatches);
    }

    private void loadSampleMatches() {
        // Sample match data to test
        dao.MatchDAO mm = new MatchDAO(); 
        dao.TeamDAO tt = new TeamDAO(); 
        ArrayList<dao.Match> matches = mm.getAllMatches();
        for(int i = 0;i<matches.size();i++){
            allMatches.add( "Matches " + matches.get(i).getMatchId() + ": " + tt.getTeam(matches.get(i).getTeam1Id()).getName() + " vs " + tt.getTeam(matches.get(i).getTeam2Id()).getName() + " - " + matches.get(i).getTeam1Score() + ":" + matches.get(i).getTeam2Score());
        }
        /*allMatches.add("Match 1: Team A vs Team B - 2:1");
        allMatches.add("Match 2: Team C vs Team D - 0:0");
        allMatches.add("Match 3: Team E vs Team F - 3:2");
        allMatches.add("Match 4: Team A vs Team C - 1:1");
        allMatches.add("Match 5: Team B vs Team D - 4:3");*/

        // Display the last 3 matches
        recentMatches.addAll(allMatches.subList(Math.max(allMatches.size() - 3, 0), allMatches.size()));
    }

    @FXML
    void searchMatches(ActionEvent event) {
        String query = searchField.getText().toLowerCase();
        if (query.isEmpty()) {
            showAlert("Error", "Search field cannot be empty.");
            return;
        }
        List<String> filteredMatches = allMatches.stream()
                .filter(match -> match.toLowerCase().contains(query))
                .collect(Collectors.toList());
        recentMatches.setAll(filteredMatches);
    }

    @FXML
    void showMatchDetails(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selectedMatch = recentMatchesListView.getSelectionModel().getSelectedItem();
            if (selectedMatch != null) {
                try {
                    Stage matchDetailsStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MatchDetails.fxml"));
                    Parent root = loader.load();

                    MatchDetailsController controller = loader.getController();
                    controller.loadMatchDetails(selectedMatch); // Pass the selected match to the details controller

                    matchDetailsStage.setScene(new Scene(root, 950, 600));
                    matchDetailsStage.setTitle("Match Details");
                    matchDetailsStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ViewMatches.fxml"));
        primaryStage.setTitle("View Matches");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
