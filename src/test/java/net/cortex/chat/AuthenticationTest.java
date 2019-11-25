package net.cortex.chat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationTest {

    @Test
    void getLogin() {
        Authentication authentication = new Authentication();
        authentication.setLogin("login");
        assertEquals("login", authentication.getLogin());
    }

    @Test
    void setLogin() {
        Authentication authentication = new Authentication();
        authentication.setLogin("login");
        assertEquals("login", authentication.getLogin());
    }

    @Test
    void getPassword() {
        Authentication authentication = new Authentication();
        authentication.setPassword("pass");
        assertEquals("pass", authentication.getPassword());
    }

    @Test
    void setPassword() {
        Authentication authentication = new Authentication();
        authentication.setPassword("pass");
        assertEquals("pass", authentication.getPassword());
    }
}