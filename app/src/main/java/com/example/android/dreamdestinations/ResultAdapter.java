package com.example.android.dreamdestinations;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.dreamdestinations.Model.Itineraries;
import com.example.android.dreamdestinations.Model.PricingOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.android.dreamdestinations.Utilities.PredictionJsonUtils.getItinerariesFromJson;


/**
 * Created by joycelin12 on 9/27/18.
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

        try {
            itinerariesJsonData  = getItinerariesFromJson(holder.listItemRoute.getContext(), mFlight);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("postition ", String.valueOf(position));

        String name = itinerariesJsonData.get(position).getOutboundLegId();
        Log.e("name", name);
        //String name = "testing";
        holder.listItemRoute.setText("name " +name);

        String bookUrl = itinerariesJsonData.get(position).getBookingDetailsLink().get(0).getUri();
        holder.listItemPrice.setText("book " + bookUrl);
        String price = null;
        ArrayList<PricingOptions> pricing = itinerariesJsonData.get(position).getPricingOptions();
       // Log.e("pricing size", String.valueOf(pricing.size()));
        /*if(pricing.get(0).getPrice() != null) {
            Log.e("pricing", pricing.get(0).getPrice());
        }*/
        if (itinerariesJsonData.get(position).getPricingOptions() != null) {
            price = itinerariesJsonData.get(position).getPricingOptions().get(0).getPrice();
        }


        holder.listItemPrice.setText("Price " +price);
        Log.e("price", String.valueOf(price));




        Context context = holder.listItemAirlineImage.getContext();
        /*Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(holder.listItemAirlineImage);*/

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
