package de.htw.spaetiapp.controller;

import android.util.Log;

import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.htw.spaetiapp.models.MarkerRepository;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;
import de.htw.spaetiapp.util.ToastResponse;
import de.htw.spaetiapp.view.MainActivity;

public class AddSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository spaetiRepository;
    private MarkerRepository markerRepository;
    private MainActivity mainActivity;

    public AddSpaetiController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.socketIO = SocketIO.getInstance();
        this.spaetiRepository = SpaetiRepository.getInstance();
        this.markerRepository = MarkerRepository.getInstance();
        gson = new Gson();
    }

    public void addSpaeti(Spaeti spaeti) throws JSONException {
        JSONObject spaetiJson = new JSONObject(gson.toJson(spaeti));
        socketIO.addSpaetiSendToServer(spaetiJson);
        mainActivity.showMainView();
    }

    public void addSpaetiSuccess(JSONObject data, boolean isBroadcast) {
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);

        if (!spaetiRepository.addSpaeti(spaeti)) {
            Log.i("addSpaetiSuccess","failed");
            if (!isBroadcast) {
                makeToastAddSpaetiUnsuccessful(ToastResponse.SPAETI_ADD_REPO_UNSUCCESSFUL);
            }
        } else {
            Log.i("addSpaetiSuccess","success");
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
            spaetiRepository.addSpaeti(spaeti);
        }
        addInitialMarkers();
    }

    private void addInitialMarkers() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Spaeti spaeti : spaetiRepository.getSpaetiList()) {
                    mainActivity.addMarkerToMap(spaeti, true);
                }
            }
        });
    }

    public void addMarkerToMarkerRepo(String id, Marker marker){
        markerRepository.addMarkerToMap(id, marker);
    }

}
