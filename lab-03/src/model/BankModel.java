package model;

import data.DataResource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.HistoryCell;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class BankModel {
    private static BankModel instance;

    private Client currentClient;
    private final ObservableList<Account> currentAccounts = FXCollections.observableArrayList();
    private final ObservableList<HistoryRecord> currentHistory = FXCollections.observableArrayList();

    // Реализация Одиночки
    public static BankModel getInstance() {
        if (instance == null) {
            instance = new BankModel();
        }
        return instance;
    }

    public final void saveData() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/data/database");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new ArrayList<>(DataResource.clients));
            objectOutputStream.writeObject(new ArrayList<>(DataResource.accounts));
            objectOutputStream.writeObject(new ArrayList<>(DataResource.history));
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            System.err.println();
        }
    }

    public final void loadData() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/data/database");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            DataResource.clients = FXCollections.observableArrayList((ArrayList<Client>)objectInputStream.readObject());
            DataResource.accounts = FXCollections.observableArrayList((ArrayList<Account>)objectInputStream.readObject());
            DataResource.history = FXCollections.observableArrayList((ArrayList<HistoryRecord>)objectInputStream.readObject());
            objectInputStream.close();
        } catch (Exception e) {
            System.err.println();
        }
    }

    // Управление счетами
    public final ObservableList<Account> getAccounts(){
        return DataResource.accounts;
    }
    public final void setAccounts(ArrayList<Account> accounts){
        if (getAccounts().isEmpty())
            DataResource.accounts.setAll(accounts);
        else
            throw new IllegalStateException();
    }
    public Account getAccountById(int id){
        for (Account account : getAccounts())
            if (account.getId() == id)
                return account;
        throw new IllegalArgumentException("Счёт не найден");
    }
    public final List<Account> getAccountsByClientId(int id){
        List<Account> current = new ArrayList<>();
        for (Account account : getAccounts())
            if (account.getClient().getId() == id)
                current.add(account);
        return current;
    }
    public final ObservableList<Account> getCurrentAccounts(){
        return this.currentAccounts;
    }

    // Управление клиентами
    public final ObservableList<Client> getClients(){
        return DataResource.clients;
    }
    public final void setClients(ArrayList<Client> clients){
        if (getClients().isEmpty())
            DataResource.clients.setAll(clients);
        else
            throw new IllegalStateException();
    }
    public Client getClientByLogin(String login){
        for (Client client : getClients())
            if (client.getLogin().equals(login))
                return client;
        throw new NoSuchElementException("Пользователь не найден");
    }
    public final void setCurrentClient(Client current){
        this.currentClient = current;
        this.currentAccounts.setAll(getAccountsByClientId(currentClient.getId()));
    }
    public final Client getCurrentClient(){
        return this.currentClient;
    }

    // Вход в систему
    public final Client validateClient(String login, String password) {
        Client potentialClient = getClientByLogin(login);
        if (potentialClient.validatePassword(password))
            return potentialClient;
        else
            throw new NoSuchElementException("Неправильный пароль");
    }

    // Регистрация клиента
    public final void createClient(int id, String name, String login, String password) {
        try {
            getClientByLogin(login);
        }
        catch (Exception e){
            DataResource.clients.add(new Client(id, name, login.toLowerCase(), password));
            throw new RuntimeException("Регистрация успешна, выполните вход");
        }
        throw new IllegalArgumentException("Такой пользователь уже существует");
    }

    // Открытие и закрытие счёта
    public final void openAccount(Client client) {
        DataResource.accounts.add(new Account(client, 0, 0));
        this.currentAccounts.setAll(getAccountsByClientId(currentClient.getId()));
    }
    public final void closeAccount(Account account){
        DataResource.accounts.remove(account);
        this.currentAccounts.setAll(getAccountsByClientId(currentClient.getId()));
    }

    // История
    public final ObservableList<HistoryRecord> getHistory() {
        return DataResource.history;
    }
    public final void addHistoryRecord(int accountId, int id, String message, double value){
        DataResource.history.add(0, new HistoryRecord(accountId, id, message, value));
    }
    public final List<HistoryRecord> getHistoryForAccountId(int id){
        List<HistoryRecord> current = new ArrayList<>();
        for (HistoryRecord record : getHistory())
            if (record.getAccountId() == id)
                current.add(record);
        return current;
    }
    public final void setCurrentHistory(Account current){
        this.currentHistory.setAll(getHistoryForAccountId(current.getId()));
    }
    public final ObservableList<HistoryRecord> getCurrentHistory(){
        return this.currentHistory;
    }

    // Пополнение
    public final void topup(Account destination, double amount){
        destination.setBalance(destination.getBalance()+amount);
        String message = "Пополнение ↑";
        addHistoryRecord(destination.getId(), 0, message, amount);
    }

    // Снятие
    public final void withdraw(Account source, double amount){
        if (source.validateAction(amount)){
            source.setBalance(source.getBalance()-amount);
            String message = "Снятие ↓";
            addHistoryRecord(source.getId(), 0, message, -amount);
        } else
            throw new IllegalArgumentException();
    }

    // Перевод
    public final void transfer(Account source, Account destination, double amount){
        if (source.validateAction(amount)){
            source.setBalance(source.getBalance()-amount);
            destination.setBalance(destination.getBalance()+amount);
            String message = String.format("Перевод #%d → #%d", source.getId(), destination.getId());
            addHistoryRecord(source.getId(), 0, message, -amount);
            addHistoryRecord(destination.getId(), 0, message, amount);
        } else
            throw new IllegalArgumentException("У вас недостаточно средств");
    }

    //Выписка
    public final void getReceipt(){
        try(FileWriter writer = new FileWriter(String.format("receipt-%06d.txt", currentClient.getId()), false)){
            writer.write(String.format("Клиент %s\n#%06d", currentClient.getName(), currentClient.getId()));
            for (Account account : getCurrentAccounts()) {
                writer.write(account.toString());
                setCurrentHistory(account);
                writer.write("\n---История операций:");
                for (HistoryRecord record : getCurrentHistory())
                    writer.write(record.toString());
            }
        } catch (IOException e) {
            System.err.println();
        }
    }
}
