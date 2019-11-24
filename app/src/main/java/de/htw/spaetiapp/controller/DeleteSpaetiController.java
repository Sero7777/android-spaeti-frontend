package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;

//TODO maybe singleton
public class DeleteSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;

    public DeleteSpaetiController() {
        this.socketIO = SocketIO.getInstance();
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void deleteSpaeti(Spaeti spaeti){
        String spaetiJson =  gson.toJson(spaeti);
        socketIO.deleteSpaeti(spaetiJson);
    }

    public void spaetiDeleted(JSONObject data) {
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);
        repository.deleteSpaeti(spaeti);
        //Send data to repo
    }

    public void spaetiNotDeleted() {
        // Send Data to GUI
    }
}
