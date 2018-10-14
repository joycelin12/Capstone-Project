package com.example.android.dreamdestinations;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.dreamdestinations.Model.Trip;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_DEST_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_DEST_NAME;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_FROM_DATE;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_ORIGIN_ID;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_ORIGIN_NAME;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_TIMESTAMP;
import static com.example.android.dreamdestinations.FavouritesContract.FavouritesEntry.COLUMN_TO_DATE;

//referencing from https://stackoverflow.com/questions/12496700/maximum-length-of-intent-putextra-method-force-close
//https://stackoverflow.com/questions/41305148/how-to-stop-the-items-duplication-in-recyclerview-android
//https://stackoverflow.com/questions/32377857/recyclerview-with-header


public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnBClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

  //  private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0 ;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    protected Location mLastLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //private boolean mLocationPermissionGranted;
    public String status;
    private String[] params;
    ArrayList<Trip> tripList = new ArrayList<>();
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_plane);

        //allow up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Create a DB helper (this will create the DB if run for the first time)
        FavouritesDbHelper dbHelper = new FavouritesDbHelper(this);

        // Keep a reference to the mDb until paused or killed.
        mDb = dbHelper.getReadableDatabase();
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(0, null, this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favourite) {

            if (tripList.size() == 0) {

                Toast.makeText(getApplicationContext(), "There is no saved trips.",Toast.LENGTH_LONG).show();

            } else {

                Intent intent = new Intent(this, TripActivity.class);
                intent.putParcelableArrayListExtra(TripActivity.TRIP_LIST, tripList);
                startActivity(intent);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation(final View v, final MainActivity activity,final String status) {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            String[] location = new String[2];

                            Log.e("TAG", String.format(Locale.ENGLISH, "%s: %f",
                                    "Latitude",
                                    mLastLocation.getLatitude()));
                            Log.e("TAG", String.format(Locale.ENGLISH, "%s: %f",
                                    "Longitude",
                                    mLastLocation.getLongitude()));


                            location[0] = String.format(Locale.ENGLISH, "%f",
                                    mLastLocation.getLatitude());
                            location[1] = String.format(Locale.ENGLISH, "%f",
                                    mLastLocation.getLongitude());


                            new PlaceTask(getApplicationContext(), v, activity, status).execute(location);


                        } else {

                            Log.e("TAG", "getLastLocation:exception", task.getException());
                            Log.e("TAG","No location detected. ");

                            // Your startActivity code which throws exception
                            showSnackbar(R.string.no_location, R.string.settings,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // Build intent that displays the App settings screen.
                                            Intent intent = new Intent();
                                            intent.setAction(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
                                            ); //ACTION_APPLICATION_DETAILS_SETTINGS
                                            Uri uri = Uri.fromParts("package",
                                                    BuildConfig.APPLICATION_ID, null);
                                            intent.setData(uri);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                            try {
                                                startActivity(intent);

                                            } catch (ActivityNotFoundException activityNotFound) {

                                                // Now, You can catch the exception here and do what you want
                                                Toast.makeText(getApplicationContext(), "Please turn on location services.",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    });


                        }
                    }
                });
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i("TAG", "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i("TAG", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i("TAG", "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i("TAG", "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                //TextView popup = (TextView) findViewById(R.id.nearbyFrom);
                getLastLocation(this.findViewById(android.R.id.content), this, status);
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    private void showSnackbar(final String text) {
        View container = this.findViewById(R.id.fragment_main);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(this.findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }


    //referencing https://stackoverflow.com/questions/3328757/how-to-click-or-tap-on-a-textview-text
    public void showPopupFrom(View v) {

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            status = "from";
            getLastLocation(v, this, status);
        }
    }

    //referencing https://stackoverflow.com/questions/3328757/how-to-click-or-tap-on-a-textview-text
    public void showPopupTo(View v) {

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            status = "to";
            getLastLocation(v, this, status);
        }
    }

    //referencing from https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out/27312494#27312494
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public void findFlights(View view) {

       if (params != null) {
            if (params[0] == null) {
                Toast.makeText(this, "Please select a departure airport.", Toast.LENGTH_LONG).show();
            } else if (params[1] == null) {
                Toast.makeText(this, "Please select an arrival airport.", Toast.LENGTH_LONG).show();
            } else if (params[2] == null) {
                Toast.makeText(this, "Please select a departure date.", Toast.LENGTH_LONG).show();
            } else if (isOnline()) {
                ResultActivity.params = params;
                new SessionTask(this).execute(params);
            } else {
                String message = "There is no internet connection";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        } else {
            String message = "Please fill in the departure airport, arrival airport and departure date.";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onButtonSelected(String[] param) {

        if (param != null) {

            params = param;
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

       // Cursor cursor = getContentResolver()
         //       .query(FavouritesContract.FavouritesEntry.CONTENT_URI,null,null,null,COLUMN_TIMESTAMP);
        return new CursorLoader(this, FavouritesContract.FavouritesEntry.CONTENT_URI,
                null, null, null, COLUMN_TIMESTAMP);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        tripList.clear();

        if (data != null) {
            //https://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
            // looping through all rows and adding to list
            int count =0;

            if (data.moveToFirst()) {
                do {
                    count++;
                    Trip trip = new Trip();
                    trip.setOrigin_id(data.getString(data.getColumnIndex(COLUMN_ORIGIN_ID)));
                    trip.setDest_id(data.getString(data.getColumnIndex(COLUMN_DEST_ID)));
                    trip.setFrom_date(data.getString(data.getColumnIndex(COLUMN_FROM_DATE)));
                    trip.setTo_date(data.getString(data.getColumnIndex(COLUMN_TO_DATE)));
                    trip.setOrigin_name(data.getString(data.getColumnIndex(COLUMN_ORIGIN_NAME)));
                    trip.setDest_name(data.getString(data.getColumnIndex(COLUMN_DEST_NAME)));
                    trip.setTimestamp(data.getString(data.getColumnIndex(COLUMN_TIMESTAMP)));
                    tripList.add(trip);
                } while (data.moveToNext());
            }

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        loader = null;
    }

}
