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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
    }

    /**
     * This function gets called before each test is executed to delete the database.  This makes
     * sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    /**
     * Students: Note that this only tests that the LOCATION table has the correct columns, since
     * we give you the code for the weather table.  This test does not look at the
     */
    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(WeatherContract.LocationEntry.TABLE_NAME);
        tableNameHashSet.add(WeatherContract.WeatherEntry.TABLE_NAME);

        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new WeatherDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + WeatherContract.LocationEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(WeatherContract.LocationEntry._ID);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_CITY_NAME);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_COORD_LAT);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_COORD_LONG);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                locationColumnHashSet.isEmpty());
        db.close();
    }

    /**
     Helper method for the next two tests. Inserts a location into the location table of the given
     database. Returns the id of the new location in the table
     */
    public long insertLocation(SQLiteDatabase db, ContentValues values) {
        // Insert ContentValues into database and get a row ID back
        long rowID = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, values);
        return rowID;
    }

    /**
     * Students:  Here is where you will build code to test that we can insert and query the
     * location database.  We've done a lot of work for you.  You'll want to look in TestUtilities
     * where you can uncomment out the "createNorthPoleLocationValues" function.  You can
     * also make use of the ValidateCurrentRecord function from within TestUtilities.
     */
    public void testLocationTable() {
        // First step: Get reference to writable database
        SQLiteDatabase db = new WeatherDbHelper(this.mContext).getWritableDatabase();

        // Make sample location values
        ContentValues testValues = TestUtilities.createNorthPoleLocationValues();

        // Insert the location
        long rowID = insertLocation(db, testValues);

        // Validate that the insertion worked
        assertTrue("Error: Failed to enter North Pole location into the database", rowID != -1);

        // Query the database and receive a Cursor back
        Cursor cursor = db.query(
                WeatherContract.LocationEntry.TABLE_NAME,  // String tableName
                null,    // String[] columns
                null,    // String selection
                null,    // String[] selectionArgs
                null,    // String groupBy
                null,    // String having
                null     // String orderBy
        );

        // Move the cursor to the row just entered in the database
        cursor.moveToFirst();

        // Validate data in resulting Cursor with the original ContentValues
        TestUtilities.validateCurrentRecord("Error: The row retrieved by the query is not the North Pole row", cursor, testValues);

        // Validate that the cursor only had the one row
        assertFalse("Error: The cursor found more than one entry in the database", cursor.moveToNext());

        // Finally, close the cursor and database
        cursor.close();
        db.close();
    }

    /**
        Students:  Here is where you will build code to test that we can insert and query the
        database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can use the "createWeatherValues" function.  You can
        also make use of the validateCurrentRecord function from within TestUtilities.
     */
    public void testWeatherTable() {
        // First step: Get reference to writable database
        SQLiteDatabase db = new WeatherDbHelper(this.mContext).getWritableDatabase();

        // First insert the location, get the location's row id back, and then use it to
        // insert the weather
        long locationRowId = insertLocation(db, TestUtilities.createNorthPoleLocationValues());

        // Validate that the insertion worked
        assertTrue("Error: Failed to enter North Pole location into the database", locationRowId != -1);

        // Create the weather entry, insert, and validate a successful insertion
        ContentValues testValues = TestUtilities.createWeatherValues(locationRowId);
        long weatherRowId = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, testValues);
        assertTrue("Error: Failed to enter the sample weather entry into the database", weatherRowId != -1);

        // Query the database and receive a Cursor back
        Cursor cursor = db.query(
                WeatherContract.WeatherEntry.TABLE_NAME,  // String tableName
                null,    // String[] columns
                null,    // String selection
                null,    // String[] selectionArgs
                null,    // String groupBy
                null,    // String having
                null     // String orderBy
        );

        // Move the cursor to a valid database row
        cursor.moveToFirst();

        // Validate data in resulting Cursor with the original ContentValues
        TestUtilities.validateCurrentRecord("Error: The row retrieved by the query is not the sample weather entry", cursor, testValues);

        // Validate that the cursor only had the one row
        assertFalse("Error: The cursor found more than one entry in the database", cursor.moveToNext());

        // Finally, close the cursor and database
        cursor.close();
        db.close();
    }
}
