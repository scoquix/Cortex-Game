
package net.io.cortex.model;

import java.util.ArrayList;
import java.util.Base64;

public class Riddle {

    private String id;
    private String answers;
    private String riddleContent;

    public Riddle(String id, ArrayList<byte[]> answers, byte[] riddleContent) {
        this.id = id;
        this.answers = encryptAnswers(answers);
        this.riddleContent = encryptContent(riddleContent);
    }

    public Riddle() {
    }

    private String encryptAnswers(ArrayList<byte[]> answers) {
        StringBuilder answersBuilder = new StringBuilder();
        for (byte[] answer : answers) {
            String encodeAnswer = Base64.getEncoder().encodeToString(answer);
            answersBuilder.append(encodeAnswer);
            answersBuilder.append("|");
        }
        answersBuilder.deleteCharAt(answersBuilder.length() - 1);
        return answersBuilder.toString();
    }

    private String encryptContent(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswers() {
        return answers;
    }

    void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getRiddleContent() {
        return riddleContent;
    }

    void setRiddleContent(String riddleContent) {
        this.riddleContent = riddleContent;
    }

}