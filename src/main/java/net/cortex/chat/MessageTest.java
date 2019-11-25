package net.cortex.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {

    @org.junit.jupiter.api.Test
    void getName() {
        Message m = new Message("message", "hejo");
        assertEquals("message", m.getName());
    }

    @org.junit.jupiter.api.Test
    void setName() {
    }

    @org.junit.jupiter.api.Test
    void getMessage() {
    }

    @org.junit.jupiter.api.Test
    void setMessage() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }
}