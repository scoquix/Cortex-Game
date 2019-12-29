package net.io.cortex.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RiddleTest {
    private Riddle emptyRiddle = new Riddle();
    private byte[] image = null;
    private Riddle riddle = new Riddle("1", new String[]{"A", "B", "C", "D"}, image);

    @Test
    void getId() {
        assertEquals("1", riddle.getId(), "Id not equals");
        assertNull(emptyRiddle.getId(), "getId empty test - failed");
    }

    @Test
    void setId() {
        riddle.setId("2");
        assertEquals("2", riddle.getId(), "setId test - failed");
    }

    @Test
    void getAnswers() {
        String[] arr = riddle.getAnswers();
        assertEquals(arr, riddle.getAnswers(), "getAnswers test failed");
        assertNull(emptyRiddle.getAnswers());
    }

    @Test
    void setAnswers() {
        String[] arr = {"X", "Y", "Z"};
        riddle.setAnswers(arr);
        assertEquals(arr, riddle.getAnswers(), "setAnswer test - failed");
    }

    @Test
    void getRiddleContent() {
        //TODO: test do napisania
    }

    @Test
    void setRiddleContent() {
        //TODO: test do napisania
    }
}