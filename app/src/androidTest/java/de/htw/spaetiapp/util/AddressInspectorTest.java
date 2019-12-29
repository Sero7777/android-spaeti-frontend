package de.htw.spaetiapp.util;

import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Rule;
import org.junit.Test;

import de.htw.spaetiapp.view.MainActivity;

import static org.junit.Assert.*;

public class AddressInspectorTest {
    @Rule
    public ActivityTestRule<MainActivity> addRule = new ActivityTestRule<>(MainActivity.class, true, true);
    @Test
    public void getLocationFromAddressUseCase() {
        String address = "Rathenaustraße 3 12459 Berlin";
        LatLng latLng = AddressInspector.getLocationFromAddress(addRule.getActivity() ,address);
        assertNotNull(latLng);
    }

    @Test
    public void getLocationFromAddressBadAddressShouldReturnNull() {
        String address = "brug 3 21323 Kotzen";
        LatLng latLng = AddressInspector.getLocationFromAddress(addRule.getActivity() ,address);
        assertNull(latLng);
    }
}