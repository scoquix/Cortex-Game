package net.io.cortex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RiddleOperationsTest {

    @Test
    void downloadImageFromMongoDB() {
        String s = RiddleOperations.downloadImageFromMongoDB();
        assertNotNull(s);
    }

    @Test
    void downloadRiddleFromMongoDB() {
        String s = RiddleOperations.downloadRiddleFromMongoDB();
        assertNotNull(s);
    }

    @Test
    void uploadImages() {
        RiddleOperations.uploadImages();
    }
}