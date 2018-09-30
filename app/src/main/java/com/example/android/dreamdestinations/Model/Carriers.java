package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class Carriers implements Parcelable {

    String Id;
    String Code;
    String Name;
    String ImageUrl;
    String DisplayCode;

    public Carriers(String id, String code, String name, String imageUrl, String displayCode) {
        Id = id;
        Code = code;
        Name = name;
        ImageUrl = imageUrl;
        DisplayCode = displayCode;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDisplayCode() {
        return DisplayCode;
    }

    public void setDisplayCode(String displayCode) {
        DisplayCode = displayCode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.Code);
        dest.writeString(this.Name);
        dest.writeString(this.ImageUrl);
        dest.writeString(this.DisplayCode);
    }

    protected Carriers(Parcel in) {
        this.Id = in.readString();
        this.Code = in.readString();
        this.Name = in.readString();
        this.ImageUrl = in.readString();
        this.DisplayCode = in.readString();
    }

    public static final Parcelable.Creator<Carriers> CREATOR = new Parcelable.Creator<Carriers>() {
        @Override
        public Carriers createFromParcel(Parcel source) {
            return new Carriers(source);
        }

        @Override
        public Carriers[] newArray(int size) {
            return new Carriers[size];
        }
    };
}
