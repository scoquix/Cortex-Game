package net.io.cortex.model;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.ArrayList;
import java.util.UUID;

public class Lobby {
    private UUID id;
    private ArrayList<UUID> users;
    private ArrayList<SocketIOClient> clients;
    private String owner;

    /**
     *
     * @param id - lobby's id
     * @param users - lobby's users
     * @param owner - lobby's owner
     */
    public Lobby(UUID id, ArrayList<UUID> users, String owner) {
        this.id = id;
        this.users = users;
        this.owner = owner;
    }

    /**
     *
     * @param owner - lobby's owner
     */
    public Lobby(String owner) {
        this.id = UUID.randomUUID();
        this.users = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.owner = owner;
    }

    /**
     *
     * @return - UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     *
     * @param id - id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     *
     * @return - ArrayList
     */
    public ArrayList<UUID> getUsers() {
        return users;
    }

    /**
     *
     * @param users - user's id
     */
    public void setUsers(ArrayList<UUID> users) {
        this.users = users;
    }

    /**
     *
     * @return - string
     */
    public String getClientsNames() {
        StringBuilder playersNames = new StringBuilder();
        for (SocketIOClient s : clients)
            playersNames.append(s.getSessionId()).append(" ");

        playersNames.deleteCharAt(playersNames.length() - 1);
        return playersNames.toString();
    }

    /**
     *
     * @param user - user to add
     * @return - bool
     */
    public boolean addUser(UUID user) {
        if (user == null)
            return false;
        this.users.add(user);
        return true;
    }

    /**
     *
     * @param user - client to add
     * @return - bool
     */
    public boolean addClient(SocketIOClient user) {
        if (user == null)
            return false;
        this.clients.add(user);
        return true;
    }

    /**
     *
     * @return - string owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner - owner's name
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return - ArrayList Clients
     */
    public ArrayList<SocketIOClient> getClients() {
        return this.clients;
    }
}
