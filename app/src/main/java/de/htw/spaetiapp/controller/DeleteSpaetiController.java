package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.networking.SocketIO;

//TODO maybe singleton
public class DeleteSpaetiController {

    private SocketIO socketIO;
    private Gson gson;

    public DeleteSpaetiController() {
        this.socketIO = SocketIO.getInstance();
        gson = new Gson();
    }

    public void deleteSpaeti(Spaeti spaeti){
        String spaetiJson =  gson.toJson(spaeti);
        socketIO.deleteSpaeti(spaetiJson);
    }
}
