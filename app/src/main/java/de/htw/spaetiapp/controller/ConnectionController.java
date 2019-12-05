package de.htw.spaetiapp.controller;

import de.htw.spaetiapp.networking.SocketIO;
//TODO this maybe a singleton
public class ConnectionController {
    private SocketIO socketIO;

    public ConnectionController() {
        this.socketIO = SocketIO.getInstance();
        System.out.println("-------- SocketIO Instance has been retrieved -----------");
        connectClient();
    }

    //maybe useless
    public SocketIO getConnection() {
        return socketIO;
    }

    private void connectClient() {
        System.out.println("-------- started socketIO connection -----------");
        socketIO.startConnection();
    }

    public void closeConnection() {

    }
}
