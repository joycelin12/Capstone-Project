package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class PricingOptions implements Parcelable{

    ArrayList<Integer> Agents;
    String QuoteAgeInMinutes;
    String Price;
    String DeeplinkUrl;

    public PricingOptions(ArrayList<Integer> agents, String quoteAgeInMinutes, String price, String deeplinkUrl) {
        Agents = agents;
        QuoteAgeInMinutes = quoteAgeInMinutes;
        Price = price;
        DeeplinkUrl = deeplinkUrl;
    }

    public ArrayList<Integer> getAgents() {
        return Agents;
    }

    public void setAgents(ArrayList<Integer> agents) {
        Agents = agents;
    }

    public String getQuoteAgeInMinutes() {
        return QuoteAgeInMinutes;
    }

    public void setQuoteAgeInMinutes(String quoteAgeInMinutes) {
        QuoteAgeInMinutes = quoteAgeInMinutes;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDeeplinkUrl() {
        return DeeplinkUrl;
    }

    public void setDeeplinkUrl(String deeplinkUrl) {
        DeeplinkUrl = deeplinkUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.Agents);
        dest.writeString(this.QuoteAgeInMinutes);
        dest.writeString(this.Price);
        dest.writeString(this.DeeplinkUrl);
    }

    protected PricingOptions(Parcel in) {
        this.Agents = new ArrayList<Integer>();
        in.readList(this.Agents, Integer.class.getClassLoader());
        this.QuoteAgeInMinutes = in.readString();
        this.Price = in.readString();
        this.DeeplinkUrl = in.readString();
    }

    public static final Creator<PricingOptions> CREATOR = new Creator<PricingOptions>() {
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
