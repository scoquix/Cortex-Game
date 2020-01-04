package net.io.cortex;

import com.mongodb.MongoClient;
import net.io.cortex.model.Riddle;
import net.io.cortex.repository.RiddleRepositoryMongoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class RiddleOperations {
    private static byte[] createInputStream(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int) file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }

    private static FileInputStream createInputStream2(String filePath) {
        File file = new File(filePath);
        int size = (int) file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(buffer);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }


    public static String downloadImageFromMongoDB() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        //Riddle riddle = new Riddle(listOfFiles[i].getName(),answers,imageBytes);
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
        Random rand = new Random();
        int randInt = rand.nextInt(5) + 1;
        Optional<String> image = riddleRepositoryMongo.findByName(Integer.toString(randInt));
        mongoClient.close();
        return image.orElse(null);
    }

    public static String downloadRiddleFromMongoDB() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        //Riddle riddle = new Riddle(listOfFiles[i].getName(),answers,imageBytes);
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
        Random rand = new Random();
        int randInt = rand.nextInt(5) + 1;
        Optional<String> image = riddleRepositoryMongo.findByName(Integer.toString(randInt));
        mongoClient.close();
        return image.orElse(null);
    }

    public static void uploadImages() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        File folder = new File("src/main/resources/gfx");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    System.out.println("File in gfx!");

                } else if (listOfFile.isDirectory()) {
                    System.out.println("Directory " + listOfFile.getName());
                    uploadRiddle(listOfFile);
                }
            }
        }
        mongoClient.close();
    }

    private static void uploadRiddle(File directory) {
        byte[] imageBytes = null;
        ArrayList<byte[]> answers = new ArrayList<>();

        File[] listOfFilesInDirectory = directory.listFiles();
        if (listOfFilesInDirectory != null) {
            for (File fileInDirectory : listOfFilesInDirectory) {
                if (fileInDirectory.isFile())
                    if (fileInDirectory.getName().equals("obrazekzagadki.jpg"))
                        imageBytes = convertToBytes(fileInDirectory);
                    else {
                        answers.add(convertToBytes(fileInDirectory));
                    }
            }
        }
        String riddleIndex = directory.getName().replace("zagadka", "");
        Riddle riddle = new Riddle(riddleIndex, answers, imageBytes);
        RiddleRepositoryMongoImpl riddleRepositoryMongo = new RiddleRepositoryMongoImpl();
        riddleRepositoryMongo.create(riddle);
    }

    private static byte[] convertToBytes(File listOfFile) {
        File file = new File(String.valueOf(listOfFile));
        String absolutePath = file.getAbsolutePath();
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = createInputStream(absolutePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

}
