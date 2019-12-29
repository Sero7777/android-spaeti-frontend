package de.htw.spaetiapp.controller;

import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import de.htw.spaetiapp.models.MarkerRepository;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;
import de.htw.spaetiapp.util.ToastResponse;
import de.htw.spaetiapp.view.MainActivity;

public class UpdateSpaetiController {

    private MainActivity mainActivity;
    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository spaetiRepository;
    private MarkerRepository markerRepository;

    public UpdateSpaetiController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.socketIO = SocketIO.getInstance();
        this.spaetiRepository = SpaetiRepository.getInstance();
        this.markerRepository = MarkerRepository.getInstance();
        gson = new Gson();
    }

    public void updateSpaeti(Spaeti spaeti) throws JSONException {
        JSONObject spaetiJson = new JSONObject(gson.toJson(spaeti));
        socketIO.updateSpaetiSendToServer(spaetiJson);
        mainActivity.showMainView();
    }


    public void updatedSpaeti(JSONObject data, boolean isBroadcast) {
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);

        if (!spaetiRepository.updateSpaeti(spaeti)) {
            if (!isBroadcast) {
                makeToastUpdateSpaetiUnsuccessful(ToastResponse.SPAETI_UPDATE_REPO_UNSUCCESSFUL);
            }
        } else {
            makeToastUpdateSpaetiSuccessful(isBroadcast, spaeti);
        }
    }

    private void makeToastUpdateSpaetiSuccessful(boolean isBroadcast, Spaeti spaeti) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.updateMarkerOnMap(spaeti, isBroadcast, (Marker)markerRepository.getMarkerMap().get(spaeti.get_id()));
                if(!isBroadcast) {
                    mainActivity.toastInMap(ToastResponse.SPAETI_UPDATE_SUCCESSFUL);
                }
            }
        });
    }

    private void makeToastUpdateSpaetiUnsuccessful(ToastResponse spaetiUpdateRepoUnsuccessful) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.toastInMap(spaetiUpdateRepoUnsuccessful);

            }
        });
    }

    public void spaetiNotUpdated() {
        makeToastUpdateSpaetiUnsuccessful(ToastResponse.SPAETI_UPDATE_SERVER_UNSUCCESSFUL);
    }
}
