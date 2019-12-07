package de.htw.spaetiapp.view;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import de.htw.spaetiapp.controller.AddSpaetiController;
import de.htw.spaetiapp.models.Spaeti;

import de.htw.spaetiapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSpaetiFragment extends Fragment {

    private TextView openT;
    private TextView closeT;
    private Button addButton;
    private EditText spaetiName;
    private EditText number;
    private EditText description;
    private EditText zip;
    private EditText address;
    private EditText country;
    private EditText city;
    private AddSpaetiController addSpaetiController;


    public AddSpaetiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addSpaetiController = new AddSpaetiController();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_spaeti, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        openT = view.findViewById(R.id.openingTime);
        closeT = view.findViewById(R.id.closingTime);
        openT.setOnClickListener(new TimeViewListener(openT));
        closeT.setOnClickListener(new TimeViewListener(closeT));

        spaetiName = view.findViewById(R.id.addName);
        addButton = view.findViewById(R.id.addButton);
        country = view.findViewById(R.id.addCity);
        address = view.findViewById(R.id.addAddress);
        zip = view.findViewById(R.id.addZip);
        description = view.findViewById(R.id.addDescription);
        number = view.findViewById(R.id.addNumber);
        city = view.findViewById(R.id.addCity);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("---------------------------");

                if (isEmpty(spaetiName)){
                    spaetiName.setText("Default name");
                }
                if (isEmpty(openT)) {
                    openT.setText("n/a");
                }
                if (isEmpty(closeT)){
                    closeT.setText("n/a");
                }

                Spaeti spaeti = new Spaeti();
                spaeti.setCountry(country.getText().toString());
                spaeti.setDescription(description.getText().toString());
                spaeti.setOpeningTime(openT.getText().toString());
                spaeti.setClosingTime(closeT.getText().toString());
                spaeti.setStreetName(address.getText().toString() + " " + number.getText().toString());
                spaeti.setName(spaetiName.getText().toString());
                spaeti.setZip(Integer.parseInt(zip.getText().toString()));
                spaeti.setCity(city.getText().toString());

                LatLng latlong = getLocationFromAddress(getContext(), spaeti.getStreetName() + " " + spaeti.getZip() + " " + spaeti.getCity());
                if(null != latlong) {
                    spaeti.setLat(latlong.latitude);
                    spaeti.setLon(latlong.longitude);
                    try {
                        addSpaetiController.addSpaeti(spaeti);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //TODO TOAST error invalid adress
                }


            }
        });

    }

    public static boolean isEmpty(EditText editText) {

        return editText.getText().toString().trim().length() == 0;

    }
    public static boolean isEmpty(TextView textView) {

        return textView.getText().toString().equalsIgnoreCase("Opening Time") || textView.getText().toString().equalsIgnoreCase("Closing Time");

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.isEmpty()) {
                return null;
            }


            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}
