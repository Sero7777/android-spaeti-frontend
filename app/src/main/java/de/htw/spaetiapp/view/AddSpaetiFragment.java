package de.htw.spaetiapp.view;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import de.htw.spaetiapp.controller.AddSpaetiController;
import de.htw.spaetiapp.models.Spaeti;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.util.AddressInspector;

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
        addSpaetiController = ((MainActivity)getActivity()).getAddSpaetiController();

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
        country = view.findViewById(R.id.addCountry);
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
                   spaetiName.setError("Name is required");
                   return;
               }

                if (isEmpty(address)){
                    address.setError("Address is required");
                    return;
                }
                if(isEmpty(number)){
                    number.setError("Street number is required");
                    return;
                }

                if(isEmpty(zip)){
                    zip.setError("Postal code is required");
                    return;
                }
                if(isEmpty(city)){
                    city.setError("City is required");
                    return;
                }
                if(isEmpty(country)){
                    country.setError("Country is required");
                    return;
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

                LatLng latlong = AddressInspector.getLocationFromAddress(getContext(), spaeti.getStreetName() + " " + spaeti.getZip() + " " + spaeti.getCity());

                if(null != latlong) {
                    spaeti.setLatitude(latlong.latitude);
                    spaeti.setLongitude(latlong.longitude);
                    try {
                        addSpaetiController.addSpaeti(spaeti);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //TODO TOAST error invalid adress
                    Toast.makeText(getContext(), "No such address was found!", Toast.LENGTH_LONG).show();

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

    @SuppressLint("ResourceType")
    public void clearFields() {
        spaetiName.getText().clear();
        openT.setText(R.string.hint_opening_time);
        closeT.setText(R.string.hint_closing_time);
        description.getText().clear();
        address.getText().clear();
        number.getText().clear();
        zip.getText().clear();
        city.getText().clear();
        country.getText().clear();
    }
}
