package model;

import java.io.Serializable;

public class HistoryRecord implements Serializable {
    private int id;
    private int accountId;
    private String message;
    private Double value;

    // ID
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    // Счёт
    public int getAccountId() {
        return accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    // Сообщение
    public final String getMessage() {
        return this.message;
    }
    private void setMessage(final String message){
        this.message = message;
    }

    // Balance
    public final double getValue() {
        return this.value;
    }
    private void setValue(final double value){
        this.value = value;
    }

    public HistoryRecord(int accountId, int id, String message, double value) {
        setId(id);
        setAccountId(accountId);
        setMessage(message);
        setValue(value);
    }

    @Override
    public String toString() {
        return "\n   " + getMessage() +
                "\t" + getValue();
    }
}
