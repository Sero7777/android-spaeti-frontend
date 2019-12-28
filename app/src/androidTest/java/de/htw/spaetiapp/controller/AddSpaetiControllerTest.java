package de.htw.spaetiapp.controller;

import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;

import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.view.MainActivity;

import static org.junit.Assert.*;

public class AddSpaetiControllerTest {
    @Rule
    public ActivityTestRule<MainActivity> addRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void addSpaeti() {
//        addRule.getActivity().getAddSpaetiController();
    }

    @Test
    public void addSpaetiSuccessUseCase() {
        AddSpaetiController controller = addRule.getActivity().getAddSpaetiController();
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
        spaeti.set_id("1");

        Gson gson = new Gson();
        String jsonSpaeti = gson.toJson(spaeti);
        JSONObject jsonObject = gson.fromJson(jsonSpaeti, JSONObject.class);
        controller.addSpaetiSuccess(jsonObject, false);

        assertEquals(1, SpaetiRepository.getInstance().getSpaetiList().size());
    }

    @Test
    public void addSpaetiSuccessIsObjectValid() {
        AddSpaetiController controller = addRule.getActivity().getAddSpaetiController();
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
        spaeti.set_id("1");

        Gson gson = new Gson();
        String jsonSpaeti = gson.toJson(spaeti);

        try {
            JSONObject jsonObject = new JSONObject(jsonSpaeti);
            controller.addSpaetiSuccess(jsonObject, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(spaeti.getName(), SpaetiRepository.getInstance().getSpaetiList().get(0).getName());
    }

    @Test
    public void addSpaetiNotsuccess() {
    }

    @Test
    public void addInitialSpaetis() {
    }
}