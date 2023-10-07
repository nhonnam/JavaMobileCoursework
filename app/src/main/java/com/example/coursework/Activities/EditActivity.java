package com.example.coursework.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.coursework.Database.AppDatabase;
import com.example.coursework.Models.Hike;
import com.example.coursework.R;

public class EditActivity extends AppCompatActivity {
    Button back, saveBtn;
    TextView dateControl;
    EditText nameText, locationText, lengthText, descriptionText;
    RadioGroup parkingGroup;
    Spinner levelSpinner;
    AppDatabase appDatabase;
    Hike hike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        back = findViewById(R.id.back);
        saveBtn = findViewById(R.id.save);
        nameText = findViewById(R.id.nameText);
        locationText = findViewById(R.id.locationText);
        lengthText = findViewById(R.id.lengthText);
        dateControl = findViewById(R.id.dateText);
        descriptionText = findViewById(R.id.descriptionText);
        parkingGroup = findViewById(R.id.parkingGroup);
        levelSpinner = findViewById(R.id.levelSpinner);

        back.setOnClickListener(view -> goToHomeFragment());

        appDatabase = Room.databaseBuilder(this.getApplicationContext(), AppDatabase.class,
                        "hikes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        Intent intent = getIntent();
        if (intent != null) {
            hike = intent.getSerializableExtra("HikeObject", Hike.class);

            if (hike != null) {
                System.out.println(hike.name);

                nameText.setText(hike.name);
                locationText.setText(hike.location);
                lengthText.setText(hike.length.toString());
                dateControl.setText(hike.date);
                descriptionText.setText(hike.description);

                int selectedRadioButtonId = hike.has_parking ? R.id.yesValue : R.id.noValue;
                parkingGroup.check(selectedRadioButtonId);

                levelSpinner.setSelection(hike.level);
            }
        }

        saveBtn.setOnClickListener(view -> editHike());
    }

    private void editHike() {
        String name = nameText.getText().toString().trim();
        String location = locationText.getText().toString().trim();
        String date = dateControl.getText().toString().trim();
        String length = lengthText.getText().toString().trim();
        int selectedRadioButtonId = parkingGroup.getCheckedRadioButtonId();
        String description = descriptionText.getText().toString().trim();

        if (name.isEmpty() || location.isEmpty() || date.equals("Date") || length.isEmpty()
                || selectedRadioButtonId == -1 || description.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("All required fields must be filled.")
                    .setNeutralButton("Ok", (dialogInterface, i) -> {
                    })
                    .show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String parkingTag = (String) selectedRadioButton.getTag();
        long level = levelSpinner.getSelectedItemId();

        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Hike will be updated:\nName: " + name + "\nLocation: " + location
                        + "\nDate of the hike: " + date + "\nParking available: " +
                        (parkingTag.equals("true") ? "Yes" : "No") + "\nLength of the hike: " +
                        length + "\nDifficulty level: " + level + "\n\n Are you sure?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Yes", (dialog, which) -> {
                    hike.name = name;
                    hike.location = location;
                    hike.date = date;
                    hike.has_parking = parkingTag.equals("true") ? true : false;
                    hike.length = Float.parseFloat(length);
                    hike.level = Math.toIntExact(level);
                    hike.description = description;
                    appDatabase.hikeDao().update(hike);
                    goToHomeFragment();
                })
                .show();
    }

    private void goToHomeFragment(){
        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.putExtra("selectedFragment", "home");
        startActivity(intent1);
    }
}