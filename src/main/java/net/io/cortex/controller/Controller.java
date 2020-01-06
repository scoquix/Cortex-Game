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
import net.io.cortex.model.Lobby;
import net.io.cortex.model.Message;
import net.io.cortex.model.Registration;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author scoquix
 */
public class Controller {
    private static int range = 10;
    private static Map<UUID, String> usersSessionsID = new HashMap<>();
    private static List<UUID> gameSessionID = new ArrayList<>();
    private static List<String> correctAnswers = new ArrayList<>();
    private static List<Lobby> lobbies = new ArrayList<>();

    private static int a;
    private static int b;

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
            //            server.getBroadcastOperations().sendEvent("message", data);
            //
            //            if (data.getMessage().equals(rightAnswer[0])) {
            //                Message serverMessage = new Message("GameBot", "Jako pierwszy poprawnej odpowiedzi udzielił gracz " + data.getName());
            //                server.getBroadcastOperations().sendEvent("message", serverMessage);
            //                Message nextRoundMessage = new Message("GameBot", "Nastepna runda");
            //                server.getBroadcastOperations().sendEvent("message", nextRoundMessage);
            //
            //                server.getBroadcastOperations().sendEvent("message", new Message("GameBot", "Ile jest " + a + "+" + b + " ?"));
            //                server.getBroadcastOperations().sendEvent("wrongAnswers", new Message("", wrongAnswers[0] + " " + wrongAnswers[1] + " " + wrongAnswers[2] + " " + rightAnswer[0]));
            //            }
        });

        //------------------------------------------------
        // eventy rejestracja i logowanie
        //------------------------------------------------
        server.addEventListener("logging", Authentication.class, (socketIOClient, authentication, ackRequest) -> {
            System.out.println("Logging start");
            Authentication auth = new Authentication(authentication.getLogin(), authentication.getPassword());
            if (auth.logging()) {
                // TODO: Funkcja ktora sprawdza co 5 min czy user aktywny i czysc usersSessionID
                usersSessionsID.put(socketIOClient.getSessionId(), auth.getLogin());
                socketIOClient.sendEvent("eventLogging", new Message("Server", "Authentication completed"));
            } else {
                socketIOClient.sendEvent("eventLogging", new Message("Server", "Authentication failed"));
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
        //------------------------------------------------

        //------------------------------------------------
        // event do pobrania zagadki
        //------------------------------------------------
        server.addEventListener("riddle", Message.class, (socketIOClient, message, ackRequest) -> {
            System.out.println("Server odebral event riddle");
            if (gameSessionID.indexOf(socketIOClient.getSessionId()) == -1) {
                gameSessionID.add(socketIOClient.getSessionId());
            }
            String riddle = RiddleOperations.downloadImageFromMongoDB();
            System.out.println(riddle);

            int imageStartIndex = riddle.indexOf("/");
            int imageEndIndex = riddle.indexOf("\"", imageStartIndex);
            String base64EncodedImage = riddle.substring(imageStartIndex, imageEndIndex);

            int answersStartIndex = riddle.indexOf("\"/", imageEndIndex);
            int answersEndIndex = riddle.indexOf("\"", answersStartIndex + 1);
            String base64EncodedAnswers = riddle.substring(answersStartIndex + 1, answersEndIndex);

            String[] sa = base64EncodedAnswers.split(Pattern.quote("|"));
            correctAnswers.add(sa[3]);

            List<String> answersList = Arrays.asList(sa);
            Collections.shuffle(answersList);
            String encodedAnswers = String.join("|", answersList);

            server.getBroadcastOperations().sendEvent("eventImage", new Message("Server", base64EncodedImage));
            server.getBroadcastOperations().sendEvent("eventAnswers", new Message("Server", encodedAnswers));
        });
        //------------------------------------------------

        //------------------------------------------------
        // event do sprawdzenia poprawnej odpowiedzi
        //------------------------------------------------
        server.addEventListener("answer", Message.class, (socketIOClient, message, ackRequest) -> {
            System.out.println("Server odebral event answers");
            System.out.println(message.getName() + " przesyla odp: " + message.getMessage());
            System.out.println("All rooms: " + socketIOClient.getAllRooms());
            System.out.println("getHandshakeData: " + socketIOClient.getHandshakeData());
            System.out.println("getRemoteAddress: " + socketIOClient.getRemoteAddress());
            System.out.println("getSessionId: " + socketIOClient.getSessionId());


            int index = gameSessionID.indexOf(socketIOClient.getSessionId());

            if (index != -1) {
                if (message.getMessage().equals(correctAnswers.get(index))) {

                    Message serverMessage = new Message("GameBot", "Jako pierwszy poprawnej odpowiedzi udzielił gracz " + message.getName());
                    server.getBroadcastOperations().sendEvent("eventCorrectAnswer", serverMessage);

                } else {
                    socketIOClient.sendEvent("eventWrongAnswer", new Message(message.getName(), "Wrong Answer"));
                }
            }
        });
        //------------------------------------------------

        //------------------------------------------------
        // event do pokazania aktywnych pokoi
        //------------------------------------------------
        server.addEventListener("showLobbies", Message.class, (socketIOClient, message, ackRequest) -> {
            System.out.println("Server odebral event showLobbies");
            System.out.println(message.getName() + " przesyla odp: " + message.getMessage());
            System.out.println("All rooms: " + socketIOClient.getAllRooms());
            System.out.println("getHandshakeData: " + socketIOClient.getHandshakeData());
            System.out.println("getRemoteAddress: " + socketIOClient.getRemoteAddress());
            System.out.println("getSessionId: " + socketIOClient.getSessionId());

            StringBuilder lobbiesNames = new StringBuilder();
            lobbies.forEach(lobby -> {
                lobbiesNames.append(lobby.getOwner());
                lobbiesNames.append(",");
            });

            if (lobbiesNames.length() > 1)
                lobbiesNames.deleteCharAt(lobbiesNames.length() - 1);

            socketIOClient.sendEvent("eventShowLobbies", new Message("Server", lobbiesNames.toString()));
        });

        //------------------------------------------------
        // event do utworzenia pokoju
        //------------------------------------------------
        server.addEventListener("createLobby", Message.class, (socketIOClient, message, ackRequest) -> {
            System.out.println("Server odebral event create lobby");
            System.out.println(message.getName() + " przesyla odp: " + message.getMessage());
            System.out.println("All rooms: " + socketIOClient.getAllRooms());
            System.out.println("getSessionId: " + socketIOClient.getSessionId());

            Lobby lobby = new Lobby(socketIOClient.getSessionId().toString());
            lobby.addUser(socketIOClient.getSessionId());

            boolean hasLobby = false;
            for (Lobby l : lobbies) {
                if (l.getOwner().equals(lobby.getOwner())) {
                    hasLobby = true;
                    break;
                }
            }

            if (hasLobby) {
                System.out.println("Nie stworzono lobby");
                socketIOClient.sendEvent("eventCreateLobby", new Message("Server", "You've already joined or created a lobby"));
            } else {
                lobbies.add(lobby);
                System.out.println("Stworzono lobby o nazwie: " + lobby.getOwner());
                socketIOClient.sendEvent("eventCreateLobby", new Message("Server", "Created lobby with name: " + lobby.getOwner()));
            }

        });

        //------------------------------------------------
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started");
    }

}
