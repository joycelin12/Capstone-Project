package com.example.android.dreamdestinations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry;
import com.example.android.dreamdestinations.FavouritesContract.FavouritesPriceEntry;


/**
 * Created by joycelin12 on 10/6/18.
 */

public class FavouritesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favourites.db";

    private static final int DATABASE_VERSION = 1;

    public FavouritesDbHelper (Context context) {
        super(context, DATABASE_NAME,  null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_TRIP_TABLE = "CREATE TABLE " +
                FavouritesEntry.TABLE_NAME + " (" +
                FavouritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouritesEntry.COLUMN_ORIGIN_ID + " TEXT NOT NULL, "  +
                FavouritesEntry.COLUMN_DEST_ID + " TEXT NOT NULL, "  +
                FavouritesEntry.COLUMN_FROM_DATE + " TEXT NOT NULL, "  +
                FavouritesEntry.COLUMN_TO_DATE + " TEXT , "  +
                FavouritesEntry.COLUMN_ORIGIN_NAME + " TEXT NOT NULL, "  +
                FavouritesEntry.COLUMN_DEST_NAME + " TEXT NOT NULL, "  +
                FavouritesEntry.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, "  +
                //");";
                //add unique to replace entry with the same column id
                " UNIQUE ("+ FavouritesEntry.COLUMN_ORIGIN_ID + "," +
                FavouritesEntry.COLUMN_DEST_ID + "," +
                FavouritesEntry.COLUMN_FROM_DATE + "," +
                FavouritesEntry.COLUMN_TO_DATE + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_PRICE_TABLE = "CREATE TABLE " +
                FavouritesPriceEntry.TABLE_NAME + " (" +
                FavouritesPriceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouritesPriceEntry.COLUMN_TRIP_ID + " TEXT NOT NULL, "  +
                FavouritesPriceEntry.COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "  +
                FavouritesPriceEntry.COLUMN_PRICE + " TEXT NOT NULL, "  +
                  //");";
                //add unique to replace entry with the same column id
                " UNIQUE ("+ FavouritesPriceEntry.COLUMN_TRIP_ID + "," +
                FavouritesPriceEntry.COLUMN_DATE + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_TRIP_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PRICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouritesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouritesPriceEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
