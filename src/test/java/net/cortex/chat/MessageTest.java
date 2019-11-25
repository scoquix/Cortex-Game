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
        Message m = new Message();
        m.setName("mess");
        m.setMessage("hejo");
        assertEquals("mess", m.getName());
    }

    @org.junit.jupiter.api.Test
    void getMessage() {
        Message m = new Message("mess", "hejo");
        assertEquals("hejo", m.getMessage());
    }

    @org.junit.jupiter.api.Test
    void setMessage() {
        Message m = new Message();
        m.setName("mess");
        m.setMessage("hejo");
        assertEquals("hejo", m.getMessage());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        //return "Message{" + "name=" + name + ", message=" + message + '}'
        Message m = new Message("message", "hejo");
        assertEquals("Message{" + "name=" + m.getName() + ", message=" + m.getMessage() + '}', m.toString());
    }
}