package de.htw.spaetiapp.controller;

import de.htw.spaetiapp.networking.SocketIO;
//TODO this maybe a singleton
public class ConnectionController {
    private SocketIO socketIO;

    public ConnectionController(AddSpaetiController addSpaetiController, UpdateSpaetiController updateSpaetiController, DeleteSpaetiController deleteSpaetiController) {
        this.socketIO = SocketIO.getInstance();
        socketIO.setAddController(addSpaetiController);
        socketIO.setUpdateController(updateSpaetiController);
        socketIO.setDeleteController(deleteSpaetiController);
        connectClient();
    }

    //maybe useless
    public SocketIO getConnection() {
        return socketIO;
    }

    private void connectClient() {
        socketIO.startConnection();
    }

    public void closeConnection() {

    }
}
