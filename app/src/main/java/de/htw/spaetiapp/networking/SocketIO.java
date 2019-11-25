package de.htw.spaetiapp.networking;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.JsonObject;

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

    private SocketIO() {
        try {
            this.mSocket = IO.socket("localhost");
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
    }

    public void addSpaeti(String spaeti) {
        mSocket.emit("addSpaeti", spaeti);
    }

    public void updateSpaeti(String spaeti) {
        mSocket.emit("updateSpaeti", spaeti);
    }

    public void deleteSpaeti(String spaeti) {
        mSocket.emit("deleteSpaeti", spaeti);
    }

    private void setListerner() {
        mSocket.on("addedSpaetiSuccessfully", spaetiAddSuccess);
        mSocket.on("couldNotAddSpaeti", spaetiAddNotSuccess);
        mSocket.on("couldNotFindSpaetiInDB", spaetiNotFound);
        mSocket.on("updatedSpaetiSuccessfully", spaetiUpdateSuccess);
        mSocket.on("couldNotUpdateSpaeti", spaetiUpdateNotSuccess);
        mSocket.on("deletedSpaetiSuccessfully", spaetiDeleteSuccess);
        mSocket.on("couldNotDeleteSpaeti", spaetiDeleteNotSuccess);
    }

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
}
