package net.io.cortex.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.io.cortex.model.Authentication;
import net.io.cortex.model.Registration;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryMongoImplTest {
/*    @Test
    public void shouldCreateANewMongoClientConnectedToLocalhost() throws Exception {
        // When
        // TODO: get/create the MongoClient
        MongoClient mongoClient = null;

        // Then
        assertThat(mongoClient, is(notNullValue()));
    }*/

    @Test
    void findAll() {
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        List<String> users = userRepositoryMongo.findAll();
        assertEquals(users, userRepositoryMongo.findAll(), "findAll test: method failure");
    }

    @Test
    void findById() {
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        Optional<String> user = userRepositoryMongo.findByName("Ala");
        String mappedObject = null;
        Authentication authUser = null;
        String idString = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mappedObject = mapper.writeValueAsString(user.get());
            int startIndex = mappedObject.indexOf("\\\"$oid\\\" : ") + 11;
            int endIndex = mappedObject.indexOf("}", startIndex);
            idString = mappedObject.substring(startIndex, endIndex);
            idString = idString.replaceAll("\\\\\"", "").trim();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        assertEquals("5deb6c49dfdf9856b8c9f263", idString, "findById: Different id");

        String id = UUID.randomUUID().toString();
        assertEquals(Optional.empty(), userRepositoryMongo.findById(id), "findById: Somebody has that id - IMPOSSIBLE");
        assertEquals(Optional.empty(), userRepositoryMongo.findById(null), "findById: null id");
        assertEquals(Optional.empty(), userRepositoryMongo.findById(""), "findById: blank string id");
        Optional<String> jsonId = userRepositoryMongo.findById(idString);
        assertEquals(jsonId, userRepositoryMongo.findById(idString), "findByName: Different id");
    }

    @Test
    void findByName() {
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        assertEquals(Optional.empty(), userRepositoryMongo.findByName(null), "Null string test failed");
        Optional<String> jsonName = userRepositoryMongo.findByName("Ala");

        assertEquals(jsonName, userRepositoryMongo.findByName("Ala"), "findByName User that exist test failed");
        assertEquals(Optional.empty(), userRepositoryMongo.findByName("Ola"), "findByName User that do not exist test failed");
        assertEquals(Optional.empty(), userRepositoryMongo.findByName(""), "findByName Blank String");
    }

    @Test
    void delete() {
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        Registration userToRemove = new Registration("deleteTest", "deleteTest");
        userRepositoryMongo.create(userToRemove);

        assertTrue(userRepositoryMongo.delete(new Authentication(userToRemove.getLogin(), userToRemove.getPassword())), "delete test: delete method failure (parameter exists)");
        assertFalse(userRepositoryMongo.delete(new Authentication(userToRemove.getLogin(), userToRemove.getPassword())), "delete test: delete method failure (parameter does not exist)");
        assertEquals(Optional.empty(), userRepositoryMongo.findByName("deleteUser"), "delete test: User has not been deleted");


        assertFalse(userRepositoryMongo.delete(null), "delete test: delete method failure (parameter is null)");
    }

    @Test
    void create() {

        String id = UUID.randomUUID().toString();
        Registration userToCreate = new Registration("Ala" + id, "Kot");
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();

        assertTrue(userRepositoryMongo.create(userToCreate));
        assertFalse(userRepositoryMongo.create(userToCreate));

        assertFalse(userRepositoryMongo.create(null));
        assertFalse(userRepositoryMongo.create(new Registration(null, null)));
    }

    @Test
    void update() {
        String id = UUID.randomUUID().toString();
        Authentication oldUser = new Authentication("Ala", "Kot");
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        Authentication newUser = new Authentication("Ala", "Kot2");

        Authentication unknownUserToUpdate = new Authentication("Ala" + id, "Kot");
        assertFalse(userRepositoryMongo.update(unknownUserToUpdate, newUser));

        assertFalse(userRepositoryMongo.update(null, null));
        assertFalse(userRepositoryMongo.update(null, newUser));
        assertFalse(userRepositoryMongo.update(oldUser, null));
        assertFalse(userRepositoryMongo.update(new Authentication(null, null), new Authentication(null, null)));
        assertFalse(userRepositoryMongo.update(oldUser, new Authentication(null, null)));

        assertTrue(userRepositoryMongo.update(oldUser, newUser));
    }
}