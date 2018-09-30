package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class Places implements Parcelable {

    String Id;
    String ParentId;
    String Code;
    String Type;
    String Name;

    public Places(String id, String parentId, String code, String type, String name) {
        Id = id;
        ParentId = parentId;
        Code = code;
        Type = type;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.ParentId);
        dest.writeString(this.Code);
        dest.writeString(this.Type);
        dest.writeString(this.Name);
    }

    protected Places(Parcel in) {
        this.Id = in.readString();
        this.ParentId = in.readString();
        this.Code = in.readString();
        this.Type = in.readString();
        this.Name = in.readString();
    }

    public static final Parcelable.Creator<Places> CREATOR = new Parcelable.Creator<Places>() {
        @Override
        public Places createFromParcel(Parcel source) {
            return new Places(source);
        }

        @Override
        public Places[] newArray(int size) {
            return new Places[size];
        }
    };
}
