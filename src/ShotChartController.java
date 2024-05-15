import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ShotChartController {

    @FXML
    private ImageView pitchImageView;

    @FXML
    private Pane pane;

    private int[][] shootCoordinatesTeamA;
    private int[][] shootCoordinatesTeamB;

    public void setShotCoordinates(int[][] shootCoordinatesTeamA, int[][] shootCoordinatesTeamB) {
        this.shootCoordinatesTeamA = shootCoordinatesTeamA;
        this.shootCoordinatesTeamB = shootCoordinatesTeamB;
        drawShotChart();
    }

    private void drawShotChart() {
        if (shootCoordinatesTeamA != null) {
            for (int[] coordinate : shootCoordinatesTeamA) {
                Circle shotDot = new Circle(coordinate[0], coordinate[1], 5, Color.BLUE);
                pane.getChildren().add(shotDot);
            }
        }

        if (shootCoordinatesTeamB != null) {
            for (int[] coordinate : shootCoordinatesTeamB) {
                Circle shotDot = new Circle(coordinate[0], coordinate[1], 5, Color.RED);
                pane.getChildren().add(shotDot);
            }
        }
    }
}
