package com.example.android.dreamdestinations;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.dreamdestinations.Model.Trip;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 10/7/18.
 */

class TripAdapter extends RecyclerView.Adapter<TripAdapter.NumberViewHolder>  {

    private int mNumberItems;
    private ItemClickListener mClickListener;
    private Context mContext;
    private ArrayList<Trip> mTrip;

    //create a constructor that accepts int as a parameter for number of items and store in the variable
    public TripAdapter(int numberOfItems, ArrayList<Trip> trip, Context context){
        mNumberItems = numberOfItems;
        this.mTrip = trip;
        this.mContext = context;

    }

    @Override
    public TripAdapter.NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForItem = R.layout.trip_list_item;
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForItem, viewGroup, shouldAttachToParentImmediately);
        TripAdapter.NumberViewHolder viewHolder = new TripAdapter.NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TripAdapter.NumberViewHolder holder, int position) {

         if (position > 0) {
            holder.listItemOriginHeader.setVisibility(View.GONE);
            holder.listItemDestHeader.setVisibility(View.GONE);
            holder.listItemFromHeader.setVisibility(View.GONE);
            holder.listItemToHeader.setVisibility(View.GONE);

        }
        holder.listItemOriginName.setText(mTrip.get(position).getOrigin_name());
        holder.listItemDestName.setText(mTrip.get(position).getDest_name());
        holder.listItemFromDate.setText(mTrip.get(position).getFrom_date());
        holder.listItemToDate.setText(mTrip.get(position).getTo_date());

    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView listItemOriginName;
        TextView listItemDestName;
        TextView listItemFromDate;
        TextView listItemToDate;
        TextView listItemOriginHeader;
        TextView listItemDestHeader;
        TextView listItemFromHeader;
        TextView listItemToHeader;

        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemOriginName = (TextView) itemView.findViewById(R.id.origin_name);
            listItemDestName = (TextView) itemView.findViewById(R.id.dest_name);
            listItemFromDate = (TextView) itemView.findViewById(R.id.from_date);
            listItemToDate = (TextView) itemView.findViewById(R.id.to_date);
            listItemOriginHeader = (TextView) itemView.findViewById(R.id.origin_name_header);
            listItemDestHeader = (TextView) itemView.findViewById(R.id.dest_name_header);
            listItemFromHeader = (TextView) itemView.findViewById(R.id.from_header);
            listItemToHeader = (TextView) itemView.findViewById(R.id.to_header);

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
