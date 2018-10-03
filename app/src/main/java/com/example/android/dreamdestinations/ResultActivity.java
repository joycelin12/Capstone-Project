package com.example.android.dreamdestinations;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    public static final String ITINERARIES = "itineraries";
    public static String flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (savedInstanceState == null) {

          /*  Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }

          flight = intent.getStringExtra(ITINERARIES); */
            Log.e("flight", flight);


            ResultActivityFragment resultFragment = new ResultActivityFragment();

            resultFragment.setItineraries(flight);

            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_result, resultFragment, "Result")
                    .commit();


        } else {
            flight = savedInstanceState.getString(ITINERARIES);

            ResultActivityFragment resultFragment = (ResultActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_result);

            resultFragment.setItineraries(flight);

            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_result, resultFragment)
                    .commit();


        }


    }

    private void closeOnError() {

        finish();
        Toast.makeText(this, R.string.result_error_message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
       outState.putString(ITINERARIES, flight);
       super.onSaveInstanceState(outState);
    }
}
