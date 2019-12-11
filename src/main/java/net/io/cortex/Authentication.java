package net.io.cortex;

public class Authentication {
    private String login;
    private String password;

    public Authentication() {
    }

    public Authentication(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean logging() {

        return true;
    }
}
