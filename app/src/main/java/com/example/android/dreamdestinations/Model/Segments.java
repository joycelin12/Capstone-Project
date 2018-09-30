package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class Segments implements Parcelable {

    String Id;
    String OriginStation;
    String DestinationStation;
    String DepartureDateTime;
    String ArrivalDateTime;
    String Carrier;
    String OperatingCarrier;
    String Duration;
    String FlightNumber;
    String JourneyMode;
    String Directionality;

    public Segments(String id, String originStation, String destinationStation,
                    String departureDateTime, String arrivalDateTime, String carrier,
                    String operatingCarrier, String duration, String flightNumber,
                    String journeyMode, String directionality) {
        Id = id;
        OriginStation = originStation;
        DestinationStation = destinationStation;
        DepartureDateTime = departureDateTime;
        ArrivalDateTime = arrivalDateTime;
        Carrier = carrier;
        OperatingCarrier = operatingCarrier;
        Duration = duration;
        FlightNumber = flightNumber;
        JourneyMode = journeyMode;
        Directionality = directionality;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOriginStation() {
        return OriginStation;
    }

    public void setOriginStation(String originStation) {
        OriginStation = originStation;
    }

    public String getDestinationStation() {
        return DestinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        DestinationStation = destinationStation;
    }

    public String getDepartureDateTime() {
        return DepartureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        DepartureDateTime = departureDateTime;
    }

    public String getArrivalDateTime() {
        return ArrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        ArrivalDateTime = arrivalDateTime;
    }

    public String getCarrier() {
        return Carrier;
    }

    public void setCarrier(String carrier) {
        Carrier = carrier;
    }

    public String getOperatingCarrier() {
        return OperatingCarrier;
    }

    public void setOperatingCarrier(String operatingCarrier) {
        OperatingCarrier = operatingCarrier;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getFlightNumber() {
        return FlightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        FlightNumber = flightNumber;
    }

    public String getJourneyMode() {
        return JourneyMode;
    }

    public void setJourneyMode(String journeyMode) {
        JourneyMode = journeyMode;
    }

    public String getDirectionality() {
        return Directionality;
    }

    public void setDirectionality(String directionality) {
        Directionality = directionality;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.OriginStation);
        dest.writeString(this.DestinationStation);
        dest.writeString(this.DepartureDateTime);
        dest.writeString(this.ArrivalDateTime);
        dest.writeString(this.Carrier);
        dest.writeString(this.OperatingCarrier);
        dest.writeString(this.Duration);
        dest.writeString(this.FlightNumber);
        dest.writeString(this.JourneyMode);
        dest.writeString(this.Directionality);
    }

    protected Segments(Parcel in) {
        this.Id = in.readString();
        this.OriginStation = in.readString();
        this.DestinationStation = in.readString();
        this.DepartureDateTime = in.readString();
        this.ArrivalDateTime = in.readString();
        this.Carrier = in.readString();
        this.OperatingCarrier = in.readString();
        this.Duration = in.readString();
        this.FlightNumber = in.readString();
        this.JourneyMode = in.readString();
        this.Directionality = in.readString();
    }

    public static final Parcelable.Creator<Segments> CREATOR = new Parcelable.Creator<Segments>() {
        @Override
        public Segments createFromParcel(Parcel source) {
            return new Segments(source);
        }

        @Override
        public Segments[] newArray(int size) {
            return new Segments[size];
        }
    };
}
