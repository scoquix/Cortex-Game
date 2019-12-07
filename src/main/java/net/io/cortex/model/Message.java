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

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" + "name=" + name + ", message=" + message + '}';
    }

}
