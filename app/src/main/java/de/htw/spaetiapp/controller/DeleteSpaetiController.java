package de.htw.spaetiapp.controller;

import android.util.Log;

import com.google.gson.Gson;

import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;
import de.htw.spaetiapp.util.ToastResponse;
import de.htw.spaetiapp.view.MainActivity;

public class DeleteSpaetiController {

    private SocketIO socketIO;
    private Gson gson;
    private SpaetiRepository repository;
    private MainActivity mainActivity;

    public DeleteSpaetiController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.socketIO = SocketIO.getInstance();
        this.repository = SpaetiRepository.getInstance();
        gson = new Gson();
    }

    public void deleteSpaeti(String id){
        socketIO.deleteSpaeti(id);
    }

    public void spaetiDeleted(String id) {
        Log.i("DeleteSpaetiController",id + "deleteController spaeitDeleted");
        //String id = gson.fromJson(data.toString(),String.class);
        if(!repository.deleteSpaeti(id)){
            Log.i("DeleteSpaetiController","could not delete spaeti with id in repo " +id);
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.toastInMap(ToastResponse.SPAETI_DELETE_REPO_UNSUCCESSFUL);

                }
            });
        } else {
            Log.i("DeleteSpaetiController","spaeti with id " + id + " has successfully been removed from repo");
            // TODO map marker löschen
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.removeMarkerFromMap(id);
                    mainActivity.toastInMap(ToastResponse.SPAETI_DELETE_SUCCESSFUL);

                }
            });
        }
        //Send data to repo
    }

    public void spaetiNotDeleted() {
        // Send Data to GUI
        // TODO toast could not delete blabla
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.toastInMap(ToastResponse.SPAETI_DELETE_SERVER_UNSUCCESSFUL);

            }
        });
    }
}
