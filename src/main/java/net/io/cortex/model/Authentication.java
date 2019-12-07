package net.io.cortex.model;

public class Authentication {
    private String login;
    private String password;

    Authentication() {
    }

    public Authentication(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public boolean logging() {

        return true;
    }
}
