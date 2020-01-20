/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.io.cortex.model;

/**
 * @author Unknown
 */
public class Message {
    private String name;
    private String message;

    //Bardzo ważne
    public Message() {
    }

    /**
     *
     * @param name - message's name
     * @param message - string
     */
    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }

    /**
     *
     * @return - string
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name - name setter
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return - string
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message - message setter
     */
    void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" + "name=" + name + ", message=" + message + '}';
    }

}
