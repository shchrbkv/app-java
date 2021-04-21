package controllers;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Account;
import model.BankModel;
import model.HistoryRecord;
import view.AccountCell;
import view.HistoryCell;


public class MainController {
    private final BankModel model = BankModel.getInstance();
    private Stage modal;
    private PromptController promptController;
    private Account currentAccount;
    private FadeTransition ft;

    // Меню
    @FXML
    private Label LClientId;
    @FXML
    private Label LClientName;
    @FXML
    private Button BPay;
    @FXML
    private Button BTopup;
    @FXML
    private Button BWithdraw;
    @FXML
    private Button BOpenAccount;
    @FXML
    private Button BCloseAccount;
    @FXML
    private Button BReceipt;
    @FXML
    private Button BLogout;

    // Счета
    @FXML
    private Label LAccountsInfo;
    @FXML
    private ListView<Account> VAccountsList;
    @FXML
    private HBox PAccountsCard;

    // История
    @FXML
    private Label LHistoryInfo;
    @FXML
    private ListView<HistoryRecord> VHistoryList;
    @FXML
    private HBox PHistoryCard;

    @FXML
    public final void initialize(){
        LClientId.setText(String.format("#%06d", model.getCurrentClient().getId()));
        LClientName.setText(model.getCurrentClient().getName());

        ft = new FadeTransition(Duration.millis(1500), LAccountsInfo);

        VAccountsList.setCellFactory(f -> new AccountCell());
        VHistoryList.setCellFactory(f -> new HistoryCell());

        System.out.println(model.getCurrentAccounts());
        VAccountsList.setItems(model.getCurrentAccounts());

        // Обновление доступных действий со счетами
        BPay.disableProperty().bind(Bindings.isEmpty(VAccountsList.getItems()));
        BTopup.disableProperty().bind(Bindings.isEmpty(VAccountsList.getItems()));
        BWithdraw.disableProperty().bind(Bindings.isEmpty(VAccountsList.getItems()));

        // Проверка наличия истории
        LHistoryInfo.textProperty().bind(Bindings
                .when(Bindings.isEmpty(model.getCurrentHistory()))
                .then("Операции отсутствуют")
                .otherwise("Операции по счёту"));

        // Выбор аккаунта
        VAccountsList.getSelectionModel().selectedItemProperty().addListener((obs, os, ns) -> {
            if (ns != null) {
                currentAccount = ns;
                checkCurrentSelection();
                LAccountsInfo.setText("Доступно:");
                model.setCurrentHistory(currentAccount);
                VHistoryList.setItems(model.getCurrentHistory());
            }
        });
    }

    private void createModal() throws Exception{
        checkCurrentSelection();
        FXMLLoader promptLoader = new FXMLLoader(getClass().getResource("/view/BankModalPrompt.fxml"));
        Parent root = promptLoader.load();
        Scene promptScene = new Scene(root, 200, 280);

        modal = new Stage();
        modal.setScene(promptScene);
        modal.initOwner(LClientId.getScene().getWindow());
        modal.initModality(Modality.APPLICATION_MODAL);

        promptController = promptLoader.getController();
        //checkCurrentSelection();
    }

    private void checkCurrentSelection(){
        if (currentAccount == null){
            LAccountsInfo.setText("Выберите счёт");
            LAccountsInfo.setTextFill(Color.web("#d85151"));
            ft.stop();
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished (e -> {
                LAccountsInfo.setOpacity(1);
                LAccountsInfo.setText("Доступно:");
                LAccountsInfo.setTextFill(Color.web("#525252"));
            });
            ft.play();
            throw new IllegalStateException();
        } else {
            ft.stop();
            LAccountsInfo.setOpacity(1);
            LAccountsInfo.setTextFill(Color.web("#525252"));
        }
    }

    @FXML
    private void transfer() {
        try {
            createModal();
            promptController.transfer(currentAccount);
            modal.showAndWait();
            VAccountsList.refresh();
        }
        catch (Exception e) {
            System.err.println();
        }
    }

    @FXML
    private void topup() {
        try{
            createModal();
            promptController.topup(currentAccount);
            modal.showAndWait();
            VAccountsList.refresh();
        }
        catch (Exception e) {
            System.err.println();
        }
    }

    @FXML
    private void withdraw() {
        try{
            createModal();
            promptController.withdraw(currentAccount);
            modal.showAndWait();
            VAccountsList.refresh();
        }
        catch (Exception e) {
            System.err.println();
        }
    }

    @FXML
    private void openAccount(){
        model.openAccount(model.getCurrentClient());
        if (VAccountsList.getItems().size() == 1) LAccountsInfo.setText("Доступно");
        VAccountsList.getSelectionModel().clearSelection();
        currentAccount = null;
        VHistoryList.getItems().clear();
    }

    @FXML
    private void closeAccount(){
        try{
            createModal();
            promptController.closeAccount(currentAccount);
            modal.showAndWait();
            VAccountsList.getSelectionModel().clearSelection();
            currentAccount = null;
            if (VAccountsList.getItems().size() == 0) LAccountsInfo.setText("Нет открытых счетов");
            VHistoryList.getItems().clear();
        }
        catch (Exception e) {
            System.err.println();
        }
    }

    @FXML
    private void getReceipt(){
        model.getReceipt();
    }

    @FXML
    private void logout() throws Exception{
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/view/BankLogin.fxml"));
        Parent loginRoot = loginLoader.load();
        Scene loginScene = new Scene(loginRoot, 200, 280);
        Stage primaryStage = (Stage) BLogout.getScene().getWindow();
        primaryStage.setTitle("Вход");
        primaryStage.setScene(loginScene);
    }
}
