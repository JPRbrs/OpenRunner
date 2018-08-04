package com.openrunner.jprapps.openrunner;

import java.util.Calendar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;


public class Main extends AppCompatActivity implements OnClickListener, LocationListener {

    private LocationManager locationManager;
    private static final long MIN_TIME = 1 * 30 * 1000; // GPS signal check every minute
    private static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss.SSS";
    MySQLiteHelper db = new MySQLiteHelper(this);
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        findViewById(R.id.settings_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location loc) {
        float lat = (float) (loc.getLatitude());
        float lng = (float) (loc.getLongitude());
        long time = System.currentTimeMillis();



        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String human_time = formatter.format(calendar.getTime());


        SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy"); // see
        sdf.format(calendar.getTime());
        ((TextView) findViewById(R.id.locat_label)).setText(
                "Fine location = lat:" + Float.toString(lat) +
                "longitude:" + Float.toString(lng) + " time: " + human_time
        );

        db.addInstant(new Instant(time, lat, lng, 1), getApplicationContext());

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
