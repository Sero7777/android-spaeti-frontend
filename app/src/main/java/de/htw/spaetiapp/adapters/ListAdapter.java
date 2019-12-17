package de.htw.spaetiapp.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.htw.spaetiapp.models.Spaeti;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ArrayList<Spaeti> spaetiList;

    public ListAdapter(ArrayList<Spaeti> spaetiList){
        this.spaetiList = spaetiList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new TextView(parent.getContext());
        return new ListViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
    holder.textView.setText(spaetiList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return spaetiList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}