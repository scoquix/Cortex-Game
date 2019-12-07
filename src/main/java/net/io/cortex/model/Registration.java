package net.io.cortex.model;

import net.io.cortex.repository.UserRepositoryMongoImpl;

public class Registration {
    private String login;
    private String password;

    Registration() {
    }

    public Registration(String login, String password) {
        this.login = login;
        this.password = password;
    }


    public boolean register() {
        //Validation.validateLogin(this.getLogin());
        //Validation.validatePassword(this.getPassword());
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        return userRepositoryMongo.create(this);
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
}
