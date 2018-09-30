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
    ArrayList<PricingOptions> pricingOptions;
    ArrayList<BookingDetailsLink> bookingDetailsLink;

    public String getOutboundLegId() {
        return OutboundLegId;
    }

    public void setOutboundLegId(String OutboundLegId) {
        this.OutboundLegId = OutboundLegId;
    }

    public String getInboundLegId() {
        return InboundLegId;
    }

    public void setInboundLegId(String InboundLegId) {
        this.InboundLegId = InboundLegId;
    }

    public ArrayList<PricingOptions> getPricingOptions() {
        return pricingOptions;
    }

    public void setPricingOptions(ArrayList<PricingOptions> pricingOptions) {
        this.pricingOptions = pricingOptions;
    }

    public ArrayList<BookingDetailsLink> getBookingDetailsLink() {
        return bookingDetailsLink;
    }

    public void setBookingDetailsLink(ArrayList<BookingDetailsLink> bookingDetailsLink) {
        this.bookingDetailsLink = bookingDetailsLink;
    }


    public Itineraries(String OutboundLegId, String InboundLegId ,ArrayList<PricingOptions> pricingOptions,
                  ArrayList<BookingDetailsLink> bookingDetailsLink ) {

        this.OutboundLegId = OutboundLegId;
        this.InboundLegId = InboundLegId;
        this.pricingOptions = pricingOptions;
        this.bookingDetailsLink = bookingDetailsLink;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.OutboundLegId);
        dest.writeString(this.InboundLegId);
        dest.writeList(this.pricingOptions);
        dest.writeList(this.bookingDetailsLink);
    }

    protected Itineraries(Parcel in) {
        this.OutboundLegId = in.readString();
        this.InboundLegId = in.readString();
        this.pricingOptions = new ArrayList<PricingOptions>();
        in.readList(this.pricingOptions, PricingOptions.class.getClassLoader());
        this.bookingDetailsLink = new ArrayList<BookingDetailsLink>();
        in.readList(this.bookingDetailsLink, BookingDetailsLink.class.getClassLoader());
    }

    public static final Parcelable.Creator<Itineraries> CREATOR = new Parcelable.Creator<Itineraries>() {
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
