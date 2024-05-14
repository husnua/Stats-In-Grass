import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;


public class MainScreenCode extends Application{
    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }

    @FXML
    public void showCreateMatchScreen (ActionEvent event) {
        System.out.println("Creating Match");
        try {
            CreateMatchCode cmc = new CreateMatchCode();
            cmc.start(stage); 
            
        } catch (Exception e) {
            System.out.println("Could not change screen to create match");
        }
    }

    @FXML
    public void showViewMatchScreen(ActionEvent event) {
        System.out.println("Viewing Match");
        try {
            ViewMatchesController vmc = new ViewMatchesController();
            vmc.start(stage); 
        } catch (Exception e) {
            System.out.println("Could not change screen to view match");
        }
    }

    @FXML
    public void startMatch(ActionEvent event) {
        System.out.println("Starting Match");
    }

    public static void main(String[] args)  {
        System.out.println("Hello, World!");
        launch(args);
    }


    

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Homepage");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();

    
        
    }
}
