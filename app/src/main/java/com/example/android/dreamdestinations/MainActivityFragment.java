package com.example.android.dreamdestinations;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by joycelin12 on 8/24/18.
 * referencing https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
 * referencing https://stackoverflow.com/questions/30919299/comparing-calendar-objects
 */

public class MainActivityFragment extends Fragment {

    private static final String YOUR_ADMOB_APP_ID = BuildConfig.YOUR_ADMOB_APP_ID;
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0 ;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    protected Location mLastLocation;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;

    DatePickerDialog picker;
    EditText departEditText;
    EditText arriveEditText;
    private Date departDate;
    private Date arrivalDate;
    private String[] params = new String[4];

    // Define a new interface OnBClickListener that triggers a callback in the host activity
    OnBClickListener mCallback;

    // OnBClickListener interface, calls a method in the host activity named onButtonSelected
    public interface OnBClickListener {
        void onButtonSelected(String[] params);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnBClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //this is the test ca-app-pub-3940256099942544/6300978111
        MobileAds.initialize(getActivity(), YOUR_ADMOB_APP_ID);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //store airport id and airport string
        final String[] airportIdArray = getResources().getStringArray(R.array.airports_id_array);
        final List<String> airportArray = Arrays.asList(getResources().getStringArray(R.array.airports_array));


        //add from airport
        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.airports_array, android.R.layout.simple_dropdown_item_1line);
        AutoCompleteTextView fromTextView = (AutoCompleteTextView)
                root.findViewById(R.id.fromAirport);
        //referencing https://stackoverflow.com/questions/8863964/android-autocomplete-textview-drop-down-width
        fromTextView.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        fromTextView.setAdapter(fromAdapter);
        fromTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

                int pos = airportArray.indexOf(parent.getItemAtPosition(position));
                params[0] = airportIdArray[pos];
                Toast.makeText(getActivity(), params[0] + " " + parent.getItemAtPosition(position) +" departure c "  + airportIdArray[pos] + " " +pos,Toast.LENGTH_LONG).show();
                mCallback.onButtonSelected(params);
                InputMethodManager inputManager =
                        (InputMethodManager) getActivity().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        //add to airport
        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.airports_array, android.R.layout.simple_dropdown_item_1line);
        AutoCompleteTextView toTextView = (AutoCompleteTextView)
                root.findViewById(R.id.toAirport);
        //referencing https://stackoverflow.com/questions/8863964/android-autocomplete-textview-drop-down-width
        toTextView.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        toTextView.setAdapter(toAdapter);
        toTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                int pos = airportArray.indexOf(parent.getItemAtPosition(position));
                params[1] = airportIdArray[pos];
                Toast.makeText(getActivity(), params[1] + " arrival c "   + airportIdArray[pos] + " " + pos, Toast.LENGTH_LONG).show();
                mCallback.onButtonSelected(params);
                InputMethodManager inputManager =
                (InputMethodManager) getActivity().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        departEditText= (EditText) root.findViewById(R.id.departDateEditText);
        departEditText.setInputType(InputType.TYPE_NULL);
        departEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                 String dateString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                 departEditText.setText(dateString);
                                 params[2] = dateString;
                                Toast.makeText(getActivity(), params[2] + " departure d" ,Toast.LENGTH_LONG).show();
                                mCallback.onButtonSelected(params);
                                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    departDate = sdf.parse(dateString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);
                picker.show();
            }
        });

        arriveEditText= (EditText) root.findViewById(R.id.arriveDateEditText);
        arriveEditText.setInputType(InputType.TYPE_NULL);
        arriveEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                // check if the date is later than departure date
                    picker = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                    String dateString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        arrivalDate = sdf.parse(dateString);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                     if(checkDate(departDate, arrivalDate)) {

                                        arriveEditText.setText(dateString);
                                        params[3] = dateString;
                                        Toast.makeText(getActivity(), params[3] + " arrival d",Toast.LENGTH_LONG).show();
                                        mCallback.onButtonSelected(params);

                                     } else {

                                        Toast.makeText(getActivity(), "Please choose a later arrival date than departure date.",Toast.LENGTH_LONG).show();

                                    }
                                }
                            }, year, month, day);
                    picker.show();


            }
        });

        return root;
    }

    protected boolean checkDate(Date departDate, Date arrivalDate){

        if (departDate != null && arrivalDate != null) {

            if(arrivalDate.before(departDate)){
             return false;
             } else {
            return true;
            }
        } else {

            Toast.makeText(getActivity(), "Please select the departure date first.",Toast.LENGTH_LONG).show();
            return false;
        }
    }


    //private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    /*    if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    //referencing from https://github.com/googlesamples/android-play-location/blob/master/BasicLocationSample/java/app/src/main/java/com/google/android/gms/location/sample/basiclocationsample/MainActivity.java
    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            //getLastLocation();
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            //getLastLocation();
        }

    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation(final View v) {
          mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
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

                            new PlaceTask(getActivity(), v).execute(location);


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
                                                Toast.makeText(getActivity(), "Please turn on location services.",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    });


                        }
                    }
                });
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
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
                getLastLocation(getView());
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
        View container = getActivity().findViewById(R.id.fragment_main);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(getActivity().findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }


    //referencing https://stackoverflow.com/questions/3328757/how-to-click-or-tap-on-a-textview-text
    public void showPopup(View v) {

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation(v);
        }
    }

    */
}
