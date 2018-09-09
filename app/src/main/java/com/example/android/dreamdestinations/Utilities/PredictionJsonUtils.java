package com.example.android.dreamdestinations.Utilities;

import android.content.Context;
import android.gesture.Prediction;

import com.example.android.dreamdestinations.Model.Predictions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 9/2/18.
 */

public class PredictionJsonUtils {


    public static ArrayList<Predictions> getPredictionsFromJson(Context context, String PredictionJsonStr)
            throws JSONException {

        Gson gson = new GsonBuilder().create();

        ArrayList<Predictions> parsedPredictionsData = gson.fromJson(PredictionJsonStr, new TypeToken<ArrayList<Predictions>>() {
        }.getType());


        return parsedPredictionsData;
    }

}
