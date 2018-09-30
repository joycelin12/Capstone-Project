package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class OperatingCarriers implements Parcelable {

    @SerializedName("0")
    private String OperatingCarriers;

    public OperatingCarriers(String operatingCarriers) {
        OperatingCarriers = operatingCarriers;
    }

    public String getOperatingCarriers() {
        return OperatingCarriers;
    }

    public void setOperatingCarriers(String operatingCarriers) {
        OperatingCarriers = operatingCarriers;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.OperatingCarriers);
    }

    protected OperatingCarriers(Parcel in) {
        this.OperatingCarriers = in.readString();
    }

    public static final Creator<OperatingCarriers> CREATOR = new Creator<OperatingCarriers>() {
        @Override
        public OperatingCarriers createFromParcel(Parcel source) {
            return new OperatingCarriers(source);
        }

        @Override
        public OperatingCarriers[] newArray(int size) {
            return new OperatingCarriers[size];
        }
    };
}
