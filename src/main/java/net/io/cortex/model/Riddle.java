package net.io.cortex.model;

public class Riddle {

    private int id;
    private String[] answers;
    private String riddleContent;

    public Riddle() {
    }

    public Riddle(int id, String[] answers, String riddleContent) {
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

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getRiddleContent() {
        return riddleContent;
    }

    public void setRiddleContent(String riddleContent) {
        this.riddleContent = riddleContent;
    }

}
