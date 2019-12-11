package net.io.cortex.repository;

import com.mongodb.MongoClient;
import net.io.cortex.model.Authentication;
import net.io.cortex.model.Registration;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryMongoImplTest {
    @Test
    public void shouldCreateANewMongoClientConnectedToLocalhost() throws Exception {
        // When
        // TODO: get/create the MongoClient
        MongoClient mongoClient = null;

        // Then
        assertThat(mongoClient, is(notNullValue()));
    }

    @Test
    void findAll() {
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        List<String> users = userRepositoryMongo.findAll();
        assertEquals(users, userRepositoryMongo.findAll(), "findAll test: method failure");
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        assertEquals(Optional.empty(), userRepositoryMongo.findByName(null), "Null string test failed");
        Optional<String> jsonName = userRepositoryMongo.findByName("Ala");

        assertEquals(jsonName, userRepositoryMongo.findByName("Ala"), "findByName User that exist test failed");
        assertEquals(Optional.empty(), userRepositoryMongo.findByName("Ola"), "findByName User that do not exist test failed");
    }

    @Test
    void delete() {
        UserRepositoryMongoImpl userRepositoryMongo = new UserRepositoryMongoImpl();
        Registration userToRemove = new Registration("deleteTest", "deleteTest");
        userRepositoryMongo.create(userToRemove);

        assertEquals(true, userRepositoryMongo.delete(new Authentication(userToRemove.getLogin(), userToRemove.getPassword())), "delete test: delete method failure (parameter exists)");
        assertEquals(false, userRepositoryMongo.delete(new Authentication(userToRemove.getLogin(), userToRemove.getPassword())), "delete test: delete method failure (parameter does not exist)");
        assertEquals(Optional.empty(), userRepositoryMongo.findByName("deleteUser"), "delete test: User has not been deleted");


        //assertEquals(false, userRepositoryMongo.delete(null), "delete test: delete method failure (parameter is null)");
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}