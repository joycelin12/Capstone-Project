package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class PricingOptions implements Parcelable{

    ArrayList<Agents> agents;
    String quoteAgeInMinutes;
    String price;
    String deeplinkUrl;

    public PricingOptions(ArrayList<Agents> agents, String quoteAgeInMinutes, String price, String deeplinkUrl) {
        this.agents = agents;
        this.quoteAgeInMinutes = quoteAgeInMinutes;
        this.price = price;
        this.deeplinkUrl = deeplinkUrl;
    }


    public ArrayList<Agents> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agents> agents) {
        this.agents = agents;
    }

    public String getQuoteAgeInMinutes() {
        return quoteAgeInMinutes;
    }

    public void setQuoteAgeInMinutes(String quoteAgeInMinutes) {
        this.quoteAgeInMinutes = quoteAgeInMinutes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeeplinkUrl() {
        return deeplinkUrl;
    }

    public void setDeeplinkUrl(String deeplinkUrl) {
        this.deeplinkUrl = deeplinkUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        this.agents = agents;
        this.quoteAgeInMinutes = quoteAgeInMinutes;
        this.price = price;
        this.deeplinkUrl = deeplinkUrl;
        dest.writeList(this.agents);
        dest.writeString(this.quoteAgeInMinutes);
        dest.writeString(this.price);
        dest.writeString(this.deeplinkUrl);
    }

    protected PricingOptions(Parcel in) {
       this.agents = new ArrayList<Agents>();
        in.readList(this.agents, Agents.class.getClassLoader());
        this.quoteAgeInMinutes = in.readString();
        this.price = in.readString();
        this.deeplinkUrl = in.readString();



    }

    public static final Parcelable.Creator<PricingOptions> CREATOR = new Parcelable.Creator<PricingOptions>() {
        @Override
        public PricingOptions createFromParcel(Parcel source) {
            return new PricingOptions(source);
        }

        @Override
        public PricingOptions[] newArray(int size) {
            return new PricingOptions[size];
        }
    };

}
