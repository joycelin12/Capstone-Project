package com.example.android.dreamdestinations;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.dreamdestinations.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by joycelin12 on 10/23/18.
 */

public class SessionBGTask extends AsyncTask<String[] , Void, String> {

    private Context context;
    private int tripId;
    private String JSONString;

    public SessionBGTask(Context context, int tripId) {
        this.context = context;
        this.tripId = tripId;
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
            //JSONString = test.runSession(sessionUrl.toString());

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
        new SearchBGTask(context, tripId).execute(search[7]);


    }
}
