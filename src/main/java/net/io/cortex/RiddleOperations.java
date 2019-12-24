package net.io.cortex;

import com.mongodb.MongoClient;
import net.io.cortex.model.Riddle;
import net.io.cortex.repository.RiddleRepositoryMongoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

public class RiddleOperations {
    private static byte[] LoadImage(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int) file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }

    public static String downloadImageFromMongoDB() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        //Riddle riddle = new Riddle(listOfFiles[i].getName(),answers,imageBytes);
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
        Optional<String> image = riddleRepositoryMongo.findByName("cat0.jpg");
        return image.orElse(null);
    }

    public static void uploadImages() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        File folder = new File("src/main/resources/gfx");
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                File file = new File(String.valueOf(listOfFile));
                String absolutePath = file.getAbsolutePath();
                byte[] imageBytes = new byte[0];
                try {
                    imageBytes = LoadImage(absolutePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String[] answers = {"A", "B", "C", "D"};

                Riddle riddle = new Riddle(listOfFile.getName(), answers, imageBytes);
                RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
                riddleRepositoryMongo.create(riddle);

            } else if (listOfFile.isDirectory()) {
                System.out.println("Directory " + listOfFile.getName());
            }
        }
        mongoClient.close();
    }
}
