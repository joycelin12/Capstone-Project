package com.example.android.dreamdestinations.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joycelin12 on 9/21/18.
 */

public class Currencies implements Parcelable {

    String Code;
    String Symbol;
    String ThousandsSeparator;
    String DecimalSeparator;
    Boolean SymbolOnLeft;
    Boolean SpaceBetweenAmountAndSymbol;
    String RoundingCoefficient;
    String DecimalDigits;

    public Currencies(String code, String symbol, String thousandsSeparator, String decimalSeparator, Boolean symbolOnLeft,
                      Boolean spaceBetweenAmountAndSymbol, String roundingCoefficient, String decimalDigits) {
        Code = code;
        Symbol = symbol;
        ThousandsSeparator = thousandsSeparator;
        DecimalSeparator = decimalSeparator;
        SymbolOnLeft = symbolOnLeft;
        SpaceBetweenAmountAndSymbol = spaceBetweenAmountAndSymbol;
        RoundingCoefficient = roundingCoefficient;
        DecimalDigits = decimalDigits;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getThousandsSeparator() {
        return ThousandsSeparator;
    }

    public void setThousandsSeparator(String thousandsSeparator) {
        ThousandsSeparator = thousandsSeparator;
    }

    public String getDecimalSeparator() {
        return DecimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        DecimalSeparator = decimalSeparator;
    }

    public Boolean getSymbolOnLeft() {
        return SymbolOnLeft;
    }

    public void setSymbolOnLeft(Boolean symbolOnLeft) {
        SymbolOnLeft = symbolOnLeft;
    }

    public Boolean getSpaceBetweenAmountAndSymbol() {
        return SpaceBetweenAmountAndSymbol;
    }

    public void setSpaceBetweenAmountAndSymbol(Boolean spaceBetweenAmountAndSymbol) {
        SpaceBetweenAmountAndSymbol = spaceBetweenAmountAndSymbol;
    }

    public String getRoundingCoefficient() {
        return RoundingCoefficient;
    }

    public void setRoundingCoefficient(String roundingCoefficient) {
        RoundingCoefficient = roundingCoefficient;
    }

    public String getDecimalDigits() {
        return DecimalDigits;
    }

    public void setDecimalDigits(String decimalDigits) {
        DecimalDigits = decimalDigits;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Code);
        dest.writeString(this.Symbol);
        dest.writeString(this.ThousandsSeparator);
        dest.writeString(this.DecimalSeparator);
        dest.writeValue(this.SymbolOnLeft);
        dest.writeValue(this.SpaceBetweenAmountAndSymbol);
        dest.writeString(this.RoundingCoefficient);
        dest.writeString(this.DecimalDigits);
    }

    protected Currencies(Parcel in) {
        this.Code = in.readString();
        this.Symbol = in.readString();
        this.ThousandsSeparator = in.readString();
        this.DecimalSeparator = in.readString();
        this.SymbolOnLeft = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.SpaceBetweenAmountAndSymbol = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.RoundingCoefficient = in.readString();
        this.DecimalDigits = in.readString();
    }

    public static final Parcelable.Creator<Currencies> CREATOR = new Parcelable.Creator<Currencies>() {
        @Override
        public Currencies createFromParcel(Parcel source) {
            return new Currencies(source);
        }

        @Override
        public Currencies[] newArray(int size) {
            return new Currencies[size];
        }
    };
}
