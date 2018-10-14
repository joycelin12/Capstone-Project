package com.example.android.dreamdestinations;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
import com.example.android.dreamdestinations.Model.Segments;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getCarriersFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getItinerariesFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getLegsFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getPlacesFromJson;
import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getSegmentsFromJson;


/**
 * Created by joycelin12 on 9/27/18.
 *
 * referencing from https://www.locked.de/how-to-make-html-links-in-android-text-view-work/
 * https://stackoverflow.com/questions/43025993/how-do-i-open-a-browser-on-clicking-a-text-link-in-textview
 * https://stackoverflow.com/questions/5882656/no-activity-found-to-handle-intent-android-intent-action-view
 * https://stackoverflow.com/questions/21569477/click-on-text-a-dialog-box-must-open
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.NumberViewHolder> {

    //add a variable to display the number of items
    private int mNumberItems;
    private ItemClickListener mClickListener;
    private Context mContext;
    private String mFlight;
    private String[] mParams;



    //create a constructor that accepts int as a parameter for number of items and store in the variable
    public ResultAdapter(int numberOfItems, String flight, String[] params, Context context){
        mNumberItems = numberOfItems;
        this.mFlight = flight;
        this.mContext = context;
        this.mParams = params;
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
    public void onBindViewHolder(NumberViewHolder holder, final int position) {

        ArrayList<Itineraries> itinerariesJsonData = new ArrayList<Itineraries>();
        ArrayList<Legs> legsJsonData = new ArrayList<Legs>();
        ArrayList<Carriers> carriersData = new ArrayList<Carriers>();

        try {
            itinerariesJsonData  = getItinerariesFromJson(mContext, mFlight);
            legsJsonData  = getLegsFromJson(mContext, mFlight);
            carriersData = getCarriersFromJson(mContext, mFlight);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String outboundLegId = itinerariesJsonData.get(position).getOutboundLegId();
        String inboundLegId = itinerariesJsonData.get(position).getInboundLegId();
        String route ;
        int carr = 0;

        int outIndex = getLegsData(outboundLegId);

        String outOriginStation = legsJsonData.get(outIndex).getOriginStation();
        String outDestStation = legsJsonData.get(outIndex).getDestinationStation();
        String outTiming = legsJsonData.get(outIndex).getDuration();
        int outTime = Integer.parseInt(outTiming);
        int outHours = outTime / 60;
        int outMinutes = outTime % 60;

        String outFromPlace = getPlacesData(outOriginStation);
        String outToPlace = getPlacesData(outDestStation);

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

        holder.listItemAirline.setText(carriersData.get(carr).getName() + "\n\n");

        route = "Outgoing: "+ outFromPlace + " - ";
        String stopOut = getStopsData(outIndex);
        route = route + stopOut;
        route = route + outToPlace + "\n";
        route = route + "Duration: " + outHours + ":" + outMinutes + "\n";

        int inIndex = getLegsData(inboundLegId);

        String inOriginStation = legsJsonData.get(inIndex).getOriginStation();
        String inDestStation = legsJsonData.get(inIndex).getDestinationStation();
        String inTiming = legsJsonData.get(inIndex).getDuration();
        int inTime = Integer.parseInt(inTiming);
        int inHours = inTime / 60;
        int inMinutes = inTime % 60;

        String inFromPlace = getPlacesData(inOriginStation);
        String inToPlace = getPlacesData(inDestStation);

        route = route + "\nIncoming: " + inFromPlace + " - ";

        String stopIn = getStopsData(inIndex);
        route = route + stopIn;
        route = route + inToPlace + "\n";
        route = route + "Duration: " + inHours + ":" + inMinutes + "\n";

        holder.listItemRoute.setText(route);

        final Context bookContext = holder.listItemBook.getContext();
        final String booking = itinerariesJsonData.get(position).getPricingOptions().get(0).getDeeplinkUrl();
        holder.listItemBook.setText(Html.fromHtml("<a href=\"+ " + booking + "\">Book Now</a>"));
        holder.listItemBook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(booking));
                bookContext.startActivity(browserIntent);
            }
        });

        String pricing = itinerariesJsonData.get(position).getPricingOptions().get(0).getPrice();
        holder.listItemPrice.setText("Price: " + pricing + " USD");

        // Set On ClickListener
        holder.listItemDetails.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    ShowDetailsDialog(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void ShowDetailsDialog(int position) throws JSONException {

        LayoutInflater li = LayoutInflater.from(mContext);
        View dialogView = li.inflate(R.layout.details, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setTitle(R.string.flight_title);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(dialogView);

        ArrayList<Itineraries> itinerariesJsonData  = getItinerariesFromJson(mContext, mFlight);
         String outboundLegId = itinerariesJsonData.get(position).getOutboundLegId();
        String inboundLegId = itinerariesJsonData.get(position).getInboundLegId();

        String outSchedule = getSegments(outboundLegId);
        String inSchedule = getSegments(inboundLegId);
        String pricing = itinerariesJsonData.get(position).getPricingOptions().get(0).getPrice();


        final TextView outbound = (TextView) dialogView.findViewById(R.id.outboundFlight);
        outbound.setText(outSchedule);
        final TextView inbound = (TextView) dialogView.findViewById(R.id.inboundFlight);
        inbound.setText(inSchedule);
        final TextView price = (TextView) dialogView.findViewById(R.id.dialogPrice);
        price.setText("Price: " + pricing + " USD");


        // set dialog message
        alertDialogBuilder.setCancelable(true).setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                                int id) {

            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


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
        TextView listItemDetails;

        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemAirlineImage = (ImageView) itemView.findViewById(R.id.airlineImage);
            listItemRoute = (TextView) itemView.findViewById(R.id.route);
            listItemAirline = (TextView) itemView.findViewById(R.id.airline);
            listItemPrice = (TextView) itemView.findViewById(R.id.price);
            listItemBook = (TextView) itemView.findViewById(R.id.book);
            listItemDetails = (TextView) itemView.findViewById(R.id.details);
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

    public int getLegsData(String legId)  {

        int index = 0;

        ArrayList<Legs> legsJsonData  = null;
        try {
            legsJsonData = getLegsFromJson(mContext, mFlight);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Legs legs:legsJsonData) {

            if(legs.getId().equals(legId)) {
                index = legsJsonData.indexOf(legs);
            }

        }
        return index;

    }

    public String getPlacesData(String station)  {

        ArrayList<Places> placesData  = null;
        try {
            placesData = getPlacesFromJson(mContext, mFlight);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int index = 0;

        for (Places places:placesData) {

            if(places.getId().equals(station)) {
                index = placesData.indexOf(places);
            }

        }

        String place = placesData.get(index).getName();

        return place;
    }

    public String getStopsData(int index) {

        String route = "";

        ArrayList<Legs> legsJsonData  = null;
        ArrayList<Places> placesData  = null;

        try {
            legsJsonData = getLegsFromJson(mContext, mFlight);
            placesData = getPlacesFromJson(mContext, mFlight);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int stopIndex = legsJsonData.get(index).getStops().size();
        for (int i = 0; i < stopIndex; i++) {

            int stop =0;

            for (Places places : placesData) {

                if (places.getId().equals(String.valueOf(legsJsonData.get(index).getStops().get(i)))) {
                    stop = placesData.indexOf(places);
                }

            }
            String stopPlace = placesData.get(stop).getName();
            route = route + stopPlace + " - ";

        }

        return route;

    }

    public int getSegmentId(String id) {

        ArrayList<Segments> segmentsData  = null;
        try {
            segmentsData = getSegmentsFromJson(mContext, mFlight);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int index = 0;

        for (Segments seg:segmentsData) {

            if(seg.getId().equals(id)) {
                index = segmentsData.indexOf(seg);
            }

        }

        return index;


    }



    public String getSegments(String legId) {

        String schedule = "";

        ArrayList<Segments> segmentsData  = null;
        ArrayList<Legs> legsJsonData  = null;

        try {
            legsJsonData = getLegsFromJson(mContext, mFlight);
             segmentsData = getSegmentsFromJson(mContext, mFlight);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ArrayList<Integer> segments = legsJsonData.get(getLegsData(legId)).getSegmentIds();

        for(int i=0; i<segments.size(); i++) {

        int index = getSegmentId(String.valueOf(segments.get(i)));
        String departTime = segmentsData.get(index).getDepartureDateTime();
        String arrivalTime = segmentsData.get(index).getArrivalDateTime();
        String origin = segmentsData.get(index).getOriginStation();
        String dest = segmentsData.get(index).getDestinationStation();

        String originPlace = getPlacesData(origin);
        String destPlace = getPlacesData(dest);
        schedule = schedule + departTime + " - " + arrivalTime + "\n" + originPlace + " - " + destPlace + "\n";
        }

        return schedule;
    }




}
