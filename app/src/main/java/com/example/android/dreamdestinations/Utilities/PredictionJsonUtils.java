package com.example.android.dreamdestinations.Utilities;

import android.content.Context;

import com.example.android.dreamdestinations.Model.Agents;
import com.example.android.dreamdestinations.Model.Carriers;
import com.example.android.dreamdestinations.Model.Currencies;
import com.example.android.dreamdestinations.Model.Itineraries;
import com.example.android.dreamdestinations.Model.Legs;
import com.example.android.dreamdestinations.Model.Places;
import com.example.android.dreamdestinations.Model.Predictions;
import com.example.android.dreamdestinations.Model.Segments;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 9/2/18.
 */

public class PredictionJsonUtils {

    private static final String M_ITINERARY = "Itineraries";
    private static final String M_LEGS = "Legs";
    private static final String M_SEGMENTS = "Segments";
    private static final String M_CARRIERS = "Carriers";
    private static final String M_AGENTS = "Agents";
    private static final String M_PLACES = "Places";
    private static final String M_CURRENCIES = "Currencies";
    private static final String M_STATUS = "Status";


    public static ArrayList<Predictions> getPredictionsFromJson(Context context, String PredictionJsonStr)
            throws JSONException {

        Gson gson = new GsonBuilder().create();

        ArrayList<Predictions> parsedPredictionsData = gson.fromJson(PredictionJsonStr, new TypeToken<ArrayList<Predictions>>() {
        }.getType());


        return parsedPredictionsData;
    }

    public static ArrayList<Itineraries> getItinerariesFromJson(Context context, String SearchJsonStr)
            throws JSONException {

        JSONObject searchJson = new JSONObject(SearchJsonStr);

        JSONArray searchArray = searchJson.getJSONArray(M_ITINERARY);


        Gson gson = new GsonBuilder().create();

        ArrayList<Itineraries> parsedSearchData = gson.fromJson(String.valueOf(searchArray), new TypeToken<ArrayList<Itineraries>>() {
        }.getType());


        return parsedSearchData;
    }

    public static ArrayList<Legs> getLegsFromJson(Context context, String SearchJsonStr)
            throws JSONException {

        JSONObject searchJson = new JSONObject(SearchJsonStr);

        JSONArray searchArray = searchJson.getJSONArray(M_LEGS);

        Gson gson = new GsonBuilder().create();

        ArrayList<Legs> parsedSearchData = gson.fromJson(String.valueOf(searchArray), new TypeToken<ArrayList<Legs>>() {
        }.getType());

        return parsedSearchData;
    }

    public static ArrayList<Segments> getSegmentsFromJson(Context context, String SearchJsonStr)
            throws JSONException {

        JSONObject searchJson = new JSONObject(SearchJsonStr);

        JSONArray searchArray = searchJson.getJSONArray(M_SEGMENTS);


        Gson gson = new GsonBuilder().create();

        ArrayList<Segments> parsedSearchData = gson.fromJson(String.valueOf(searchArray), new TypeToken<ArrayList<Segments>>() {
        }.getType());


        return parsedSearchData;
    }

    public static ArrayList<Carriers> getCarriersFromJson(Context context, String SearchJsonStr)
            throws JSONException {

        JSONObject searchJson = new JSONObject(SearchJsonStr);

        JSONArray searchArray = searchJson.getJSONArray(M_CARRIERS);


        Gson gson = new GsonBuilder().create();

        ArrayList<Carriers> parsedSearchData = gson.fromJson(String.valueOf(searchArray), new TypeToken<ArrayList<Carriers>>() {
        }.getType());


        return parsedSearchData;
    }

    public static ArrayList<Agents> getAgentsFromJson(Context context, String SearchJsonStr)
            throws JSONException {

        JSONObject searchJson = new JSONObject(SearchJsonStr);

        JSONArray searchArray = searchJson.getJSONArray(M_AGENTS);


        Gson gson = new GsonBuilder().create();

        ArrayList<Agents> parsedSearchData = gson.fromJson(String.valueOf(searchArray), new TypeToken<ArrayList<Agents>>() {
        }.getType());


        return parsedSearchData;
    }

    public static ArrayList<Places> getPlacesFromJson(Context context, String SearchJsonStr)
            throws JSONException {

        JSONObject searchJson = new JSONObject(SearchJsonStr);

        JSONArray searchArray = searchJson.getJSONArray(M_PLACES);


        Gson gson = new GsonBuilder().create();

        ArrayList<Places> parsedSearchData = gson.fromJson(String.valueOf(searchArray), new TypeToken<ArrayList<Places>>() {
        }.getType());


        return parsedSearchData;
    }

    public static ArrayList<Currencies> getCurrenciesFromJson(Context context, String SearchJsonStr)
            throws JSONException {

        JSONObject searchJson = new JSONObject(SearchJsonStr);

        JSONArray searchArray = searchJson.getJSONArray(M_CURRENCIES);


        Gson gson = new GsonBuilder().create();

        ArrayList<Currencies> parsedSearchData = gson.fromJson(String.valueOf(searchArray), new TypeToken<ArrayList<Currencies>>() {
        }.getType());


        return parsedSearchData;
    }

    public static String getStatusFromJson(Context context, String SearchJsonStr)
            throws JSONException {

        JSONObject searchJson = new JSONObject(SearchJsonStr);
        String parsedSearchData = searchJson.getString(M_STATUS);

        return parsedSearchData;
    }


}
