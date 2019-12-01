package de.htw.spaetiapp.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;

//TODO maybe singleton
public class AddSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;

    public AddSpaetiController(SocketIO socketIO) {
        this.socketIO = socketIO;
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void addSpaeti(Spaeti spaeti) {
        String spaetiJson = gson.toJson(spaeti);
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

    public void addInitialSpaeits(JSONArray data) {
        Spaeti[] spaetis = gson.fromJson(data.toString(), Spaeti[].class);

        for (Spaeti spaeti : spaetis) {
            repository.addSpaeti(spaeti);
        }

//        for (Spaeti spaeti: repository.getSpaetiList()) {
//            System.out.println(spaeti);
//        }
    }
}
