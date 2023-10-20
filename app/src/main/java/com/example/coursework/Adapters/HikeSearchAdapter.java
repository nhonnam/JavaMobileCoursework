package com.example.coursework.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Models.Hike;
import com.example.coursework.R;

import java.util.List;

public class HikeSearchAdapter extends RecyclerView.Adapter<HikeSearchAdapter.ContactViewHolder> {
    private List<Hike> hikes;

    public HikeSearchAdapter(List<Hike> hikes) {
        this.hikes = hikes;
    }

    @NonNull
    @Override
    public HikeSearchAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_search_item, parent, false);
        return new HikeSearchAdapter.ContactViewHolder(itemView);
    }

    public interface OnDeleteClickListener {
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hike_search_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HikeSearchAdapter.ContactViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.name.setText(hike.name);
    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }
}
