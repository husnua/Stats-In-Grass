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

        Stage addPlayerButtonClickedStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddPlayer.fxml"));
        addPlayerButtonClickedStage.setScene(new Scene(root, 950, 600));
        addPlayerButtonClickedStage.setTitle("Add Player");
        addPlayerButtonClickedStage.show();
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
        Parent root = FXMLLoader.load(getClass().getResource("CreateMatch.fxml"));
        primaryStage.setTitle("Create Match");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }
}

