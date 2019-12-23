package de.htw.spaetiapp.controller;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;
import de.htw.spaetiapp.util.ToastResponse;
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
        Log.i("UpdateSpaetiController", spaeti + "UPDATESPAEEEEEEEEEEETT");
        JSONObject spaetiJson = new JSONObject(gson.toJson(spaeti));
        Log.i("UpdateSpaetiController", spaetiJson + "updateJASOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOoN");
        socketIO.updateSpaeti(spaetiJson);
        mainActivity.showMainView();
    }

    public void spaetiNotFound() {
        Log.i("UpdateSpaetiController", "The specified spaeti couldnt be found in the db");
        // TODO toast to inform the user
    }

    public void updatedSpaeti(JSONObject data, boolean isBroadcast) {
        Log.i("UpdateSpaetiController", data + "check check check ");
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);
        Log.i("UpdateSpaetiController", spaeti + "chicki chicki chicki chicki");
        if (!repository.updateSpaeti(spaeti)) {
            Log.i("UpdateSpaetiController", "spaeti with id " + spaeti.get_id() + " couldnt be updated in the repository");
            // TODO NOT SUCCESSFUL toast oder so
            if (!isBroadcast) {
                mainActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        mainActivity.toastInMap(ToastResponse.SPAETI_UPDATE_REPO_UNSUCCESSFUL);

                    }
                });
            }


        } else {
            Log.i("UpdateSpaetiController", "Spaeti with id " + spaeti.get_id() + " updated successfully in repo");
            // TODO Map marker setting
            mainActivity.runOnUiThread(new Runnable() {
                public void run() {
                    mainActivity.updateMarkerOnMap(spaeti, isBroadcast);
                    if(!isBroadcast) {
                        mainActivity.toastInMap(ToastResponse.SPAETI_UPDATE_SUCCESSFUL);
                    }
                }
            });
        }
    }

    public void spaetiNotUpdated() {
        // send Info to GUI
        Log.i("UpdateSpaetiController", "Spaeti could not be updated");
        // TODO toast spaeti could not be saved remotely or sth wrong with backend in general
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.toastInMap(ToastResponse.SPAETI_UPDATE_SERVER_UNSUCCESSFUL);

            }
        });
    }
}
