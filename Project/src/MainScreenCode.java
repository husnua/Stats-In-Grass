import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;


public class MainScreenCode extends Application{

    

    @FXML
    public void showCreateMatchScreen (ActionEvent event) {
        System.out.println("Creating Match");
    }

    @FXML
    public void showViewMatchScreen(ActionEvent event) {
        System.out.println("Viewing Match");
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

        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Homepage");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();

    
        
    }
}
