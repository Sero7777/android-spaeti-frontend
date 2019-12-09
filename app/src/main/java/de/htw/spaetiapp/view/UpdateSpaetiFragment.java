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
                    spaeti.setLatitude(latlong.latitude);
                    spaeti.setLongitude(latlong.longitude);
                    try {
                        updateSpaetiController.updateSpaeti(spaeti);
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
