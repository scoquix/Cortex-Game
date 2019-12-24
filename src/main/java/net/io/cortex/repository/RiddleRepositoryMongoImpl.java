package net.io.cortex.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.io.cortex.model.Riddle;
import org.bson.Document;
import org.junit.platform.commons.util.StringUtils;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class RiddleRepositoryMongoImpl implements RiddleRepository {

    @Override
    public Optional<String> findByName(String name) {
        if (StringUtils.isBlank(name)) {
            return Optional.empty();
        }

        MongoCollection<Document> collection = openMongoDbCollection("riddles");
        Document myDoc = collection.find(eq("name", name)).first();

        if (Optional.ofNullable(myDoc).isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(myDoc.toJson());
    }

    @Override
    public boolean create(Riddle riddle) {
        if (riddle == null)
            return false;
        if (riddle.getRiddleContent() == null || riddle.getAnswers() == null)
            return false;
        //instance running on localhost on port 27017
        //MongoClient mongoClient = new MongoClient();
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //Specify the name of the database to the getDatabase() method.
        //If a database does not exist, MongoDB creates the database when you first store data for that database.
        MongoDatabase database = mongoClient.getDatabase("cortex_db");

        //Specify the name of the collection to the getCollection() method.
        //If a collection does not exist, MongoDB creates the collection when you first store data for that collection.
        MongoCollection<Document> collection = database.getCollection("riddles");
        String encode = Base64.getEncoder().encodeToString(riddle.getRiddleContent());
        Document doc = new Document("name", riddle.getId())
                .append("content", encode)
                .append("answers", Arrays.toString(riddle.getAnswers()));
        Optional<String> riddleInBase = findByName(riddle.getId());

        if (riddleInBase.isPresent())
            return false;
        collection.insertOne(doc);
        mongoClient.close();
        return true;
    }


    @Override
    public boolean update(Riddle oldRiddle, Riddle newRiddle) {
        // TODO: Update riddle content
        return false;
    }

    @Override
    public boolean delete(Riddle riddle) {
        boolean result = false;
        //this.user = user;
        if (riddle != null) {
            MongoCollection<Document> collection = openMongoDbCollection("riddles");
            if (findByName(riddle.getId()).isPresent()) {
                collection.deleteOne(eq("name", riddle.getId()));
                result = true;
            }

        }
        return result;
    }


    @Override
    public List<String> findAll(String name) {
        MongoCollection<Document> collection = openMongoDbCollection("riddles");
        List<String> allRiddles = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                String line = cursor.next().toJson();
                System.out.println(line);
                allRiddles.add(line);
            }
        }
        return allRiddles;
    }

    private MongoCollection<Document> openMongoDbCollection(String name) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("cortex_db");
        return database.getCollection(name); //MongoCollection<Document> collection = database.getCollection(name);
    }
}
