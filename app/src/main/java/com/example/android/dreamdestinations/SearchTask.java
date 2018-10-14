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

    public SearchTask(Context context, ProgressDialog progress) {
        this.context = context;
        this.progress = progress;


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

                    progress.dismiss();
                    Intent intent = new Intent(this.context, ResultActivity.class);
                    ResultActivity.flight = searchJsonStr;
                    context.startActivity(intent);

    }
}
