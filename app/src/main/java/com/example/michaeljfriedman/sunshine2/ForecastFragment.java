package com.example.michaeljfriedman.sunshine2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mfred on 7/16/2016.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Dummy data
        List<String> forecasts = new ArrayList<String>(Arrays.asList(new String[] {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        }));

        // Adapter takes each item of the list and applies the xml layout on it
        ArrayAdapter<String> forecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),                      // The current context (this activity)
                        R.layout.list_item_forecast,        // The name
                        R.id.list_item_forecast_textview,   // The ID of the textview to populate.
                        forecasts);                         // Dummy data

        // Inflate the main view, which contains the ListView, load up the list by
        // supplying the adapter to the ListView
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastAdapter);

        return rootView;
    }

}
