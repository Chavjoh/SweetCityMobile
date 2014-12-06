package ch.hesso.master.sweetcity.activity.map;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import ch.hesso.master.sweetcity.Constants;
import ch.hesso.master.sweetcity.activity.IntentTag;
import ch.hesso.master.sweetcity.callback.ReportCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentReportList;
import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.activity.report.ReportActivity;
import ch.hesso.master.sweetcity.activity.report.ShowReportActivity;
import ch.hesso.master.sweetcity.model.Report;

public class MapActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private MapLocationListener listener;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        CurrentReportList.getInstance().load(this, new MapReportCallback(this));

        configureMap();
    }

    @Override
    protected void onResume() {
        super.onResume();

        showReports();
        configureMap();

        locationManager.requestLocationUpdates(provider, 15000, 0, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IntentTag.DO_REPORTS:
                if(resultCode == RESULT_OK){
                    CurrentReportList.getInstance().load(MapActivity.this, new MapReportCallback(MapActivity.this));
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        locationManager.removeUpdates(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            try {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivityForResult(intent, IntentTag.DO_REPORTS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void configureMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(getApplicationContext(), ShowReportActivity.class);
                    intent.putExtra("image", marker.getTitle());
                    startActivity(intent);
                            
                    return false;
                }
            });

            // Check if we were successful in obtaining the map.
            if (map != null) {
                // Enabling MyLocation Layer of Google Map
                map.setMyLocationEnabled(true);

                // Getting LocationManager object from System Service LOCATION_SERVICE
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                // Getting the name of the best provider
                provider = locationManager.getBestProvider(new Criteria(), true);

                // Getting Current Location
                Location location = locationManager.getLastKnownLocation(provider);

                listener = new MapLocationListener(map);

                if (location != null){
                    listener.onLocationChanged(location);
                }
            }
        }
    }
    
    public void showReports(){
        if (map == null)
            return;

        for (Report report:CurrentReportList.getInstance().getList()){
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(report.getLatitude(), report.getLatitude()));
            map.addMarker(marker).setTitle("Something");
        }
    }

    private class MapReportCallback extends ReportCallbackImpl {

        public MapReportCallback(Activity context) {
            super(context);
        }

        @Override
        public void loaded(List<Report> list) {
            super.loaded(list);
            showReports();
        }
    }
}
