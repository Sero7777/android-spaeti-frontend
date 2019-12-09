package de.htw.spaetiapp.view;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.models.Spaeti;

public class SpaetiInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View markerSpaetiView;

    public SpaetiInfoWindowAdapter(Activity context) {
        markerSpaetiView = context.getLayoutInflater().inflate(R.layout.spaeti_windows, null);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Spaeti spaeti = (Spaeti) marker.getTag();
        //TODO null is a probably a bad idea
        if (spaeti == null) return null;
        String info = "";
        System.out.println(spaeti.getOpeningTime() + " +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        if (!spaeti.getOpeningTime().equalsIgnoreCase("Opening Time (Optional)") || spaeti.getOpeningTime() != null && !spaeti.getOpeningTime().isEmpty())
            info += "Opening Time: " + spaeti.getOpeningTime() + "\n";
        if (!spaeti.getClosingTime().equalsIgnoreCase("Closing Time (Optional)") || spaeti.getClosingTime() != null && !spaeti.getClosingTime().isEmpty())
            info += "Closing Time: " + spaeti.getClosingTime() + "\n";
        if (spaeti.getDescription() != null && !spaeti.getDescription().isEmpty())
            info += "Description: " + spaeti.getDescription() + "\n";
        if (spaeti.getStreetName() != null && !spaeti.getStreetName().isEmpty())
            info += "Address: " + spaeti.getStreetName() + "\n";
        if (spaeti.getZip() != 0)
            info += "ZIP: " + spaeti.getZip() + "\n";
        if (spaeti.getCity() != null && !spaeti.getCity().isEmpty())
            info += "City: " + spaeti.getCity();

        TextView spaetiName = markerSpaetiView.findViewById(R.id.spaeti_title);
        TextView spaetiInfo = markerSpaetiView.findViewById(R.id.spaeti_info);

        spaetiName.setTypeface(null, Typeface.BOLD);
        spaetiName.setText(spaeti.getName());
        spaetiInfo.setText(info);

        return markerSpaetiView;
    }
}
