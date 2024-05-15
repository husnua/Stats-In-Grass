import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScreenCode extends Application {

    @FXML
    public void showCreateMatchScreen() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("CreateMatch.fxml"));
            stage.setTitle("Create Match");
            stage.setScene(new Scene(root, 950, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showViewMatchScreen() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("ViewMatches.fxml"));
            stage.setTitle("View Matches");
            stage.setScene(new Scene(root, 950, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Homepage");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
