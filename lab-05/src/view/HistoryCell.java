package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.HistoryRecord;

public class HistoryCell extends ListCell<HistoryRecord> {
    HBox card = new HBox();
    Label title = new Label();
    Label value = new Label();

    public HistoryCell() {
        card.setMaxWidth(Double.MAX_VALUE);
        card.setPrefHeight(40.0);
        card.setPadding(new Insets(4, 8, 4, 8));
        card.setStyle("-fx-background-color: #FAFAFA;");
        card.getStyleClass().addAll("rounded");

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
    protected void updateItem(HistoryRecord record, boolean empty){
        super.updateItem(record, empty);

        if(empty || record == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (record.getValue() < 0)
                value.setTextFill(Color.web("#d85151"));
            else {
                value.setTextFill(Color.web("#53b096"));
            }
            title.setText(record.getMessage());
            value.setText(String.format("%+.2f", record.getValue()));
            setGraphic(card);
        }
    }
}
