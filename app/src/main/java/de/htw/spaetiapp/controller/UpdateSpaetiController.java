package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.networking.SocketIO;

//TODO maybe singleton
public class UpdateSpaetiController {

    private SocketIO socketIO;
    private Gson gson;

    public UpdateSpaetiController() {
        this.socketIO = SocketIO.getInstance();
        gson = new Gson();
    }

    public void updateSpaeti(Spaeti spaeti){
        String spaetiJson =  gson.toJson(spaeti);
        socketIO.updateSpaeti(spaetiJson);
    }

    public void spaetiNotFound() {
        // send info to GUI
    }

    public void updatedSpaeti(String data) {
        Spaeti updatedSpaeti = gson.fromJson(data, Spaeti.class);
        // send the spaeti to repo / gui
    }
}
