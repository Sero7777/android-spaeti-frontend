package de.htw.spaetiapp.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
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


    ListAdapter adapter;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView rv = rootView.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListAdapter(SpaetiRepository.getInstance().getSpaetiList());
        rv.setAdapter(adapter);
        return rootView;
    }

    public void notifyAdapter(){
        adapter.notifyDataSetChanged();
    }

}
