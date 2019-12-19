package net.io.cortex.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    @Test
    void getLogin() {
        Authentication authentication = new Authentication("login", "password");
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

    @Test
    void logging() {
        String id = UUID.randomUUID().toString();
        Authentication authentication = new Authentication("Ala" + id, "Kot");

        assertFalse(authentication.logging());

        authentication.setLogin("Ala");
        authentication.setPassword("Kot");
        assertTrue(authentication.logging());

        Authentication regNull = new Authentication();

        //Walidacja nie powinna do tego dopuścić!
        assertFalse(regNull.logging());
    }
}