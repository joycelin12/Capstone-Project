package com.example.android.dreamdestinations;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.android.dreamdestinations.Model.Agents;
import com.example.android.dreamdestinations.Model.Carriers;
import com.example.android.dreamdestinations.Model.Currencies;
import com.example.android.dreamdestinations.Model.Itineraries;
import com.example.android.dreamdestinations.Model.Legs;
import com.example.android.dreamdestinations.Model.Places;
import com.example.android.dreamdestinations.Model.Segments;
import com.example.android.dreamdestinations.Utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry.COLUMN_PRICE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry.COLUMN_TRIP_ID;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getItinerariesFromJson;

/**
 * Created by joycelin12 on 10/23/18.
 */

public class SearchBGTask extends AsyncTask<String , Void, String> {

    private Context context;
    private int tripId;
    private String JSONString;
    private String statusData;
    private ArrayList<Itineraries> itinerariesJsonData;
    private ArrayList<Legs> legsJsonData;
    private ArrayList<Segments> segmentsData;
    private ArrayList<Carriers> carriersData;
    private ArrayList<Agents> agentsData;
    private ArrayList<Places> placesData;
    private ArrayList<Currencies> currenciesData;
    private ResultAdapter rAdapter;
    private RecyclerView mResultsList;
    private ResultAdapter.ItemClickListener mClickListener;

    public SearchBGTask(Context context, int tripId) {
        this.context = context;
        this.tripId = tripId;
    }

    @Override
    protected String doInBackground(String... strings) {

        if (strings.length == 0) {
            return null;
        }

        URL searchUrl = NetworkUtils.buildSearchUrl(strings[0]);

        try {

            NetworkUtils test = new NetworkUtils();

            JSONString = test.runSearch(searchUrl.toString());

            Thread.sleep(60000);

            JSONString = test.runSearch(searchUrl.toString());

            return JSONString;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Override onPostExecute to display the results in the GridView
    @Override
    protected void onPostExecute(final String searchJsonStr) {

       addTrip(tripId, searchJsonStr);

    }

    private Uri addTrip(int tripId, String searchJsonStr) {

        ArrayList<Itineraries> itinerariesJsonData = null;
        try {
            itinerariesJsonData = getItinerariesFromJson(context, searchJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String pricing = itinerariesJsonData.get(0).getPricingOptions().get(0).getPrice();

        ContentValues cv2 = new ContentValues();
        //call put to insert name value with the key COLUMN_TITLE
        cv2.put(COLUMN_TRIP_ID, tripId);
        cv2.put(COLUMN_PRICE, pricing);

        return context.getContentResolver().insert(FavouritesContract.FavouritesPriceEntry.CONTENT_URI, cv2);


    }
}
