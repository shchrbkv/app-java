package model;

import data.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public final class BankModel {
    private static BankModel instance;
    private DAO dao;

    private Client currentClient;
    private final ObservableList<Account> currentAccounts = FXCollections.observableArrayList();
    private final ObservableList<HistoryRecord> currentHistory = FXCollections.observableArrayList();

    // Реализация Одиночки
    public static BankModel getInstance() {
        if (instance == null) {
            instance = new BankModel();
            instance.dao = new DAO();
        }
        return instance;
    }

    //Создание объектов
    private Account parseAccount(ResultSet rs) throws SQLException {
        return new Account(getClientById(rs.getInt("client_id")), rs.getInt("account_id"), rs.getDouble("balance"));
    }
    private Client parseClient(ResultSet rs) throws SQLException, UnsupportedEncodingException {
        return new Client(rs.getInt("client_id"), rs.getString("name"), rs.getString("login"), rs.getString("password"));
    }
    private HistoryRecord parseRecord(ResultSet rs) throws SQLException {
        return new HistoryRecord(rs.getInt("account_id"), rs.getInt("history_id"), rs.getString("message"), rs.getDouble("value"));
    }

    // Обновление счетов
    private void updateAccount(Account account) {
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE account_id = ?");
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getId());
            ps.executeUpdate();
        }
        catch (Exception e){
            System.err.println();
        }
    }

    // Управление счетами
    public Account getAccountById(int id){
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE account_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return parseAccount(rs);
            }
            rs.close();
        }
        catch (Exception e){
            System.err.println();
        }
        throw new NoSuchElementException("Счёт не найден");
    }
    public final List<Account> getAccountsByClient(Client client){
        List<Account> current = new ArrayList<>();
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE client_id = ?");
            ps.setInt(1, client.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = parseAccount(rs);
                current.add(account);
            }
            rs.close();
        }
        catch (Exception e){
            System.err.println();
        }
        return current;
    }
    public final ObservableList<Account> getCurrentAccounts(){
        return this.currentAccounts;
    }

    // Управление клиентами
    public final Client getClientById(int id){
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM clients WHERE client_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return parseClient(rs);
            }
            rs.close();
        } catch (SQLException | UnsupportedEncodingException throwables) {
            throwables.printStackTrace();
        }
        throw new NoSuchElementException("Счёт не найден");
    }
    public Client getClientByLogin(String login){
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM clients WHERE login = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return parseClient(rs);
            }
            rs.close();
        }
        catch (Exception e){
            System.err.println();
        }
        throw new NoSuchElementException("Пользователь не найден");
    }
    public final void setCurrentClient(Client current){
        this.currentClient = current;
        this.currentAccounts.setAll(getAccountsByClient(currentClient));
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
    public final void createClient(String name, String login, String password) {
        try {
            getClientByLogin(login);
        }
        catch (Exception e){
            try (Connection conn = dao.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO clients(login, password, name) VALUES (?, ?, ?) RETURNING client_id");
                ps.setString(1, login.toLowerCase());
                ps.setString(2, Client.hashPassword(password));
                System.out.println(Client.hashPassword(password));
                ps.setString(3, name);
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            throw new RuntimeException("Регистрация успешна, выполните вход");
        }
        throw new IllegalArgumentException("Такой пользователь уже существует");
    }

    // Открытие и закрытие счёта
    public final void openAccount(Client client) {
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO accounts(client_id, balance) VALUES (?, ?) RETURNING account_id");
            ps.setInt(1, client.getId());
            ps.setFloat(2, 0);
            ps.execute();
            ps.close();
        }
        catch (Exception err){
            System.err.println();
        }
        this.currentAccounts.setAll(getAccountsByClient(client));
    }
    public final void closeAccount(Account account){
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM accounts WHERE account_id = ? ");
            ps.setInt(1, account.getId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        this.currentAccounts.setAll(getAccountsByClient(currentClient));
    }

    // История
    public final void addHistoryRecord(int accountId, String message, double value){
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO history(account_id, message, value) VALUES (?, ?, ?) RETURNING history_id");
            ps.setInt(1, accountId);
            ps.setString(2, message);
            ps.setDouble(3, value);
            ps.executeUpdate();
        }
        catch (Exception err){
            System.err.println();
        }
    }
    public final List<HistoryRecord> getHistoryForAccountId(int id){
        List<HistoryRecord> current = new ArrayList<>();
        try (Connection conn = dao.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM history WHERE account_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                HistoryRecord record = parseRecord(rs);
                current.add(0, record);
            }
        }
        catch (Exception e){
            System.err.println();
        }
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
        updateAccount(destination);
        String message = "Пополнение ↑";
        addHistoryRecord(destination.getId(), message, amount);
    }

    // Снятие
    public final void withdraw(Account source, double amount){
        if (source.validateAction(amount)){
            source.setBalance(source.getBalance()-amount);
            updateAccount(source);
            String message = "Снятие ↓";
            addHistoryRecord(source.getId(), message, -amount);
        } else
            throw new IllegalArgumentException("У вас недостаточно средств");
    }

    // Перевод
    public final void transfer(Account source, Account destination, double amount){
        if (source.validateAction(amount)){
            source.setBalance(source.getBalance()-amount);
            updateAccount(source);
            destination.setBalance(destination.getBalance()+amount);
            updateAccount(destination);
            String message = String.format("Перевод #%d → #%d", source.getId(), destination.getId());
            addHistoryRecord(source.getId(), message, -amount);
            addHistoryRecord(destination.getId(), message, amount);
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
