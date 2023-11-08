package com.example.coursework.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.coursework.Activities.EditActivity;
import com.example.coursework.Adapters.HikeAdapter;
import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

import java.util.List;

public class HomeFragment extends Fragment implements HikeAdapter.OnDeleteClickListener {

    AppDatabase appDatabase;
    RecyclerView recyclerView;
    HikeAdapter hikeAdapter;
    List<Hike> hikes;
    Button deleteAllBtn;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "hikes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        recyclerView = v.findViewById(R.id.hike_list);
        deleteAllBtn = v.findViewById(R.id.delete_all);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hikes = appDatabase.hikeDao().getAll();
        hikeAdapter = new HikeAdapter(hikes, this);
        recyclerView.setAdapter(hikeAdapter);

        deleteAllBtn.setOnClickListener(view -> onDeleteAllClick());
        return v;
    }

    @Override
    public void onDeleteClick(Hike hike) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete Hike")
                .setMessage("Are you sure you want to delete this hike: " + hike.name +"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    appDatabase.hikeDao().delete(hike);
                    hikes.remove(hike);
                    hikeAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onEditClick(Hike hike){
        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra("HikeObject", hike);
        startActivity(intent);
    }

    private void onDeleteAllClick(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete All Hikes")
                .setMessage("Are you sure you want to delete all hikes?")
                .setPositiveButton("Delete All", (dialog, which) -> {
                    appDatabase.hikeDao().deleteAll();
                    hikes.clear();
                    hikeAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}