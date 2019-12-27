package de.htw.spaetiapp.controller;

import de.htw.spaetiapp.networking.SocketIO;

public class ConnectionController {
    private SocketIO socketIO;

    public ConnectionController() {
        this.socketIO = SocketIO.getInstance();
    }

    public void connectClient() {
        socketIO.startConnection();
    }

    public ConnectionController setAddController(AddSpaetiController addController){
        socketIO.setAddController(addController);
        return this;
    }

    public ConnectionController setUpdateController(UpdateSpaetiController updateController){
        socketIO.setUpdateController(updateController);
        return this;
    }

    public ConnectionController setDeleteController(DeleteSpaetiController deleteController){
        socketIO.setDeleteController(deleteController);
        return this;
    }

}
