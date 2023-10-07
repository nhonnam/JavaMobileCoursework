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

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.ContactViewHolder> {
    private List<Hike> hikes;
    private OnDeleteClickListener onDeleteClickListener;
    Button deleteBtn;

    public interface OnDeleteClickListener {
        void onEditClick(Hike hike);
        void onDeleteClick(Hike hike);
    }

    public HikeAdapter(List<Hike> hikes, OnDeleteClickListener onDeleteClickListener) {
        this.hikes = hikes;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hike_name);
            deleteBtn = itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.name.setText(hike.name);

        holder.itemView.setOnClickListener(view -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onEditClick(hikes.get(position));
            }
        });

        deleteBtn.setOnClickListener(view -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(hikes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }
}
