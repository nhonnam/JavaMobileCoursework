package com.example.coursework.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;

public class AddFragment extends Fragment {

    AppDatabase appDatabase;
    TextView dateControl;
    EditText nameText, locationText, lengthText, descriptionText;
    RadioGroup parkingGroup;
    Spinner levelSpinner;
    Button addButton;
    View v;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add, container, false);

        nameText = v.findViewById(R.id.nameText);
        locationText = v.findViewById(R.id.locationText);
        lengthText = v.findViewById(R.id.lengthText);
        dateControl = v.findViewById(R.id.dateText);
        parkingGroup = v.findViewById(R.id.parkingGroup);
        levelSpinner = v.findViewById(R.id.levelSpinner);
        addButton = v.findViewById(R.id.save);
        descriptionText = v.findViewById(R.id.descriptionText);

        addButton.setOnClickListener(view -> addHike());

        dateControl.setOnClickListener(view -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getChildFragmentManager(), "dateText");
        });

        appDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                        "hikes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        return v;
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
        {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, --month, day);}
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            LocalDate dob = LocalDate.of(year, ++month, day);
            ((AddFragment)getParentFragment()).updateDateOfBirth(dob);
        }
    }

    public void updateDateOfBirth(LocalDate date){
        dateControl.setText(date.toString());
    }

    public void addHike() {
        String name = nameText.getText().toString().trim();
        String location = locationText.getText().toString().trim();
        String date = dateControl.getText().toString().trim();
        String length = lengthText.getText().toString().trim();
        int selectedRadioButtonId = parkingGroup.getCheckedRadioButtonId();
        String description = descriptionText.getText().toString().trim();

        if (name.isEmpty() || location.isEmpty() || date.equals("Date") || length.isEmpty()
            || selectedRadioButtonId == -1) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("All required fields must be filled.")
                    .setNeutralButton("Ok", (dialogInterface, i) -> {
                    })
                    .show();
            return;
        }

        RadioButton selectedRadioButton = v.findViewById(selectedRadioButtonId);
        String parkingTag = (String) selectedRadioButton.getTag();
        long level = levelSpinner.getSelectedItemId();
        String[] levelName = {"High", "Medium", "Low"};

        new AlertDialog.Builder(getActivity())
                .setTitle("Confirmation")
                .setMessage("New hike will be added:\nName: " + name + "\nLocation: " + location
                + "\nDate of the hike: " + date + "\nParking available: " +
                        (parkingTag.equals("true") ? "Yes" : "No") + "\nLength of the hike: " +
                        length + "\nDifficulty level: " + levelName[Math.toIntExact(level)] +
                        "\n\n Are you sure?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> {
                    Hike hike = new Hike();
                    hike.name = name;
                    hike.location = location;
                    hike.date = date;
                    hike.has_parking = parkingTag.equals("true") ? true : false;
                    hike.length = Float.parseFloat(length);
                    hike.level = Math.toIntExact(level);
                    hike.description = description;
                    appDatabase.hikeDao().add(hike);
                    switchToHomeFragment();
                })
                .show();
    }

    private void switchToHomeFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, new HomeFragment());
        fragmentTransaction.commit();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
    }
}