package com.example.coursework.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.coursework.Adapters.HikeAdapter;
import com.example.coursework.Adapters.HikeSearchAdapter;
import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements HikeSearchAdapter.OnDeleteClickListener {
    AppDatabase appDatabase;
    RecyclerView recyclerView;
    HikeSearchAdapter hikeSearchAdapter;
    List<Hike> hikes = new ArrayList<Hike>();
    EditText searchText;
    Button searchBtn;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "hikes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        searchText = v.findViewById(R.id.search_text);
        searchBtn = v.findViewById(R.id.search_btn);
        recyclerView = v.findViewById(R.id.hike_search_list);

        searchBtn.setOnClickListener(view -> searchHike());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hikeSearchAdapter = new HikeSearchAdapter(hikes);
        recyclerView.setAdapter(hikeSearchAdapter);

        return v;
    }

    private void searchHike(){
        String search = searchText.getText().toString();
        hikes = appDatabase.hikeDao().findByName(search);
        hikeSearchAdapter = new HikeSearchAdapter(hikes);
        recyclerView.setAdapter(hikeSearchAdapter);
    }
}