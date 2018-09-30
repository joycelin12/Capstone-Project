package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class Legs implements Parcelable {

    String Id;
   // Map<Integer, String> SegmentIds;
    ArrayList<Integer> SegmentIds;
    String OriginStation;
    String DestinationStation;
    String Departure;
    String Arrival;
    String Duration;
    String JourneyMode;
    // Map<Integer, String> Stops;
    // Map<Integer, String> Carriers;
    //Map<Integer, String> OperatingCarriers;
    ArrayList<Integer> Stops;
    ArrayList<Integer> Carriers;
    ArrayList<Integer> OperatingCarriers;
    String Directionality;
    ArrayList<FlightNumbers> FlightNumbers;


    public Legs(String id, ArrayList<Integer> segmentIds, String originStation, String destinationStation, String departure, String arrival, String duration, String journeyMode, ArrayList<Integer> stops, ArrayList<Integer> carriers, ArrayList<Integer> operatingCarriers, String directionality, ArrayList<com.example.android.dreamdestinations.Model.FlightNumbers> flightNumbers) {
        Id = id;
        SegmentIds = segmentIds;
        OriginStation = originStation;
        DestinationStation = destinationStation;
        Departure = departure;
        Arrival = arrival;
        Duration = duration;
        JourneyMode = journeyMode;
        Stops = stops;
        Carriers = carriers;
        OperatingCarriers = operatingCarriers;
        Directionality = directionality;
        FlightNumbers = flightNumbers;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public ArrayList<Integer> getSegmentIds() {
        return SegmentIds;
    }

    public void setSegmentIds(ArrayList<Integer> segmentIds) {
        SegmentIds = segmentIds;
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

    public String getDeparture() {
        return Departure;
    }

    public void setDeparture(String departure) {
        Departure = departure;
    }

    public String getArrival() {
        return Arrival;
    }

    public void setArrival(String arrival) {
        Arrival = arrival;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getJourneyMode() {
        return JourneyMode;
    }

    public void setJourneyMode(String journeyMode) {
        JourneyMode = journeyMode;
    }

    public ArrayList<Integer> getStops() {
        return Stops;
    }

    public void setStops(ArrayList<Integer> stops) {
        Stops = stops;
    }

    public ArrayList<Integer> getCarriers() {
        return Carriers;
    }

    public void setCarriers(ArrayList<Integer> carriers) {
        Carriers = carriers;
    }

    public ArrayList<Integer> getOperatingCarriers() {
        return OperatingCarriers;
    }

    public void setOperatingCarriers(ArrayList<Integer> operatingCarriers) {
        OperatingCarriers = operatingCarriers;
    }

    public String getDirectionality() {
        return Directionality;
    }

    public void setDirectionality(String directionality) {
        Directionality = directionality;
    }

    public ArrayList<com.example.android.dreamdestinations.Model.FlightNumbers> getFlightNumbers() {
        return FlightNumbers;
    }

    public void setFlightNumbers(ArrayList<com.example.android.dreamdestinations.Model.FlightNumbers> flightNumbers) {
        FlightNumbers = flightNumbers;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeList(this.SegmentIds);
        dest.writeString(this.OriginStation);
        dest.writeString(this.DestinationStation);
        dest.writeString(this.Departure);
        dest.writeString(this.Arrival);
        dest.writeString(this.Duration);
        dest.writeString(this.JourneyMode);
        dest.writeList(this.Stops);
        dest.writeList(this.Carriers);
        dest.writeList(this.OperatingCarriers);
        dest.writeString(this.Directionality);
        dest.writeTypedList(this.FlightNumbers);
    }

    protected Legs(Parcel in) {
        this.Id = in.readString();
        this.SegmentIds = new ArrayList<Integer>();
        in.readList(this.SegmentIds, Integer.class.getClassLoader());
        this.OriginStation = in.readString();
        this.DestinationStation = in.readString();
        this.Departure = in.readString();
        this.Arrival = in.readString();
        this.Duration = in.readString();
        this.JourneyMode = in.readString();
        this.Stops = new ArrayList<Integer>();
        in.readList(this.Stops, Integer.class.getClassLoader());
        this.Carriers = new ArrayList<Integer>();
        in.readList(this.Carriers, Integer.class.getClassLoader());
        this.OperatingCarriers = new ArrayList<Integer>();
        in.readList(this.OperatingCarriers, Integer.class.getClassLoader());
        this.Directionality = in.readString();
        this.FlightNumbers = in.createTypedArrayList(com.example.android.dreamdestinations.Model.FlightNumbers.CREATOR);
    }

    public static final Creator<Legs> CREATOR = new Creator<Legs>() {
        @Override
        public Legs createFromParcel(Parcel source) {
            return new Legs(source);
        }

        @Override
        public Legs[] newArray(int size) {
            return new Legs[size];
        }
    };
}
