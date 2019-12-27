package de.htw.spaetiapp.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.models.Spaeti;
import de.htw.spaetiapp.util.AddressInspector;

public class SettingsFragment extends Fragment {

    private EditText name;
    private Button saveButton;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        name = view.findViewById(R.id.nameText);
        name.setText(((MainActivity)getActivity()).loadName());
        saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(name)){
                    name.setText("anonymous user");
                    return;
                }

                ((MainActivity)getActivity()).saveName(name.getText().toString());
                ((MainActivity)getActivity()).hideKeyboard();
                ((MainActivity)getActivity()).showMainView();
            }
        });
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
