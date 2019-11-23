package de.htw.spaetiapp.networking;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import de.htw.spaetiapp.controller.AddSpaetiController;
import de.htw.spaetiapp.controller.UpdateSpaetiController;
import de.htw.spaetiapp.models.Spaeti;

//this is a singleton btw
public class SocketIO {
    private static SocketIO instance = null;
    private Socket mSocket;
    private AddSpaetiController addController;
    private UpdateSpaetiController updateController;

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
        mSocket.on("couldNotFindSpaetiInDB", spaetiNotFound );
        mSocket.on("updatedSpaetiSuccessfully", spaetiUpdateSuccess);
    }

    private Emitter.Listener spaetiAddSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            addController.addSpaetiSuccess();
        }
    };

    private Emitter.Listener spaetiAddNotSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            addController.addSpaetiNotsuccess();
        }
    };

    private  Emitter.Listener spaetiNotFound = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            updateController.spaetiNotFound();
        }
    };

    private Emitter.Listener spaetiUpdateSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //not sure which Type is correct
            String data = (String) args[0];
            updateController.updatedSpaeti(data);

        }
    };
}
