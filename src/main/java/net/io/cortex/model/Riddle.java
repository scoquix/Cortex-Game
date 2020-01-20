
package net.io.cortex.model;

import java.util.ArrayList;
import java.util.Base64;

public class Riddle {

    private String id;
    private String answers;
    private String riddleContent;

    /**
     *
     * @param id - ID of the riddle
     * @param answers - answer to the riddle
     * @param riddleContent - content of the riddle
     */
    public Riddle(String id, ArrayList<byte[]> answers, byte[] riddleContent) {
        this.id = id;
        this.answers = encryptAnswers(answers);
        this.riddleContent = encryptContent(riddleContent);
    }

    Riddle() {
    }

    /**
     *
     * @param answers - answer of the riddle
     * @return - string
     */
    private String encryptAnswers(ArrayList<byte[]> answers) {
        if (answers != null) {
            StringBuilder answersBuilder = new StringBuilder();
            for (byte[] answer : answers) {
                String encodeAnswer = Base64.getEncoder().encodeToString(answer);
                answersBuilder.append(encodeAnswer);
                answersBuilder.append("|");
            }
            answersBuilder.deleteCharAt(answersBuilder.length() - 1);
            return answersBuilder.toString();
        }
        return null;
    }

    /**
     *
     * @param content - content of the riddle
     * @return - string
     */
    private String encryptContent(byte[] content) {
        if (content != null)
            return Base64.getEncoder().encodeToString(content);
        else
            return null;
    }

    /**
     *
     * @return - string id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id - set id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return - string answer
     */
    public String getAnswers() {
        return answers;
    }

    /**
     *
     * @param answers - answer of the riddle
     */
    void setAnswers(String answers) {
        this.answers = answers;
    }

    /**
     *
     * @return - string
     */
    public String getRiddleContent() {
        return riddleContent;
    }

    /**
     *
     * @param riddleContent - content of the riddle
     */
    void setRiddleContent(String riddleContent) {
        this.riddleContent = riddleContent;
    }

}