package controllers;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Account;
import model.BankModel;

import java.util.NoSuchElementException;

public class PromptController {
    BankModel model = BankModel.getInstance();

    @FXML
    private Label LTitle;
    @FXML
    private Label LHeader;
    @FXML
    private TextField FDestination;
    @FXML
    private TextField FAmount;
    @FXML
    private Label LMessage;

    @FXML
    private Button BAction;
    @FXML
    private Button BCancel;

    private void message(String message){
        FadeTransition ft = new FadeTransition(Duration.millis(3000), LMessage);
        ft.setFromValue(1);
        ft.setToValue(0);
        LMessage.setVisible(true);
        LMessage.setText(message);
        ft.play();
    }
    @FXML
    private void close(){
        ((Stage) BAction.getScene().getWindow()).close();
    }
    public final void transfer(Account source){
        LTitle.setText("Перевод");
        LHeader.setText("Счёт получателя\nи сумма");
        FDestination.setPromptText("Получатель");
        FAmount.setPromptText("Сумма");
        BAction.textProperty().bind(Bindings.concat("Перевести $", FAmount.textProperty()));
        BAction.setOnAction(event -> {
            try{
                model.transfer(
                        source,
                        model.getAccountById(Integer.parseInt(FDestination.getText())),
                        Double.parseDouble(FAmount.getText()));
                model.setCurrentHistory(source);
                close();
            }
            catch (NumberFormatException e){
                message("Проверьте введённые данные");
            }
            catch (IllegalArgumentException e){
                message(e.getMessage());
            }
        });
    }
    public final void topup(Account account){
        LTitle.setText("Пополнить");
        LHeader.setText("Сумма пополнения");
        FDestination.setManaged(false);
        FAmount.setPromptText("Сумма");
        BAction.textProperty().bind(Bindings.concat("Пополнить $", FAmount.textProperty()));
        BAction.setOnAction(event -> {
            try{
                model.topup(account, Double.parseDouble(FAmount.getText()));
                model.setCurrentHistory(account);
                close();
            }
            catch (NumberFormatException e){
                message("Проверьте введённые данные");
            }
        });
    }
    public final void withdraw(Account account){
        LTitle.setText("Снятие");
        LHeader.setText("Сумма снятия");
        FDestination.setManaged(false);
        FAmount.setPromptText("Сумма");
        BAction.textProperty().bind(Bindings.concat("Снять $", FAmount.textProperty()));
        BAction.setOnAction(event -> {
            try{
                model.withdraw(account, Double.parseDouble(FAmount.getText()));
                model.setCurrentHistory(account);
                close();
            }
            catch (NumberFormatException e){
                message("Проверьте введённые данные");
            }
            catch (IllegalArgumentException e){
                message(e.getMessage());
            }
        });
    }
    public final void closeAccount(Account account){
        LTitle.setText("Закрытие счёта");
        LHeader.setText("Вы действительно хотите закрыть счёт?");
        FDestination.setManaged(false);
        FAmount.setManaged(false);
        LMessage.setManaged(false);
        BAction.setText("Закрыть счёт");
        BAction.setOnAction(event -> {
            model.closeAccount(account);
            close();
        });
    }
    public final void getReceipt(){
        LTitle.setText("Закрытие счёта");
        LHeader.setText("Файл выписки сохранён");
        FDestination.setManaged(false);
        FAmount.setManaged(false);
        LMessage.setManaged(false);
        BAction.setText("Отлично");
        BAction.setOnAction(event -> close());
        BCancel.setManaged(false);
    }
}
