package com.example.michaeljfriedman.sunshine2;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load up the fragment that displays the forecasts. We use the SupportFragmentManager
        // to maintain compatibility with Gingerbread
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, new ForecastFragment());
            fragmentTransaction.commit();
        }
    }
}
