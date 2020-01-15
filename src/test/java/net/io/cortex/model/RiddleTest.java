package net.io.cortex.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RiddleTest {
    private static Riddle emptyRiddle;
    private static ArrayList<byte[]> answers;
    private static byte[] arr;
    private static Riddle riddle;

    @BeforeAll
    static void init() {
        arr = new byte[]{127, 127};
        answers = new ArrayList<>();
        answers.add(arr);
        emptyRiddle = new Riddle(null, null, null);
        riddle = new Riddle("1", answers, arr);
        Riddle defaultRiddle = new Riddle();
    }

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
        String content = "Content";
        riddle.setRiddleContent(content);

        assertEquals(content, riddle.getRiddleContent(), "getRiddleContent -- failed");

        String encoded = Base64.getEncoder().encodeToString(arr);
        riddle.setRiddleContent(encoded);
        assertEquals(encoded, riddle.getRiddleContent(), "Encoded -- failed");
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