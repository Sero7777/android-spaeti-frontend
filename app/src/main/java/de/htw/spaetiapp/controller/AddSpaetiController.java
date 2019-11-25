package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;

//TODO maybe singleton
public class AddSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;

    public AddSpaetiController() {
        this.socketIO = SocketIO.getInstance();
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void addSpaeti(Spaeti spaeti){
        String spaetiJson =  gson.toJson(spaeti);
        socketIO.addSpaeti(spaetiJson);
    }

    public void addSpaetiSuccess(JSONObject data) {
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);
        repository.addSpaeti(spaeti);
        //send info to the repo
    }

    public void addSpaetiNotsuccess() {
        //send info to the GUI
    }
}
