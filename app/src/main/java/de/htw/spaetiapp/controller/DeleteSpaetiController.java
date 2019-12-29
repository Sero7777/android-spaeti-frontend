package de.htw.spaetiapp.controller;

import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import de.htw.spaetiapp.models.MarkerRepository;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;
import de.htw.spaetiapp.util.ToastResponse;
import de.htw.spaetiapp.view.MainActivity;

public class DeleteSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository spaetiRepository;
    private MarkerRepository markerRepository;
    private MainActivity mainActivity;

    public DeleteSpaetiController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.socketIO = SocketIO.getInstance();
        this.spaetiRepository = SpaetiRepository.getInstance();
        this.markerRepository = MarkerRepository.getInstance();
        gson = new Gson();
    }

    public void deleteSpaeti(String id) {
        socketIO.deleteSpaetiSendToServer(id);
    }

    public void spaetiDeleted(String id, boolean isBroadcast) {
        if (!spaetiRepository.deleteSpaeti(id)) {
            if (!isBroadcast) {
                makeToastDeleteSpaetiUnsuccessful(ToastResponse.SPAETI_DELETE_REPO_UNSUCCESSFUL);
            }
        } else {
            Marker marker = (Marker) markerRepository.getMarkerMap().get(id);
            markerRepository.deleteMarkerFromMap(id);
            makeToastDeleteSpaetiSucessful(marker, isBroadcast);
        }
    }

    private void makeToastDeleteSpaetiSucessful(Marker marker, boolean isBroadcast) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.removeMarkerFromMap(marker, isBroadcast);
                if (!isBroadcast) {
                    mainActivity.toastInMap(ToastResponse.SPAETI_DELETE_SUCCESSFUL);
                }
            }
        });
    }

    private void makeToastDeleteSpaetiUnsuccessful(ToastResponse spaetiDeleteRepoUnsuccessful) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.toastInMap(spaetiDeleteRepoUnsuccessful);
            }
        });
    }

    public void spaetiNotDeleted() {
        makeToastDeleteSpaetiUnsuccessful(ToastResponse.SPAETI_DELETE_SERVER_UNSUCCESSFUL);
    }

}
