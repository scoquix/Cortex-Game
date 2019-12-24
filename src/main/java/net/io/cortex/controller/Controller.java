/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.io.cortex.controller;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import net.io.cortex.RiddleOperations;
import net.io.cortex.model.Authentication;
import net.io.cortex.model.Message;
import net.io.cortex.model.Registration;
import net.io.cortex.model.Riddle;

/**
 * @author Unknown
 */
public class Controller {


    private static int range = 10;
    private static String[] rightAnswer;
    private static int a;
    private static int b;
    private static String[] wrongAnswers;

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3700);
        //-------------------------------------------
        RiddleOperations.uploadImages();
        //-------------------------------------------
        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(client -> {
            System.out.println("onConnected");
            client.sendEvent("message", new Message("", "Welcome to the Cortex!"));
            //client.sendEvent("message", new Message("GameBot", "Ile jest " + a + "+" + b + " ?"));
            //client.sendEvent("wrongAnswers", new Message("", wrongAnswers[0] + " " + wrongAnswers[1] + " " + wrongAnswers[2] + " " + rightAnswer[0]));
        });
        server.addDisconnectListener(client -> System.out.println("onDisconnected"));
        server.addEventListener("send", Message.class, (client, data, ackSender) -> {
            System.out.println("onSend: " + data.toString());
            server.getBroadcastOperations().sendEvent("message", data);

            if (data.getMessage().equals(rightAnswer[0])) {
                Message serverMessage = new Message("GameBot", "Jako pierwszy poprawnej odpowiedzi udzielił gracz " + data.getName());
                server.getBroadcastOperations().sendEvent("message", serverMessage);
                Message nextRoundMessage = new Message("GameBot", "Nastepna runda");
                server.getBroadcastOperations().sendEvent("message", nextRoundMessage);

                server.getBroadcastOperations().sendEvent("message", new Message("GameBot", "Ile jest " + a + "+" + b + " ?"));
                server.getBroadcastOperations().sendEvent("wrongAnswers", new Message("", wrongAnswers[0] + " " + wrongAnswers[1] + " " + wrongAnswers[2] + " " + rightAnswer[0]));
            }

        });
        server.addEventListener("logging", Authentication.class, (socketIOClient, authentication, ackRequest) -> {
            System.out.println("Logging start");
            Authentication auth = new Authentication(authentication.getLogin(), authentication.getPassword());
            if (auth.logging()) {
                socketIOClient.sendEvent("auth", "true");
            } else {
                socketIOClient.sendEvent("auth", "false");
            }
        });

        server.addEventListener("register", Registration.class, (socketIOClient, message, ackRequest) -> {
            System.out.println("Server odebral event register");
            Registration reg = new Registration(message.getLogin(), message.getPassword());
            if (reg.register()) {
                socketIOClient.sendEvent("eventRegister", new Message("Server", "Registration completed"));
            } else {
                socketIOClient.sendEvent("eventRegister", new Message("Server", "Something went wrong :( - Database do not create user"));
            }
        });
        server.addEventListener("image", Riddle.class, (socketIOClient, message, ackRequest) -> {
            System.out.println("Server odebral event image");
            String riddle = RiddleOperations.downloadImageFromMongoDB();
            int imageStartIndex = riddle.indexOf("/");
            int imageEndIndex = riddle.indexOf("\"", imageStartIndex);
            String base64EncodedImage = riddle.substring(imageStartIndex, imageEndIndex);

            socketIOClient.sendEvent("eventImage", new Message("Server", base64EncodedImage));
        });


        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started");
    }

}
