package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 10/6/18.
 */

public class Trip implements Parcelable {

     int id;
     String origin_id;
     String dest_id;
     String from_date;
     String to_date;
     String origin_name;
     String dest_name;
     String timestamp;

    public Trip() {
    }

    public Trip(int id, String origin_id, String dest_id, String from_date,
                String to_date, String origin_name, String dest_name, String timestamp) {
        this.id = id;
        this.origin_id = origin_id;
        this.dest_id = dest_id;
        this.from_date = from_date;
        this.to_date = to_date;
        this.origin_name = origin_name;
        this.dest_name = dest_name;
        this.timestamp = timestamp;
    }

    public Trip(String origin_id, String dest_id, String from_date,
                String to_date, String origin_name, String dest_name, String timestamp) {
        this.origin_id = origin_id;
        this.dest_id = dest_id;
        this.from_date = from_date;
        this.to_date = to_date;
        this.origin_name = origin_name;
        this.dest_name = dest_name;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = id;
    }

    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }

    public String getDest_id() {
        return dest_id;
    }

    public void setDest_id(String dest_id) {
        this.dest_id = dest_id;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }

    public String getDest_name() {
        return dest_name;
    }

    public void setDest_name(String dest_name) {
        this.dest_name = dest_name;
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
        dest.writeString(this.origin_id);
        dest.writeString(this.dest_id);
        dest.writeString(this.from_date);
        dest.writeString(this.to_date);
        dest.writeString(this.origin_name);
        dest.writeString(this.dest_name);
        dest.writeString(this.timestamp);
    }

    protected Trip(Parcel in) {
        this.id = in.readInt();
        this.origin_id = in.readString();
        this.dest_id = in.readString();
        this.from_date = in.readString();
        this.to_date = in.readString();
        this.origin_name = in.readString();
        this.dest_name = in.readString();
        this.timestamp = in.readString();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}
