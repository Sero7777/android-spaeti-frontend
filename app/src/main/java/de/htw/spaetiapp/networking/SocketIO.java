package de.htw.spaetiapp.networking;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import de.htw.spaetiapp.controller.AddSpaetiController;
import de.htw.spaetiapp.controller.DeleteSpaetiController;
import de.htw.spaetiapp.controller.UpdateSpaetiController;
import de.htw.spaetiapp.models.Spaeti;

//this is a singleton btw
public class SocketIO {
    private static SocketIO instance = null;
    private Socket mSocket;
    private AddSpaetiController addController;

    private UpdateSpaetiController updateController;
    private DeleteSpaetiController deleteController;
    private final String URL = "http://3.88.62.163:52300";
//    private final String URL = "http://10.51.17.1:52300";


    private Emitter.Listener spaetisFetched = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("----------------- Fetch all spaetis event has been invoked ---------------");

            JSONArray data = null;

            try {
                data = ((JSONObject) args[0]).getJSONArray("spaetis");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            addController.addInitialSpaeits(data);
//            Spaeti spaeti = new Spaeti();
//            spaeti.setCity("Berlin");
//            spaeti.setOpeningTime("12:00");
//            spaeti.setClosingTime("19:00");
//            spaeti.setCountry("Deutschland");
//            spaeti.setDescription("gute diese");
//            spaeti.setLat(13.37f);
//            spaeti.setLon(69.69f);
//            spaeti.setZip(12345);
//            spaeti.setStreetName("Hauptstr");
//            spaeti.setName("Haram Spätkauf");
//            try {
//                addController.addSpaeti(spaeti);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    };
    private Emitter.Listener spaetiAddSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //TODO not sure which Type is correct maybe with JSONObject so gson maybe useless
            JSONObject data = (JSONObject) args[0];
            addController.addSpaetiSuccess(data);
            //TODO check if works!!!!!!
        }
    };

    private Emitter.Listener spaetiAddNotSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            addController.addSpaetiNotsuccess();
        }
    };

    private Emitter.Listener spaetiNotFound = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            updateController.spaetiNotFound();
        }
    };

    private Emitter.Listener spaetiUpdateSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //TODO not sure which Type is correct maybe with JSONObject so gson maybe useless
            JSONObject data = (JSONObject) args[0];
            updateController.updatedSpaeti(data);
            //TODO check if works!!!!!!
        }
    };
    private Emitter.Listener spaetiUpdateNotSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            updateController.spaetiNotUpdated();
        }
    };

    private Emitter.Listener spaetiDeleteSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //TODO not sure which Type is correct maybe with JSONObject so gson maybe useless
            JSONObject data = (JSONObject) args[0];
            deleteController.spaetiDeleted(data);
            //TODO check if works!!!!!!
        }
    };

    private Emitter.Listener spaetiDeleteNotSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            deleteController.spaetiNotDeleted();
        }
    };

    private SocketIO() {
        try {
            this.mSocket = IO.socket(URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        setListerner();
        addController = new AddSpaetiController(this);
        deleteController = new DeleteSpaetiController(this);
        updateController = new UpdateSpaetiController(this);
        deleteController = new DeleteSpaetiController(this);
    }

    public static SocketIO getInstance() {
        if (null == instance) instance = new SocketIO();
        return instance;
    }

    public void startConnection() {
        mSocket.connect();
        System.out.println("-------- SocketIO Connection has been initialized -----------");



    }

    private void setListerner() {

        mSocket.on("fetchAllSpaetis", spaetisFetched);
        mSocket.on("addedSpaetiSuccessfully", spaetiAddSuccess);
        mSocket.on("couldNotAddSpaeti", spaetiAddNotSuccess);
        mSocket.on("couldNotFindSpaetiInDB", spaetiNotFound);
        mSocket.on("updatedSpaetiSuccessfully", spaetiUpdateSuccess);
        mSocket.on("couldNotUpdateSpaeti", spaetiUpdateNotSuccess);
        mSocket.on("deletedSpaetiSuccessfully", spaetiDeleteSuccess);
        mSocket.on("couldNotDeleteSpaeti", spaetiDeleteNotSuccess);

        System.out.println("-------- SocketIO Event Listeners have been set -----------");

    }

    public void addSpaeti(JSONObject spaeti) {
        System.out.println("333333333333333333333333");
        System.out.println(spaeti);
        mSocket.emit("addSpaeti", spaeti);
    }

    public void updateSpaeti(String spaeti) {
        mSocket.emit("updateSpaeti", spaeti);
    }

    public void deleteSpaeti(String spaeti) {
        mSocket.emit("deleteSpaeti", spaeti);
    }


}
