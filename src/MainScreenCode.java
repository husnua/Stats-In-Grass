import dao.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScreenCode extends Application {
    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }
    @FXML
    public void showCreateMatchScreen() {
        try {
            CreateMatchCode cmc = new CreateMatchCode();
            cmc.start(getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showViewMatchScreen() {
        try {
            ViewMatchesController vmc = new ViewMatchesController();
            vmc.start(getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Homepage");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        launch(args);
    }
}
