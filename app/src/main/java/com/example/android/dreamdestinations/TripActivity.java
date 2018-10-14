package com.example.android.dreamdestinations;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.dreamdestinations.Model.Trip;
import com.example.android.dreamdestinations.Model.Trip_Price;

import org.json.JSONException;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_DEST_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_FROM_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_ORIGIN_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_TO_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.CONTENT_URI;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry.COLUMN_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry.COLUMN_PRICE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry.COLUMN_TRIP_ID;

public class TripActivity extends AppCompatActivity implements TripAdapter.ItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TRIP_LIST = "trip_list";
    private RecyclerView mTripList;
    private TripAdapter tAdapter;
    private TripAdapter.ItemClickListener mClickListener;
    private int tripId;
    private ArrayList<Trip> tripList;
    private ArrayList<Trip_Price> priceList = new ArrayList<>();
    private String[] params = new String[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        //allow up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTripList = (RecyclerView) findViewById(R.id.recycler_view_trip);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        tripList = intent.getParcelableArrayListExtra(TRIP_LIST);

        mTripList.setLayoutManager(new LinearLayoutManager(this));

        tAdapter = new TripAdapter(tripList.size(), tripList, this);
        tAdapter.setClickListener(this);
        mTripList.setAdapter(tAdapter);

        getSupportLoaderManager().initLoader(1, null, this);


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) throws JSONException {
        Log.e("adapter position", String.valueOf(position));
        Log.e("tripid", String.valueOf(tripList.get(position).getId()));
        params[0] = tripList.get(position).getOrigin_id();
        params[1] = tripList.get(position).getDest_id();
        params[2] = tripList.get(position).getFrom_date();
        params[3] = tripList.get(position).getTo_date();

        //view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        tripId = getTrip(params);
        Log.e("tripid", String.valueOf(tripId));

        getSupportLoaderManager().restartLoader(1, null, this);

        TripActivityFragment tripFragment = new TripActivityFragment();

        tripFragment.setPrice(priceList);

        //Use a FragmentManager and transaction to add the fragment to the screen
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Fragment transaction
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_trip, tripFragment, "Trip")
                .commit();

        Button button = findViewById(R.id.deleteButton);
        button.setVisibility(View.VISIBLE);


    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        // Cursor cursor = getContentResolver()
        //       .query(FavouritesContract.FavouritesEntry.CONTENT_URI,null,null,null,COLUMN_TIMESTAMP);
        Log.e("load create", "am i here");
        Log.e("load create tripId", String.valueOf(tripId));
        Log.e("uri", FavouritesContract.FavouritesPriceEntry.buildPriceUriWithId(String.valueOf(tripId)).toString());

        // return new CursorLoader(this, FavouritesContract.FavouritesPriceEntry.CONTENT_URI,
        //       null, COLUMN_TRIP_ID + "=?", new String[] {String.valueOf(tripId)}, COLUMN_DATE);

        return new CursorLoader(this, FavouritesContract.FavouritesPriceEntry.buildPriceUriWithId(String.valueOf(tripId)),
                null, null, null, COLUMN_DATE);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        Log.e("loadfinished", "am i here");

        if ((data != null) && (data.getCount() > 0)) {
            Log.e("data", "not null");

            //https://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
            // looping through all rows and adding to list
            if (data.moveToFirst()) {
                do {
                    Log.e("data", "not null do while");
                    Trip_Price price = new Trip_Price();
                    price.setPrice(data.getString(data.getColumnIndex(COLUMN_PRICE)));
                    price.setTimestamp(data.getString(data.getColumnIndex(COLUMN_DATE)));
                    Log.e("price", data.getString(data.getColumnIndex(COLUMN_PRICE)));
                    Log.e("price date", data.getString(data.getColumnIndex(COLUMN_DATE)));
                    priceList.add(price);
                } while (data.moveToNext());
            }

        } else {

            Log.e("data", "null");

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        loader = null;
    }

    private int getTrip(String[] params) {

        Cursor cursor = getContentResolver()
                .query(CONTENT_URI, null,
                        COLUMN_ORIGIN_ID + "=? and " +
                                COLUMN_DEST_ID + "=? and " +
                                COLUMN_FROM_DATE + "=? and " +
                                COLUMN_TO_DATE + "=? "
                        , new String[]{
                                params[0],
                                params[1],
                                params[2],
                                params[3]}, null);


        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(_ID));

            return id;
        } else {
            return -1;
        }


    }

    public void deleteTrip(View view) {

        if (tripId  < 0 ) {
            Toast.makeText(this, "Please select a trip to delete.", Toast.LENGTH_LONG).show();

        } else {

            int rows = getContentResolver()
                    .delete(FavouritesContract.FavouritesEntry.buildTripUriWithId(String.valueOf(tripId)), _ID,
                            new String[]{String.valueOf(tripId)});

            int rows2 = getContentResolver()
                    .delete(FavouritesContract.FavouritesPriceEntry.buildPriceUriWithId(String.valueOf(tripId)),
                            COLUMN_TRIP_ID, new String[]{String.valueOf(tripId)});


            if (rows > 0 && rows2 > 0) {
                Toast.makeText(this, "Trip deleted successfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Trip not deleted", Toast.LENGTH_LONG).show();

            }

        }

    }

}
