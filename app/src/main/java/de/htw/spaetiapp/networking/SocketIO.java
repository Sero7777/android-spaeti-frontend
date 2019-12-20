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

//this is a singleton btw
public class SocketIO {
    private static SocketIO instance = null;
    private Socket mSocket;
    private AddSpaetiController addController;

    private UpdateSpaetiController updateController;
    private DeleteSpaetiController deleteController;
//    private final String URL = "http://52.206.97.4:52300"; //t3.medium
    private final String URL = "http://18.233.9.23:52300"; //t3.micro
//    private final String URL = "http://10.51.17.1:52300";


    private Emitter.Listener spaetisFetched = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("SocketIO","--------------------------------");

            JSONArray data = null;

            try {
                data = ((JSONObject) args[0]).getJSONArray("spaetis");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            addController.addInitialSpaetis(data);
        }
    };
    private Emitter.Listener spaetiAddSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //TODO not sure which Type is correct maybe with JSONObject so gson maybe useless
            JSONObject data = null;
            try {
                data = ((JSONObject) args[0]).getJSONObject("spaeti");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("SocketIO","JSON DATA" + data);
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
            JSONObject data = null;
            try {
                data = ((JSONObject) args[0]).getJSONObject("fetchedSpaeti");
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
//            // JSONObject id = null;
//            JSONObject id = (JSONObject) args[0];
//            System.out.println(id);
//            try {
//                String id2 =  id.getJSONObject("newSpaeti").toString();
//                System.out.println(id2 + ("id222222222222222222222222222222222222222222222222222222222"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            deleteController.spaetiDeleted(id);
//            //TODO check if works!!!!!!
            try {
                Log.i("SocketIO",args[0].toString());
                String id = ((JSONObject) args[0]).getString("id");
                deleteController.spaetiDeleted(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener spaetiDeleteNotSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            deleteController.spaetiNotDeleted();
        }
    };
    private Emitter.Listener test = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("SocketIO","test");
        }
    };

    private SocketIO() {
        try {
            this.mSocket = IO.socket(URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        setListerner();
    }

    public static SocketIO getInstance() {
        if (null == instance) instance = new SocketIO();
        return instance;
    }

    public void startConnection() {
        mSocket.connect();
        Log.i("SocketIO", "start connection has been invoked");
        boolean test = mSocket.connected();
        Log.i("SocketIO", Boolean.toString(test));

    }

    private void setListerner() {
        mSocket.on("fetch", spaetisFetched);
        mSocket.on("test", test);
        mSocket.on("addedSpaetiSuccessfully", spaetiAddSuccess);
        mSocket.on("couldNotAddSpaeti", spaetiAddNotSuccess);
        mSocket.on("couldNotFindSpaetiInDB", spaetiNotFound);
        mSocket.on("updatedSpaetiSuccessfully", spaetiUpdateSuccess);
        mSocket.on("couldNotUpdateSpaeti", spaetiUpdateNotSuccess);
        mSocket.on("deletedSpaetiSuccessfully", spaetiDeleteSuccess);
        mSocket.on("couldNotDeleteSpaeti", spaetiDeleteNotSuccess);
    }

    public void addSpaeti(JSONObject spaeti) {
        mSocket.emit("addSpaeti", spaeti);
    }

    public void updateSpaeti(JSONObject spaeti) {
        mSocket.emit("updateSpaeti", spaeti);
    }

    public void deleteSpaeti(String id) {
        mSocket.emit("deleteSpaeti", id);
    }

    public void setAddController(AddSpaetiController addController) {
        this.addController = addController;
    }

    public void setUpdateController(UpdateSpaetiController updateController) {
        this.updateController = updateController;
    }

    public void setDeleteController(DeleteSpaetiController deleteController) {
        this.deleteController = deleteController;
    }
}
