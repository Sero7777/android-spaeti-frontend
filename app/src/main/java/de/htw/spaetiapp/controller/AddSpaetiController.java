package de.htw.spaetiapp.controller;

import com.google.gson.Gson;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.networking.SocketIO;

//TODO maybe singleton
public class AddSpaetiController {

    private SocketIO socketIO;
    private Gson gson;

    public AddSpaetiController() {
        this.socketIO = SocketIO.getInstance();
        gson = new Gson();
    }

    public void addSpaeti(Spaeti spaeti){
        String spaetiJson =  gson.toJson(spaeti);
        socketIO.addSpaeti(spaetiJson);
    }
}
