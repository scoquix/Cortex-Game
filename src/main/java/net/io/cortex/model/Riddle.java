package net.io.cortex.model;

public class Riddle {

    private int id;
    private String[] answers;
    private String riddleContent;

    Riddle() {
    }

    Riddle(int id, String[] answers, String riddleContent) {
        this.id = id;
        this.answers = answers;
        this.riddleContent = riddleContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String[] getAnswers() {
        return answers;
    }

    void setAnswers(String[] answers) {
        this.answers = answers;
    }

    String getRiddleContent() {
        return riddleContent;
    }

    void setRiddleContent(String riddleContent) {
        this.riddleContent = riddleContent;
    }

}
