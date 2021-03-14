package com.example.android.dreamdestinations.Utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.dreamdestinations.BuildConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by joycelin12 on 8/30/18.
 */

public class NetworkUtils {

    final static String PARAM_API_KEY = "key";
    final static String PARAM_LAT_KEY = "lat";
    final static String PARAM_LNG_KEY ="lng";
    final static String PARAM_DIST_KEY = "distance";
    final static String DIST ="150";
    
   /* final static String PARAM_COUNTRY_KEY = "country";
    final static String PARAM_CURRENCY_KEY = "currency";
    final static String PARAM_LOCALE_KEY = "locale";
    final static String PARAM_ORIGIN_KEY = "originPlace";
    final static String PARAM_DEST_KEY ="destinationPlace";
    final static String PARAM_OUT_KEY ="outboundDate";
    final static String PARAM_IN_KEY ="inboundDate"; */
    final static String PARAM_COUNTRY_KEY = "";
    final static String PARAM_CURRENCY_KEY = "";
    final static String PARAM_LOCALE_KEY = "";
    final static String PARAM_ORIGIN_KEY = "";
    final static String PARAM_DEST_KEY ="";
    final static String PARAM_OUT_KEY ="";
    final static String PARAM_IN_KEY ="";
    final static String PARAM_CABIN_KEY = "cabinClass";
    final static String PARAM_ADULT_KEY = "adults";
    final static String PARAM_CHILD_KEY = "children";
    final static String PARAM_INFANTS_KEY = "infants";
    final static String PARAM_INC_KEY = "includeCarriers";
    final static String PARAM_EXC_KEY = "excludeCarriers";
    final static String PARAM_CONTENT_KEY = "Content-Type";
    final static String PARAM_SORTTYPE_KEY = "sortType";
    final static String PARAM_SORTORDER_KEY = "sortOrder";
    final static String CONTENT = "application/x-www-form-urlencoded";
    final static String PARAM_X_KEY = "X-RapidAPI-Key";
    final static String PARAM_HOST_KEY = "X-RapidAPI-Host";
    final static String PARAM_SHORT_API_KEY = "shortapikey";
    final static String PARAM_S_API_KEY = "apiKey";
    private static final String X_KEY = BuildConfig.X_RapidAPI_Key;
    private static final String X_HOST = BuildConfig.X_RapidAPI_Host;
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String SHORT_API_KEY = BuildConfig.SHORT_API_KEY;

    private final static String PLACE_BASE_URL = "http://aviation-edge.com/api/public/nearby";
    private final static String SESSION_BASE_URL =
        //    "https://skyscanner-skyscanner-flight-search-v1.p.mashape.com/apiservices/pricing/v1.0";
         //   "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/referral/v1.0/US/USD/en-US/JAX-sky/SIN-sky/2021-02-10/";
        "https://partners.api.skyscanner.net/apiservices/pricing/v1.0";
            //"https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/SFO-sky/JFK-sky/2021-03-01?inboundpartialdate=2021-03-10";
    private static final String SEARCH_BASE_URL =
            //"https://skyscanner-skyscanner-flight-search-v1.p.mashape.com/apiservices/pricing/uk2/v1.0/";
             "https://partners.api.skyscanner.net/apiservices/pricing/uk1/v1.0/";
         //   "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0";
    private static final String PARAM_INDEX_KEY = "pageIndex";
    private static final String PARAM_SIZE_KEY = "pageSize";


