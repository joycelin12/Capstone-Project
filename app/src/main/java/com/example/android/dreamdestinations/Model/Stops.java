package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class Stops implements Parcelable {

    @SerializedName("0")
    private String Stops;

    public Stops(String stops) {
        Stops = stops;
    }

    public String getStops() {
        return Stops;
    }

    public void setStops(String stops) {
        Stops = stops;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Stops);
    }

    protected Stops(Parcel in) {
        this.Stops = in.readString();
    }

    public static final Creator<Stops> CREATOR = new Creator<Stops>() {
        @Override
        public Stops createFromParcel(Parcel source) {
            return new Stops(source);
        }

        @Override
        public Stops[] newArray(int size) {
            return new Stops[size];
        }
    };
}


