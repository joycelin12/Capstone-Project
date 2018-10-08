package com.example.android.dreamdestinations;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getStatusFromJson;

/**
 * Created by joycelin12 on 9/17/18.
 */

public class SearchTask extends AsyncTask<String , Void, String> {

    private Context context;
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
    private ProgressDialog progress;

    public SearchTask(Context context, ProgressDialog progress) { //, MovieAdapter.ItemClickListener itemClickListener, RecyclerView mMoviesList,
        //MovieResponse movies){
        this.context = context;
        this.progress = progress;
        //this.mClickListener = itemClickListener;
        //this.mMoviesList = mMoviesList;
        //this.movies = movies;


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

            Log.e("search1", JSONString);
            Log.e("search1 lenght", String.valueOf(JSONString.length()));


            statusData = getStatusFromJson(context, JSONString);
            Log.e("status1", statusData);

            Thread.sleep(60000);

            JSONString = test.runSearch(searchUrl.toString());
            statusData = getStatusFromJson(context, JSONString);

            Log.e("search2 lenght", String.valueOf(JSONString.length()));

            Log.e("status2", statusData);

            return JSONString;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
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
      /*

        try {
            statusData = getStatusFromJson(context, searchJsonStr);
            itinerariesJsonData  = getItinerariesFromJson(context, searchJsonStr);
            legsJsonData  = getLegsFromJson(context, searchJsonStr);
            segmentsData = getSegmentsFromJson(context, searchJsonStr);
            carriersData = getCarriersFromJson(context, searchJsonStr);
            agentsData = getAgentsFromJson(context, searchJsonStr);
            placesData = getPlacesFromJson(context, searchJsonStr);
            currenciesData = getCurrenciesFromJson(context, searchJsonStr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

        /*
        rAdapter = new ResultAdapter(itinerariesJsonData.size(), searchJsonStr, this.context);
        rAdapter.setClickListener(this.mClickListener);
        this.mResultsList.setAdapter(rAdapter); */
        //Log.e("TAG2", statusData);
/*
        for(int i=0; i<itinerariesJsonData.size(); i++) {
            Log.e("TAG", String.valueOf(itinerariesJsonData.get(i)));
            Log.e("TAG2",itinerariesJsonData.get(i).getPricingOptions().get(i).getPrice());
        }

        for(int i=0; i<legsJsonData.size(); i++) {
            Log.e("TAG", String.valueOf(legsJsonData.get(i)));
        }*/

       /* if (statusData != "UpdatesComplete"){
            Toast.makeText(context, "not finished", Toast.LENGTH_LONG).show();

        }
        Runnable runTask = null;
        final Handler[] handlerTask = new Handler[1];
        final Runnable finalRunTask = runTask;
        runTask = new Runnable() {
            public void run() {
                handlerTask[0] = new Handler();
                handlerTask[0].postDelayed(this, 100);
                Toast.makeText(context, "not finished", Toast.LENGTH_LONG).show();

                if (statusData == "UpdatesComplete") {
                    Toast.makeText(context, "finished", Toast.LENGTH_LONG).show();
                    handlerTask[0].removeCallbacks(finalRunTask); */
                    progress.dismiss();
                    Intent intent = new Intent(this.context, ResultActivity.class);
                    ResultActivity.flight = searchJsonStr;
                   // intent.putExtra(ResultActivity.ITINERARIES, searchJsonStr);
                    context.startActivity(intent);

            /*    } else {
                    Toast.makeText(context, " still not finished", Toast.LENGTH_LONG).show();


                }
            }
        }; */

        //new SearchTask(this).execute(session[7]);

    }
}
