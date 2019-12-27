package de.htw.spaetiapp.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class AddressInspector {
    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng result = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);

            if (address == null || address.isEmpty()) {
                return null;
            }

            Address location = address.get(0);
            result = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
