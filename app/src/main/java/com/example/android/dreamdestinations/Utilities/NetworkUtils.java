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
    final static String PARAM_LAT_KEY = "lat";
    final static String PARAM_LNG_KEY ="lng";
    final static String PARAM_DIST_KEY = "distance";
    final static String DIST ="150";


    private static final String API_KEY = BuildConfig.API_KEY;

    final static String PLACE_BASE_URL =
            "http://aviation-edge.com/api/public/nearby";


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