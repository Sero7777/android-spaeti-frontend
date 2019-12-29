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

public class DeleteSpaetiControllerTest {


    @Rule
    public ActivityTestRule<MainActivity> addRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void spaetiDeleted() {
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

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonSpaeti);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        controller.addSpaetiSuccess(jsonObject, false);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int listSizeBeforeDeletion = SpaetiRepository.getInstance().getSpaetiList().size();
        DeleteSpaetiController controller2 = addRule.getActivity().getDeleteSpaetiController();

        controller2.spaetiDeleted("1", false);

        assertEquals(listSizeBeforeDeletion - 1, SpaetiRepository.getInstance().getSpaetiList().size());
    }
}