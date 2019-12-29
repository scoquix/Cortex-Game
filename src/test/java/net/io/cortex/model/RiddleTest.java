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
        assertNull(emptyRiddle.getRiddleContent());
        assertNull(riddle.getRiddleContent());
        byte[] arr = {123, 123, 123};
        riddle.setRiddleContent(arr);

        assertEquals(arr, riddle.getRiddleContent(), "getRiddleContent -- failed");
    }

    @Test
    void setRiddleContent() {
        riddle.setRiddleContent(null);
        assertNull(riddle.getRiddleContent());

        byte[] arr = {123, 123, 123};
        riddle.setRiddleContent(arr);

        assertEquals(arr, riddle.getRiddleContent(), "getRiddleContent -- failed");
    }
}