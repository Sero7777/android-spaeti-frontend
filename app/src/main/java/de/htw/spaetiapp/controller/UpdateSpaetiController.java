package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;
import de.htw.spaetiapp.view.MainActivity;

public class UpdateSpaetiController {

    private MainActivity mainActivity;
    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;

    public UpdateSpaetiController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.socketIO = SocketIO.getInstance();
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void updateSpaeti(Spaeti spaeti) throws JSONException {
        System.out.println(spaeti + "UPDATESPAEEEEEEEEEEETT");
        JSONObject spaetiJson = new JSONObject(gson.toJson(spaeti));
        System.out.println(spaetiJson + "updateJASOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOoN");
        socketIO.updateSpaeti(spaetiJson);
    }

    public void spaetiNotFound() {
        System.out.println("The specified spaeti couldnt be found in the db");
        // TODO toast to inform the user
    }

    public void updatedSpaeti(JSONObject data) {
        System.out.println(data + "check check check ");
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);
        System.out.println(spaeti + "chicki chicki chicki chicki");
        if (!repository.updateSpaeti(spaeti)) {
            System.out.println("spaeti with id " + spaeti.get_id() + " couldnt be updated in the repository");
            // TODO NOT SUCCESSFUL toast oder so

        } else {
            System.out.println("Spaeti with id " + spaeti.get_id() + " updated successfully in repo");
            // TODO Map marker setting
            mainActivity.runOnUiThread(new Runnable() {
                public void run() {
                    mainActivity.updateMarkerOnMap(spaeti);
                }
            });
        }
    }

    public void spaetiNotUpdated() {
        // send Info to GUI
        System.out.println("Spaeti could not be updated");
        // TODO toast spaeti could not be saved remotely or sth wrong with backend in general
    }
}
