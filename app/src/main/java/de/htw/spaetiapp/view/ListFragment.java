package de.htw.spaetiapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.view.adapters.ListAdapter;
import de.htw.spaetiapp.models.SpaetiRepository;

public class ListFragment extends Fragment {

    private ListAdapter adapter;
    private RecyclerView rv;
    private View rootView;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rv = rootView.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListAdapter(SpaetiRepository.getInstance().getSpaetiList());
        rv.setAdapter(adapter);
        return rootView;
    }

    void notifyAdapter(){
        adapter.notifyDataSetChanged();
    }
}
