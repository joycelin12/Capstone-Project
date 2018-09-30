package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class Agents implements Parcelable {

    String Id;
    String Name;
    String ImageUrl;
    String Status;
    String OptimisedForMobile;
    String Type;

    public Agents(String id, String name, String imageUrl, String status, String optimisedForMobile, String type) {
        Id = id;
        Name = name;
        ImageUrl = imageUrl;
        Status = status;
        OptimisedForMobile = optimisedForMobile;
        Type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOptimisedForMobile() {
        return OptimisedForMobile;
    }

    public void setOptimisedForMobile(String optimisedForMobile) {
        OptimisedForMobile = optimisedForMobile;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.Name);
        dest.writeString(this.ImageUrl);
        dest.writeString(this.Status);
        dest.writeString(this.OptimisedForMobile);
        dest.writeString(this.Type);
    }

    protected Agents(Parcel in) {
        this.Id = in.readString();
        this.Name = in.readString();
        this.ImageUrl = in.readString();
        this.Status = in.readString();
        this.OptimisedForMobile = in.readString();
        this.Type = in.readString();
    }

    public static final Parcelable.Creator<Agents> CREATOR = new Parcelable.Creator<Agents>() {
        @Override
        public Agents createFromParcel(Parcel source) {
            return new Agents(source);
        }

        @Override
        public Agents[] newArray(int size) {
            return new Agents[size];
        }
    };
}
