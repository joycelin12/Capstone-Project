package com.example.android.dreamdestinations;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;


import static android.provider.BaseColumns._ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_DEST_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_FROM_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_ORIGIN_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_TIMESTAMP;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_TO_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.CONTENT_URI;

/**
 * Created by joycelin12 on 10/14/18.
 * referencing https://stackoverflow.com/questions/8696146/can-you-use-a-loadermanager-from-a-service
 */

public class AddPriceService extends IntentService {

    String[] params = new String[4];
    public static final String ACTION="com.example.android.dreamdestinations.ResponsePriceBroadcastReceiver";

    public AddPriceService() {
        super("AddPriceService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Cursor cursor = getContentResolver()
                .query(FavouritesContract.FavouritesEntry.CONTENT_URI,null,null,null,COLUMN_TIMESTAMP);


        //https://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                params[0] = (cursor.getString(cursor.getColumnIndex(COLUMN_ORIGIN_ID)));
                params[1] = (cursor.getString(cursor.getColumnIndex(COLUMN_DEST_ID)));
                params[2] = (cursor.getString(cursor.getColumnIndex(COLUMN_FROM_DATE)));
                params[3] = (cursor.getString(cursor.getColumnIndex(COLUMN_TO_DATE)));
                final int tripId = getTrip(params);
                Log.e("backgroundService","tripId" + tripId);
                new SessionBGTask(this, tripId).execute(params);

            } while (cursor.moveToNext());
        }

        // This describes what will happen when service is triggered
        Log.i("backgroundService","Service running");

        //create a broadcast to send the toast message
        Intent toastIntent= new Intent(ACTION);
        toastIntent.putExtra("toastMessage","I'm running after ever 15 minutes");
        sendBroadcast(toastIntent);


    }

    private int getTrip(String[] params) {

        Cursor cursor = getContentResolver()
                .query(CONTENT_URI,null,
                        COLUMN_ORIGIN_ID + "=? and " +
                                COLUMN_DEST_ID + "=? and " +
                                COLUMN_FROM_DATE + "=? and " +
                                COLUMN_TO_DATE+ "=? "
                        , new String[] {
                                params[0],
                                params[1],
                                params[2],
                                params[3]},null);


        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(_ID));

            return id;
        } else {
            return -1;
        }


    }


}
