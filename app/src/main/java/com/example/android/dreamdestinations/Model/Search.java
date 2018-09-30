package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 9/26/18.
 */

public class Search implements Parcelable {

    private ArrayList<Legs> legs;

    public Search(ArrayList<Legs> legs) {
        this.legs = legs;
    }

    public ArrayList<Legs> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Legs> legs) {
        this.legs = legs;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.legs);
    }

    protected Search(Parcel in) {
        this.legs = in.createTypedArrayList(Legs.CREATOR);
    }

    public static final Parcelable.Creator<Search> CREATOR = new Parcelable.Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel source) {
            return new Search(source);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}
