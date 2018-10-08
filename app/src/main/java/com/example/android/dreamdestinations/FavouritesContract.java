package com.example.android.dreamdestinations;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by joycelin12 on 10/6/18.
 */

public class FavouritesContract {

    //referencing from https://medium.com/@sanjeevy133/an-idiots-guide-to-android-content-providers-part-1-970cba5d7b42
    public static final String CONTENT_AUTHORITY = "com.example.android.dreamdestinations";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    //Create an inner class that implements BaseColumns interface
    public static final class FavouritesEntry implements BaseColumns {

        public static final String TABLE_NAME = "trips";
        public static final String COLUMN_ORIGIN_ID ="origin_id";
        public static final String COLUMN_DEST_ID ="dest_id";
        public static final String COLUMN_FROM_DATE ="from_date";
        public static final String COLUMN_TO_DATE = "to_date";
        public static final String COLUMN_ORIGIN_NAME = "origin_name";
        public static final String COLUMN_DEST_NAME = "dest_name";
        public static final String COLUMN_TIMESTAMP = "timestamp";



        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

        public static Uri buildTripUriWithId(String id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }

    }

    //Create an inner class that implements BaseColumns interface
    public static final class FavouritesPriceEntry implements BaseColumns {

        public static final String TABLE_NAME = "trips_price";
        public static final String COLUMN_TRIP_ID ="tripId";
        public static final String COLUMN_DATE ="timestamp";
        public static final String COLUMN_PRICE ="price";


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

        public static Uri buildPriceUriWithId(String id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }

    }
}
