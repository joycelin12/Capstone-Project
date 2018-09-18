package com.example.android.dreamdestinations;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.dreamdestinations.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by joycelin12 on 9/17/18.
 */

public class SearchTask extends AsyncTask<String , Void, String> {

    private Context context;
    private String JSONString;


    public SearchTask(Context context) { //, MovieAdapter.ItemClickListener itemClickListener, RecyclerView mMoviesList,
        //MovieResponse movies){
        this.context = context;
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

             //Log.e("search", JSONString);

            //ArrayList<Predictions> simpleJsonData = new ArrayList<>();


            // simpleJsonData  = getPredictionsFromJson(context, JSONString);

            return JSONString;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Override onPostExecute to display the results in the GridView
    @Override
    protected void onPostExecute(final String location) {


        Log.e("TAG", location);


        //new SearchTask(this).execute(session[7]);


    }
}
