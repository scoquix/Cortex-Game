package net.io.cortex.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationTest {

    @Test
    void register() {
        String id = UUID.randomUUID().toString();
        Registration registration = new Registration("Ala" + id, "Kot");

        assertTrue(registration.register());
        assertFalse(registration.register());

        Registration regNull = new Registration();

        //Walidacja nie powinna do tego dopuścić!
        assertTrue(regNull.register());
    }

    @Test
    void getLogin() {
        Registration registration = new Registration("Ala", "Kot");
        assertEquals("Ala", registration.getLogin());

        Registration regNull = new Registration();
        assertNull(regNull.getLogin());
    }

    @Test
    void setLogin() {
        Registration registration = new Registration("Ala", "Kot");
        registration.setLogin("Tomek");
        assertEquals("Tomek", registration.getLogin());

    }

    @Test
    void getPassword() {
        Registration registration = new Registration("Ala", "Kot");
        assertEquals("Kot", registration.getPassword());

        Registration regNull = new Registration();
        assertNull(regNull.getPassword());
    }

    @Test
    void setPassword() {
        Registration registration = new Registration("Ala", "Kot");
        registration.setPassword("Kotek");
        assertEquals("Kotek", registration.getPassword());
    }
}