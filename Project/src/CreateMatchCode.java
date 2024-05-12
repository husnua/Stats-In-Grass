import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;



public class CreateMatchCode extends Application{

    @FXML
    private String nameTeamA;
    private String nameTeamB;
    private Team teamA;
    private Team teamB;
    

    @FXML
    void addPlayerForTeamA(ActionEvent event) {
        
    }

    @FXML
    void addPlayerForTeamB(ActionEvent event) {
        
    }

    @FXML
    void startMatch(ActionEvent event) {
        System.out.println("MATCH STARTING");
    }


    public static void main(String[] args)  {
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
