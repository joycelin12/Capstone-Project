package com.example.android.dreamdestinations;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.dreamdestinations.Model.Agents;
import com.example.android.dreamdestinations.Model.Carriers;
import com.example.android.dreamdestinations.Model.Currencies;
import com.example.android.dreamdestinations.Model.Itineraries;
import com.example.android.dreamdestinations.Model.Legs;
import com.example.android.dreamdestinations.Model.Places;
import com.example.android.dreamdestinations.Model.PricingOptions;
import com.example.android.dreamdestinations.Model.Segments;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getAgentsFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getCarriersFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getCurrenciesFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getItinerariesFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getLegsFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getPlacesFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getSegmentsFromJson;


/**
 * Created by joycelin12 on 9/27/18.
 *
 * referencing from https://www.locked.de/how-to-make-html-links-in-android-text-view-work/
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.NumberViewHolder> {

    //add a variable to display the number of items
    private int mNumberItems;
    private ItemClickListener mClickListener;
    private Context mContext;
    private Cursor mCursor;
    private String mFlight;


    //create a constructor that accepts int as a parameter for number of items and store in the variable
    public ResultAdapter(int numberOfItems, String flight, Context context){
        mNumberItems = numberOfItems;
        this.mFlight = flight;
        this.mContext = context;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForItem = R.layout.flight_list_item;
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {

        String url = null;
        ArrayList<Itineraries> itinerariesJsonData = new ArrayList<Itineraries>();
        ArrayList<Legs> legsJsonData = new ArrayList<Legs>();
        ArrayList<Segments> segmentsData = new ArrayList<Segments>();
        ArrayList<Carriers> carriersData = new ArrayList<Carriers>();
        ArrayList<Agents> agentsData = new ArrayList<Agents>();
        ArrayList<Places> placesData = new ArrayList<Places>();
        ArrayList<Currencies> currenciesData = new ArrayList<Currencies>();

        try {
            itinerariesJsonData  = getItinerariesFromJson(mContext, mFlight);
            legsJsonData  = getLegsFromJson(mContext, mFlight);
         //   segmentsData = getSegmentsFromJson(mContext, mFlight);
            carriersData = getCarriersFromJson(mContext, mFlight);
         //   agentsData = getAgentsFromJson(mContext, mFlight);
            placesData = getPlacesFromJson(mContext, mFlight);
        //    currenciesData = getCurrenciesFromJson(mContext, mFlight);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        String outboundLegId = itinerariesJsonData.get(position).getOutboundLegId();
        String inboundLegId = itinerariesJsonData.get(position).getInboundLegId();
        String route ;
        int outIndex = 0;
        int inIndex = 0;
        int outFrom = 0;
        int outTo = 0;
        int stopOut =0;
        int inFrom = 0;
        int inTo = 0;
        int stopIn = 0;
        int carr = 0;


        for (Legs legs:legsJsonData) {

            if(legs.getId().equals(outboundLegId)) {
                outIndex = legsJsonData.indexOf(legs);
            }

        }

        //int outIndex = legsJsonData.map(function(x) {return x.id; }).indexOf(outboundLegId); // legsJsonData.indexOf(outboundLegId);
        String outOriginStation = legsJsonData.get(outIndex).getOriginStation();
        String outDestStation = legsJsonData.get(outIndex).getDestinationStation();

        for (Places places:placesData) {

            if(places.getId().equals(outOriginStation)) {
                outFrom = placesData.indexOf(places);
            }

        }

        //int outFrom = placesData.indexOf(outOriginStation);
        String outFromPlace = placesData.get(outFrom).getName();

        for (Places places:placesData) {

            if(places.getId().equals(outDestStation)) {
                outTo = placesData.indexOf(places);
            }

        }
       // int outTo = placesData.indexOf(outDestStation);
        String outToPlace = placesData.get(outTo).getName();

        String carrierId = legsJsonData.get(outIndex).getCarriers().get(0).toString();
        for (Carriers carrier:carriersData) {

            if(carrier.getId().equals(carrierId)) {
                carr = carriersData.indexOf(carrier);
            }

        }
        String carrierImage = carriersData.get(carr).getImageUrl();

        Context context = holder.listItemAirlineImage.getContext();
        Picasso.with(context)
                .load(carrierImage)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(holder.listItemAirlineImage);

        holder.listItemAirline.setText(carriersData.get(carr).getName());

        route = position +" : "+ outFromPlace + " - ";
        Log.e("route", route);

        int stopOutIndex = legsJsonData.get(outIndex).getStops().size();
        for (int i=0; i<stopOutIndex ; i++) {

            //int stopOut = placesData.indexOf(legsJsonData.get(outIndex).getStops().get(i));
            for (Places places:placesData) {

                if(places.getId().equals(legsJsonData.get(outIndex).getStops().get(i))) {
                    stopOut = placesData.indexOf(places);

                }

            }
            String stopOutPlace = placesData.get(stopOut).getName();
            route = route + stopOutPlace + " - ";
            Log.e("route stop out id ", stopOutPlace);
            Log.e("route stop out ", route);

        }

        route = route + outToPlace + "\n lala";
        Log.e("route outto", route);


        for (Legs legs:legsJsonData) {

            if(legs.getId().equals(inboundLegId)) {
                inIndex = legsJsonData.indexOf(legs);
            }

        }

        //inIndex = legsJsonData.indexOf(inboundLegId);
        String inOriginStation = legsJsonData.get(inIndex).getOriginStation();
        String inDestStation = legsJsonData.get(inIndex).getDestinationStation();

        //int inFrom = placesData.indexOf(inOriginStation);
        for (Places places:placesData) {

            if(places.getId().equals(inOriginStation)) {
                inFrom = placesData.indexOf(places);
            }

        }

        String inFromPlace = placesData.get(inFrom).getName();
        //int inTo = placesData.indexOf(inDestStation);

        for (Places places:placesData) {

            if(places.getId().equals(inDestStation)) {
                inTo = placesData.indexOf(places);
            }

        }
        String inToPlace = placesData.get(inTo).getName();

        route = route + inFromPlace + " - ";
        Log.e("route in from", route);


        int stopInIndex = legsJsonData.get(inIndex).getStops().size();
        for (int i=0; i<stopInIndex ; i++) {

            //int stopIn = placesData.indexOf(legsJsonData.get(inIndex).getStops().get(i));
            for (Places places:placesData) {

                if(places.getId().equals(legsJsonData.get(inIndex).getStops().get(i))) {
                    stopIn = placesData.indexOf(places);
                }

            }
            String stopInPlace = placesData.get(stopIn).getName();
            route = route + stopInPlace + " - ";
            Log.e("route stop in", route);

        }
        route = route + inToPlace;
        Log.e("route into", route);

        holder.listItemRoute.setText(route);

        String booking = itinerariesJsonData.get(position).getPricingOptions().get(0).getDeeplinkUrl();
        int bookingint = itinerariesJsonData.get(position).getPricingOptions().size();
        Log.e("bookin", booking);
        holder.listItemBook.setText(Html.fromHtml("<a href=\"+ " + booking + "\">Book Now</a>"));
        holder.listItemBook.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());

        //holder.listItemBook.setText(booking);
        String pricing = itinerariesJsonData.get(position).getPricingOptions().get(0).getPrice();
        holder.listItemPrice.setText(pricing);

    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView listItemAirlineImage;
        TextView listItemRoute;
        TextView listItemAirline;
        TextView listItemPrice;
        TextView listItemBook;


        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemAirlineImage = (ImageView) itemView.findViewById(R.id.airlineImage);
            listItemRoute = (TextView) itemView.findViewById(R.id.route);
            listItemAirline = (TextView) itemView.findViewById(R.id.airline);
            listItemPrice = (TextView) itemView.findViewById(R.id.price);
            listItemBook = (TextView) itemView.findViewById(R.id.book);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) try {
                mClickListener.onItemClick(view, getAdapterPosition());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // convenience method for getting data at click position
    //Movie getItem(int id) {
      //  return mData.get(id);
    //}

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position) throws JSONException;
    }

}
