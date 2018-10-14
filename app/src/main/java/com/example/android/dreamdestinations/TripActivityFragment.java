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

    public TripActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trip, container, false);



        Log.e("fragment", "inside");
        /*
        values = new DataPoint[mPriceList.size()];
        for (int i=0; i<size; i++) {
            Integer xi = Integer.parseInt(Dates.get(i));
            Integer yi = Integer.parseInt(Amount.get(i));
            DataPoint v = new DataPoint(xi, yi);
            values[i] = v;
        } */
        /*
        if (mPriceList.size() > 0) {

            GraphView graph = (GraphView) root.findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            DataPoint[] values = new DataPoint[mPriceList.size()+2];
            graph.getViewport().setScrollable(true);

            Log.e("inside fragment", "inside");
            Date firstDate = null;
            Date lastDate = null;

            for (int i=0; i< mPriceList.size(); i++) {

                String pricing = mPriceList.get(i).getPrice();
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(mPriceList.get(i).getTimestamp().substring(0,10));
                    if (i == 0) {
                        firstDate = date;
                    }
                    if (i == mPriceList.size()-1) {
                        lastDate = date;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DataPoint data = new DataPoint(date, Double.parseDouble(pricing));
                values[i] = data;

              //  Calendar calendar = Calendar.getInstance();
               // Date d1 = calendar.getTime();



                //series.add(new DataPoint(date, pricing));
            }
            Calendar calendar = Calendar.getInstance();
            Date d1 = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date d2 = calendar.getTime();

            DataPoint data = new DataPoint(d1, 1000);
            values[1] = data;
            DataPoint data2 = new DataPoint(d2, 900);
            values[2] = data2;

            graph.addSeries(series);

            //series = new LineGraphSeries<DataPoint>(values);
             // set date label formatter
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            graph.getGridLabelRenderer().setNumHorizontalLabels(mPriceList.size()+3); // only 4 because of the space

            // set manual x bounds to have nice steps
            graph.getViewport().setMinX(firstDate.getTime());
            graph.getViewport().setMaxX(lastDate.getTime());
            graph.getViewport().setMaxX(d2.getTime());
            graph.getViewport().setXAxisBoundsManual(true);

            // as we use dates as labels, the human rounding to nice readable numbers
            // is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);

            graph.onDataChanged(false, false);
            graph.refreshDrawableState();




        } else{

            Log.e("inside fragment", "null size");

        } */
        //series.add(new DataPoint(date, pricing));


        /*
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        */


        Log.e("outside fragment", "outside");


        return root;
    }

    public void setPrice(ArrayList<Trip_Price> priceList) {
        mPriceList = priceList;

    }

}
