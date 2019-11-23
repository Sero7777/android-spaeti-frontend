package de.htw.spaetiapp.networking;

import android.net.SocketKeepalive;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

//this is a singleton btw
public class SocketIO {
    private static SocketIO instance = null;
    private Socket mSocket;

    private SocketIO() {
        try {
            this.mSocket = IO.socket("localhost");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
}
