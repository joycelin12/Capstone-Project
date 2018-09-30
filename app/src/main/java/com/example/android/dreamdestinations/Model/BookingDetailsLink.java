package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class BookingDetailsLink implements Parcelable {

    String Uri;
    String Body;
    String Method;

    public BookingDetailsLink(String uri, String body, String method) {
        Uri = uri;
        Body = body;
        Method = method;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Uri);
        dest.writeString(this.Body);
        dest.writeString(this.Method);
    }

    protected BookingDetailsLink(Parcel in) {
        this.Uri = in.readString();
        this.Body = in.readString();
        this.Method = in.readString();
    }

    public static final Parcelable.Creator<BookingDetailsLink> CREATOR = new Parcelable.Creator<BookingDetailsLink>() {
        @Override
        public BookingDetailsLink createFromParcel(Parcel source) {
            return new BookingDetailsLink(source);
        }

        @Override
        public BookingDetailsLink[] newArray(int size) {
            return new BookingDetailsLink[size];
        }
    };
}
