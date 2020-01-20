package net.io.cortex.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.io.cortex.model.Riddle;
import org.bson.Document;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class RiddleRepositoryMongoImpl implements RiddleRepository {
    private MongoClient mongoClient;
    @Override
    /*
     * @param name - name to find
     * @return - optional
     */
    public Optional<String> findByName(String name) {
        if (StringUtils.isBlank(name)) {
            return Optional.empty();
        }

        MongoCollection<Document> collection = openMongoDbCollection("riddles");
        Document myDoc = collection.find(eq("name", name)).first();

        if (Optional.ofNullable(myDoc).isPresent()) {
            return Optional.ofNullable(myDoc.toJson());
        }
        return Optional.empty();

    }

    @Override
    /*
     * @param riddle - riddle to create
     * @return - bool
     */
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

        Document doc = new Document("name", riddle.getId())
                .append("content", riddle.getRiddleContent())
                .append("answers", riddle.getAnswers());
        Optional<String> riddleInBase = findByName(riddle.getId());

        if (riddleInBase.isPresent())
            return false;
        collection.insertOne(doc);
        mongoClient.close();
        return true;
    }


    @Override
    /*
     * @param oldRiddle - oldRiddle's data
     * @param newRiddle - newRiddle's data
     * @return - bool
     */
    public boolean update(Riddle oldRiddle, Riddle newRiddle) {
        if (oldRiddle == null || newRiddle == null) {
            mongoClient.close();
            return false;
        } else if (newRiddle.getAnswers() == null || newRiddle.getRiddleContent() == null) {
            mongoClient.close();
            return false;
        } else if (findByName(oldRiddle.getId()).equals(Optional.empty())) {
            mongoClient.close();
            return false;
        }
        MongoCollection<Document> collection = openMongoDbCollection("riddles");
        Document oldDoc = new Document("name", oldRiddle.getId())
                .append("content", oldRiddle.getRiddleContent())
                .append("answers", oldRiddle.getAnswers());

        Document newDoc = new Document("name", newRiddle.getId())
                .append("content", newRiddle.getRiddleContent())
                .append("answers", newRiddle.getAnswers());

        collection.findOneAndReplace(oldDoc, newDoc);

        mongoClient.close();
        return true;
    }

    @Override
    /*
     * @param riddle - riddle to delete
     * @return - bool
     */
    public boolean delete(Riddle riddle) {
        boolean result = false;
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
    /*
     *
     * @return - list of string
     */
    public List<String> findAll() {
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

    /*
     *
     * @param name - collection's name
     * @return - doc
     */
    private MongoCollection<Document> openMongoDbCollection(String name) {
        mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("cortex_db");
        return database.getCollection(name); //MongoCollection<Document> collection = database.getCollection(name);
    }
}
