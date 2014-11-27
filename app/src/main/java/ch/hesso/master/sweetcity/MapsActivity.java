package ch.hesso.master.sweetcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    private List<Report> reports;
    public static final int REPORT_ACTIVITY = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        loadReportsFromPreferences();
        setReportsOnMap();
        
        setUpMapIfNeeded();
        Button btnDoReport = (Button)findViewById(R.id.btnDoReport);
        btnDoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivityForResult(intent, REPORT_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REPORT_ACTIVITY:
                if(resultCode == RESULT_OK){
                    Report reportResult = (Report)data.getParcelableExtra(Report.INTENT_TAG);
                    reports.add(reportResult);
                    onResume();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setReportsOnMap();
        setUpMapIfNeeded();
    }
    
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the SupportMapFragment.
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(getApplicationContext(), ShowReportActivity.class);
                    intent.putExtra("image",marker.getTitle());
                    startActivity(intent);
                            
                    return false;
                }
            });
            // Check if we were successful in obtaining the map.
            if (map != null) {
                setUpMap();
            }
        }
    }
    
    public void setReportsOnMap(){
//        Toast.makeText(getApplicationContext(), "cocool", Toast.LENGTH_SHORT).show();
        if(map == null)return;
        for(Report report:reports){
            map.addMarker(new MarkerOptions().position(report.getLatlng())).setTitle(report.getPictureFileName());
        }
    }
    
    public void loadReportsFromPreferences(){
        reports = new ArrayList<Report>();
        
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(this, Report.INTENT_TAG, MODE_PRIVATE);
        ListReport complexObject = complexPreferences.getObject("list", ListReport.class);
        if(complexObject==null)return;
        
        for(Report report: complexObject.getReports()){
            reports.add(report);
//            Toast.makeText(getApplicationContext(),"proute",Toast.LENGTH_SHORT).show();
//            Log.i("ComplexPreferences", "item " + report);
        }
    }
    
    public void saveReportsToPreferences(){
        // populate your List
        ListReport complexObject = new ListReport();
        complexObject.setReports(reports);

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(this, Report.INTENT_TAG, MODE_PRIVATE);;
        complexPreferences.putObject("list", complexObject);
        complexPreferences.commit();
    }
    
    @Override
    protected void onDestroy(){
        saveReportsToPreferences();
        super.onDestroy();
    }

    private void setUpMap() {
//        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        try {
            setReportsOnMap();
//            map.setMyLocationEnabled(true);
//            Location location = map.getMyLocation();
//            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10));
        }catch(RuntimeException e) {
            return;
        }
    }
}
