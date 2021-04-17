package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;
import model.Client;
import model.HistoryRecord;

import java.io.Serializable;

public class DataResource implements Serializable {
    public static ObservableList<Client> clients = FXCollections.observableArrayList();
    public static ObservableList<Account> accounts = FXCollections.observableArrayList();
    public static ObservableList<HistoryRecord> history = FXCollections.observableArrayList();
}
