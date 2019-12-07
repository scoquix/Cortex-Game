/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.io.cortex.controller;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import net.io.cortex.model.Authentication;
import net.io.cortex.model.Message;
import net.io.cortex.model.Registration;

import java.util.Random;

/**
 * @author Unknown
 */
public class Controller {


    private static int range = 10;
    private static String[] rightAnswer;
    private static int a;
    private static int b;
    private static String[] wrongAnswers;

    private static void prepareAnswers() {
        Random random = new Random();
        a = random.nextInt(range);
        b = random.nextInt(range);
        rightAnswer = new String[]{String.valueOf(a + b)};
        wrongAnswers = new String[]{String.valueOf(random.nextInt(range)), String.valueOf(random.nextInt(range)), String.valueOf(random.nextInt(range))};
        System.out.println("Haloo");
        for (int i = 0; i < wrongAnswers.length; ++i) {
            System.out.println("Dla elementu: " + wrongAnswers[i]);
            while (Integer.parseInt(wrongAnswers[i]) == a + b) {
                wrongAnswers[i] = String.valueOf(random.nextInt(range));
                System.out.println("Zmienilem wartosc na " + wrongAnswers[i]);
            }
        }
    }

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3700);

        prepareAnswers();
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
                prepareAnswers();
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
            System.out.println("Jestem");
            Registration reg = new Registration(message.getLogin(), message.getPassword());
            if (reg.register()) {
                socketIOClient.sendEvent("register", "Julka działa xD");
            } else {
                socketIOClient.sendEvent("register", "false");
            }
        });
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started");
        Registration registration = new Registration("ala2", "kot");
        registration.register();
    }

}
