package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 10/6/18.
 */

public class Trip_Price implements Parcelable {

    int id;
    int tripId;
    String price;
    String timestamp;

    public Trip_Price(int id, int tripId, String price, String timestamp) {
        this.id = id;
        this.tripId = tripId;
        this.price = price;
        this.timestamp = timestamp;
    }

    public Trip_Price(int tripId, String price, String timestamp) {
        this.tripId = tripId;
        this.price = price;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.tripId);
        dest.writeString(this.price);
        dest.writeString(this.timestamp);
    }

    protected Trip_Price(Parcel in) {
        this.id = in.readInt();
        this.tripId = in.readInt();
        this.price = in.readString();
        this.timestamp = in.readString();
    }

    public static final Creator<Trip_Price> CREATOR = new Creator<Trip_Price>() {
        @Override
        public Trip_Price createFromParcel(Parcel source) {
            return new Trip_Price(source);
        }

        @Override
        public Trip_Price[] newArray(int size) {
            return new Trip_Price[size];
        }
    };
}
