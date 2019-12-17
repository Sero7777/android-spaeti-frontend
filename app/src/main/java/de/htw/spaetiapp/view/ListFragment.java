package de.htw.spaetiapp.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.adapters.ListAdapter;
import de.htw.spaetiapp.models.SpaetiRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ListAdapter(SpaetiRepository.getInstance().getSpaetiList()));
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_list, container, false);
        return rv;
    }

}
