package net.io.cortex.model;

public class Riddle {

    private String id;
    private String[] answers;
    private byte[] riddleContent;

    Riddle() {
    }

    public Riddle(String id, String[] answers, byte[] riddleContent) {
        this.id = id;
        this.answers = answers;
        this.riddleContent = riddleContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getAnswers() {
        return answers;
    }

    void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public byte[] getRiddleContent() {
        return riddleContent;
    }

    public void setRiddleContent(byte[] riddleContent) {
        this.riddleContent = riddleContent;
    }

}
