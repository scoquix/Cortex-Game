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
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class UserRepositoryMongoImpl implements UserRepository {
    private Authentication user;
    private MongoClient mongoClient;
    @Override
    public List<String> findAll() {
        MongoCollection<Document> collection = openMongoDbCollection("test");
        List<String> allUsers = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                String line = cursor.next().toJson();
                System.out.println(line);
                allUsers.add(line);
            }
        }
        mongoClient.close();
        return allUsers;
    }

    @Override
    public Optional<String> findById(String id) {
        // TODO: dodanie ID dla kazdego usera
        //        if (StringUtils.isBlank(id)) {
        //            return Optional.empty();
        //        }
        //        ObjectId x = new ObjectId(id);
        //        MongoCollection<Document> collection = openMongoDbCollection("test");
        //        Document myDoc = collection.find(eq("_id", x)).first();
        //        if (!Optional.ofNullable(myDoc).isPresent()) {
        //            return Optional.empty();
        //        }
        //        return Optional.ofNullable(myDoc.toJson());
        return Optional.empty();
    }

    @Override
    public Optional<String> findByName(String username, String pass) {
        if (StringUtils.isBlank(username)) {
            return Optional.empty();
        }
        MongoCollection<Document> collection = openMongoDbCollection("test");
        Document myDoc = collection.find(eq("name", username)).first();

        mongoClient.close();
        if (Optional.ofNullable(myDoc).isPresent()) {
            if (!myDoc.containsValue(pass))
                return Optional.empty();
            return Optional.ofNullable(myDoc.toJson());
        } else {
            return Optional.empty();
        }

    }

    @Override
    public boolean delete(Authentication user) {
        boolean result = false;
        this.user = user;
        if (user != null) {
            MongoCollection<Document> collection = openMongoDbCollection("test");
            if (findByName(user.getLogin(), user.getPassword()).isPresent()) {
                collection.deleteOne(eq("name", user.getLogin()));
                result = true;
            }
            mongoClient.close();
        }
        return result;
    }

    @Override
    public boolean create(Registration user) {
        if (user == null)
            return false;
        if (user.getLogin() == null || user.getPassword() == null)
            return false;
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
                .append("type", (user.getLogin().equals("admin")) ? "admin" : "user");
        Optional<String> userInBase = findByName(user.getLogin(), user.getPassword());

        if (userInBase.isPresent())
            return false;
        collection.insertOne(doc);
        mongoClient.close();
        return true;
    }

    @Override
    public boolean update(Authentication oldUser, Authentication newUser) {
        if (oldUser == null || newUser == null)
            return false;
        else if (newUser.getLogin() == null || newUser.getPassword() == null)
            return false;
        else if (Optional.empty().equals(findByName(oldUser.getLogin(), oldUser.getPassword())))
            return false;
        MongoCollection<Document> collection = openMongoDbCollection("test");
        Document oldDoc = new Document("name", oldUser.getLogin())
                .append("password", oldUser.getPassword());

        Document newDoc = new Document("name", newUser.getLogin())
                .append("password", newUser.getPassword());
        collection.findOneAndReplace(oldDoc, newDoc);
        mongoClient.close();
        return true;
    }
    private MongoCollection<Document> openMongoDbCollection(String name) {
        mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("cortex_db");
        return database.getCollection(name); //MongoCollection<Document> collection = database.getCollection(name);
    }
}