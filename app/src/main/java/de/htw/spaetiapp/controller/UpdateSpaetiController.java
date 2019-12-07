package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;

public class UpdateSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;

    public UpdateSpaetiController(SocketIO socketIO) {
        this.socketIO = socketIO;
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void updateSpaeti(Spaeti spaeti) throws JSONException {
        JSONObject spaetiJson = new JSONObject(gson.toJson(spaeti));
        socketIO.updateSpaeti(spaetiJson);
    }

    public void spaetiNotFound() {
        System.out.println("The specified spaeti couldnt be found in the db");
        // TODO toast to inform the user
    }

    public void updatedSpaeti(JSONObject data) {
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);
        if (repository.updateSpaeti(spaeti) == null){
            System.out.println("spaeti with id " + spaeti.get_id() + "couldnt be updated in the repository");
            // TODO NOT SUCCESSFUL toast oder so

        }else {
            System.out.println("Spaeti with id " + spaeti.get_id() + " updated successfully in repo");
            // TODO Map marker setting
        }
    }

    public void spaetiNotUpdated() {
        // send Info to GUI
        System.out.println("Spaeti could not be updated");
        // TODO toast spaeti could not be saved remotely or sth wrong with backend in general
    }
}
