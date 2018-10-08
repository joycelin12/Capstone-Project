package com.example.android.dreamdestinations;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.TABLE_NAME;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry.COLUMN_TRIP_ID;

/**
 * Created by joycelin12 on 10/6/18.
 * referencing from https://medium.com/@sanjeevy133/an-idiots-guide-to-android-content-providers-part-2-7ccfbc88d75c
 * referencing from https://stackoverflow.com/questions/3814005/best-practices-for-exposing-multiple-tables-using-content-providers-in-android
 *
 */

public class FavouritesProvider extends ContentProvider {

    private FavouritesDbHelper mHelper;
    public static final int CODE_TRIP = 100;
    public static final int CODE_TRIP_WITH_ID = 101;
    public static final int CODE_PRICE = 200;
    public static final int CODE_PRICE_WITH_ID = 201;


    private static final UriMatcher mUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavouritesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, TABLE_NAME, CODE_TRIP);
        matcher.addURI(authority, TABLE_NAME + "/#", CODE_TRIP_WITH_ID);
        matcher.addURI(authority, FavouritesContract.FavouritesPriceEntry.TABLE_NAME, CODE_PRICE);
        matcher.addURI(authority, FavouritesContract.FavouritesPriceEntry.TABLE_NAME + "/#", CODE_PRICE_WITH_ID);

        return matcher;

    }

    @Override
    public boolean onCreate() {
        mHelper = new FavouritesDbHelper(getContext());
        if (mHelper != null)
            return true;
        else
            return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (mUriMatcher.match(uri)) {

            case CODE_TRIP_WITH_ID: {
                String _ID = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{_ID};

                cursor = mHelper.getReadableDatabase().query(
                        TABLE_NAME,
                        projection,
                        FavouritesContract.FavouritesEntry._ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }

            case CODE_TRIP: {
                cursor = mHelper.getReadableDatabase().query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            case CODE_PRICE_WITH_ID: {
                String _ID = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{_ID};

                cursor = mHelper.getReadableDatabase().query(
                        FavouritesContract.FavouritesPriceEntry.TABLE_NAME,
                        projection,
                        FavouritesContract.FavouritesPriceEntry._ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }

            case CODE_PRICE: {
                cursor = mHelper.getReadableDatabase().query(
                        FavouritesContract.FavouritesPriceEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mHelper.getWritableDatabase();

        switch (mUriMatcher.match(uri)) {
            case CODE_TRIP:

                long _id = db.insert(TABLE_NAME, null, values);

            /* if _id is equal to -1 insertion failed */
                if (_id != -1) {
                /*
                 * This will help to broadcast that database has been changed,
                 * and will inform entities to perform automatic update.
                 */
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return FavouritesContract.FavouritesEntry.buildTripUriWithId(Long.toString(_id));

            case CODE_PRICE:

                long _id2 = db.insert(FavouritesContract.FavouritesPriceEntry.TABLE_NAME, null, values);

            /* if _id is equal to -1 insertion failed */
                if (_id2 != -1) {
                /*
                 * This will help to broadcast that database has been changed,
                 * and will inform entities to perform automatic update.
                 */
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return FavouritesContract.FavouritesPriceEntry.buildPriceUriWithId(Long.toString(_id2));

            default:
                return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();

        int numRowsDeleted;

        String _ID = uri.getLastPathSegment();
        String[] selectionArguments = new String[]{_ID};

        switch(mUriMatcher.match(uri)) {

            case CODE_TRIP_WITH_ID:

                numRowsDeleted = db.delete(TABLE_NAME, _ID + "=?", selectionArguments);

                break;

            case CODE_PRICE_WITH_ID:

                numRowsDeleted = db.delete(FavouritesContract.FavouritesPriceEntry.TABLE_NAME, COLUMN_TRIP_ID + "=?", selectionArguments);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
