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

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.controller.UpdateSpaetiController;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.util.AddressInspector;
import de.htw.spaetiapp.util.InputValidator;

public class UpdateSpaetiFragment extends Fragment implements EditSpaetiFragment{

    private TextView openT;
    private TextView closeT;
    private Button updateButton;
    private EditText spaetiName;
    private EditText number;
    private EditText description;
    private EditText zip;
    private EditText address;
    private EditText country;
    private EditText city;
    private UpdateSpaetiController updateSpaetiController;
    private Spaeti spaetiToBeUpdated;
    private UpdateSpaetiFragment updateSpaetiFragment;


    public UpdateSpaetiFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        updateSpaetiController = ((MainActivity) getActivity()).getUpdateSpaetiController();
        return inflater.inflate(R.layout.fragment_update_spaeti, container, false);
    }

    void setFields(Spaeti spaeti) {
        spaetiToBeUpdated = spaeti;
        spaetiName.setText(spaeti.getName());
        if (!spaeti.getOpeningTime().equals("n/a"))
            openT.setText(spaeti.getOpeningTime());
        if (!spaeti.getClosingTime().equals("n/a"))
            closeT.setText(spaeti.getOpeningTime());
        if (null != spaeti.getDescription())
            description.setText(spaeti.getDescription());

        String[] streetAndNumber = spaeti.getStreetName().split(" ");

        address.setText(streetAndNumber[0]);
        number.setText(streetAndNumber[1]);
        city.setText(spaeti.getCity());
        zip.setText(Integer.toString(spaeti.getZip()), TextView.BufferType.EDITABLE);
        country.setText(spaeti.getCountry());
    }

    void clearFields() {
        InputValidator.clearFields(updateSpaetiFragment);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        updateSpaetiFragment = this;
        openT = view.findViewById(R.id.openingTime);
        closeT = view.findViewById(R.id.closingTime);
        openT.setOnClickListener(new TimeViewListener(openT));
        closeT.setOnClickListener(new TimeViewListener(closeT));

        spaetiName = view.findViewById(R.id.updateName);
        updateButton = view.findViewById(R.id.updateButton);
        country = view.findViewById(R.id.updateCountry);
        address = view.findViewById(R.id.updateAddress);
        zip = view.findViewById(R.id.updateZip);
        description = view.findViewById(R.id.updateDescription);
        number = view.findViewById(R.id.updateNumber);
        city = view.findViewById(R.id.updateCity);

        setUpdateButtonListener();

    }

    private void setUpdateButtonListener() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InputValidator.checkForEmptyFields(updateSpaetiFragment)) return;

                spaetiToBeUpdated.setCity(country.getText().toString());
                spaetiToBeUpdated.setDescription(description.getText().toString());
                spaetiToBeUpdated.setOpeningTime(openT.getText().toString());
                spaetiToBeUpdated.setClosingTime(closeT.getText().toString());
                spaetiToBeUpdated.setStreetName(address.getText().toString() + " " + number.getText().toString());
                spaetiToBeUpdated.setName(spaetiName.getText().toString());
                spaetiToBeUpdated.setZip(Integer.parseInt(zip.getText().toString()));
                spaetiToBeUpdated.setCity(city.getText().toString());

                LatLng latlong = AddressInspector.getLocationFromAddress(getContext(), spaetiToBeUpdated.getStreetName() + " " + spaetiToBeUpdated.getZip() + " " + spaetiToBeUpdated.getCity());

                if (null != latlong) {
                    spaetiToBeUpdated.setLatitude(latlong.latitude);
                    spaetiToBeUpdated.setLongitude(latlong.longitude);
                    try {
                        updateSpaetiController.updateSpaeti(spaetiToBeUpdated);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "No such address was found!", Toast.LENGTH_LONG).show();
                }
            }
        });
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
