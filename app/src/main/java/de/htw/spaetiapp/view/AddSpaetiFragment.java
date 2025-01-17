package de.htw.spaetiapp.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import de.htw.spaetiapp.util.InputValidator;

public class AddSpaetiFragment extends Fragment implements EditSpaetiFragment {

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
    private AddSpaetiFragment addSpaetiFragment;

    public AddSpaetiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addSpaetiController = ((MainActivity) getActivity()).getAddSpaetiController();
        return inflater.inflate(R.layout.fragment_add_spaeti, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addSpaetiFragment = this;
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


        setAddButtonListener();

    }

    private void setAddButtonListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InputValidator.checkForEmptyFields(addSpaetiFragment)) return;

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

                if (null != latlong) {
                    spaeti.setLatitude(latlong.latitude);
                    spaeti.setLongitude(latlong.longitude);
                    try {
                        addSpaetiController.addSpaeti(spaeti);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "No such address was found!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void clearFields() {
        InputValidator.clearFields(addSpaetiFragment);
    }

    @Override
    public EditText getSpaetiName() {
        return spaetiName;
    }

    @Override
    public EditText getAdress() {
        return address;
    }

    @Override
    public EditText getNumber() {
        return number;
    }

    @Override
    public EditText getZip() {
        return zip;
    }

    @Override
    public EditText getCity() {
        return city;
    }

    @Override
    public EditText getCountry() {
        return country;
    }

    @Override
    public TextView getOpenT() {
        return openT;
    }

    @Override
    public TextView getCloseT() {
        return closeT;
    }
    @Override
    public EditText getDescription() {
        return description;
    }
}