    /**
     * Builds the URL used to get the nearest airport from the current location.
     * using the following nearby API from https://aviation-edge.com/developers/
     *
     * @return The URL to use to query the google places api.
     */
    public static URL buildUrl(String[] location) {
        // TODO (1) Fill in this method to build the proper place URL
        Uri builtUri = Uri.parse(PLACE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LAT_KEY, location[0])
                .appendQueryParameter(PARAM_LNG_KEY, location[1])
                .appendQueryParameter(PARAM_DIST_KEY, DIST)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    /**
     * Builds the URL used to get the session for the flight search
     * using the https://rapidapi.com/skyscanner/api/Skyscanner%20Flight%20Search/functions/Create%20session
     *
     * @return The URL to use to query the session which will be use to get flight search results.
     */
    public static URL buildSessionUrl() {
        Uri builtUri = Uri.parse(SESSION_BASE_URL).buildUpon()
              /*  .appendQueryParameter(PARAM_COUNTRY_KEY, "US")
                .appendQueryParameter(PARAM_CURRENCY_KEY, "USD")
                .appendQueryParameter(PARAM_LOCALE_KEY, "en-US")
                .appendQueryParameter(PARAM_ORIGIN_KEY, "SFO-sky")
                .appendQueryParameter(PARAM_DEST_KEY, "LHR-sky")
                .appendQueryParameter(PARAM_OUT_KEY, "2018-11-01")
                .appendQueryParameter(PARAM_IN_KEY, "2018-11-10")
                .appendQueryParameter(PARAM_CABIN_KEY, "economy")
                .appendQueryParameter(PARAM_ADULT_KEY, "1")
                .appendQueryParameter(PARAM_CHILD_KEY, "0")
                .appendQueryParameter(PARAM_INFANTS_KEY, "0")
                .appendQueryParameter(PARAM_INC_KEY, "")
                .appendQueryParameter(PARAM_EXC_KEY, "") */
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    //trying out OkHttp https://square.github.io/okhttp/
    OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body().string();
    }

    public String runSession(String url, String[] params) throws IOException {
   //   public String runSession(String url) throws IOException {

        String location ="";

       RequestBody formBody = new FormBody.Builder().add(PARAM_COUNTRY_KEY, "US")
                .add(PARAM_CURRENCY_KEY, "USD")
                .add(PARAM_LOCALE_KEY, "en-US")
                .add(PARAM_ORIGIN_KEY, params[0])
                .add(PARAM_DEST_KEY, params[1])
                .add(PARAM_OUT_KEY, params[2])
                .add(PARAM_IN_KEY, params[3])
               /* .add(PARAM_SHORT_API_KEY, SHORT_API_KEY)
                .add(PARAM_S_API_KEY, PARAM_SHORT_API_KEY)
                .add(PARAM_ORIGIN_KEY, "SFO-sky")
                .add(PARAM_DEST_KEY, "LHR-sky")
                .add(PARAM_OUT_KEY, "2018-11-01")
                .add(PARAM_IN_KEY, "2018-11-10") */
                .add(PARAM_CABIN_KEY, "economy")
                .add(PARAM_ADULT_KEY, "1")
                .add(PARAM_CHILD_KEY, "0")
                .add(PARAM_INFANTS_KEY, "0")
                .add(PARAM_INC_KEY, "")
                .add(PARAM_EXC_KEY, "")
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader(PARAM_CONTENT_KEY, CONTENT)
                .addHeader(PARAM_X_KEY, X_KEY)
                .addHeader(PARAM_HOST_KEY, X_HOST)
                .build();

        Response response = client.newCall(request).execute();

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            if (responseHeaders.name(i).equals("Location")) {

                location = responseHeaders.value(i);
            }

            //for testing purposes
            Log.e("Headers", responseHeaders.name(i) + ": " + responseHeaders.value(i));
            //Log.e("url", formBody.toString());
        }



        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);


          //Toast.makeText(context, "Please select the airports from the dropdown list.", Toast.LENGTH_LONG).show();

        return location;

    }

    /**
     * Builds the URL used to get the results for the flight search
     * using the https://rapidapi.com/skyscanner/api/Skyscanner%20Flight%20Search/functions/Poll%20session%20results
     *
     * @return The URL to use to query the flight results.
     */
    public static URL buildSearchUrl(String params) {
        Uri builtUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                .appendPath(params)
                .appendQueryParameter(PARAM_SORTTYPE_KEY, "price")
                .appendQueryParameter(PARAM_SORTORDER_KEY, "asc")
                .appendQueryParameter(PARAM_INDEX_KEY, "0")
                .appendQueryParameter(PARAM_SIZE_KEY, "10")
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

    public String runSearch(String url) throws IOException {

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .addHeader(PARAM_X_KEY, X_KEY)
                .addHeader(PARAM_HOST_KEY, X_HOST)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Log.e("response" , String.valueOf(response));

        return response.body().string();

    }
}