package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Account;

public class AccountCell extends ListCell<Account> {
    HBox card = new HBox();
    Label title = new Label();
    Label value = new Label();

    public AccountCell() {
        card.setMaxWidth(Double.MAX_VALUE);
        card.setPrefHeight(40.0);
        card.setPadding(new Insets(4, 8, 4, 8));
        card.setStyle("-fx-background-color: #FFFFFF;");
        card.getStyleClass().addAll("rounded", "selectable");

        title.setMinWidth(70.0);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(title, Priority.ALWAYS);
        title.setFont(new Font("Roboto", 12));
        title.setTextFill(Color.web("#525252"));
        value.setAlignment(Pos.CENTER_LEFT);
        title.setWrapText(true);

        value.setMinWidth(80.0);
        value.setMaxWidth(Double.MAX_VALUE);
        value.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(value, Priority.ALWAYS);
        value.setFont(new Font("Roboto Black", 14));
        value.setTextFill(Color.web("#53b096"));
        value.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(title, value);
    }

    @Override
    protected void updateItem(Account account, boolean empty){
        super.updateItem(account, empty);

        if(empty || account == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            title.setText("Счёт #" + account.getId());
            value.setText(String.format("$%.2f", account.getBalance()));
            setGraphic(card);
        }
    }
}
