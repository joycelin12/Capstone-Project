package com.example.android.dreamdestinations;

import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private String[] params = new String[6];

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
                params[4] = String.valueOf(parent.getItemAtPosition(position));
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
                params[5] = String.valueOf(parent.getItemAtPosition(position));
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

                                //referencing https://stackoverflow.com/questions/41845793/how-to-get-datepicker-month-field-in-two-digit-format-in-android
                                monthOfYear+=1;
                                String mt,dy;   //local variable
                                if(monthOfYear<10)
                                    mt="0"+monthOfYear; //if month less than 10 then ad 0 before month
                                else mt=String.valueOf(monthOfYear);

                                if(dayOfMonth<10)
                                    dy = "0"+dayOfMonth;
                                else dy = String.valueOf(dayOfMonth);

                                 String dateString = year + "-" + mt + "-" + dy;
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

                                    //referencing from https://stackoverflow.com/questions/41845793/how-to-get-datepicker-month-field-in-two-digit-format-in-android
                                    monthOfYear+=1;
                                    String mt,dy;   //local variable
                                    if(monthOfYear<10)
                                        mt="0"+monthOfYear; //if month less than 10 then ad 0 before month
                                    else mt=String.valueOf(monthOfYear);

                                    if(dayOfMonth<10)
                                        dy = "0"+dayOfMonth;
                                    else dy = String.valueOf(dayOfMonth);

                                    String dateString = year + "-" + mt + "-" + dy;
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

}
