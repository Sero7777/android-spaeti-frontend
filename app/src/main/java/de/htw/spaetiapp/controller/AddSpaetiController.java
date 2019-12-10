package de.htw.spaetiapp.controller;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.networking.SocketIO;
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
        Log.i("AddSpaetiController",spaeti + "addS");
        JSONObject spaetiJson = new JSONObject(gson.toJson(spaeti));
        Log.i("AddSpaetiController",spaetiJson+ "addJASOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOON");
        socketIO.addSpaeti(spaetiJson);
        mainActivity.showMainView();
    }

    public void addSpaetiSuccess(JSONObject data) {
        Spaeti spaeti = gson.fromJson(data.toString(), Spaeti.class);
        if(!repository.addSpaeti(spaeti)){
            Log.i("AddSpaetiController","Spaeti could not be added to repository");
        } else {
            Log.i("AddSpaetiController",spaeti+"add Spaeti Succes");
            Log.i("AddSpaetiController","Spaeti has succesfully been added to repository");
            // TODO set marker, take spaeti instance from above
           // MapsFragment fragment =(MapsFragment)mainActivity.getMapsFragment();
            //mainActivity.addMarkerToMap(spaeti);
            mainActivity.runOnUiThread(new Runnable(){
                public void run(){
                   mainActivity.addMarkerToMap(spaeti);
                }
            });
        }

    }

    public void addSpaetiNotsuccess() {
        System.out.println("Spaeti could not be added to repository");
        // TODO toast message oder so vllt mit mainActivity.runOnUIThread
    }

    public void addInitialSpaetis(JSONArray data) {
        Spaeti[] spaetis = gson.fromJson(data.toString(), Spaeti[].class);
        Log.i("AddSpaetiController","I reached this pointAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        for (Spaeti spaeti : spaetis) {
            repository.addSpaeti(spaeti);
        }
        //TODO alle init spaetis mit markern laden vllt so:



        for (Spaeti spaeti: repository.getSpaetiList()) {
            System.out.println(spaeti);
        }
    }

    public void AddInitialMarkers() {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Spaeti spaeti: repository.getSpaetiList()) {
                    System.out.println(spaeti);
                mainActivity.addMarkerToMap(spaeti);
            }}
        });
    }

}
