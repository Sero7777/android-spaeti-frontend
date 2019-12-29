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

public class SocketIO {
    private static SocketIO instance = null;
    private Socket mSocket;
    private AddSpaetiController addController;
    private UpdateSpaetiController updateController;
    private DeleteSpaetiController deleteController;
    private final String URL = "http://3.84.38.0:52300";

    private Emitter.Listener spaetisFetched = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("SocketIO","--------------------------------");
            Log.i("SocketIO","Added initial Spaetis");

            try {
                JSONArray data = ((JSONObject) args[0]).getJSONArray("spaetis");
                addController.addInitialSpaetis(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private Emitter.Listener spaetiAddSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            Log.i("SocketIO", "----------------------We did it");
            try {
                JSONObject data = ((JSONObject) args[0]).getJSONObject("spaeti");
                addController.addSpaetiSuccess(data, false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener spaetiAddNotSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            addController.addSpaetiNotsuccess();
        }
    };


    private Emitter.Listener spaetiUpdateSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject data = ((JSONObject) args[0]).getJSONObject("fetchedSpaeti");
                updateController.updatedSpaeti(data, false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            try {
                String id = ((JSONObject) args[0]).getString("id");
                deleteController.spaetiDeleted(id, false);
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

    private Emitter.Listener spaetiAddSuccessBroadcast = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject data = ((JSONObject) args[0]).getJSONObject("spaeti");
                addController.addSpaetiSuccess(data, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private Emitter.Listener spaetiUpdateSuccessBroadcast = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                JSONObject data = ((JSONObject) args[0]).getJSONObject("fetchedSpaeti");
                updateController.updatedSpaeti(data, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private Emitter.Listener spaetiDeleteSuccessBroadcast = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                String id = ((JSONObject) args[0]).getString("id");
                deleteController.spaetiDeleted(id,true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    public void setAddController(AddSpaetiController addController) {
        this.addController = addController;
    }

    public void setUpdateController(UpdateSpaetiController updateController) {
        this.updateController = updateController;
    }

    public void setDeleteController(DeleteSpaetiController deleteController) {
        this.deleteController = deleteController;
    }

    public static SocketIO getInstance() {
        if (null == instance) instance = new SocketIO();
        return instance;
    }

    public void startConnection() {
        mSocket.connect();
    }

    private void setListerner() {
        mSocket.on("fetch", spaetisFetched);
        mSocket.on("addedSpaetiSuccessfully", spaetiAddSuccess);
        mSocket.on("addedSpaetiSuccessfullyBroadcast", spaetiAddSuccessBroadcast);
        mSocket.on("couldNotAddSpaeti", spaetiAddNotSuccess);
        mSocket.on("updatedSpaetiSuccessfully", spaetiUpdateSuccess);
        mSocket.on("updatedSpaetiSuccessfullyBroadcast", spaetiUpdateSuccessBroadcast);
        mSocket.on("couldNotUpdateSpaeti", spaetiUpdateNotSuccess);
        mSocket.on("deletedSpaetiSuccessfully", spaetiDeleteSuccess);
        mSocket.on("deletedSpaetiSuccessfullyBroadcast", spaetiDeleteSuccessBroadcast);
        mSocket.on("couldNotDeleteSpaeti", spaetiDeleteNotSuccess);
    }

    public void addSpaetiSendToServer(JSONObject spaeti) {
        mSocket.emit("addSpaeti", spaeti);
    }

    public void updateSpaetiSendToServer(JSONObject spaeti) {
        mSocket.emit("updateSpaeti", spaeti);
    }

    public void deleteSpaetiSendToServer(String id) {
        mSocket.emit("deleteSpaeti", id);
    }


}
