package de.htw.spaetiapp.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.htw.spaetiapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSpaetiFragment extends Fragment {


    public AddSpaetiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_spaeti, container, false);
    }

}
