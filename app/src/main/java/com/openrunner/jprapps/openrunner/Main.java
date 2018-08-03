package com.openrunner.jprapps.openrunner;

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


public class Main extends AppCompatActivity implements OnClickListener, LocationListener {

    private LocationManager locationManager;
    private static final long MIN_TIME = 1 * 60 * 1000; // GPS signal check every minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);
        findViewById(R.id.settings_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location loc) {
        float lat = (float) (loc.getLatitude());
        float lng = (float) (loc.getLongitude());

        ((TextView) findViewById(R.id.locat_label)).setText(
                "Fine location = (lat:" + Float.toString(lat) +
                        " longitude:" + Float.toString(lng) + ")");
    }

    @Override

    protected void onResume() {

        super.onResume();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 0, this);

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
