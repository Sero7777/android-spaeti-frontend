package de.htw.spaetiapp.controller;

import androidx.test.rule.ActivityTestRule;

import com.github.nkzawa.socketio.client.Socket;

import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;

import de.htw.spaetiapp.networking.SocketIO;
import de.htw.spaetiapp.view.MainActivity;

import static org.junit.Assert.*;

public class ConnectionControllerTest {

    @Rule
    public ActivityTestRule<MainActivity> addRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void connectClient() {
        Field connectionControllerField = null;
        Field socketIO = null;
        try {
            connectionControllerField = MainActivity.class.getDeclaredField("connectionController");
            socketIO = SocketIO.class.getDeclaredField(("mSocket"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        connectionControllerField.setAccessible(true);
        socketIO.setAccessible(true);

        ConnectionController connectionController = null;
        Socket socket = null;
        try {
             connectionController = (ConnectionController) connectionControllerField.get(addRule.getActivity());
             socket = (Socket) socketIO.get(SocketIO.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        connectionController.connectClient();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(socket.connected());
    }

    @Test
    public void setAddController() {
        Field socketIO = null;
        try {
            socketIO = SocketIO.class.getDeclaredField(("addController"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        socketIO.setAccessible(true);

        AddSpaetiController addSpaetiController  = null;
        try {
            addSpaetiController = (AddSpaetiController) socketIO.get(SocketIO.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertEquals(addRule.getActivity().getAddSpaetiController(), addSpaetiController);
    }

    @Test
    public void setUpdateController() {
        Field socketIO = null;
        try {
            socketIO = SocketIO.class.getDeclaredField(("updateController"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        socketIO.setAccessible(true);

        UpdateSpaetiController updateSpaetiController  = null;
        try {
            updateSpaetiController = (UpdateSpaetiController) socketIO.get(SocketIO.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertEquals(addRule.getActivity().getUpdateSpaetiController(), updateSpaetiController);
    }


    @Test
    public void setDeleteController() {
        Field socketIO = null;
        try {
            socketIO = SocketIO.class.getDeclaredField(("deleteController"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        socketIO.setAccessible(true);

        DeleteSpaetiController deleteSpaetiController  = null;
        try {
            deleteSpaetiController = (DeleteSpaetiController) socketIO.get(SocketIO.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertEquals(addRule.getActivity().getDeleteSpaetiController(), deleteSpaetiController);
    }

}