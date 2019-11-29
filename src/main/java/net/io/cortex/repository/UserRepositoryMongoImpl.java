package net.io.cortex.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
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
                System.out.println(cursor.next().toJson());
                allUsers.add(cursor.next().toJson());
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

        return Optional.ofNullable(myDoc.toJson().toString());
    }

    @Override
    public Optional<String> findByName(String id) {
        if (StringUtils.isBlank(id)) {
            return Optional.empty();
        }
        MongoCollection<Document> collection = openMongoDbCollection("test");
        Document myDoc = collection.find(eq("id", id)).first();

        return Optional.ofNullable(myDoc.toJson().toString());
    }

    @Override
    public void delete(String id) {
        if (!StringUtils.isBlank(id)) {
            MongoCollection<Document> collection = openMongoDbCollection("test");

            //The following example deletes at most one document that meets the filter i equals 110:
            collection.deleteOne(eq("i", 110));
        }
    }

    @Override
    public void create(String id) {
        //instance running on localhost on port 27017
        //MongoClient mongoClient = new MongoClient();
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //Specify the name of the database to the getDatabase() method.
        //If a database does not exist, MongoDB creates the database when you first store data for that database.
        MongoDatabase database = mongoClient.getDatabase("cortex_db");

        //Specify the name of the collection to the getCollection() method.
        //If a collection does not exist, MongoDB creates the collection when you first store data for that collection.
        MongoCollection<Document> collection = database.getCollection("test");

        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));

        collection.insertOne(doc);
    }

    @Override
    public void update(String id) {
        MongoCollection<Document> collection = openMongoDbCollection("test");
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));

        collection.insertOne(doc);

    }

    private MongoCollection<Document> openMongoDbCollection(String name) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("cortex_db");
        MongoCollection<Document> collection = database.getCollection(name);
        return collection;
    }
}
