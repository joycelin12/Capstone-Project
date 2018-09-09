package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 9/2/18.
 */

public class Predictions implements Parcelable {

    String code;
    String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Predictions(String code, String name) {

        this.code = code;
        this.name = name;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);

    }

    protected Predictions(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        
    }

    public static final Creator<Predictions> CREATOR = new Creator<Predictions>() {
        @Override
        public Predictions createFromParcel(Parcel source) {
            return new Predictions(source);
        }

        @Override
        public Predictions[] newArray(int size) {
            return new Predictions[size];
        }
    };
}
