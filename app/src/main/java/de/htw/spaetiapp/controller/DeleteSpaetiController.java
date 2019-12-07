package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;

public class DeleteSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;

    public DeleteSpaetiController(SocketIO socketIO) {
        this.socketIO = socketIO;
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void deleteSpaeti(String id){
        socketIO.deleteSpaeti(id);
    }

    public void spaetiDeleted(String id) {

        if(!repository.deleteSpaeti(id)){
            System.out.println("could not delete spaeti with id in repo " +id);
        } else {
            System.out.println("spaeti with id " + id + " has successfully been removed from repo");
            // TODO map marker löschen
        }
        //Send data to repo
    }

    public void spaetiNotDeleted() {
        // Send Data to GUI
        // TODO toast could not delete blabla
    }
}
