package com.example.android.dreamdestinations;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dreamdestinations.Model.Predictions;
import com.example.android.dreamdestinations.Utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getPredictionsFromJson;

/**
 * Created by joycelin12 on 8/30/18.
 * referencing https://stackoverflow.com/questions/15580111/how-can-i-dynamically-create-menu-items
 * referencing https://stackoverflow.com/questions/3328757/how-to-click-or-tap-on-a-textview-text
 */

public class PlaceTask extends AsyncTask<String[] , Void, ArrayList<Predictions>> {

    private Context context;
    private String JSONString;
    private View view;



    public PlaceTask(Context context, View view) { //, MovieAdapter.ItemClickListener itemClickListener, RecyclerView mMoviesList,
                     //MovieResponse movies){
        this.context = context;
        this.view = view;
        //this.mClickListener = itemClickListener;
        //this.mMoviesList = mMoviesList;
        //this.movies = movies;

    }

    @Override
    protected ArrayList<Predictions> doInBackground(String[] ... params) {

        if (params.length == 0) {
            return null;
        }

        String[] location = params[0];
        URL movieUrl = NetworkUtils.buildUrl(location);

        try {

            //JSONString = NetworkUtils.getResponseFromHttpUrl(movieUrl);
            NetworkUtils test = new NetworkUtils();
            JSONString = test.run(movieUrl.toString());

            ArrayList<Predictions> simpleJsonData= new ArrayList<>();


            simpleJsonData  = getPredictionsFromJson(context, JSONString);

            return simpleJsonData;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }

    }


    // Override onPostExecute to display the results in the GridView
    @Override
    protected void onPostExecute(final ArrayList<Predictions> predictionsData) {

        String predictlist = "";

        for (Predictions in: predictionsData) {

            predictlist =  predictlist + in.getCode() + " " +  in.getName() +"\n";
        }

        Log.e("Prediction", "this is " + predictlist);

       // PopupMenu popup = new PopupMenu(context, );
         //for (Predictions s : predictionsData) {
           // popup.getMenu().add(s.getCode() + " " +  s.getName());
        //}
        //popup.show();

        final TextView popup = (TextView) view.findViewById(R.id.nearbyFrom);
        Context wrapper = new ContextThemeWrapper(context, R.style.MyPopupMenu);
        PopupMenu menu = new PopupMenu(wrapper, popup);

        //PopupMenu menu = new PopupMenu(context, popup);
        for (Predictions s : predictionsData) {

            menu.getMenu().add(s.getCode() + " " +  s.getName());
        }


        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(context,
                        "Clicked popup menu item " + item.getTitle(),
                        Toast.LENGTH_SHORT).show();

                //AutoCompleteTextView fromAirport = (AutoCompleteTextView) view.findViewById(R.id.fromAirport);

                //fromAirport.setText("lalala");

                return true;
            }
        });

        menu.show();

        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Toast.makeText(context,"You Clicked : " ,Toast.LENGTH_SHORT).show();

            }
        });


        //mAdapter = new MovieAdapter(NUM_LIST_ITEMS, movieData, this.context);
        //mAdapter.setClickListener(this.mClickListener);
        //this.mMoviesList.setAdapter(mAdapter);

    }
}
