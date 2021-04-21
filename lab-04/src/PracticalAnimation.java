import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PracticalAnimation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Объекты
        Circle circle = new Circle(50);
        circle.setFill(Color.RED);
        circle.setVisible(false);

        Rectangle square = new Rectangle(80, 80);
        square.setFill(Color.YELLOW);
        square.setVisible(false);

        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(0.0, 80.0, 80.0, 80.0, 40.0, 0.0);
        triangle.setFill(Color.GREEN);
        triangle.setVisible(false);

        // Таймлайн
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {triangle.setVisible(false); circle.setVisible(true);}),
                new KeyFrame(Duration.seconds(1), e -> {circle.setVisible(false); square.setVisible(true);}),
                new KeyFrame(Duration.seconds(2), e -> {square.setVisible(false); triangle.setVisible(true);}),
                new KeyFrame(Duration.seconds(3), e -> {})
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Кнопка
        ToggleButton play = new ToggleButton("▶");
        play.setFont(Font.font(20));
        play.setOnAction((event) -> {
            ToggleButton source = (ToggleButton) event.getSource();
            if (source.isSelected()) {
                play.setText("II");
                timeline.play();
            } else {
                play.setText("▶︎");
                timeline.pause();
            }
        });

        // Сцена
        StackPane root = new StackPane(circle, square, triangle, play);
        StackPane.setAlignment(play, Pos.BOTTOM_CENTER);
        StackPane.setMargin(play, new Insets(0, 0, 10, 0));

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Figures");

        primaryStage.show();
    }

}
