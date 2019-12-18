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
import de.htw.spaetiapp.decorator.EqualSpacingItemDecorator;
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
     //   RecyclerView recList = (RecyclerView) getActivity().findViewById(R.id.cardList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new ListAdapter(SpaetiRepository.getInstance().getSpaetiList()));
        rv.addItemDecoration(new EqualSpacingItemDecorator(16, EqualSpacingItemDecorator.VERTICAL));
        rv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return rv;
    }

}
