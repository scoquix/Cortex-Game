package net.io.cortex;

public class Registration {
    private String login;
    private String password;

    public Registration(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean register() {
        return true;
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
}
