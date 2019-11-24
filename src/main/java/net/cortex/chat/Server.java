/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cortex.chat;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import java.util.Random;

/**
 * @author Unknown
 */
public class Server {


    static int range = 10;
    static String[] rightAnswer;
    static int a;
    static int b;
    static String[] wrongAnswers;

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
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("onConnected");
                client.sendEvent("message", new Message("", "Welcome to the chat!"));
                client.sendEvent("message", new Message("GameBot", "Ile jest " + a + "+" + b + " ?"));
                client.sendEvent("wrongAnswers", new Message("", wrongAnswers[0] + " " + wrongAnswers[1] + " " + wrongAnswers[2] + " " + rightAnswer[0]));
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("onDisconnected");
            }
        });
        server.addEventListener("send", Message.class, new DataListener<Message>() {
            @Override
            public void onData(SocketIOClient client, Message data, AckRequest ackSender) throws Exception {
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

            }
        });
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started");

    }

}
