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

public class UpdateSpaetiControllerTest {

    @Rule
    public ActivityTestRule<MainActivity> addRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void updatedSpaeti() {
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


        UpdateSpaetiController controller2 = addRule.getActivity().getUpdateSpaetiController();
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
        spaeti2.set_id(spaeti.get_id());


        String jsonSpaeti2 = gson.toJson(spaeti2);

        JSONObject jsonObject2 = null;
        try {
            jsonObject2 = new JSONObject(jsonSpaeti2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        controller2.updatedSpaeti(jsonObject2, false);

        int index = 0;
        for (Spaeti spaetiFromRepo : SpaetiRepository.getInstance().getSpaetiList()) {
            if (spaetiFromRepo.getName().equals(spaeti2.getName())) {
                index = SpaetiRepository.getInstance().getSpaetiList().indexOf(spaetiFromRepo);
            }
        }

        assertEquals(spaeti2.getName(), SpaetiRepository.getInstance().getSpaetiList().get(index).getName());
    }

}