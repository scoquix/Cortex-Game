package net.io.cortex.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RiddleTest {
    private Riddle emptyRiddle = new Riddle();
    private ArrayList<byte[]> answers = new ArrayList<>();
    private byte[] image = {127, 127};
    private Riddle riddle = new Riddle("1", answers, image);

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
        String ans = riddle.getAnswers();
        assertEquals(ans, riddle.getAnswers(), "getAnswers test failed");
        assertNull(emptyRiddle.getAnswers());
    }

    @Test
    void setAnswers() {
        String arr = "XYZ";
        riddle.setAnswers(arr);
        assertEquals(arr, riddle.getAnswers(), "setAnswer test - failed");
    }

    @Test
    void getRiddleContent() {
        assertNull(emptyRiddle.getRiddleContent());
        assertNull(riddle.getRiddleContent());
        String content = "Content";
        riddle.setRiddleContent(content);

        assertEquals(content, riddle.getRiddleContent(), "getRiddleContent -- failed");
    }

    @Test
    void setRiddleContent() {
        riddle.setRiddleContent(null);
        assertNull(riddle.getRiddleContent());

        String arr = "content";
        riddle.setRiddleContent(arr);

        assertEquals(arr, riddle.getRiddleContent(), "getRiddleContent -- failed");
    }
}