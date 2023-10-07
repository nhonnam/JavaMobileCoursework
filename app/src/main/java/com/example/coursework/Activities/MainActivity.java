package com.example.coursework.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.coursework.Fragments.AddFragment;
import com.example.coursework.Fragments.HomeFragment;
import com.example.coursework.Fragments.SearchFragment;
import com.example.coursework.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.add);

        Intent intent = getIntent();
        if (intent != null) {
            String selectedFragment = intent.getStringExtra("selectedFragment");

            if (selectedFragment != null) {
                navigateToFragment(selectedFragment);
            } else {
                navigateToFragment("add");
                bottomNavigationView.setSelectedItemId(R.id.add);
            }
        }
    }

    AddFragment addFragment = new AddFragment();
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add) {
            getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, addFragment)
                        .commit();
            return true;
        } else if (itemId == R.id.home) {
            getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, homeFragment)
                        .commit();
            return true;
        } else if (itemId == R.id.search) {
            getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, searchFragment)
                        .commit();
            return true;
        }
        return false;
    }

    public void navigateToFragment(String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentTag.equals("home")) {
            fragmentTransaction.replace(R.id.flFragment, homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.home);
        } else if (fragmentTag.equals("add")) {
            fragmentTransaction.replace(R.id.flFragment, addFragment);
            bottomNavigationView.setSelectedItemId(R.id.add);
        } else if (fragmentTag.equals("search")) {
            fragmentTransaction.replace(R.id.flFragment, searchFragment);
            bottomNavigationView.setSelectedItemId(R.id.search);
        }

        fragmentTransaction.commit();
    }
}