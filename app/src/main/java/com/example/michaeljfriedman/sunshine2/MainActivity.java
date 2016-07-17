package com.example.michaeljfriedman.sunshine2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
     * top bar. Here we create the overflow menu laid out in main.xml
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
        if (id == R.id.action_settings) {
            // Show settings menu
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        } else if (id == R.id.action_map) {
            openPostalCodeOnMap();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Open a map showing the postal code the user specified in the settings
     */
    private void openPostalCodeOnMap() {
        // Get user's postal code from preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String postalCode = prefs.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        // Create and send intent to view a map
        Intent mapIntent = new Intent();
        mapIntent.setAction(Intent.ACTION_VIEW);
        Uri geoLocation = Uri.parse("geo:0,0?")
                .buildUpon()
                .appendQueryParameter("q", postalCode)
                .build();
        mapIntent.setData(geoLocation);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "You don't have an maps app!", Toast.LENGTH_SHORT).show();
        }
    }
}
