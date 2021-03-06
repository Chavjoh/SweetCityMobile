/*
* Copyright 2014-2015 University of Applied Sciences and Arts Western Switzerland
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package ch.hesso.master.sweetcity.activity.map;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hesso.master.sweetcity.R;
import ch.hesso.master.sweetcity.activity.IntentTag;
import ch.hesso.master.sweetcity.activity.ranking.RankingActivity;
import ch.hesso.master.sweetcity.activity.report.ReportActivity;
import ch.hesso.master.sweetcity.activity.report.ShowReportActivity;
import ch.hesso.master.sweetcity.activity.reward.RewardActivity;
import ch.hesso.master.sweetcity.callback.ReportCallbackImpl;
import ch.hesso.master.sweetcity.data.CurrentReportList;
import ch.hesso.master.sweetcity.model.Report;
import progress.menu.item.ProgressMenuItemHelper;

/**
 * @author Chavaillaz Johan
 * @author Burri Grégory
 * @author Frank Etienne
 * @version 1.0
 */
public class MapActivity extends FragmentActivity {

    /**
     * Might be null if Google Play services APK is not available.
     */
    private GoogleMap map;

    /**
     * Location service to use with map
     */
    private LocationManager locationManager;

    /**
     * Used to show the current position on the map
     */
    private MapLocationListener listener;

    /**
     * Best location provider
     */
    private String locationProvider;

    /**
     * Stores the report associated to each marker on the map
     */
    private HashMap<Marker, Report> markerReport;

    /**
     * Report list wrapper
     */
    private CurrentReportList reportList;

    private ProgressMenuItemHelper progressHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        markerReport = new HashMap<Marker, Report>();

        reportList = CurrentReportList.getInstance();

        configureMap();
    }

    @Override
    protected void onResume() {
        super.onResume();

        showMarkers();
        configureMap();
        showCurrentPosition();

        locationManager.requestLocationUpdates(locationProvider, 15000, 0, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case IntentTag.DO_REPORTS:
                if(resultCode == RESULT_OK){
                    reportList.load(MapActivity.this, new MapReportCallback(MapActivity.this));
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        locationManager.removeUpdates(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);

        progressHelper = new ProgressMenuItemHelper(menu.findItem(R.id.action_refresh_report));
        reportList.load(this, new MapReportCallback(this));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_add_report:
                try {
                    Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                    startActivityForResult(intent, IntentTag.DO_REPORTS);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;

            case R.id.action_show_reward:
                try {
                    Intent intent = new Intent(getApplicationContext(), RewardActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;

            case R.id.action_show_ranking:
                try {
                    Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;

            case R.id.action_refresh_report:
                reportList.load(MapActivity.this, new MapReportCallback(MapActivity.this));

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    private void configureMap() {
        // Do a null check to confirm that we have not already instantiated the map
        if (map != null)
            return;

        // Try to obtain the map from the SupportMapFragment.
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), ShowReportActivity.class);
                intent.putExtra("report", reportList.getPosition(markerReport.get(marker)));
                startActivity(intent);

                return false;
            }
        });
    }

    public void deleteMarkers() {
        for(Map.Entry<Marker, Report> entry : markerReport.entrySet()) {
            entry.getKey().remove();
        }

        markerReport.clear();
    }
    
    public void showMarkers(){
        if (map == null)
            return;

        for (Report report:reportList.getList()){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(report.getLatitude(), report.getLongitude()));
            Marker marker = map.addMarker(markerOptions);
            markerReport.put(marker, report);
        }
    }

    public void showCurrentPosition() {
        if (map == null)
            return;

        // Enabling MyLocation Layer of Google Map
        map.setMyLocationEnabled(true);

        Criteria lightCriteria = new Criteria();
        lightCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        lightCriteria.setAltitudeRequired(false);
        lightCriteria.setBearingRequired(false);
        lightCriteria.setCostAllowed(false);
        lightCriteria.setHorizontalAccuracy(Criteria.ACCURACY_MEDIUM);
        lightCriteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        lightCriteria.setSpeedAccuracy(Criteria.NO_REQUIREMENT);
        lightCriteria.setSpeedRequired(false);

        listener = new MapLocationListener(map);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestSingleUpdate(lightCriteria, listener, null);
        locationProvider = locationManager.getBestProvider(new Criteria(), true);
        Location location = locationManager.getLastKnownLocation(locationProvider);

        if (location != null) {
            listener.onLocationChanged(location);
            map.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    /**
     * Report callback with display of the report list on the map
     */
    private class MapReportCallback extends ReportCallbackImpl {

        public MapReportCallback(Activity context) {
            super(context);
        }

        @Override
        public void beforeLoading() {
            super.beforeLoading();
            progressHelper.startProgress();
        }

        @Override
        public void loaded(List<Report> list) {
            super.loaded(list);
            progressHelper.stopProgress();

            deleteMarkers();
            showMarkers();
        }
    }
}
