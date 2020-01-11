package net.io.cortex.model;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.ArrayList;
import java.util.UUID;

public class Lobby {
    private UUID id;
    private ArrayList<UUID> users;
    private ArrayList<SocketIOClient> clients;
    private String owner;

    public Lobby(UUID id, ArrayList<UUID> users, String owner) {
        this.id = id;
        this.users = users;
        this.owner = owner;
    }

    public Lobby(String owner) {
        this.id = UUID.randomUUID();
        this.users = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ArrayList<UUID> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UUID> users) {
        this.users = users;
    }

    public String getClientsNames() {
        StringBuilder playersNames = new StringBuilder();
        for (SocketIOClient s : clients)
            playersNames.append(s.getSessionId()).append(" ");

        playersNames.deleteCharAt(playersNames.length() - 1);
        return playersNames.toString();
    }
    public boolean addUser(UUID user) {
        if (user == null)
            return false;
        this.users.add(user);
        return true;
    }

    public boolean addClient(SocketIOClient user) {
        if (user == null)
            return false;
        this.clients.add(user);
        return true;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<SocketIOClient> getClients() {
        return this.clients;
    }
}
