package net.io.cortex.model;

import net.io.cortex.repository.UserRepositoryMongoImpl;

public class Registration {
    private String login;
    private String password;

    Registration() {
    }

    /**
     * @param login
     * @param password
     */
    public Registration(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     *
     * @return
     */
    public boolean register() {
        if (login != null && password != null)
            if (Validation.validateLogin(login) && Validation.validatePassword(password)) {
                System.out.println("Walidacja przeszla");
                UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
                return userRepositoryMongo.create(this);
            }
        return false;
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    void setPassword(String password) {
        this.password = password;
    }
}
