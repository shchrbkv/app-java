package controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BankModel;
import model.Client;

public class LoginController {
    BankModel model = BankModel.getInstance();

    boolean isRegister = false;

    @FXML
    private TextField FLogin;
    @FXML
    private PasswordField FPassword;
    @FXML
    private TextField FName;
    @FXML
    private Label LMessage;
    @FXML
    private Button BLogin;
    @FXML
    private Button BRegister;

    @FXML
    private void go(){
        if (!isRegister) {
            try{
                validate();
                Client current = model.validateClient(FLogin.getText(), FPassword.getText());
                model.setCurrentClient(current);
                goToMain();
            }
            catch (Exception e){
                message(e.getMessage());
            }
        }
        else {
            try{
                validate();
                model.createClient(FName.getText(), FLogin.getText(), FPassword.getText());
            }
            catch (Exception e){
                message(e.getMessage());
            }
        }
    }

    @FXML
    private void change(){
        if (isRegister){
            isRegister = false;
            BRegister.setText("Регистрация");
            BLogin.setText("Войти");
            FName.setVisible(false);
        }
        else{
            isRegister = true;
            BRegister.setText("← Вход");
            BLogin.setText("Зарегистрироваться");
            FName.setVisible(true);
        }
    }

    private void validate(){
        boolean[] fields = {
                FLogin.getText().isEmpty(),
                FPassword.getText().isEmpty(),
                isRegister && FName.getText().isEmpty()
        };
        if (fields[0] || fields[1] || fields[2]){
            throw new IllegalStateException("Все поля должны быть заполнены!");
        }
    }

    private void message(String message){
        FadeTransition ft = new FadeTransition(Duration.millis(3000), LMessage);
        ft.setFromValue(1);
        ft.setToValue(0);

        LMessage.setVisible(true);
        LMessage.setText(message);
        ft.play();
    }

    private void goToMain() throws Exception {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/view/BankMain.fxml"));
        Parent mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot);
        Stage primaryStage = (Stage) BLogin.getScene().getWindow();
        primaryStage.setTitle("Хороший Банк");
        primaryStage.setScene(mainScene);

        MainController mainController = mainLoader.getController();
        //mainController.load();
    }
}
