package com.example.android.dreamdestinations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.dreamdestinations.Model.Itineraries;

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getItinerariesFromJson;

/**
 * Created by joycelin12 on 8/25/18.
 */

public class ResultActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    public static final String ITINERARIES = "itineraries";
    public static final String PARAMS = "params";
    private String mItineraries;
    private String[] mParams;
    private ArrayList<Itineraries> itinerariesJsonData;
    private ResultAdapter rAdapter;
    private ResultAdapter.ItemClickListener mClickListener;


    public ResultActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_result, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        if (savedInstanceState != null) {
            mItineraries = savedInstanceState.getString(ITINERARIES);
            mParams = savedInstanceState.getStringArray(PARAMS);



        }

        if (mItineraries != null) {
            try {
                itinerariesJsonData = getItinerariesFromJson(getContext(), mItineraries);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);

            rAdapter = new ResultAdapter(itinerariesJsonData.size(), mItineraries, mParams, getContext());
            rAdapter.setClickListener(this.mClickListener);
            recyclerView.setAdapter(rAdapter);

        }

        return root;

    }

    public void setItineraries(String itineraries) { mItineraries = itineraries; }
    public void setParams(String[] params) { mParams = params; }



    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(ITINERARIES, mItineraries);
        outState.putStringArray(PARAMS, mParams);
        super.onSaveInstanceState(outState);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();

    }
}
