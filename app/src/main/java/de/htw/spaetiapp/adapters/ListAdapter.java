package de.htw.spaetiapp.adapters;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.htw.spaetiapp.R;
import de.htw.spaetiapp.models.Spaeti;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private ArrayList<Spaeti> spaetiList;

    public ListAdapter(ArrayList<Spaeti> spaetiList) {
        this.spaetiList = spaetiList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_spaeti_recyclerview, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.textView.setText(spaetiList.get(position).toString());
        holder.textView.setBackgroundResource(R.drawable.spaeti_container_recycler_view);

    }

    @Override
    public int getItemCount() {
        return spaetiList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.spaeti_text);
        }
    }
}
