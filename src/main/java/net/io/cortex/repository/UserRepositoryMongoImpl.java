package net.io.cortex.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.io.cortex.model.Authentication;
import net.io.cortex.model.Registration;
import org.bson.Document;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class UserRepositoryMongoImpl implements UserRepository {
    @Override
    public List<String> findAll() {
        MongoCollection<Document> collection = openMongoDbCollection("test");
        List<String> allUsers = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                String line = cursor.next().toJson();
                System.out.println(line);
                allUsers.add(line);
            }
        } finally {
            cursor.close();
        }
        return allUsers;
    }

    @Override
    public Optional<String> findById(String id) {
        if (StringUtils.isBlank(id)) {
            return Optional.empty();
        }
        MongoCollection<Document> collection = openMongoDbCollection("test");
        Document myDoc = collection.find(eq("id", id)).first();

        return Optional.ofNullable(myDoc.toJson());
    }

    @Override
    public Optional<String> findByName(String name) {
        if (StringUtils.isBlank(name)) {
            return Optional.empty();
        }
        MongoCollection<Document> collection = openMongoDbCollection("test");
        Document myDoc = collection.find(eq("name", name)).first();
        if (!Optional.ofNullable(myDoc).isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(myDoc.toJson());
    }

    @Override
    public boolean delete(Authentication user) {
        if (user != null) {
            MongoCollection<Document> collection = openMongoDbCollection("test");

            //The following example deletes at most one document that meets the filter i equals 110:
            collection.deleteOne(eq("i", 110));
        }
        return false;
    }
    @Override
    public boolean create(Registration user) {
        //instance running on localhost on port 27017
        //MongoClient mongoClient = new MongoClient();
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //Specify the name of the database to the getDatabase() method.
        //If a database does not exist, MongoDB creates the database when you first store data for that database.
        MongoDatabase database = mongoClient.getDatabase("cortex_db");

        //Specify the name of the collection to the getCollection() method.
        //If a collection does not exist, MongoDB creates the collection when you first store data for that collection.
        MongoCollection<Document> collection = database.getCollection("test");

        Document doc = new Document("name", user.getLogin())
                .append("password", user.getPassword())
                .append("type", (user.getLogin() != null && user.getLogin().equals("admin")) ? "admin" : "user");
        Optional<String> userInBase = findByName(user.getLogin());

        if (userInBase.isPresent()) {
            System.out.println("User " + user.getLogin() + " jest juz w bazie");
            return false;
        } else {
            collection.insertOne(doc);
            mongoClient.close();
            return true;
        }

    }

    @Override
    public boolean update(Authentication user) {
        MongoCollection<Document> collection = openMongoDbCollection("test");
        Document doc = new Document("name", user.getLogin())
                .append("password", user.getPassword())
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));

        collection.insertOne(doc);
        return false;
    }
    private MongoCollection<Document> openMongoDbCollection(String name) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("cortex_db");
        return database.getCollection(name); //MongoCollection<Document> collection = database.getCollection(name);
    }
}
