package de.htw.spaetiapp.controller;

import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;

import java.lang.reflect.Field;

import de.htw.spaetiapp.models.MarkerRepository;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.models.SpaetiRepository;
import de.htw.spaetiapp.view.MainActivity;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddSpaetiControllerTest {
    @Rule
    public ActivityTestRule<MainActivity> addRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void addSpaetiSuccessUseCase() {
        int listSizeBeforeAddition = SpaetiRepository.getInstance().getSpaetiList().size();
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



        assertEquals(listSizeBeforeAddition + 1, SpaetiRepository.getInstance().getSpaetiList().size());
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

        int index = 0;
        for (Spaeti spaetiFromRepo : SpaetiRepository.getInstance().getSpaetiList()) {
            if (spaetiFromRepo.getName().equals(spaeti.getName())) {
                index = SpaetiRepository.getInstance().getSpaetiList().indexOf(spaetiFromRepo);
            }
        }
        assertEquals(spaeti.getName(), SpaetiRepository.getInstance().getSpaetiList().get(index).getName());
    }

    @Test
    public void a_addInitSpaetisShouldPolulateTheRepo(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(SpaetiRepository.getInstance().getSpaetiList().isEmpty());
    }

    @Test
    public void addMarkerToMarkerRepo() {
        int listSizeBeforeAddition = SpaetiRepository.getInstance().getSpaetiList().size();
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

        controller.addMarkerToMarkerRepo(spaeti.get_id(), null);

        assertEquals(listSizeBeforeAddition + 1, MarkerRepository.getInstance().getMarkerMap().size());
    }
}