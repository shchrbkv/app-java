package model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client implements Serializable {
    private int id;
    private  String name;
    private String login;
    private String password;
    private static int clientId = 0;

    // ID
    public final int getId() {
        return this.id;
    }
    private void setId(final int id) { this.id = id; }

    // Имя
    public final String getName() {
        return this.name;
    }
    private void setName(final String name){
        this.name = name;
    }

    // Логин
    public final String getLogin() {
        return this.login;
    }
    private void setLogin(final String login){
        this.login = login;
    }

    // Пароль c хэшем
    public final boolean validatePassword(final String password) {
        return this.password.equals(hashPassword(password));
    }
    private void setPassword(final String password){
        this.password = password;
    }
    public static String hashPassword(final String password){
        try {
            return new String(MessageDigest.getInstance("MD5")
                    .digest(password.getBytes("UTF-8")));
        } catch (Exception e) {
            System.err.println();
            return null;
        }
    }

    // Конструктор
    public Client(int id, String name, String login, String password) {
        setId(id);
        setName(name);
        setLogin(login);
        setPassword(password);
    }

}
