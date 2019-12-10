package de.htw.spaetiapp.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
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

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.controller.UpdateSpaetiController;
import de.htw.spaetiapp.models.Spaeti;


public class UpdateSpaetiFragment extends Fragment {

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


    public UpdateSpaetiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        updateSpaetiController = ((MainActivity)getActivity()).getUpdateSpaetiController();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_spaeti, container, false);
    }

    public void setFields(Spaeti spaeti) {
        spaetiToBeUpdated = spaeti;
        spaetiName.setText(spaeti.getName());
        if(!spaeti.getOpeningTime().equals("n/a"))
            openT.setText(spaeti.getOpeningTime());
        if(!spaeti.getClosingTime().equals("n/a"))
            closeT.setText(spaeti.getOpeningTime());
        if(null != spaeti.getDescription())
            description.setText(spaeti.getDescription());

        String[] streetAndNumber = spaeti.getStreetName().split(" ");


        address.setText(streetAndNumber[0]);
        number.setText(streetAndNumber[1]);
        city.setText(spaeti.getCity());
        zip.setText(Integer.toString(spaeti.getZip()), TextView.BufferType.EDITABLE);
        country.setText(spaeti.getCountry());

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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


        updateButton.setOnClickListener(new View.OnClickListener() {
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


                spaetiToBeUpdated.setCity(country.getText().toString());
                spaetiToBeUpdated.setDescription(description.getText().toString());
                spaetiToBeUpdated.setOpeningTime(openT.getText().toString());
                spaetiToBeUpdated.setClosingTime(closeT.getText().toString());
                spaetiToBeUpdated.setStreetName(address.getText().toString() + " " + number.getText().toString());
                spaetiToBeUpdated.setName(spaetiName.getText().toString());
                spaetiToBeUpdated.setZip(Integer.parseInt(zip.getText().toString()));
                spaetiToBeUpdated.setCity(city.getText().toString());

                LatLng latlong = getLocationFromAddress(getContext(), spaetiToBeUpdated.getStreetName() + " " + spaetiToBeUpdated.getZip() + " " + spaetiToBeUpdated.getCity());
                if(null != latlong) {
                    spaetiToBeUpdated.setLatitude(latlong.latitude);
                    spaetiToBeUpdated.setLongitude(latlong.longitude);
                    try {
                        updateSpaetiController.updateSpaeti(spaetiToBeUpdated);
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
