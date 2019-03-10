package com.example.android.dreamdestinations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.dreamdestinations.Model.Trip_Price;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by joycelin12 on 10/6/18.
 * referencing https://stackoverflow.com/questions/31716405/add-value-to-datapoint-by-user
 * http://www.android-graphview.org/dates-as-labels/
 *
 */

public class TripActivityFragment extends Fragment {

    private ArrayList<Trip_Price> mPriceList = new ArrayList<Trip_Price>();

    //add LineGraphSeries of DataPoint type
    LineGraphSeries<DataPoint> series;

    GraphView graph;

    public TripActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trip, container, false);
        graph = (GraphView) root.findViewById(R.id.graph);

    /*    Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d2.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graph.getSeries().clear(); */






        //referencing from https://github.com/mitchtabian/Adding-Data-in-REAL-TIME-to-a-Graph-Graphview-lib-/blob/master/ScatterPlotDynamic/app/src/main/java/com/tabian/scatterplotdynamic/MainActivity.java
        init();
      
        return root;
    }

    private void init() {

        series = new LineGraphSeries<>();

        if(mPriceList.size() != 0){
            createPlot();
        }else{
            Log.e("Plot", "onCreate: No data to plot.");
        }
    }

    private void createPlot() {

        Date prevDate = null;
        Date firstDate = null;
        Date lastDate = null;

        for (int i=0; i< mPriceList.size(); i++) {

            try {


                String pricing = mPriceList.get(i).getPrice();
                int id = mPriceList.get(i).getId();
                Date date = null;

                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(mPriceList.get(i).getTimestamp().substring(0, 10));
                    if (i == 0) {
                        firstDate = date;
                    }
                    if (i == mPriceList.size() - 1) {
                        lastDate = date;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("graph", date + " and price " + pricing + "and id " + id);

                if (prevDate != date) {

                    Log.e("date", date + " is next date " + prevDate + "is prev date .");

                    series.appendData(new DataPoint(date, Double.parseDouble(pricing)), true, mPriceList.size());
                    prevDate = date;
                }
            } catch (IllegalArgumentException e){
                Log.e("Plot", "createPlot: IllegalArgumentException: " + e.getMessage() );
            }

        }

        //set Scrollable and Scaleable
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graph.getViewport().setMinX(firstDate.getTime());
        graph.getViewport().setMaxX(lastDate.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.addSeries(series);


    }

    public void setPrice(ArrayList<Trip_Price> priceList) {
        mPriceList = priceList;

    }


}
