package com.example.michaeljfriedman.sunshine2;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    /**
     * Runs when the activity first starts
     */
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

    /**
     * Once the activity is started, the system will call this method to create a menu in the
     * top bar. Here we create the overflow menu laid out in forecastfragment.xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * When menu items are clicked, the system calls this method to take action on those clicks
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                // pull up settings menu
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
