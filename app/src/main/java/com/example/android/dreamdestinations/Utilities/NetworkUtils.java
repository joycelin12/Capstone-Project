package com.example.android.dreamdestinations.Utilities;

import android.net.Uri;

import com.example.android.dreamdestinations.BuildConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by joycelin12 on 8/30/18.
 */

public class NetworkUtils {

    final static String PARAM_API_KEY = "key";
    final static String PARAM_INP_KEY = "input";
    final static String PARAM_LOC_KEY = "location";
    final static String PARAM_RAD_KEY = "radius";
    final static String PARAM_TYPE_KEY ="types";
    final static String EST ="establishment";
    final static String RAD ="500";




    private static final String API_KEY = BuildConfig.API_KEY;

    final static String PLACE_BASE_URL =
            "https://maps.googleapis.com/maps/api/place/autocomplete/xml?input=Amoeba&types=establishment&location=37.76999,-122.44696&radius=500&key=YOUR_API_KEY";


    /**
     * Builds the URL used to get the nearest airport from the current location.
     *
     * @return The URL to use to query the google places api.
     */
    public static URL buildUrl(String input,String location) {
        // TODO (1) Fill in this method to build the proper place URL
        Uri builtUri = Uri.parse(PLACE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_INP_KEY, input)
                .appendQueryParameter(PARAM_TYPE_KEY, EST)
                .appendQueryParameter(PARAM_LOC_KEY, location)
                .appendQueryParameter(PARAM_RAD_KEY, RAD)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
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
}