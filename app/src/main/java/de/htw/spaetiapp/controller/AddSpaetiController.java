package de.htw.spaetiapp.controller;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;
import de.htw.spaetiapp.util.ToastResponse;
import de.htw.spaetiapp.view.MainActivity;

public class AddSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;
    private MainActivity mainActivity;

    public AddSpaetiController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.socketIO = SocketIO.getInstance();
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void addSpaeti(Spaeti spaeti) throws JSONException {
        JSONObject spaetiJson = new JSONObject(gson.toJson(spaeti));
        socketIO.addSpaetiSendToServer(spaetiJson);
        mainActivity.showMainView();
    }

    public void addSpaetiSuccess(JSONObject data, boolean isBroadcast) {
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);

        if (!repository.addSpaeti(spaeti)) {
            if (!isBroadcast) {
                makeToastAddSpaetiUnsuccessful(ToastResponse.SPAETI_ADD_REPO_UNSUCCESSFUL);
            }
        } else {
            makeToastAddSpaetiSuccessful(isBroadcast, spaeti);
        }

    }

    private void makeToastAddSpaetiSuccessful(boolean isBroadcast, Spaeti spaeti) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.addMarkerToMap(spaeti, isBroadcast);
                if (!isBroadcast) {
                    mainActivity.toastInMap(ToastResponse.SPAETI_ADD_SUCCESSFUL);
                }
            }
        });
    }

    private void makeToastAddSpaetiUnsuccessful(ToastResponse spaetiAddRepoUnsuccessful) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.toastInMap(spaetiAddRepoUnsuccessful);
            }
        });
    }

    public void addSpaetiNotsuccess() {
        makeToastAddSpaetiUnsuccessful(ToastResponse.SPAETI_ADD_SERVER_UNSUCCESSFUL);
    }

    public void addInitialSpaetis(JSONArray data) {
        Spaeti[] spaetis = gson.fromJson(data.toString(), Spaeti[].class);

        for (Spaeti spaeti : spaetis) {
            repository.addSpaeti(spaeti);
        }
        addInitialMarkers();
    }

    private void addInitialMarkers() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Spaeti spaeti : repository.getSpaetiList()) {
                    mainActivity.addMarkerToMap(spaeti, true);
                }
            }
        });
    }
}
