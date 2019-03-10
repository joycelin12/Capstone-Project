package com.example.android.dreamdestinations;

import android.database.Cursor;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import static android.provider.BaseColumns._ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_DEST_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_FROM_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_ORIGIN_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_TIMESTAMP;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_TO_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.CONTENT_URI;

/**
 * Created by joycelin12 on 11/25/18.
 * referencing from https://github.com/firebase/firebase-jobdispatcher-android
 */

public class AddPriceJobService extends JobService {

    String[] params = new String[4];

    @Override
    public boolean onStartJob(JobParameters job) {

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
                Log.e("jobService","tripId" + tripId);
                new SessionBGTask(this, tripId).execute(params);

            } while (cursor.moveToNext());
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
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
