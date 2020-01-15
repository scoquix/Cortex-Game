package net.io.cortex.repository;

import net.io.cortex.model.Riddle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RiddleRepositoryMongoImplTest {
    private static ArrayList<byte[]> answers;
    private static ArrayList<byte[]> emptyAnswers;
    private static byte[] content;
    private static byte[] emptyContent;
    private static String id;
    private static Riddle riddle;
    private static Riddle emptyRiddle;

    @BeforeAll
    static void init() {
        id = UUID.randomUUID().toString();
        emptyContent = null;
        content = new byte[]{1, 2, 3, 4};
        emptyAnswers = null;
        answers = new ArrayList<>();
        answers.add(content);
        riddle = new Riddle("zagadka" + id, answers, content);
        emptyRiddle = new Riddle("", emptyAnswers, emptyContent);
    }

    @Test
    void findByName() {
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
        assertEquals(Optional.empty(), riddleRepositoryMongo.findByName(null), "Null string test failed");
        Optional<String> jsonName = riddleRepositoryMongo.findByName("cat0.jpg");

        assertEquals(jsonName, riddleRepositoryMongo.findByName("cat0.jpg"), "findByName User that exist test failed");
        assertEquals(Optional.empty(), riddleRepositoryMongo.findByName("unexisted_riddle"), "findByName User that do not exist test failed");
        assertEquals(Optional.empty(), riddleRepositoryMongo.findByName(""), "findByName Blank String");

    }

    @Test
    void create() {
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();

        assertTrue(riddleRepositoryMongo.create(riddle));
        assertFalse(riddleRepositoryMongo.create(riddle));
        assertFalse(riddleRepositoryMongo.create(emptyRiddle));

        assertFalse(riddleRepositoryMongo.create(null));
        assertFalse(riddleRepositoryMongo.create(new Riddle(null, null, null)));

    }

    @Test
    void update() {
        String id = UUID.randomUUID().toString();
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
        Riddle oldRiddle = new Riddle(riddle.getId(), answers, content);
        riddleRepositoryMongo.create(oldRiddle);
        byte[] newContent = {1, 2, 3};
        Riddle newRiddle = new Riddle(riddle.getId(), answers, newContent);

        Riddle unknownRiddle = new Riddle("unknown_riddle", answers, content);
        assertFalse(riddleRepositoryMongo.update(unknownRiddle, newRiddle));

        assertFalse(riddleRepositoryMongo.update(null, null));
        assertFalse(riddleRepositoryMongo.update(null, newRiddle));
        assertFalse(riddleRepositoryMongo.update(oldRiddle, null));
        assertFalse(riddleRepositoryMongo.update(new Riddle(null, null, null), new Riddle(null, null, null)));
        assertFalse(riddleRepositoryMongo.update(oldRiddle, new Riddle(null, null, null)));

        assertTrue(riddleRepositoryMongo.update(oldRiddle, newRiddle));
    }

    @Test
    void delete() {
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
        riddleRepositoryMongo.create(riddle);
        assertTrue(riddleRepositoryMongo.delete(riddle), "delete test: delete method failure (parameter exists)");
        assertEquals(Optional.empty(), riddleRepositoryMongo.findByName(riddle.getId()), "delete test: User has not been deleted");

        assertFalse(riddleRepositoryMongo.delete(null), "delete test: delete method failure (parameter is null)");

    }

    @Test
    void findAll() {
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
        List<String> users = riddleRepositoryMongo.findAll();
        assertEquals(users, riddleRepositoryMongo.findAll(), "findAll test: method failure");

    }
}