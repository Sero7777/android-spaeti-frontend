package de.htw.spaetiapp.controller;

import de.htw.spaetiapp.networking.SocketIO;
//TODO this maybe a singleton
public class ConnectionController {
    private SocketIO socketIO;

    public ConnectionController() {
        this.socketIO = SocketIO.getInstance();
    }

    public SocketIO getConnection() {
        return socketIO;
    }

    public void connectClient() {
        socketIO.startConnection();
    }

    public void closeConnection() {

    }
}
