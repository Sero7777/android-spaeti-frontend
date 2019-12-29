package de.htw.spaetiapp.networking;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import androidx.test.rule.ActivityTestRule;

import java.lang.reflect.Field;

import de.htw.spaetiapp.controller.AddSpaetiController;
import de.htw.spaetiapp.controller.DeleteSpaetiController;
import de.htw.spaetiapp.controller.UpdateSpaetiController;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.view.MainActivity;

import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SocketIOTest {

    @Rule
    public ActivityTestRule<MainActivity> addRule = new ActivityTestRule<>(MainActivity.class, true, true);


    @Test
    public void setAddControllerUseCase() {
        SocketIO socketIO = SocketIO.getInstance();
        socketIO.setAddController(addRule.getActivity().getAddSpaetiController());

        Field privateStringField = null;
        try {
            privateStringField = SocketIO.class.getDeclaredField("addController");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        privateStringField.setAccessible(true);
        AddSpaetiController fieldValue = null;
        try {
            fieldValue = (AddSpaetiController) privateStringField.get(socketIO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertEquals(addRule.getActivity().getAddSpaetiController(), fieldValue);
    }

    @Test
    public void setUpdateControllerUseCase() {
        SocketIO socketIO = SocketIO.getInstance();
        socketIO.setAddController(addRule.getActivity().getAddSpaetiController());

        Field privateStringField = null;
        try {
            privateStringField = SocketIO.class.getDeclaredField("updateController");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        privateStringField.setAccessible(true);
        UpdateSpaetiController fieldValue = null;
        try {
            fieldValue = (UpdateSpaetiController) privateStringField.get(socketIO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertEquals(addRule.getActivity().getUpdateSpaetiController(), fieldValue);
    }

    @Test
    public void setDeleteControllerUseCase() {
        SocketIO socketIO = SocketIO.getInstance();
        socketIO.setAddController(addRule.getActivity().getAddSpaetiController());

        Field privateStringField = null;
        try {
            privateStringField = SocketIO.class.getDeclaredField("deleteController");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        privateStringField.setAccessible(true);
        DeleteSpaetiController fieldValue = null;
        try {
            fieldValue = (DeleteSpaetiController) privateStringField.get(socketIO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        assertEquals(addRule.getActivity().getDeleteSpaetiController(), fieldValue);
    }

    @Test
    public void getInstanceUseCase() {
        assertNotNull(SocketIO.getInstance());
    }

    @Test
    public void getInstanceReturnsSameObject() {
        SocketIO socketIO1 = SocketIO.getInstance();
        SocketIO socketIO2 = SocketIO.getInstance();
        assertEquals(socketIO1, socketIO2);
    }

    @Test
    public void a_addSpaetiSendToServer() {

        SocketIO socketIO = SocketIO.getInstance();
        Spaeti spaeti = new Spaeti();
        spaeti.setName("Hallo");
        spaeti.setLatitude(0.0);
        spaeti.setLongitude(1.0);
        spaeti.setCity("Berlin");
        spaeti.setZip(12345);
        spaeti.setStreetName("Peterstr. 137");
        spaeti.setClosingTime("3");
        spaeti.setOpeningTime("1");
        spaeti.setCountry("Deutsch");
        spaeti.setDescription("nice");

        Gson gson = new Gson();
        String jsonSpaeti = gson.toJson(spaeti);

        try {
            JSONObject jsonObject = new JSONObject(jsonSpaeti);
            socketIO.addSpaetiSendToServer(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int index = 0;
        for (Spaeti spaetiFromRepo : SpaetiRepository.getInstance().getSpaetiList()) {
            if (spaetiFromRepo.getName().equals(spaeti.getName())) {
                index = SpaetiRepository.getInstance().getSpaetiList().indexOf(spaetiFromRepo);
            }
        }
        assertEquals(spaeti.getName(), SpaetiRepository.getInstance().getSpaetiList().get(index).getName());
        System.out.println("Nach a_addSpaeti " + SpaetiRepository.getInstance().getSpaetiList().size());
    }

    @Test
    public void b_updateSpaetiSendToServer() {

        SocketIO socketIO = SocketIO.getInstance();
        Spaeti spaeti = new Spaeti();
        spaeti.setName("Hallo");
        spaeti.setLatitude(0.0);
        spaeti.setLongitude(1.0);
        spaeti.setCity("Berlin");
        spaeti.setZip(12345);
        spaeti.setStreetName("Peterstr. 137");
        spaeti.setClosingTime("3");
        spaeti.setOpeningTime("1");
        spaeti.setCountry("Deutsch");
        spaeti.setDescription("nice");


        Gson gson = new Gson();
        String jsonSpaeti = gson.toJson(spaeti);

        try {
            JSONObject jsonObject = new JSONObject(jsonSpaeti);
            socketIO.addSpaetiSendToServer(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int index = 0;
        for (Spaeti spaetiFromRepo : SpaetiRepository.getInstance().getSpaetiList()) {
            if (spaetiFromRepo.getName().equals(spaeti.getName())) {
                index = SpaetiRepository.getInstance().getSpaetiList().indexOf(spaetiFromRepo);
            }
        }

        System.out.println("Nach b_updateSpaeti 1 " + SpaetiRepository.getInstance().getSpaetiList().size());


        Spaeti spaeti2 = new Spaeti();
        spaeti2.setName("HalloUpdated");
        spaeti2.setLatitude(spaeti.getLatitude());
        spaeti2.setLongitude(spaeti.getLongitude());
        spaeti2.setCity(spaeti.getCity());
        spaeti2.setZip(spaeti.getZip());
        spaeti2.setStreetName(spaeti.getStreetName());
        spaeti2.setClosingTime(spaeti.getClosingTime());
        spaeti2.setOpeningTime(spaeti.getOpeningTime());
        spaeti2.setCountry(spaeti.getCountry());
        spaeti2.setDescription(spaeti.getDescription());
        spaeti2.set_id(SpaetiRepository.getInstance().getSpaetiList().get(index).get_id());

        String jsonSpaeti2 = gson.toJson(spaeti2);

        try {
            JSONObject jsonObject2 = new JSONObject(jsonSpaeti2);
            socketIO.updateSpaetiSendToServer(jsonObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Spaeti spaeties : SpaetiRepository.getInstance().getSpaetiList()) {
            System.out.println(spaeties);
            System.out.println(SpaetiRepository.getInstance().getSpaetiList().indexOf(spaeties));
        }


        index = 0;
        for (Spaeti spaetiFromRepo : SpaetiRepository.getInstance().getSpaetiList()) {
            if (spaetiFromRepo.getName().equals(spaeti2.getName())) {
                index = SpaetiRepository.getInstance().getSpaetiList().indexOf(spaetiFromRepo);
            }
        }
        assertEquals(spaeti2.getName(), SpaetiRepository.getInstance().getSpaetiList().get(index).getName());
    }

    @Test
    public void c_deleteSpaetiSendToServer() {
        int listSizeBeforeDeletion = SpaetiRepository.getInstance().getSpaetiList().size();
        int index = 0;
        int index2 = 0;
        for (Spaeti spaetiFromRepo : SpaetiRepository.getInstance().getSpaetiList()) {
            if (spaetiFromRepo.getName().equals("Hallo")) {
                index = SpaetiRepository.getInstance().getSpaetiList().indexOf(spaetiFromRepo);
            }
            if (spaetiFromRepo.getName().equals("HalloUpdated")) {
                index2 = SpaetiRepository.getInstance().getSpaetiList().indexOf(spaetiFromRepo);
            }
        }
        SocketIO socketIO = SocketIO.getInstance();

        socketIO.deleteSpaetiSendToServer(SpaetiRepository.getInstance().getSpaetiList().get(index).get_id());
        socketIO.deleteSpaetiSendToServer(SpaetiRepository.getInstance().getSpaetiList().get(index2).get_id());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(listSizeBeforeDeletion - 2, SpaetiRepository.getInstance().getSpaetiList().size());
    }
}