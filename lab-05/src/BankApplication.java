import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.BankModel;

public class BankApplication extends Application{
    BankModel model = BankModel.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/view/BankLogin.fxml"));
        Parent loginRoot = loginLoader.load();
        Scene loginScene = new Scene(loginRoot, 200, 280);

        primaryStage.setTitle("Вход");
        primaryStage.setScene(loginScene);

        primaryStage.show();
    }
}