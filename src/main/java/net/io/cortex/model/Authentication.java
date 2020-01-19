package net.io.cortex.model;

import net.io.cortex.repository.UserRepositoryMongoImpl;

public class Authentication {
    private String id;
    private String login;
    private String password;

    /**
     * @param login
     * @param password
     */
    public Authentication(String login, String password) {
        this.login = login;
        this.password = password;
    }

    Authentication() {
    }

    /**
     *
     * @param id
     * @param login
     * @param password
     */
    public Authentication(String id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
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

    /**
     *
     * @return
     */
    public boolean logging() {
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        if (userRepositoryMongo.findByName(login, password).isPresent()) {
            return true;
        }
        return false;
    }
}
