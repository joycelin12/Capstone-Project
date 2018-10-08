package com.example.android.dreamdestinations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.dreamdestinations.Model.Trip;

import org.json.JSONException;

import java.util.ArrayList;

public class TripActivity extends AppCompatActivity implements TripAdapter.ItemClickListener{

    public static final String TRIP_LIST = "trip_list";
    private RecyclerView mTripList;
    private TripAdapter tAdapter;
    private TripAdapter.ItemClickListener mClickListener;
    private ArrayList<Trip> tripList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        mTripList = (RecyclerView) findViewById(R.id.recycler_view_trip);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        tripList = intent.getParcelableArrayListExtra(TRIP_LIST);

        tAdapter = new TripAdapter(tripList.size(), tripList, this);
        tAdapter.setClickListener(this);
        mTripList.setAdapter(tAdapter);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) throws JSONException {

    }
}


