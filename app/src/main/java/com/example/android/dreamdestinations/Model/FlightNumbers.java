package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class FlightNumbers implements Parcelable {

    String FlightNumber;
    String CarrierId;

    public FlightNumbers(String flightNumber, String carrierId) {
        FlightNumber = flightNumber;
        CarrierId = carrierId;
    }

    public String getFlightNumber() {
        return FlightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        FlightNumber = flightNumber;
    }

    public String getCarrierId() {
        return CarrierId;
    }

    public void setCarrierId(String carrierId) {
        CarrierId = carrierId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.FlightNumber);
        dest.writeString(this.CarrierId);
    }

    protected FlightNumbers(Parcel in) {
        this.FlightNumber = in.readString();
        this.CarrierId = in.readString();
    }

    public static final Parcelable.Creator<FlightNumbers> CREATOR = new Parcelable.Creator<FlightNumbers>() {
        @Override
        public FlightNumbers createFromParcel(Parcel source) {
            return new FlightNumbers(source);
        }

        @Override
        public FlightNumbers[] newArray(int size) {
            return new FlightNumbers[size];
        }
    };
}
