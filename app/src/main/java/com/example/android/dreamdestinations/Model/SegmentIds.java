package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class SegmentIds implements Parcelable {

    @SerializedName("0")
    private String num_zero;

    @SerializedName("1")
    private String num_one;


    public SegmentIds(String num_zero, String num_one) {
        this.num_zero = num_zero;
        this.num_one = num_one;
    }

    public String getNum_zero() {
        return num_zero;
    }

    public void setNum_zero(String num_zero) {
        this.num_zero = num_zero;
    }

    public String getNum_one() {
        return num_one;
    }

    public void setNum_one(String num_one) {
        this.num_one = num_one;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.num_zero);
        dest.writeString(this.num_one);
    }

    protected SegmentIds(Parcel in) {
        this.num_zero = in.readString();
        this.num_one = in.readString();
    }

    public static final Creator<SegmentIds> CREATOR = new Creator<SegmentIds>() {
        @Override
        public SegmentIds createFromParcel(Parcel source) {
            return new SegmentIds(source);
        }

        @Override
        public SegmentIds[] newArray(int size) {
            return new SegmentIds[size];
        }
    };
}
