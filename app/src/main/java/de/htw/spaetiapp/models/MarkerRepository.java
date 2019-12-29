package de.htw.spaetiapp.models;

import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

public class MarkerRepository {

    private static MarkerRepository instance = null;
    private HashMap<String, Marker> markerMap;

    private MarkerRepository() {
        this.markerMap = new HashMap();
    }

    public static MarkerRepository getInstance() {
        if (instance == null) instance = new MarkerRepository();
        return instance;
    }

    public HashMap getMarkerMap() {
        return markerMap;
    }

    public void addMarkerToMap(String id, Marker marker) {
        if(id != null) markerMap.put(id, marker);
    }

    public void deleteMarkerFromMap(String id) {
       if (id != null) markerMap.remove(id);
    }
}
