package com.example.android.dreamdestinations;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dreamdestinations.Model.Itineraries;

import org.json.JSONException;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_DEST_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_DEST_NAME;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_FROM_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_ORIGIN_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_ORIGIN_NAME;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_TO_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.CONTENT_URI;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry.COLUMN_PRICE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry.COLUMN_TRIP_ID;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getItinerariesFromJson;

public class ResultActivity extends AppCompatActivity {

    public static final String ITINERARIES = "itineraries";
    public static final String PARAMS = "params";

    public static String flight;
    public static String[] params;
    private SQLiteDatabase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView itinerary = findViewById(R.id.itinerary);

        if (savedInstanceState == null) {

          /*  Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }

          flight = intent.getStringExtra(ITINERARIES); */
            Log.e("flight", flight);

            itinerary.setText("Flying from " + params[4] + " to " + params[5] +
                    " on " + params[2] + " to " + params[3]);
            //itinerary.setText("Flying from \n San Francisco International to London Heathrow\n" +
              //              " on\n 2018-11-01 to 2018-11-11");



            ResultActivityFragment resultFragment = new ResultActivityFragment();

            resultFragment.setItineraries(flight);
            resultFragment.setParams(params);


            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_result, resultFragment, "Result")
                    .commit();


        } else {

            flight = savedInstanceState.getString(ITINERARIES);
            params = savedInstanceState.getStringArray(PARAMS);

            itinerary.setText("Flying from " + params[4] + " to " + params[5] +
                              " on " + params[2] + " to " + params[3]);
            //itinerary.setText("Flying from \n San Francisco International to London Heathrow\n" +
              //      " on\n 2018-11-01 to 2018-11-11");


            ResultActivityFragment resultFragment = (ResultActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_result);

            resultFragment.setItineraries(flight);
            resultFragment.setParams(params);


            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_result, resultFragment)
                    .commit();


        }

        // Create a DB helper (this will create the DB if run for the first time)
        FavouritesDbHelper dbHelper = new FavouritesDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        mDb = dbHelper.getWritableDatabase();

        //favourite
        // from the website https://alvinalexander.com/source-code/android/android-checkbox-listener-setoncheckedchangelisteneroncheckedchangelistener-exam
        CheckBox checkBox = (CheckBox) findViewById(R.id.favbutton);

        //query the database to check the checkbox if is favourite
       if (params != null) {
            final int tripId = getTrip(params);
            if (tripId > 0) {
                checkBox.setChecked(true);
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // update your model (or other business logic) based on isChecked
                    if (isChecked) {
                        addTrip(params);
                        Log.e("add", "add to database");

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("params", "Flying from " + params[4] + " to " +
                                params[5] + " on " + params[2] + " to " + params[3]);
                        editor.apply();


                    } else {
                        if (tripId > 0 ) {

                            if (removeTrip(tripId) ) {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("params", "");
                                editor.apply();

                            } else {
                                Log.e("remove", "not removed");

                            }
                        }

                    }
                }
            });
        }




    }

    private void closeOnError() {

        finish();
        Toast.makeText(this, R.string.result_error_message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
       outState.putString(ITINERARIES, flight);
       outState.putStringArray(PARAMS, params);

        super.onSaveInstanceState(outState);
    }

    private int getTrip(String[] params) {


        Cursor cursor = getContentResolver()
                .query(CONTENT_URI,null,
                                COLUMN_ORIGIN_ID + "=? and " +
                                COLUMN_DEST_ID + "=? and " +
                                COLUMN_FROM_DATE + "=? and " +
                                COLUMN_TO_DATE+ "=? "
                , new String[] {
                                params[0],
                                params[1],
                                params[2],
                                params[3]},null);


        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(_ID));

            return id;
        } else {
            return -1;
        }


    }

    private Uri addTrip(String[] params) {


        ArrayList<Itineraries> itinerariesJsonData = null;
        try {
            itinerariesJsonData = getItinerariesFromJson(getApplicationContext(), flight);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String pricing = itinerariesJsonData.get(0).getPricingOptions().get(0).getPrice();

        //create ContentValues to pass values onto insert query
        ContentValues cv = new ContentValues();
        //call put to insert name value with the key COLUMN_TITLE
        cv.put(COLUMN_ORIGIN_ID, params[0]);
        cv.put(COLUMN_DEST_ID, params[1]);
        cv.put(COLUMN_FROM_DATE, params[2]);
        cv.put(COLUMN_TO_DATE, params[3]);
        cv.put(COLUMN_ORIGIN_NAME, params[4]);
        cv.put(COLUMN_DEST_NAME, params[5]);


        //call insert to run an insert query on TABLE_NAME with content values
        Uri uri = getContentResolver().insert(FavouritesContract.FavouritesEntry.CONTENT_URI, cv);

        long id = ContentUris.parseId(uri);

        ContentValues cv2 = new ContentValues();
        //call put to insert name value with the key COLUMN_TITLE
        cv2.put(COLUMN_TRIP_ID, (int) id);
        cv2.put(COLUMN_PRICE, pricing);

       // Uri uri2 = getContentResolver().insert(FavouritesContract.FavouritesPriceEntry.CONTENT_URI, cv2);

        return getContentResolver().insert(FavouritesContract.FavouritesPriceEntry.CONTENT_URI, cv2);


    }

    private boolean removeTrip(int id) {

        int rows = getContentResolver()
                .delete(FavouritesContract.FavouritesEntry.buildTripUriWithId(String.valueOf(id)),_ID,
                        new String[]{String.valueOf(id)});

        int rows2 = getContentResolver()
                .delete(FavouritesContract.FavouritesPriceEntry.buildPriceUriWithId(String.valueOf(id)),
                        COLUMN_TRIP_ID, new String[]{String.valueOf(id)});


        if (rows > 0 && rows2 > 0 ) {
            return true;
        } else {
            return false;
        }


    }


}
