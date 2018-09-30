package com.example.android.dreamdestinations;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.android.dreamdestinations.Model.Predictions;
import com.example.android.dreamdestinations.Utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getPredictionsFromJson;

/**
 * Created by joycelin12 on 9/15/18.
 */

public class SessionTask extends AsyncTask<String[] , Void, String> {

    private Context context;
    private String JSONString;


    public SessionTask(Context context) {
        this.context = context;

    }



    @Override
    protected String doInBackground(String[]... params) {

        if (params.length == 0) {
            return null;
        }

        URL sessionUrl = NetworkUtils.buildSessionUrl();

        try {

            NetworkUtils test = new NetworkUtils();
            JSONString = test.runSession(sessionUrl.toString(), params[0]);


            return JSONString;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Override onPostExecute to display the results in the GridView
    @Override
    protected void onPostExecute(final String location) {


        String[] search = location.split("/");

        new SearchTask(context).execute(search[7]);


    }
}
