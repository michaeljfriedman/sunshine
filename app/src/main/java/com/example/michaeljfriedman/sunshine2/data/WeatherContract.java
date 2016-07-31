/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.michaeljfriedman.sunshine2.data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the weather database.
 */
public class WeatherContract {

    /**
     * To make it easy to query for the exact date, we normalize all dates that go into
     * the database to the start of the the Julian day at UTC.
     */
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    /**
     * Holds constants for the column names in the LOCATION table
     *
     * BaseColumns contains constants for standard columns in any table, namely the _ID column
     */
    public static final class LocationEntry implements BaseColumns {

        public static final String TABLE_NAME = "location";

        // Column names
        public static final String COLUMN_COORD_LAT = "coord_lat";    // (float) degrees
        public static final String COLUMN_COORD_LONG = "coord_long";

        public static final String COLUMN_CITY_NAME = "city_name";    // (String) city name

        public static final String COLUMN_LOCATION_SETTING = "location_setting";    // (int) postal code location setting
    }

    /**
     * Holds constants for the column names of the WEATHER table
     */
    public static final class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";

        // Column names
        public static final String COLUMN_LOC_KEY = "location_id";      // (int) id referencing a location stored in another table

        public static final String COLUMN_DATE = "date";                // (long) ms since the epoch

        public static final String COLUMN_WEATHER_ID = "weather_id";    // (int) identifies the icon to be used

        public static final String COLUMN_SHORT_DESC = "short_desc";    // (String) provided by OpenWeatherMap

        public static final String COLUMN_MIN_TEMP = "min";             // (float) metric temp
        public static final String COLUMN_MAX_TEMP = "max";

        public static final String COLUMN_HUMIDITY = "humidity";        // (float) percentage humidity

        public static final String COLUMN_PRESSURE = "pressure";        // (float) ??? units pressure

        public static final String COLUMN_WIND_SPEED = "wind";          // (float) mph speed

        public static final String COLUMN_DEGREES = "degrees";          // (float) meteorological degrees
    }
}
