package com.example.android.dreamdestinations;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.android.dreamdestinations.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by joycelin12 on 9/15/18.
 */

public class SessionTask extends AsyncTask<String[] , Void, String> {

    private Context context;
    private String JSONString;
    private ProgressDialog progress;



    public SessionTask(Context context) {
        this.context = context;
        progress = ProgressDialog.show(context, context.getString(R.string.search_flights),
                context.getString(R.string.wait));


    }

    @Override
    protected void onPreExecute()
    {
        progress.setCancelable(false);
        progress.isIndeterminate();
        progress.show();
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
        new SearchTask(context, progress).execute(search[7]);



    }
}
