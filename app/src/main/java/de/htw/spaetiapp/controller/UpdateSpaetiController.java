package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;

//TODO maybe singleton
public class UpdateSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;

    public UpdateSpaetiController() {
        this.socketIO = SocketIO.getInstance();
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void updateSpaeti(Spaeti spaeti){
        String spaetiJson =  gson.toJson(spaeti);
        socketIO.updateSpaeti(spaetiJson);
    }

    public void spaetiNotFound() {
        // send info to GUI
    }

    public void updatedSpaeti(JSONObject data) {
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);
        repository.updateSpaeti(spaeti);
        // send the spaeti to repo / gui
    }

    public void spaetiNotUpdated() {
        // send Info to GUI
    }
}
