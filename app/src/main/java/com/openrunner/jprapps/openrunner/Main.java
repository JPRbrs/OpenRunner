package com.openrunner.jprapps.openrunner;

import java.util.ArrayList;
import java.util.Calendar;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


public class Main extends AppCompatActivity implements OnClickListener, LocationListener {

    private LocationManager locationManager;
    private static final long MIN_TIME = 10 * 1000; // GPS signal check every 10 seconds
    private static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss.SSS";
    MySQLiteHelper db = new MySQLiteHelper(this);
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 99;
    String[] values = new String[] { "Point1", "Point2" };
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = findViewById(R.id.list_view);
        // If you want to prepopulate the list use:
        //list.add("point1");
        //list.add("point2");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );
        listview.setAdapter(adapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            // Permission is granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);
        }
        findViewById(R.id.settings_button).setOnClickListener(this);
        findViewById(R.id.add_to_list).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),"Clicked!",Toast.LENGTH_SHORT).show();
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                processLocation(location, db);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    private void processLocation(Location location, MySQLiteHelper db){
        float lat = (float) (location.getLatitude());
        float lng = (float) (location.getLongitude());
        long time = System.currentTimeMillis();

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String human_time = formatter.format(calendar.getTime());

        SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy"); // see
        sdf.format(calendar.getTime());
        String text = "Fine location = lat:" + Float.toString(lat) +
                " longitude:" + Float.toString(lng) + " time: " + human_time;
        ((TextView) findViewById(R.id.locat_label)).setText(text);

        db.addInstant(new Instant(time, lat, lng, 1), getApplicationContext());
        list.add(text);
    }

    @Override
    public void onLocationChanged(Location loc) {
        processLocation(loc, db);
    }

    @Override

    protected void onResume() {

        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);
        }
    }

    @Override

    protected void onPause() {

        super.onPause();

        locationManager.removeUpdates(this);

    }

    @Override

    public void onProviderDisabled(String arg0) {

        // TODO Auto-generated method stub

    }

    @Override

    public void onProviderEnabled(String arg0) {

        // TODO Auto-generated method stub

    }

    @Override

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

        // TODO Auto-generated method stub

    }
}
