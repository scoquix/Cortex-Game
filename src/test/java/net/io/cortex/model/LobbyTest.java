package net.io.cortex.model;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.protocol.Packet;
import org.junit.jupiter.api.Test;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {
    private Lobby lobby = new Lobby("x");
    Lobby lobby2 = new Lobby(UUID.randomUUID(), new ArrayList<>(), "owner");

    @Test
    void getId() {
        assertNotNull(lobby.getId());
    }

    @Test
    void setId() {
        UUID id = UUID.randomUUID();
        lobby.setId(id);
        assertEquals(id, lobby.getId());
    }

    @Test
    void getUsers() {
        assertNotNull(lobby.getUsers());
    }

    @Test
    void setUsers() {
        ArrayList<UUID> arrayList = new ArrayList<>();
        lobby.setUsers(arrayList);
        assertEquals(arrayList, lobby.getUsers());
    }

    @Test
    void getClientsNames() {
        lobby.addClient(new SocketIOClient() {
            @Override
            public HandshakeData getHandshakeData() {
                return null;
            }

            @Override
            public Transport getTransport() {
                return null;
            }

            @Override
            public void sendEvent(String s, AckCallback<?> ackCallback, Object... objects) {

            }

            @Override
            public void send(Packet packet, AckCallback<?> ackCallback) {

            }

            @Override
            public SocketIONamespace getNamespace() {
                return null;
            }

            @Override
            public UUID getSessionId() {
                return null;
            }

            @Override
            public SocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public boolean isChannelOpen() {
                return false;
            }

            @Override
            public void joinRoom(String s) {

            }

            @Override
            public void leaveRoom(String s) {

            }

            @Override
            public Set<String> getAllRooms() {
                return null;
            }

            @Override
            public void send(Packet packet) {

            }

            @Override
            public void disconnect() {

            }

            @Override
            public void sendEvent(String s, Object... objects) {

            }

            @Override
            public void set(String s, String s1) {

            }

            @Override
            public String get(String s) {
                return null;
            }

            @Override
            public boolean has(String s) {
                return false;
            }

            @Override
            public void del(String s) {

            }
        });
        StringBuilder playersNames = new StringBuilder();
        for (SocketIOClient s : lobby.getClients())
            playersNames.append(s.getSessionId()).append(" ");

        playersNames.deleteCharAt(playersNames.length() - 1);
        assertEquals(playersNames.toString(), lobby.getClientsNames());
    }

    @Test
    void addUser() {
        assertFalse(lobby.addUser(null));
        assertTrue(lobby.addUser(UUID.randomUUID()));
    }

    @Test
    void addClient() {
        SocketIOClient socketIOClient = new SocketIOClient() {
            @Override
            public void set(String s, String s1) {

            }

            @Override
            public String get(String s) {
                return null;
            }

            @Override
            public boolean has(String s) {
                return false;
            }

            @Override
            public void del(String s) {

            }

            @Override
            public void send(Packet packet) {

            }

            @Override
            public void disconnect() {

            }

            @Override
            public void sendEvent(String s, Object... objects) {

            }

            @Override
            public HandshakeData getHandshakeData() {
                return null;
            }

            @Override
            public Transport getTransport() {
                return null;
            }

            @Override
            public void sendEvent(String s, AckCallback<?> ackCallback, Object... objects) {

            }

            @Override
            public void send(Packet packet, AckCallback<?> ackCallback) {

            }

            @Override
            public SocketIONamespace getNamespace() {
                return null;
            }

            @Override
            public UUID getSessionId() {
                return null;
            }

            @Override
            public SocketAddress getRemoteAddress() {
                return null;
            }

            @Override
            public boolean isChannelOpen() {
                return false;
            }

            @Override
            public void joinRoom(String s) {

            }

            @Override
            public void leaveRoom(String s) {

            }

            @Override
            public Set<String> getAllRooms() {
                return null;
            }
        };
        assertFalse(lobby.addClient(null));
        assertTrue(lobby.addClient(socketIOClient));
    }

    @Test
    void getOwner() {
        lobby.setOwner("a");
        assertEquals("a", lobby.getOwner());
    }
}