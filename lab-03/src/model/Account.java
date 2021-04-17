package model;


import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private double balance;
    private Client client;
    private static int accountId = 0;

    // ID
    public final int getId() {
        return this.id;
    }
    private void setId(final int id){
        if (id == 0)
            this.id = ++accountId;
        else
            this.id = id;
    }

    // Баланс
    public final double getBalance() {
        return this.balance;
    }
    public void setBalance(final double balance){
        this.balance = balance;
    }

    // ID клиента
    public final Client getClient() {
        return this.client;
    }
    private void setClient(Client client){
        this.client = client;
    }

    public Account(Client client, int id, double balance) {
        setClient(client);
        setId(id);
        setBalance(balance);
    }

    // Проверка баланса для операции
    public final boolean validateAction(double amount){
        return getBalance() >= amount;
    }

    @Override
    public String toString() {
        return "\n--Счёт #" + getId() +
                "\n  Баланс" + getBalance();
    }
}
