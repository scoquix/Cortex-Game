package net.io.cortex.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RiddleTest {
    private Riddle emptyRiddle = new Riddle();
    private Riddle riddle = new Riddle(1, new String[]{"A", "B", "C", "D"}, "Wybierz");

    @Test
    void getId() {
        assertEquals(1, riddle.getId(), "Id not equals");
        assertEquals(0, emptyRiddle.getId(), "getId empty test - failed");
    }

    @Test
    void setId() {
        riddle.setId(2);
        assertEquals(2, riddle.getId(), "setId test - failed");
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
        assertEquals("Wybierz", riddle.getRiddleContent(), "getRiddleContent test - failed");
        assertNull(emptyRiddle.getRiddleContent());
    }

    @Test
    void setRiddleContent() {
        riddle.setRiddleContent("Select");
        assertEquals("Select", riddle.getRiddleContent(), "setRiddleContent test - failed");

    }
}