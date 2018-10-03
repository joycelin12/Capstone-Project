package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 9/20/18.
 */

public class Itineraries implements Parcelable {

    String OutboundLegId;
    String InboundLegId;
    ArrayList<PricingOptions> PricingOptions;
    BookingDetailsLink BookingDetailsLink;


    public Itineraries(String outboundLegId, String inboundLegId, ArrayList<com.example.android.dreamdestinations.Model.PricingOptions> pricingOptions,
                       com.example.android.dreamdestinations.Model.BookingDetailsLink bookingDetailsLink) {
        OutboundLegId = outboundLegId;
        InboundLegId = inboundLegId;
        PricingOptions = pricingOptions;
        BookingDetailsLink = bookingDetailsLink;
    }

    public String getOutboundLegId() {
        return OutboundLegId;
    }

    public void setOutboundLegId(String outboundLegId) {
        OutboundLegId = outboundLegId;
    }

    public String getInboundLegId() {
        return InboundLegId;
    }

    public void setInboundLegId(String inboundLegId) {
        InboundLegId = inboundLegId;
    }

    public ArrayList<com.example.android.dreamdestinations.Model.PricingOptions> getPricingOptions() {
        return PricingOptions;
    }

    public void setPricingOptions(ArrayList<com.example.android.dreamdestinations.Model.PricingOptions> pricingOptions) {
        PricingOptions = pricingOptions;
    }

    public com.example.android.dreamdestinations.Model.BookingDetailsLink getBookingDetailsLink() {
        return BookingDetailsLink;
    }

    public void setBookingDetailsLink(com.example.android.dreamdestinations.Model.BookingDetailsLink bookingDetailsLink) {
        BookingDetailsLink = bookingDetailsLink;
    }

    public static Creator<Itineraries> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.OutboundLegId);
        dest.writeString(this.InboundLegId);
        dest.writeTypedList(this.PricingOptions);
        dest.writeParcelable(this.BookingDetailsLink, flags);
    }

    protected Itineraries(Parcel in) {
        this.OutboundLegId = in.readString();
        this.InboundLegId = in.readString();
        this.PricingOptions = in.createTypedArrayList(com.example.android.dreamdestinations.Model.PricingOptions.CREATOR);
        this.BookingDetailsLink = in.readParcelable(com.example.android.dreamdestinations.Model.BookingDetailsLink.class.getClassLoader());
    }

    public static final Creator<Itineraries> CREATOR = new Creator<Itineraries>() {
        @Override
        public Itineraries createFromParcel(Parcel source) {
            return new Itineraries(source);
        }

        @Override
        public Itineraries[] newArray(int size) {
            return new Itineraries[size];
        }
    };
}
