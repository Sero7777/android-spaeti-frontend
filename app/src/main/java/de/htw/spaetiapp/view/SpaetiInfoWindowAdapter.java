package de.htw.spaetiapp.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.models.Spaeti;

public class SpaetiInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    private final View markerSpaetiView;

    public SpaetiInfoWindowAdapter(Activity context){
        this.context = context;
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
        if(spaeti== null) return null;

        TextView tvTitle = markerSpaetiView.findViewById(R.id.tv_title);
        TextView tvSubTitle = markerSpaetiView.findViewById(R.id.tv_subtitle);

        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(spaeti.getCity());

        return markerSpaetiView;
    }
}
